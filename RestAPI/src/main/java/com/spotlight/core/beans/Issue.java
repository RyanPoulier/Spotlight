package com.spotlight.core.beans;

import com.google.gson.Gson;

/**
 * Created by Padmaka on 8/1/16.
 */
public class Issue {

    private int id;
    private String title;
    private String description;
    private String address;
    private double longitude;
    private double latitude;
    private int votes;
    private long modifiedTime;
    private int estimatedDates;
    private long actualResolutionDate;
    private long closureDate;
    private int closureRating;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getClosureRating() {
        return closureRating;
    }

    public void setClosureRating(int closureRating) {
        this.closureRating = closureRating;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getEstimatedDates() {
        return estimatedDates;
    }

    public void setEstimatedDates(int estimatedDates) {
        this.estimatedDates = estimatedDates;
    }

    public long getActualResolutionDate() {
        return actualResolutionDate;
    }

    public void setActualResolutionDate(long actualResolutionDate) {
        this.actualResolutionDate = actualResolutionDate;
    }

    public long getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(long closureDate) {
        this.closureDate = closureDate;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
