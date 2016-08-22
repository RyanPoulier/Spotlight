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
    public Complaint saveComplaint(Complaint complaint) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("complaint");

        BasicDBObject complaintDoc = new BasicDBObject();
        complaintDoc.put("createdDate", complaint.getCreatedDate());
        complaintDoc.put("issueId", complaint.get_id());

        collection.insert(new BasicDBObject(complaintDoc));
        ObjectId complaintId = complaintDoc.getObjectId("_id");

        Complaint savedComplaintObj = getComplaint(complaintId.toString());

        LOGGER.info("Issue " + savedComplaintObj.get_id().get$oid() + " saved.");

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

        return complaint;
    }
}
