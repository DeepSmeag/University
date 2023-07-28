package com.example.issproject.model;




import javax.persistence.*;
import java.util.Objects;


@Entity
@NamedQuery(name = "AdminsEntity.findAdmin", query = "SELECT a FROM admins a WHERE a.username = :username AND a.password = :password")

@Table(name = "Admins", schema = "public", catalog = "ISS")
public class AdminsEntity {

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminsEntity that = (AdminsEntity) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
