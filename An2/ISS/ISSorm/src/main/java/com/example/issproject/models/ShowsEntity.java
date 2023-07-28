package com.example.issproject.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "shows", schema = "public", catalog = "ISS")
@NamedQuery(name = "ShowsEntity.getCurrentShow", query = "SELECT s FROM ShowsEntity s WHERE s.date = CURRENT_DATE ")
public class ShowsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "showid")
    private int showid;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "roomid")
    private Integer roomid;

    public int getShowid() {
        return showid;
    }

    public void setShowid(int showid) {
        this.showid = showid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowsEntity that = (ShowsEntity) o;
        return showid == that.showid && Objects.equals(date, that.date) && Objects.equals(roomid, that.roomid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showid, date, roomid);
    }
}
