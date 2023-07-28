package com.example.issproject.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "seats", schema = "public", catalog = "ISS")
@NamedQuery(name = "SeatsEntity.findSeat", query = "SELECT s FROM SeatsEntity s where s.seatid = :seatid")
public class SeatsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "seatid")
    private int seatid;
    @Basic
    @Column(name = "seatname")
    private String seatname;

    public int getSeatid() {
        return seatid;
    }

    public void setSeatid(int seatid) {
        this.seatid = seatid;
    }

    public String getSeatname() {
        return seatname;
    }

    public void setSeatname(String seatname) {
        this.seatname = seatname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatsEntity that = (SeatsEntity) o;
        return seatid == that.seatid && Objects.equals(seatname, that.seatname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatid, seatname);
    }
}
