package org.example.ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {

    private String trainId;

    private String trainNo;

    private List<List<Integer>> seats;

    private Map<String, String> stationTimes;

    private List<String> stations;


    public boolean isSeatsAvailable(){
        for(List<Integer> seat : seats){
            if(seat.contains(0))return true;
        }
        return false;
    }

    public List<int[]> getAvailableSeat(){
        List<int[]> available = new ArrayList<>();

        for(int i=0;i<seats.size();i++){
            for(int j=0;j<seats.get(i).size();j++){
                if(seats.get(i).get(j).equals(0)){
                    available.add(new int[]{i,j});
                }
            }
        }
        return available;
    }

    public boolean bookSeat(int row, int seat){
        if(seats.get(row).get(seat)==1){
            System.out.println("Seat "+seat+" is already booked");
            return false;
        }
        seats.get(row).set(seat,1);
        return true;
    }



    public String getTrainId()
    {
        return trainId;
    }

    public void setTrainId(String trainId)
    {
        this.trainId = trainId;
    }

    public String getTrainNo()
    {
        return trainNo;
    }

    public void setTrainNo(String trainNo)
    {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats()
    {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats)
    {
        this.seats = seats;
    }

    public Map<String, String > getStationTimes()
    {
        return stationTimes;
    }

    public void setStationTimes(Map<String, String> stationTimes)
    {
        this.stationTimes = stationTimes;
    }

    public List<String> getStations()
    {
        return stations;
    }

    public void setStations(List<String> stations)
    {
        this.stations = stations;
    }

    public String getTrainInfo()
    {
        return String.format("Train ID: %s Train No: %s", trainId, trainNo);
    }


}
