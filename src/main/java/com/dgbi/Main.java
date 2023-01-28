package com.dgbi;

import com.dgbi.DAL.DAL;
import com.dgbi.Json.Json;
import com.dgbi.DynamicService.Webservice;
import org.json.simple.JSONObject;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         -Display all of missing paramters

         -param_type : {identity, name, country, BIC}

        -username : "type"

         */

        /*
                -username and type are related

        -Validate only manadtory params
         */

        /*
            next task: simple webservice tool "ws import" -> summation
            -How to create webservice intelliJ
         */

        // request parameters
//        String ref = "202052";
        String ref = new Random().nextInt(9999,99999) + "";
        String type = "CASH";

        Map request = new LinkedHashMap(4);
        request.put("nationality", "Egypt");
        request.put("party_name", "Ahmed");
        request.put("official_name", "Ahmed");
        request.put("unknown_param", "unknown");
//        request.put("Date_Of_Birth", "4/2/1999");
//        request.put("identity", 18102260);
//        request.put("country", "Egypt");
//        request.put("occupation", "accountant");
//        request.put("occupation", "Accountant");
//        request.put("salary", "15000");

        Json json = new Json();
        json.createDynamicWebserviceJsonFile(ref, type, request);

        JSONObject obj = json.readDynamicWebserviceJsonFile("JSONExample.json");

        new Webservice().processRequest(obj);


        //new DAL().testConnection();
//        System.out.println(new DAL().selectAllParams("CORE").size());

    }
}