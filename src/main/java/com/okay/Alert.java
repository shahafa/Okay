package com.okay;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Alert {

    public int getId() {
        return 1;
    }

    public BasicDBObject serialize() {

        return new BasicDBObject();
    }

    public void deserialize(DBObject chargeObject) {

    }
}
