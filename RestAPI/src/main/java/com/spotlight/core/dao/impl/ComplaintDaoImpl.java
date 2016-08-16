package com.spotlight.core.dao.impl;

import com.google.gson.*;
import com.mongodb.*;
import com.spotlight.core.beans.Complaint;
import com.spotlight.core.dao.ComplaintDao;
import com.spotlight.core.dao.IssueDao;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Padmaka on 8/14/16.
 */
public class ComplaintDaoImpl implements ComplaintDao {

    private static final Logger LOGGER = Logger.getLogger(ComplaintDaoImpl.class);


    @Override
    public Complaint saveComplaint(JsonObject complaint) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("complaint");

        Map<String, Object> complaintDoc = new HashMap<>();
        complaintDoc.put("createdDate", complaint.get("createdDate").getAsLong());
        complaintDoc.put("issueIds", complaint.get("issueIds").getAsJsonArray());

        collection.insert(new BasicDBObject(complaintDoc));
        mongo.close();

        DBObject savedComplaint = collection.findOne(new BasicDBObject(complaintDoc));

        Complaint savedComplaintObj = gson.fromJson(parser.parse(savedComplaint.toString()).getAsJsonObject(), Complaint.class);

        LOGGER.info("Issue " + savedComplaintObj.get_id().get$oid() + " saved.");

        return savedComplaintObj;
    }

    @Override
    public JsonObject getComplaint(String id) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("complaint");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject complaint = new JsonObject();
        IssueDao issueDao = new IssueDaoImpl();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = collection.findOne(query);
        complaint = parser.parse(dbObj.toString()).getAsJsonObject();
        complaint.add("issues", new JsonArray());

        //Adding issues
        for (JsonElement i : complaint.get("issueIds").getAsJsonArray()){
            String issueId = i.getAsString();
            complaint.get("issues").getAsJsonArray().add(gson.toJson(issueDao.getIssue(issueId)));
        }
        return complaint;
    }

    @Override
    public JsonObject getComplaint(Complaint complaint) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("complaint");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject complaintObj = new JsonObject();
        IssueDao issueDao = new IssueDaoImpl();

        DBObject dbObj = collection.findOne(complaint);
        complaintObj = parser.parse(dbObj.toString()).getAsJsonObject();
        complaintObj.add("issues", new JsonArray());

        //Adding issues
        for (JsonElement i : complaintObj.get("issueIds").getAsJsonArray()){
            String issueId = i.getAsString();
            complaintObj.get("issues").getAsJsonArray().add(gson.toJson(issueDao.getIssue(issueId)));
        }
        return complaintObj;
    }
}
