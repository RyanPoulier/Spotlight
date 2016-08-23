package com.spotlight.core.dao.impl;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.spotlight.core.beans.User;
import com.spotlight.core.dao.UserDao;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/21/16.
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Override
    public User getUser(User user) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("user");
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        BasicDBObject query = new BasicDBObject();
        query.put("email", user.getEmail());
        query.put("password", user.getPassword());
        DBObject dbObj = collection.findOne(query);

        LOGGER.info("User - " + dbObj);

        mongo.close();
        return gson.fromJson(parser.parse(dbObj.toString()).getAsJsonObject(), User.class);
    }

    @Override
    public User saveUser(User user) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("user");

        BasicDBObject issueDoc = new BasicDBObject();
        issueDoc.put("firstName", user.getFirstName());
        issueDoc.put("lastName", user.getLastName());
        issueDoc.put("email", user.getEmail());
        issueDoc.put("contactNo", user.getContactNo());
        issueDoc.put("city", user.getCity());
        issueDoc.put("password", user.getPassword());

        collection.insert(issueDoc);
        User savedUser= getUser(user);
        LOGGER.info(user.toString());
        LOGGER.info("User " + savedUser.get_id().get$oid() + " saved.");

        mongo.close();
        return savedUser;
    }

    @Override
    public boolean checkEmail(String email) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("user");

        BasicDBObject userDoc = new BasicDBObject();
        userDoc.put("email", email);

        DBObject dbObj = collection.findOne(userDoc);

        mongo.close();

        if (dbObj == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validateUser(String id) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test-db");
        DBCollection collection = db.getCollection("user");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBObject dbObj = collection.findOne(query);
        LOGGER.info("is null " + (dbObj == null));

        mongo.close();

        if (dbObj == null) {
            return false;
        } else {
            return true;
        }
    }


}
