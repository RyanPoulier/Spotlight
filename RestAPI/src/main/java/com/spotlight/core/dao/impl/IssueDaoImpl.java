package com.spotlight.core.dao.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.IssueDao;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Padmaka on 8/8/16.
 */
public class IssueDaoImpl implements IssueDao {

    private static final Logger LOGGER = Logger.getLogger(IssueDaoImpl.class);

    @Override
    public Issue saveIssue(Issue issue) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");

        DBCollection collection = db.getCollection("issue");

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

        collection.insert(new BasicDBObject(issueDoc));
        mongo.close();

        LOGGER.info("Issue " + issue.getId() + " saved.");

        return null;
    }

    @Override
    public List<Issue> getIssues() throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        List<Issue> issues = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("issue");

        DBCursor cursor = collection.find();
        while(cursor.hasNext()) {
            String issueString = cursor.next().toString();
            Issue issue = gson.fromJson(parser.parse(issueString).getAsJsonObject(), Issue.class);
            issues.add(issue);
        }

        return issues;
    }
}
