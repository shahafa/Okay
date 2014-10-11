package com.okay.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.okay.Charge;

import java.util.ArrayList;
import java.util.List;

public class Charges {

    private static final String CHARGES_COLLECTION_NAME = "charges";

    private static Charges instance = null;
    private static DBCollection chargesCollection;

    private Charges() {
        chargesCollection = DBManager.getInstance().getCollection(CHARGES_COLLECTION_NAME);
    }

    public static Charges getInstance() {
        if (instance == null) {
            instance = new Charges();
        }
        return instance;
    }

    public Charge get(int id) {
        BasicDBObject query = new BasicDBObject("id", id);
        DBCursor cursor = chargesCollection.find(query);

        if (cursor.hasNext()) {
            DBObject chargeObject = cursor.next();

            return new Charge(chargeObject);
        }

        return null;
    }

    public List<Charge> getCharges(int accountNumber) {
        BasicDBObject query = new BasicDBObject("account_number", accountNumber);
        DBCursor cursor = chargesCollection.find(query);

        List<Charge> chargeList = new ArrayList<>();
        while (cursor.hasNext()) {
            chargeList.add(new Charge(cursor.next()));
        }

        return chargeList;
    }

    public void add(Charge charge) {
        // if charge is not in DB add it
        if (get(charge.getId()) == null) {
            chargesCollection.insert(charge.serialize());
        }
    }

    public void add(List<Charge> chargeList) {
        chargeList.forEach(this::add);
    }

    public void update(Charge charge) {
        BasicDBObject query = new BasicDBObject().append("id", charge.getId());
        BasicDBObject chargeObject = charge.serialize();

        chargesCollection.update(query, chargeObject);
    }

    public void delete(Charge charge) {
        chargesCollection.remove(charge.serialize());
    }

    public void drop() {
        chargesCollection.drop();
    }
}
