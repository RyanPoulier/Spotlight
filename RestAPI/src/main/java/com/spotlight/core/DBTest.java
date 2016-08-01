package com.spotlight.core;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by Padmaka on 8/1/16.
 */
public class DBTest {

    public static void main(String[] args) {

        MongoClient mongo = null;

        try {
            mongo = new MongoClient( "localhost" , 27017 );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DB db = mongo.getDB("test-db");
        DBCollection table = db.getCollection("user");

        BasicDBObject document = new BasicDBObject();
        document.put("name", "toxic");
        document.put("age", 25);
        document.put("createdDate", new Date());
        table.insert(document);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "toxic");

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
