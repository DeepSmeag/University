package com.example.examen.domain.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity class models User of the Network
 */
public class User extends Entity<UUID>{
    /**
     * firstName class member
     * type - String
     */
    protected String firstName;

    /**
     * lastName class member
     * type - String
     */
    protected String lastName;


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
     * Constructor for User class
     * @param firstName - String
     * @param lastName - String
     */
    public User(String firstName, String lastName, Boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /**
     * Constructor for User class - private to prevent instantiation without parameters
     */
    private User() {}



    /**
     * Method to convert user instance to string
     * @return - String
     */
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Method to check if two users are equal
     * @param that - Object ; other user
     * @return - boolean
     */

    public boolean equals(User that) {
        if (this == that) return true;

        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName());
    }

    /**
     * Method to get hashcode of user instance
     * @return - int ; hashcode
     */
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }





}
