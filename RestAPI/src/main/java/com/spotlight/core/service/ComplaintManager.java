package com.spotlight.core.service;

import com.spotlight.core.beans.Complaint;
import com.spotlight.core.exceptions.InvalidParameterException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/11/16.
 */
public interface ComplaintManager {

    Complaint saveNewComplaint(Complaint fullComplaint) throws UnknownHostException, InvalidParameterException;
    List<Complaint> getAllComplaints(String issueId) throws UnknownHostException;
}
