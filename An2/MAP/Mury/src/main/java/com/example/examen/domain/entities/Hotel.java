package com.example.examen.domain.entities;

public class Hotel extends Entity<Double>{

    private double locationId;
    private String hotelName;
    private int noRooms;
    private double pricePerNight;
    private TypeEnum type;

    public Hotel(Double id, double locationId, String hotelName, int noRooms, double pricePerNight, TypeEnum type){
        setId(id);
        this.locationId = locationId;
        this.hotelName = hotelName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public double getLocationId() {
        return locationId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public TypeEnum getType() {
        return type;
    }

    public String toString() {
        return "id: " + getId() + " locationId: " + locationId + " hotelName: " + hotelName + " noRooms: " + noRooms + " pricePerNight: " + pricePerNight + " type: " + type;
    }
}
