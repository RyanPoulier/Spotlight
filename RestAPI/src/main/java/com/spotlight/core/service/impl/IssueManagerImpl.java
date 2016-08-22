package com.spotlight.core.service.impl;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.IssueManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Padmaka on 8/16/16.
 */
public class IssueManagerImpl implements IssueManager {

    private static final Logger LOGGER = Logger.getLogger(IssueManagerImpl.class);
    private IssueDao issueDao;

    public IssueManagerImpl(){
        issueDao = new IssueDaoImpl();
    }

    @Override
    public Issue saveIssue(Issue issue) throws InvalidParameterException, UnknownHostException {

        if (issue == null) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        LOGGER.info("Saving Issue...");

        return issueDao.saveIssue(issue);
    }

    @Override
    public Issue getIssueById(String id) throws InvalidParameterException, UnknownHostException {

        if (StringUtils.isBlank(id)){
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        return issueDao.getIssue(id);
    }

    @Override
    public List<Issue> getAllIssues() throws UnknownHostException {

        return issueDao.getIssues();
    }
}
