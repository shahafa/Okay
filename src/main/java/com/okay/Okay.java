package com.okay;

import com.okay.connectors.BankHapoalimConnector;
import com.okay.db.ChargesDB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Okay {

    public static void main(String[] args) {
        System.out.println("Okay is running!!!");

        BankHapoalimConnector bhConnector = new BankHapoalimConnector();
        //bhConnector.getAccountDetails(109776);

        List<Charge> chargeList = bhConnector.getCharges(109776);
        Collections.sort(chargeList, new Comparator<Charge>() {
            public int compare(Charge m1, Charge m2) {
                return m1.getPurchaseDateTime().compareTo(m2.getPurchaseDateTime());
            }
        });

        ChargesDB.getInstance().dropCollection();
        for (Charge charge : chargeList) {
            Investigator.getInstance().investigate(charge);
            ChargesDB.getInstance().addCharge(charge);
        }
    }
}
