package com.example.lab09forward.domain.Entities;

import java.time.LocalDate;
import java.util.UUID;

public class Message extends Entity<UUID> {
    protected UUID id1;
    protected UUID id2;
    protected String message;
    protected LocalDate date;

    public Message(UUID id1, UUID id2, String message, LocalDate date) {
        this.id1 = id1;
        this.id2 = id2;
        this.message = message;
        this.date = date;
    }

    public UUID getId1() {
        return id1;
    }

    public UUID getId2() {
        return id2;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}

