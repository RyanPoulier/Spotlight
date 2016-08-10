package com.spotlight.core.dao;

import com.spotlight.core.beans.Issue;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/8/16.
 */
public interface IssueDao {

    Issue saveIssue(Issue issue) throws UnknownHostException;
}
