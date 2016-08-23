package com.spotlight.core.dao.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.spotlight.core.beans.Comment;
import com.spotlight.core.beans.Complaint;
import com.spotlight.core.dao.CommentDao;
import com.spotlight.core.util.SpotlightUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by padmaka on 8/23/16.
 */
public class CommentDaoImpl implements CommentDao {

    private static final Logger LOGGER = Logger.getLogger(CommentDaoImpl.class);


    @Override
    public Comment saveComment(Comment comment) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");

        DBCollection collection = db.getCollection("comment");

        BasicDBObject commentDoc = new BasicDBObject();
        commentDoc.put("createdDate", System.currentTimeMillis() / 1000L);
        commentDoc.put("issueId", comment.getIssueId());
        commentDoc.put("userId", comment.getUserId());
        commentDoc.put("description", comment.getDescription());
        commentDoc.put("userName", SpotlightUtils.getUserNameById(comment.getUserId()));

        collection.insert(commentDoc);
        ObjectId commentId = commentDoc.getObjectId("_id");

        Comment savedCommentObj = getComment(commentId.toString());

        LOGGER.info("Comment " + savedCommentObj.get_id().get$oid() + " saved.");

        mongo.close();
        return savedCommentObj;
    }

    @Override
    public Comment getComment(String id) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("comment");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = collection.findOne(query);
        Comment comment = gson.fromJson(parser.parse(dbObj.toString()).getAsJsonObject(), Comment.class);

        mongo.close();
        return comment;
    }

    @Override
    public List<Comment> getComments(String issueId) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        List<Comment> comments = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        DBCollection collection = db.getCollection("comment");
        BasicDBObject commentDoc = new BasicDBObject();
        commentDoc.put("issueId", issueId);

        DBCursor cursor = collection.find(commentDoc);
        while(cursor.hasNext()) {
            String issueString = cursor.next().toString();
            Comment comment = gson.fromJson(parser.parse(issueString).getAsJsonObject(), Comment.class);
            comments.add(comment);
        }

        mongo.close();
        return comments;
    }
}
