package com.spotlight.core.dao;

import com.google.gson.JsonObject;
import com.spotlight.core.beans.Complaint;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Padmaka on 8/14/16.
 */
public interface ComplaintDao {

    Complaint saveComplaint(Complaint complaint) throws UnknownHostException;
    Complaint getComplaint(String id) throws UnknownHostException;
    List<Complaint> getComplaints(String issueId) throws UnknownHostException;
}
