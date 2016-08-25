package com.spotlight.core.dao.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.spotlight.core.beans.Closure;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.IssueDao;
import com.spotlight.core.util.SpotlightUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

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
        issue.setCreatedTime(System.currentTimeMillis() / 1000L);

        DBCollection collection = db.getCollection("issue");

        BasicDBObject issueDoc =  new BasicDBObject();

        if (issue.get_id() != null) {
            issueDoc.put("_id", new ObjectId(issue.get_id().get$oid()));
        }

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
        issueDoc.put("createdTime", issue.getCreatedTime());
        issueDoc.put("status", issue.getStatus());
        issueDoc.put("userId", issue.getUserId());
        issueDoc.put("userName", issue.getUserName());
        issueDoc.put("issueType", issue.getIssueType());

        collection.save(issueDoc);
        ObjectId id = issueDoc.getObjectId("_id");
        Issue savedIssue = getIssue(id.toString());
        LOGGER.info(issue.toString());
        LOGGER.info("Issue " + savedIssue.get_id().get$oid() + " saved.");

        mongo.close();
        return savedIssue;
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

        mongo.close();
        return issues;
    }

    @Override
    public List<Issue> getIssuesByType(String type) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        List<Issue> issues = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("issue");
        BasicDBObject issueDoc = new BasicDBObject();
        issueDoc.put("issueType", type);

        DBCursor cursor = collection.find(issueDoc);
        while(cursor.hasNext()) {
            String issueString = cursor.next().toString();
            Issue issue = gson.fromJson(parser.parse(issueString).getAsJsonObject(), Issue.class);
            issues.add(issue);
        }

        mongo.close();
        return issues;
    }

    @Override
    public List<Issue> getNearbyIssues(double latitude, double longitude) throws UnknownHostException {

        List<Issue> issues = getIssues();
        List<Issue> nearbyIssues = new ArrayList<>();

        if (issues == null) {
            return nearbyIssues;
        }

        for (Issue issue : issues) {
            if (SpotlightUtils.isNearbyIssue(issue, latitude, longitude)) {
                nearbyIssues.add(issue);
            }
        }

        return nearbyIssues;
    }

    @Override
    public Issue getIssue(String id) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("issue");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = collection.findOne(query);

        LOGGER.info("Issue " + id + " retrieved");
        LOGGER.info("dbObj " + dbObj);

        mongo.close();
        return gson.fromJson(parser.parse(dbObj.toString()).getAsJsonObject(), Issue.class);
    }

    @Override
    public Issue getIssue(Issue issue) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("issue");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

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
        issueDoc.put("createdTime", issue.getCreatedTime());
        issueDoc.put("status", issue.getStatus());

        DBObject dbObj = collection.findOne(new BasicDBObject(issueDoc));
        LOGGER.info(dbObj);

        mongo.close();
        return gson.fromJson(parser.parse(dbObj.toString()).getAsJsonObject(), Issue.class);
    }
}
