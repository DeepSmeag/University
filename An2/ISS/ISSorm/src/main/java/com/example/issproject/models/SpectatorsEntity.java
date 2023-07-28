package com.example.issproject.models;

import jdk.jfr.Name;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "spectators", schema = "public", catalog = "ISS")
@NamedQuery(name = "SpectatorsEntity.findSpectator", query = "SELECT s FROM SpectatorsEntity s WHERE s.name = :name and s.age = :age")
//@NamedQuery(name = "SpectatorsEntity.addSpectator", query = "INSERT INTO SpectatorsEntity (name, age) VALUES (:name, :age)")
public class SpectatorsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "spectatorid")
    private int spectatorid;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "age")
    private Integer age;

    public int getSpectatorid() {
        return spectatorid;
    }

    public void setSpectatorid(int spectatorid) {
        this.spectatorid = spectatorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpectatorsEntity that = (SpectatorsEntity) o;
        return spectatorid == that.spectatorid && Objects.equals(name, that.name) && Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spectatorid, name, age);
    }
}
