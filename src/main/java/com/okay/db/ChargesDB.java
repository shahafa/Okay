package com.okay.db;

import com.mongodb.*;
import com.okay.Charge;

import java.util.Arrays;
import java.util.List;

public class ChargesDB {

    public static final String DB_HOST_NAME = "linus.mongohq.com";
    public static final int DB_PORT = 10003;
    public static final String DB_NAME = "Okay";
    public static final String DB_USER_NAME = "okay";
    public static final String DB_PASSWORD = "okay1";
    public static final String CHARGES_COLLECTION_NAME = "charges";

    private static ChargesDB instance = null;

    private static DB db;
    private static DBCollection chargesCollection;

    protected ChargesDB() {
        try {

            MongoCredential credential = MongoCredential.createMongoCRCredential(DB_USER_NAME, DB_NAME, DB_PASSWORD.toCharArray());
            MongoClient mongoClient = new MongoClient(new ServerAddress(DB_HOST_NAME, DB_PORT), Arrays.asList(credential));
            db = mongoClient.getDB(DB_NAME);
            chargesCollection = db.getCollection(CHARGES_COLLECTION_NAME);

        } catch (Exception exception) {
            // not implemented
        }
    }

    public static ChargesDB getInstance() {
        if (instance == null) {
            instance = new ChargesDB();
        }
        return instance;
    }

    public Charge getCharge(int id) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCursor cursor = chargesCollection.find(query);

        if (cursor.hasNext()) {
            DBObject chargeObject = cursor.next();

            return new Charge(chargeObject);
        }

        return null;
    }

    public void addCharge(Charge charge) {

        // if charge is not in DB add it
        if (getCharge(charge.getId()) == null) {
            chargesCollection.insert(charge.serialize());
        }
    }

    public void addCharges(List<Charge> chargeList) {
        chargeList.forEach(this::addCharge);
    }

    public void updateCharge(Charge charge) {

    }

    public void deleteCharge(Charge charge) {
        chargesCollection.remove(charge.serialize());
    }

    public void dropCollection() {
        chargesCollection.drop();
    }
}
