package com.spotlight.core.service.impl;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import com.spotlight.core.service.IssueManager;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by padmaka on 8/11/16.
 */
public class IssueManagerImpl implements IssueManager{

    private static final Logger LOGGER = Logger.getLogger(IssueManagerImpl.class);
    private IssueDao issueDao;

    public IssueManagerImpl(){
        issueDao = new IssueDaoImpl();
    }

    @Override
    public void saveNewIssue(Issue issue) throws UnknownHostException {

        LOGGER.info("saving new issue - " + issue.getId());
        issueDao.saveIssue(issue);

    }

    @Override
    public List<Issue> getAllIssues() throws UnknownHostException {

        LOGGER.info("Retrieving the issues...");
        return issueDao.getIssues();
    }
}
