package com.spotlight.core.beans;

/**
 * Created by Padmaka on 8/1/16.
 */
public class User {

    private ID _id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String city;
    private int reputationLevel;
    private boolean complaintAnonymity;
    private boolean commentAnonymity;
    private boolean voteRequest;
    private String password;
    private int voteCount;

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

    public boolean isVoteRequest() {
        return voteRequest;
    }

    public void setVoteRequest(boolean voteRequest) {
        this.voteRequest = voteRequest;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }
}
