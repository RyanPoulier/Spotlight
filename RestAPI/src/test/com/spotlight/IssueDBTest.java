package com.spotlight;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by padmaka on 8/10/16.
 */
public class IssueDBTest {

    private static final Logger LOGGER = Logger.getLogger(IssueDBTest.class);

    public static void main(String[] args) {

        List<Issue> issues = new ArrayList<>();

        testSaveIssue();
        issues = testGetIssues();

        LOGGER.info(testGetIssue(issues.get(0).get_id().get$oid()));
    }


    public static void testSaveIssue() {

        Issue issue = new Issue();
        issue.setTitle("sddfd");
        issue.setDescription("sfdsfsdf");
        issue.setAddress("oihsofihd,sdfkugaudgfk");
        issue.setLongitude(3425468);
        issue.setLatitude(34453456);
        issue.setVotes(23);
        issue.setModifiedTime(5476547);
        issue.setEstimatedDates(346467);
        issue.setEstimatedDates(4);
        issue.setActualResolutionDate(3456468);
        issue.setClosureDate(345647);
        issue.setClosureRating(8);

        IssueDaoImpl issueDao = new IssueDaoImpl();
        try {
            issueDao.saveIssue(issue);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static List<Issue> testGetIssues() {

        IssueDao issueDao = new IssueDaoImpl();
        List<Issue> issues = new ArrayList<>();

        try {
            issues = issueDao.getIssues();
            LOGGER.info(issues);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return issues;
    }

    public static Issue testGetIssue(String id){

        IssueDao issueDao = new IssueDaoImpl();
        Issue issue = null;
        try {
            issue = issueDao.getIssue(id);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return issue;
    }
}
