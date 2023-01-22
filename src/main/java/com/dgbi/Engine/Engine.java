package com.dgbi.Engine;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

public class Engine {

    public void engineMethod(String queryString) throws ParseException {
        JSONObject jsonObj = (JSONObject) new JSONParser().parse(queryString);

        // getting request obj from json
        Map request = ((Map)jsonObj.get("request"));

        System.out.println("parties ...");

        // iterating request Map
        Iterator<Map.Entry> itr1 = request.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }
}
