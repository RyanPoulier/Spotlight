package com.spotlight.core.service;

import com.google.gson.JsonObject;
import com.spotlight.core.beans.Complaint;
import com.spotlight.core.beans.Issue;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/11/16.
 */
public interface ComplaintManager {

    JsonObject saveNewComplaint(JsonObject fullComplaint) throws UnknownHostException;
    List<Issue> getAllComplaints() throws UnknownHostException;
}
