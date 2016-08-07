package com.spotlight.beans.model;

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
    private int closureRating;
    private String closureDescription;


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

    public String getClosureDescription() {
        return closureDescription;
    }

    public void setClosureDescription(String closureDescription) {
        this.closureDescription = closureDescription;
    }
}
