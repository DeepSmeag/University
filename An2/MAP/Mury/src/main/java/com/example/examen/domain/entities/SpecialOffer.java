package com.example.examen.domain.entities;

import java.util.Date;

public class SpecialOffer extends Entity<Double>{
    private double hotelId;
    private Date startDate;
    private Date endDate;
    private int percent;

    public SpecialOffer(Double offerId, double hotelId, Date startDate, Date endDate, int percent) {
        setId(offerId);
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
    }

    public double getHotelId() {
        return hotelId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getPercent() {
        return percent;
    }
}
