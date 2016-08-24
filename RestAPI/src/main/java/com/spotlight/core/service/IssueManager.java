package com.spotlight.core.service;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.exceptions.InvalidParameterException;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Padmaka on 8/16/16.
 */
public interface IssueManager {

    Issue saveIssue(Issue issue) throws InvalidParameterException, UnknownHostException;
    Issue getIssueById(String id) throws InvalidParameterException, UnknownHostException;
    List<Issue> getNearbyIssues(double latitude, double longitude) throws UnknownHostException;
    List<Issue> getAllIssues() throws UnknownHostException;
}
