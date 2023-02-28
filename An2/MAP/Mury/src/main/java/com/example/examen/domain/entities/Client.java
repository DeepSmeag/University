package com.example.examen.domain.entities;

public class Client extends Entity<Long>{
    private String name;
    private int fidelityGrade;
    private int varsta;
    private HobbyEnum hobbies;

    public Client(Long id, String name, int fidelityGrade, int varsta, HobbyEnum hobbies) {
        setId(id);
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.varsta = varsta;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public int getFidelityGrade() {
        return fidelityGrade;
    }

    public int getVarsta() {
        return varsta;
    }

    public HobbyEnum getHobbies() {
        return hobbies;
    }
}
