package com.spotlight.core.dao.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.beans.User;
import com.spotlight.core.beans.Vote;
import com.spotlight.core.dao.VoteDao;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public class VoteDaoImpl implements VoteDao {

    private static final Logger LOGGER = Logger.getLogger(VoteDaoImpl.class);


    @Override
    public Vote saveVote(Vote vote) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("vote");

        BasicDBObject voteDoc = new BasicDBObject();
        voteDoc.put("issueId", vote.getIssueId());
        voteDoc.put("userId", vote.getUserId());
        voteDoc.put("createdTime", System.currentTimeMillis() / 1000L);

        collection.insert(voteDoc);
        ObjectId id = voteDoc.getObjectId("_id");
        Vote savedVote = getVote(id.toString());
        LOGGER.info(savedVote.toString());
        LOGGER.info("Vote " + savedVote.get_id().get$oid() + " saved.");

        mongo.close();
        return savedVote;
    }

    @Override
    public Vote getVote(String id) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("vote");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = collection.findOne(query);

        LOGGER.info("Vote " + id + " retrieved");

        mongo.close();
        return gson.fromJson(parser.parse(dbObj.toString()).getAsJsonObject(), Vote.class);
    }

    @Override
    public List<Vote> getVotes(String issueId) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        List<Vote> votes = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("vote");
        BasicDBObject voteDoc = new BasicDBObject();
        voteDoc.put("issueId", issueId);

        DBCursor cursor = collection.find(voteDoc);
        while(cursor.hasNext()) {
            String voteString = cursor.next().toString();
            Vote vote = gson.fromJson(parser.parse(voteString).getAsJsonObject(), Vote.class);
            votes.add(vote);
        }

        mongo.close();
        return votes;
    }
}
