package com.spotlight.core.beans;

import java.sql.Timestamp;

/**
 * Created by Padmaka on 8/1/16.
 */
public class Complaint {

    private int id;
    private Timestamp createdDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
