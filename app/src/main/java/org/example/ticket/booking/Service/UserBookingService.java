package org.example.ticket.booking.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import org.example.ticket.booking.entities.Ticket;
import org.example.ticket.booking.entities.Train;
import org.example.ticket.booking.entities.User;
import org.example.ticket.booking.util.UserServiceUtil;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;



public class UserBookingService {

    private boolean isLoggedIn=false;

    private User currentUser;

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<User> allUsers;

    private Train selectedTrain;
    private String selectedSource;
    private String selectedDestination;
    private LocalDateTime selectedDate;


    private static final String USER_FILE_PATH =
            "app/src/main/resources/LocalDb/users.json";




    public UserBookingService(User inputUser) throws IOException {
        this.currentUser = inputUser;
        this.allUsers=loadUsers();
    }



    public UserBookingService() throws IOException {

        this.allUsers=loadUsers();
    }

    public List<User> loadUsers() throws IOException {

        InputStream inputStream = UserBookingService.class.getClassLoader().getResourceAsStream("LocalDb/users.json");

        if(inputStream==null){
            throw new IOException("users.json not found in resources");
        }

        allUsers=objectMapper.readValue(inputStream
                , new TypeReference<List<User>>() {
                });
        return allUsers;

    }


    public Boolean loginUser() {
        Optional<User> foundUser = allUsers.stream()
                .filter(existingUser -> {
                    return existingUser.getName().equalsIgnoreCase(currentUser.getName())
                            && UserServiceUtil.checkPassword(
                            currentUser.getPassword(),
                            existingUser.getHashedPassword()
                    );
                })
                .findFirst();

        if(foundUser.isPresent()){
            this.currentUser = foundUser.get();
            this.isLoggedIn=true;
            System.out.println("Logged in");
            return Boolean.TRUE;
        }
        System.out.println("Not logged in");
        return Boolean.FALSE;
    }

    public Boolean signUp(User newUser) {
        try {
            allUsers.add(newUser);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex)
        {

            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        objectMapper.writeValue(usersFile, allUsers);
    }

    public void fetchBooking() {

        if(!isLoggedIn||currentUser==null){
            System.out.println("please login first");
            return;
        }

        Optional<User> userFetched = allUsers.stream().filter(existinguser-> {
            return existinguser.getName().equals(currentUser.getName())
                    && UserServiceUtil.checkPassword(currentUser.getPassword(), existinguser.getHashedPassword());
        }).findFirst();
        if (userFetched.isPresent()) {
            userFetched.get().printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId) {


        if(ticketId==null||ticketId.isEmpty()){
            System.out.println("Invalid ticket ID");
            return Boolean.FALSE;
        }

        if(currentUser == null){
            System.out.println("Please login before cancelling");
            return Boolean.FALSE;
        }

        if(currentUser.getTicketsBooked() == null || currentUser.getTicketsBooked().isEmpty()){
            System.out.println("No bookings found");
            return Boolean.FALSE;
        }


        try {
            String finalTicketId = ticketId;
            boolean removed =currentUser.getTicketsBooked().removeIf(ticket ->ticket.getTicketId().equals(finalTicketId));
            if(!removed){
                System.out.println("Invalid ticket ID");
                return Boolean.FALSE;
            }
            File usersFile = new File(USER_FILE_PATH);
            objectMapper.writeValue(usersFile, allUsers);
            System.out.println("Booking cancelled successfully");
            return Boolean.TRUE;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("invalid ticket ID or something went wrong");
            return Boolean.FALSE;
        }

    }

    public List<Train> getTrains(String Source, String Destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.SearchTrains(Source,Destination);
        }catch (IOException ex){
            return new ArrayList<>();
        }
    }

    public void searchAndSelectTrain(String source, String destination) {

        List<Train> trains = getTrains(source, destination);

        if (trains.isEmpty()) {
            System.out.println("No trains found");
            return;
        }

        System.out.println("Available trains:");
        for (int i = 0; i < trains.size(); i++) {
            System.out.println(i + " : " + trains.get(i).getTrainId());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select train index:");
        int choice = scanner.nextInt();

        this.selectedTrain = trains.get(choice);
        this.selectedSource = source;
        this.selectedDestination = destination;
        this.selectedDate = LocalDateTime.now(); // or ask user

        System.out.println("Train selected successfully");
    }





    public boolean bookseat() {


        if(!isLoggedIn){
            System.out.println("please login first");
            return false;
        }

        if(selectedTrain == null){
            System.out.println("please search and select a train first");
            return false;
        }

        if(!selectedTrain.isSeatsAvailable()){
            System.out.println("seats not available");
            return false;
        }



        List<int[]> availableSeats=selectedTrain.getAvailableSeat();
        System.out.println("available seats: ");
        for(int[] availableSeat:availableSeats){
            System.out.println("Row"+availableSeat[0]+" "+"seat"+availableSeat[1]);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter row number");
        int row = scanner.nextInt();
        System.out.println("enter seat number");
        int seat = scanner.nextInt();

        int finalRow = row;
        int finalSeat = seat;

        boolean validSeat =availableSeats.stream().anyMatch(s->s[0]==finalRow && s[1]==finalSeat);


        boolean booked = selectedTrain.bookSeat(row,seat);

        if(!booked){
            System.out.println("seat is already booked");
            return false;
        }


        Ticket ticket =new Ticket(
                UUID.randomUUID().toString(),
                selectedTrain.getTrainId(),
                currentUser.getUserId(),
                selectedSource,
                selectedDestination,
                selectedDate,
                row,
                seat);


        currentUser.getTicketsBooked().add(ticket);

        System.out.println("Seat booked successfully ! Ticket ID: "+ticket.getTicketId());
        return true;

    }



}
