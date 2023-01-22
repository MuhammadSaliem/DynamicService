package com.dgbi;

import com.dgbi.Json.Json;
import com.dgbi.DynamicService.Webservice;
import org.json.simple.JSONObject;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        /*
         -Display all of missing paramters

         -param_type : {identity, name, country, BIC}

        -username : "type"
         */

        // request parameters
        String ref = "202030";
        String type= "CORE";

        Map request = new LinkedHashMap(2);
        request.put("name", "Ahmed");
        request.put("identity", 18102260);
        request.put("country", "Egypt");


        Json json = new Json();
        json.createDynamicWebserviceJsonFile(ref, type, request);

        JSONObject obj = json.readDynamicWebserviceJsonFile("JSONExample.json");

        new Webservice().processRequest(obj);

    }
}