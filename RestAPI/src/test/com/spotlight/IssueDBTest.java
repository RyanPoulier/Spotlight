package com.spotlight;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.impl.IssueDaoImpl;

import java.net.UnknownHostException;

/**
 * Created by padmaka on 8/10/16.
 */
public class IssueDBTest {

    public static void main(String[] args) {

        Issue issue = new Issue();
        issue.setId(1001);
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
}
