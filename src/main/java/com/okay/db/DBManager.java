package com.okay.db;

import com.mongodb.*;

import java.util.Arrays;

public class DBManager {

    public static final String DB_HOST_NAME = "linus.mongohq.com";
    public static final int DB_PORT = 10003;
    public static final String DB_NAME = "Okay";
    public static final String DB_USER_NAME = "okay";
    public static final String DB_PASSWORD = "okay1";

    private static DBManager instance = null;
    private static DB db;

    protected DBManager() {
        try {
            MongoCredential credential = MongoCredential.createMongoCRCredential(DB_USER_NAME, DB_NAME, DB_PASSWORD.toCharArray());
            MongoClient mongoClient = new MongoClient(new ServerAddress(DB_HOST_NAME, DB_PORT), Arrays.asList(credential));
            db = mongoClient.getDB(DB_NAME);

        } catch (Exception exception) {
            // not implemented
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

    public DBCollection getCollection(String collectionName) {
        return db.getCollection(collectionName);
    }
}
