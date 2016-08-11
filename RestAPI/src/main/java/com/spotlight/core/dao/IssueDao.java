package com.spotlight.core.dao;

import com.spotlight.core.beans.Issue;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padmaka on 8/8/16.
 */
public interface IssueDao {

    Issue saveIssue(Issue issue) throws UnknownHostException;
    List<Issue> getIssues() throws UnknownHostException;
}
