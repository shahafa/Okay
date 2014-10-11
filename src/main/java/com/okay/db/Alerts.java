package com.okay.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.okay.Alert;

import java.util.List;

public class Alerts {
    private static final String ALERTS_COLLECTION_NAME = "charges";

    private static Alerts instance = null;
    private static DBCollection alertsCollection;

    private Alerts() {
        alertsCollection = DBManager.getInstance().getCollection(ALERTS_COLLECTION_NAME);
    }

    public static Alerts getInstance() {
        if (instance == null) {
            instance = new Alerts();
        }

        return instance;
    }

    public Alert get(int id) {
        return null;
    }

    public List<Alerts> getAlerts(int accountNumber) {
        return null;
    }

    public void add(Alert alert) {
        // if charge is not in DB add it
        if (get(alert.getId()) == null) {
            alertsCollection.insert(alert.serialize());
        }
    }

    public void add(List<Alert> alertList) {
        alertList.forEach(this::add);
    }

    public void update(Alert alert) {
        BasicDBObject query = new BasicDBObject().append("id", alert.getId());
        BasicDBObject alertObject = alert.serialize();

        alertsCollection.update(query, alertObject);
    }

    public void delete(Alert alert) {
        alertsCollection.remove(alert.serialize());
    }

    public void drop() {
        alertsCollection.drop();
    }
}
