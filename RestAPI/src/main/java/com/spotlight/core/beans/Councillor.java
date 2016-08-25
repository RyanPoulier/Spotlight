package com.spotlight.core.beans;


import com.google.gson.Gson;

/**
 * Created by Padmaka on 8/25/16.
 */
public class Councillor {

    private ID _id;
    private String email;
    private String contactNo;
    private String designation;


    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
