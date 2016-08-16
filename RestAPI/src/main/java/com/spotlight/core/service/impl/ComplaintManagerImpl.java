package com.spotlight.core.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spotlight.core.beans.Complaint;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.ComplaintDao;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.dao.impl.ComplaintDaoImpl;
import com.spotlight.core.dao.impl.IssueDaoImpl;
import com.spotlight.core.service.ComplaintManager;
import org.apache.log4j.Logger;
import scala.util.parsing.combinator.testing.Str;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by padmaka on 8/11/16.
 */
public class ComplaintManagerImpl implements ComplaintManager {

    private static final Logger LOGGER = Logger.getLogger(ComplaintManagerImpl.class);
    private IssueDao issueDao;
    private ComplaintDao complaintDao = new ComplaintDaoImpl();

    public ComplaintManagerImpl(){
        issueDao = new IssueDaoImpl();
    }

    @Override
    public JsonObject saveNewComplaint(JsonObject fullComplaint) throws UnknownHostException {

        List<String> issueIds = new ArrayList<>();
        List<Issue> issues = new ArrayList<>();
        Gson gson = new Gson();

        LOGGER.info("saving new complaint ...");

        for (JsonElement issueObj : fullComplaint.get("issues").getAsJsonArray()) {
            Issue issue = gson.fromJson(issueObj.getAsJsonObject(), Issue.class);
            issueDao.saveIssue(issue);
            issues.add(issue);
            issueIds.add(issueDao.getIssue(issue).get_id().get$oid());
        }

        fullComplaint.addProperty("issueIds", gson.toJson(issueIds));
        return complaintDao.getComplaint(complaintDao.saveComplaint(fullComplaint));
    }

    @Override
    public List<Issue> getAllComplaints() throws UnknownHostException {

        LOGGER.info("Retrieving the issues...");
        return issueDao.getIssues();
    }
}
