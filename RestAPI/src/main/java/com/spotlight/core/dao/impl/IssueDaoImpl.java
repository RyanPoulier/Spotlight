package com.spotlight.core.dao.impl;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.IssueDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Padmaka on 8/8/16.
 */
public class IssueDaoImpl implements IssueDao {


    @Override
    public Issue saveIssue(Issue issue) {

        Map<String, Object> issueDoc = new HashMap<>();
        issueDoc.put("title", issue.getTitle());
        issueDoc.put("description", issue.getDescription());
        issueDoc.put("address", issue.getAddress());
        issueDoc.put("longitude", issue.getLongitude());
        issueDoc.put("latitude", issue.getLatitude());
        issueDoc.put("votes", issue.getVotes());
        issueDoc.put("modifiedTime", issue.getModifiedTime());
        issueDoc.put("estimatedDates", issue.getEstimatedDates());
        issueDoc.put("actualResolutionDate", issue.getActualResolutionDate());
        issueDoc.put("closureDate", issue.getClosureDate());
        issueDoc.put("closureRating", issue.getClosureRating());


        return null;
    }
}
