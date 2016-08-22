package com.spotlight.core.beans;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Padmaka on 8/1/16.
 */
public class Complaint {

    private ID _id;
    private Timestamp createdDate;
    private String issueId;


    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }
}
