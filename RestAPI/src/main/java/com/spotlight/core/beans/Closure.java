package com.spotlight.core.beans;

import com.google.gson.Gson;

/**
 * Created by Padmaka on 8/25/16.
 */
public class Closure {

    private String userId;
    private String issueId;
    private int closureRating;
    private long closedTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public int getClosureRating() {
        return closureRating;
    }

    public void setClosureRating(int closureRating) {
        this.closureRating = closureRating;
    }

    public long getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(long closedTime) {
        this.closedTime = closedTime;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
