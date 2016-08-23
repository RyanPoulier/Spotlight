package com.spotlight.core.beans;

import com.google.gson.Gson;

/**
 * Created by padmaka on 8/23/16.
 */
public class Vote {

    private ID _id;
    private String issueId;
    private String userId;
    private long createdTime;


    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
