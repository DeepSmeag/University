package com.example.examen.domain.entities;

public class Client extends Entity<String>{
    private String name;

    public Client(String username, String name) {
        setId(username);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String toString() {
        return "{" + this.getId() + " , " + getName() + " }\n";
    }
}
