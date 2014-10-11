package com.okay.connectors;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import com.okay.Account;
import com.okay.Charge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BankHapoalimConnector implements IConnector {

    private static final String ACCESS_KEY = "AKIAJLJR7QC4QQZT4SMA";
    private static final String SECRET_KEY = "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";
    private static final String CREDIT_CARD_TRANSACTION_TABLE = "CreditCards4";
    
    private AmazonDynamoDBClient dbClient;

    public BankHapoalimConnector() {

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        dbClient = new AmazonDynamoDBClient(credentials);
        dbClient.setRegion(Region.getRegion(Regions.EU_WEST_1));
    }

    public Account getAccountDetails(int accountNumber) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("Account_Number", new AttributeValue().withN(Integer.toString(accountNumber)));

        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName("Accounts")
                .withKey(key);

        GetItemResult result = dbClient.getItem(getItemRequest);
        Map<String, AttributeValue> accountItem = result.getItem();

        return deserializeAccountItem(accountItem);
    }

    public Account deserializeAccountItem(Map<String, AttributeValue> accountItem) {
        Account account = new Account();

        try {
            account.setNumber(Integer.parseInt(accountItem.get("Account_Number").getN()));
        } catch (NumberFormatException exception) {
            // throw new Exception("Failed to parse account number");
        }

        try {
            account.setCustomerAge(Integer.parseInt(accountItem.get("Customer_Age").getN()));
        } catch (NumberFormatException exception) {
            account.setCustomerAge(-1);
        }

        account.setBankCode(12);
        account.setBankName("Bank Hapoalim");
        account.setBranchCity(accountItem.get("Branch_City").getS());
        account.setBranchName(accountItem.get("Branch_Name").getS());
        account.setBranchNumber(accountItem.get("Branch_Number").getS());
        account.setOpenDate(accountItem.get("Account_Open_Date").getS());
        account.setType(accountItem.get("Account_Type_Description").getS());
        account.setCustomerAddress(accountItem.get("Account_Owner_Address").getS());
        account.setCustomerGender(accountItem.get("Customer_Gender").getS());
        account.setCustomerName(accountItem.get("Customer_Name").getS());
        account.setLanguagePreference(accountItem.get("Language_Preference").getS());

        return account;
    }


    public List<Charge> getCharges(int accountNumber) {
        List<Charge> chargesList = new ArrayList<>();

        Map<String,Condition> keyConditions = new HashMap<>();
        Condition hashKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withN(Integer.toString(accountNumber)));

        keyConditions.put("Account_Number", hashKeyCondition);


        Map<String,AttributeValue>lastEvaluatedKey=null;

        do {
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(CREDIT_CARD_TRANSACTION_TABLE)
                    .withKeyConditions(keyConditions)
                    .withExclusiveStartKey(lastEvaluatedKey);

            QueryResult result = dbClient.query(queryRequest);

            List<Map<String,AttributeValue>> chargeItems = result.getItems();
            chargesList.addAll(chargeItems.stream().map(this::deserializeChargeItem).collect(Collectors.toList()));

            lastEvaluatedKey=result.getLastEvaluatedKey();
        } while(lastEvaluatedKey!=null);

        return chargesList;
    }

    public Charge deserializeChargeItem(Map<String, AttributeValue> chargeItem) {
        Charge charge = new Charge();

        charge.setId(Integer.parseInt(chargeItem.get("Charge_Id").getN()));
        charge.setAccountNumber(Integer.parseInt(chargeItem.get("Account_Number").getN()));
        charge.setPurchaseAmount(Double.parseDouble(chargeItem.get("Purchase_Amount").getN()));
        charge.setNumberOfPayments(Integer.parseInt(chargeItem.get("Number_Of_Payments_In_Purchase").getS()));
        charge.setBusinessName(chargeItem.get("Business_Name").getS());
        charge.setBusinessLocation(chargeItem.get("Business_Location").getS());
        charge.setCategory(chargeItem.get("Payment_Category").getS());

        String purchaseDateTime = String.format("%s%s", chargeItem.get("Purchase_Date").getS(), chargeItem.get("Purchase_Time").getS());
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
        try {
            charge.setPurchaseDateTime(formatter.parse(purchaseDateTime));
        } catch (ParseException e) {
            // todo: handle exception
        }

        return charge;
    }
}
