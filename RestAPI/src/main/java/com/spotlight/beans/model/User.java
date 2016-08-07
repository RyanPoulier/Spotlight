package com.spotlight.beans.model;

/**
 * Created by Padmaka on 8/1/16.
 */
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String city;
    private int reputationLevel;
    private boolean complaintAnonymity;
    private boolean commentAnonymity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getReputationLevel() {
        return reputationLevel;
    }

    public void setReputationLevel(int reputationLevel) {
        this.reputationLevel = reputationLevel;
    }

    public boolean isComplaintAnonymity() {
        return complaintAnonymity;
    }

    public void setComplaintAnonymity(boolean complaintAnonymity) {
        this.complaintAnonymity = complaintAnonymity;
    }

    public boolean isCommentAnonymity() {
        return commentAnonymity;
    }

    public void setCommentAnonymity(boolean commentAnonymity) {
        this.commentAnonymity = commentAnonymity;
    }
}
