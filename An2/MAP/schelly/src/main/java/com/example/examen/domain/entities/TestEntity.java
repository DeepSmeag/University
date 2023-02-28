package com.example.examen.domain.entities;

import java.util.Date;

public class TestEntity extends Entity<Integer> {
//    private int id;
    private String name;
    private Date date;

    public TestEntity(Integer id, String name, Date date) {
//        this.id = id;
        setId(id);
        this.name = name;
        this.date = date;
    }

//    public int getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        return "id: " + getId() + " name: " + name + " date: " + date;
    }
}
