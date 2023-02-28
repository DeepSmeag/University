package com.example.examen.domain.entities;

import java.time.LocalDateTime;

public class Flight extends Entity<Long>{
    private String from;
    private String to;
    private LocalDateTime departure;
    private LocalDateTime landing;
    private int seats;
    private int remainingSeats;

    public Flight(Long id, String from, String to, LocalDateTime dep, LocalDateTime land, int seats) {
        setId(id);
        this.from = from;
        this.to = to;
        this.departure = dep;
        this.landing = land;
        this.seats = seats;
        this.remainingSeats = this.seats;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getLanding() {
        return landing;
    }

    public int getSeats() {
        return seats;
    }
    public int getRemainingSeats() {
        return remainingSeats;
    }
    public void setRemainingSeats(int no) {
        remainingSeats = no;
    }
    public String toString() {
        return "{ " + this.getId() + " , " + this.getFrom().toString() + " , " + this.getTo().toString() + " {\n";
    }
}
