package com.example.issproject.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reservations", schema = "public", catalog = "ISS")
@NamedQuery(name = "ReservationsEntity.getReservations", query = "SELECT r FROM ReservationsEntity r")
//@NamedQuery(name = "ReservationsEntity.addReservation", query = "INSERT INTO ReservationsEntity (spectatorid, showid, seatsid) VALUES (:spectatorid, :showid, :seatsid)")
public class ReservationsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reservationid")
    private int reservationid;
    @Basic
    @Column(name = "spectatorid")
    private Integer spectatorid;
    @Basic
    @Column(name = "showid")
    private Integer showid;
    @Basic
    @Column(name = "seatsid")
    private String seatsid;

    public int getReservationid() {
        return reservationid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public Integer getSpectatorid() {
        return spectatorid;
    }

    public void setSpectatorid(Integer spectatorid) {
        this.spectatorid = spectatorid;
    }

    public Integer getShowid() {
        return showid;
    }

    public void setShowid(Integer showid) {
        this.showid = showid;
    }

    public String getSeatsid() {
        return seatsid;
    }

    public void setSeatsid(String seatsid) {
        this.seatsid = seatsid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsEntity that = (ReservationsEntity) o;
        return reservationid == that.reservationid && Objects.equals(spectatorid, that.spectatorid) && Objects.equals(showid, that.showid) && Objects.equals(seatsid, that.seatsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationid, spectatorid, showid, seatsid);
    }
}
