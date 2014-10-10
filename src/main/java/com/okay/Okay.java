package com.okay;

import com.okay.connectors.BankHapoalimConnector;

public class Okay {

    public static void main(String[] args) {
        System.out.println("Okay is running!!!");

        BankHapoalimConnector bhConnector = new BankHapoalimConnector();
        //bhConnector.getAccountDetails(109776);
        bhConnector.getCharges(109776);
    }
}
