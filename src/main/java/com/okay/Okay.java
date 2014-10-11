package com.okay;

import com.okay.connectors.BankHapoalimConnector;
import com.okay.db.Charges;

import java.util.List;

public class Okay {

    public static void main(String[] args) {
        System.out.println("Okay is running!!!");

        BankHapoalimConnector bhConnector = new BankHapoalimConnector();
        List<Charge> chargeList = bhConnector.getCharges(109776);
        Charge charge = chargeList.get(3);
        charge.setBusinessName("Shahaf");
        Charges.getInstance().update(charge);

        //bhConnector.getAccountDetails(109776);

//        List<Charge> chargeList = bhConnector.getCharges(109776);
//        Collections.sort(chargeList, new Comparator<Charge>() {
//            public int compare(Charge m1, Charge m2) {
//                return m1.getPurchaseDateTime().compareTo(m2.getPurchaseDateTime());
//            }
//        });
//
//        ChargesDB.getInstance().drop();
//        for (Charge charge : chargeList) {
//            Investigator.getInstance().investigate(charge);
//            ChargesDB.getInstance().add(charge);
//        }
    }
}
