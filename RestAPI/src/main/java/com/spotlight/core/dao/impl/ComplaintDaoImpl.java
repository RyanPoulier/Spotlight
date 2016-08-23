package com.spotlight.core.dao.impl;

import com.google.gson.*;
import com.mongodb.*;
import com.spotlight.core.beans.Complaint;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.dao.ComplaintDao;
import com.spotlight.core.dao.IssueDao;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Padmaka on 8/14/16.
 */
public class ComplaintDaoImpl implements ComplaintDao {

    private static final Logger LOGGER = Logger.getLogger(ComplaintDaoImpl.class);


    @Override
    public Complaint saveComplaint(Complaint complaint) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");

        DBCollection collection = db.getCollection("complaint");

        BasicDBObject complaintDoc = new BasicDBObject();
        complaintDoc.put("createdDate", System.currentTimeMillis() / 1000L);
        complaintDoc.put("issueId", complaint.getIssueId());
        complaintDoc.put("userId", complaint.getUserId());

        collection.insert(complaintDoc);
        ObjectId complaintId = complaintDoc.getObjectId("_id");

        Complaint savedComplaintObj = getComplaint(complaintId.toString());

        LOGGER.info("Complaint " + savedComplaintObj.get_id().get$oid() + " saved.");

        mongo.close();
        return savedComplaintObj;
    }

    @Override
    public Complaint getComplaint(String id) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("complaint");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = collection.findOne(query);
        Complaint complaint = gson.fromJson(parser.parse(dbObj.toString()).getAsJsonObject(), Complaint.class);

        mongo.close();
        return complaint;
    }

    @Override
    public List<Complaint> getComplaints(String issueId) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        List<Complaint> complaints = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("complaint");
        BasicDBObject complaintDoc = new BasicDBObject();
        complaintDoc.put("issueId", issueId);

        DBCursor cursor = collection.find(complaintDoc);
        while(cursor.hasNext()) {
            String issueString = cursor.next().toString();
            Complaint complaint = gson.fromJson(parser.parse(issueString).getAsJsonObject(), Complaint.class);
            complaints.add(complaint);
        }

        mongo.close();
        return complaints;
    }
}
