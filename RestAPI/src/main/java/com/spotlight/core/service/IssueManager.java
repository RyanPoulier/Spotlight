package com.spotlight.core.service;

import com.spotlight.core.beans.Issue;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by padmaka on 8/11/16.
 */
public interface IssueManager {

    void saveNewIssue(Issue issue) throws UnknownHostException;
    List<Issue> getAllIssues() throws UnknownHostException;
}
