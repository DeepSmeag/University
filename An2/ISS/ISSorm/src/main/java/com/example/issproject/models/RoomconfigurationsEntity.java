package com.example.issproject.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roomconfigurations", schema = "public", catalog = "ISS")
@NamedQuery(name = "RoomconfigurationsEntity.retrieveConfig", query = "SELECT r FROM RoomconfigurationsEntity r WHERE r.roomid = :roomid")
public class RoomconfigurationsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roomid")
    private int roomid;
    @Basic
    @Column(name = "seatsid")
    private String seatsid;

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
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
        RoomconfigurationsEntity that = (RoomconfigurationsEntity) o;
        return roomid == that.roomid && Objects.equals(seatsid, that.seatsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomid, seatsid);
    }
}
