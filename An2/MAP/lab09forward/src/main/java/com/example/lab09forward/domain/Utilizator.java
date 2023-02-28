package com.example.lab09forward.domain;

import com.example.lab09forward.domain.Entities.Entity;

import java.util.List;
import java.util.Objects;

/**
 * Utilizator class models a User of the Network
 */
public class Utilizator extends Entity<Long> {
    /**
     * firstName class member
     * type - String
     */
    private String firstName;
    /**
     * lastName class member
     * type - String
     */
    private String lastName;
    /**
     * friendList class member
     */
    private List<Utilizator> friends;
    /**
     * Constructor for Utilizator
     * @param firstName - String
     * @param lastName - String
     */
    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**
     * Getter for firstName class member
     * @return firstName - String
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Setter for firstName class member
     * @param firstName - String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Getter for lastName class member
     * @return lastName - String
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Setter for lastName class member
     * @param lastName - String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Getter for friendList class member
     * @return friendList - List of Utilizator
     */
    public List<Utilizator> getFriends() {
        return friends;
    }
    /**
     * toString method for Utilizator
     */
    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + friends +
                '}';
    }
    /**
     * equals method for Utilizator
     * @param o - Object
     * @return - boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }
    /**
     * hashCode method for Utilizator
     * @return - int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}