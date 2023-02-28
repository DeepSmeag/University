package com.example.examen.domain.entities;

import java.time.LocalDateTime;

public class Ticket extends Entity<Integer>{
    private String username;
    private Long flightid;
    private LocalDateTime purchaseTime;

    public Ticket(Integer id, String username, Long flightid, LocalDateTime purchaseTime){
        setId(id);
        this.username = username;
        this.flightid = flightid;
        this.purchaseTime = purchaseTime;
    }

    public String getUsername() {
        return username;
    }

    public Long getFlightid() {
        return flightid;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }
    public String toString() {
        return "{ ticket for " + getUsername() + " }\n";
    }
}
