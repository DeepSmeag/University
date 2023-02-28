package com.example.examen.domain.entities;

public class Location extends Entity<Double>{
    private String locationName;

    public Location(Double id, String locationName){
        setId(id);
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }
    public String toString() {
        return "id: " + getId() + " name: " + locationName;
    }
}
