package com.abdullahandroid.coviddigital;

public class User {

    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String EmailAddress;
    private String Password;
    private String PhoneNumber;

    public User(String firstName, String middleName, String lastName, String emailAddress, String password, String phoneNumber) {
        FirstName = firstName;
        MiddleName = middleName;
        LastName = lastName;
        EmailAddress = emailAddress;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public User() {
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
