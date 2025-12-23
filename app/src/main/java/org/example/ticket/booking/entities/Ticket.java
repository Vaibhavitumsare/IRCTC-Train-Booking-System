package org.example.ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {

    private String ticketId;

    private String userId;

    private String source;

    private String destination;

    private Date dateOfTraveling;

    private Train train;



    public Ticket(String ticketId, String traindId,String userId, String source, String destination, LocalDateTime dateOfTravelling, int row, int seat){
        this.ticketId = ticketId;
        this.userId = traindId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTraveling=dateOfTraveling;
        row=row;
        seat=seat;
    }

    public String getTicketInfo(){
        return String.format("Ticket ID: %s  belongs to user %s from %s to %s on %s", ticketId,userId,source,destination,dateOfTraveling);
    }

    public Ticket(){

    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateOfTraveling() {
        return dateOfTraveling;
    }

    public void setDateOfTraveling(Date dateOfTraveling) {
        this.dateOfTraveling = dateOfTraveling;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
