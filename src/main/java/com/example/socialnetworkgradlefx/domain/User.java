package com.example.socialnetworkgradlefx.domain;

import java.time.LocalDate;

/**
 * User class
 */
public class User extends Entity {

    private String firstName;
    private String lastName;
    private String email;

    /**
     * User constructor
     * @param id String
     * @param firstName String
     * @param lastName String
     * @param email String
     */
    public User(int id, String firstName, String lastName, String email) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * First name of user getter
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * * First name of user setter
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Last name of user getter
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * * Last name of user setter
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Email of user getter
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * * Email of user setter
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Forms a string out of a User object
     * @return String
     */
    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' + ", email='" + email + '}';
    }

}