package com.spotlight.core.service.impl;

import com.spotlight.core.beans.Closure;
import com.spotlight.core.beans.Complaint;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.ComplaintDao;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.impl.ComplaintDaoImpl;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.IssueManager;
import com.spotlight.core.util.SpotlightUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Padmaka on 8/16/16.
 */
public class IssueManagerImpl implements IssueManager {

    private static final Logger LOGGER = Logger.getLogger(IssueManagerImpl.class);
    private IssueDao issueDao;
    private ComplaintDao complaintDao;

    public IssueManagerImpl(){
        issueDao = new IssueDaoImpl();
        complaintDao = new ComplaintDaoImpl();
    }

    @Override
    public Issue saveIssue(Issue issue) throws InvalidParameterException, UnknownHostException {

        if (issue == null) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (!SpotlightUtils.validateUserId(issue.getUserId())){
            String message = "User ID doesn't exist";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        String userName = SpotlightUtils.getUserNameById(issue.getUserId());
        issue.setUserName(userName);

        LOGGER.info("Saving Issue...");

        issue.setStatus("RECENTLY_SUBMITTED");
        Issue savedIssue = issueDao.saveIssue(issue);

        LOGGER.info("Saving a new complaint ...");

        Complaint complaint = new Complaint();
        complaint.setCreatedDate(System.currentTimeMillis() / 1000L);
        complaint.setIssueId(savedIssue.get_id().get$oid());
        complaint.setUserId(savedIssue.getUserId());
        complaintDao.saveComplaint(complaint);

        return savedIssue;
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
    public List<Issue> getIssuesByType(String type) throws UnknownHostException {

        return issueDao.getIssuesByType(type);
    }

    @Override
    public List<Issue> getNearbyIssues(double latitude, double longitude) throws UnknownHostException {

        return issueDao.getNearbyIssues(latitude, longitude);
    }

    @Override
    public List<Issue> getAllIssues() throws UnknownHostException {

        return issueDao.getIssues();
    }

    @Override
    public List<Issue> getUserComplainedIssues(String userId) throws InvalidParameterException, UnknownHostException {

        HashSet<String> issueIds = new HashSet<>();
        List<Issue> issues = new ArrayList<>();

        if (StringUtils.isBlank(userId)){
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (!SpotlightUtils.validateUserId(userId)) {
            String message = "User ID doesn't exist";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        List<Complaint> complaints = complaintDao.getComplaintsByUserId(userId);

        if (complaints.size() == 0) {
            return issues;
        }

        for (Complaint complaint : complaints) {
            issueIds.add(complaint.getIssueId());
        }

        for (String issueId : issueIds) {
            issues.add(issueDao.getIssue(issueId));
        }

        return issues;
    }

    @Override
    public Issue closeIssue(Closure closure) throws InvalidParameterException, UnknownHostException {

        if (closure == null) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (StringUtils.isBlank(closure.getUserId()) || StringUtils.isBlank(closure.getIssueId())) {
            String message = "Invalid parameters provided";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        if (!SpotlightUtils.validateUserId(closure.getUserId()) && !SpotlightUtils.validateIssueId(closure.getIssueId())) {
            String message = "User ID or Issue ID doesn't exist!";
            LOGGER.error(message);
            throw new InvalidParameterException(message);
        }

        Issue issue = issueDao.getIssue(closure.getIssueId());

        issue.setClosureDate(System.currentTimeMillis() / 1000L);
        issue.setClosureRating(closure.getClosureRating());
        issue.setStatus("CLOSED");

        Issue savedIssue = issueDao.saveIssue(issue);
        return savedIssue;
    }
}
