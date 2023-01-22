package com.dgbi;

import com.dgbi.DAL.DAL;
import com.dgbi.Json.Json;
import com.dgbi.Models.Request;
import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) throws Exception {

        DAL dal = new DAL();
        System.out.println("connection -> " + dal.getConnection().isClosed());
        System.out.println(dal.selectAllParams("Core"));

//        Json json = new Json();
//        JSONObject obj = json.readDynamicWebserviceJsonFile("JSONExample.json");

//        json.processRequest(obj);

    }
}