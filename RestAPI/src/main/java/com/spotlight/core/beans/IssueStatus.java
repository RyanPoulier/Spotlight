package com.spotlight.core.beans;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Padmaka on 8/20/16.
 */
public enum IssueStatus {

    RECENTLY_SUBMITTED("RECENTLY_SUBMITTED"),
    IN_PROGRESS("IN_PROGRESS"),
    CLOSED("CLOSED");

    private String name;

    private IssueStatus(String name) {
        this.setName(name);
    }


    public String getName() {
        return name;
    }

    @JsonValue
    public void setName(String name) {
        this.name = name;
    }
}
