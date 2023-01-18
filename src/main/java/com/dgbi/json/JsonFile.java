package com.dgbi.json;

import com.dgbi.BL.RequestParam;
import com.dgbi.DAL.DAL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class JsonFile {


    private JSONObject jsonObj;

    public void createJsonFile() throws FileNotFoundException {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        // putting data to JSONObject
        jo.put("firstName", "John");
        jo.put("lastName", "Smith");
        jo.put("age", 25);

        // for address data, first create LinkedHashMap
        Map m = new LinkedHashMap(4);
        m.put("streetAddress", "21 2nd Street");
        m.put("city", "New York");
        m.put("state", "NY");
        m.put("postalCode", 10021);

        // putting address to JSONObject
        jo.put("address", m);

        // for phone numbers, first create JSONArray
        JSONArray ja = new JSONArray();

        m = new LinkedHashMap(2);
        m.put("type", "home");
        m.put("number", "212 555-1234");

        // adding map to list
        ja.add(m);

        m = new LinkedHashMap(2);
        m.put("type", "fax");
        m.put("number", "212 555-1234");

        // adding map to list
        ja.add(m);

        // putting phoneNumbers to JSONObject
        jo.put("phoneNumbers", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();
    }

    public void readJsonFile() throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");

        System.out.println(firstName);
        System.out.println(lastName);

        // getting age
        long age = (long) jo.get("age");
        System.out.println(age);

        // getting address
        Map address = ((Map)jo.get("address"));

        // iterating address Map
        Iterator<Map.Entry> itr1 = address.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }

        // getting phoneNumbers
        JSONArray ja = (JSONArray) jo.get("phoneNumbers");

        // iterating phoneNumbers
        Iterator itr2 = ja.iterator();

        while (itr2.hasNext())
        {
            itr1 = ((Map) itr2.next()).entrySet().iterator();
            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                System.out.println(pair.getKey() + " : " + pair.getValue());
            }
        }
    }

    public void createDynamicWebserviceJsonFile() throws FileNotFoundException {
        JSONObject jo = new JSONObject();

        // putting data to JSONObject
        jo.put("type", "Core");
        jo.put("ref", "202010");

        Map m = new LinkedHashMap(4);
        m.put("name", "Ahmed");
        m.put("id", 18102260);
        m.put("country", "Egypt");
        m.put("address", "22 Soliman Azmy st. Cairo, Egypt");

        jo.put("request", m);

//
//        // for address data, first create LinkedHashMap
//        Map m = new LinkedHashMap(4);
//        m.put("streetAddress", "21 2nd Street");
//        m.put("city", "New York");
//        m.put("state", "NY");
//        m.put("postalCode", 10021);
//
//        // putting address to JSONObject
//        jo.put("address", m);
//
//        // for phone numbers, first create JSONArray
//        JSONArray ja = new JSONArray();
//
//        m = new LinkedHashMap(2);
//        m.put("type", "home");
//        m.put("number", "212 555-1234");
//
//        // adding map to list
//        ja.add(m);
//
//        m = new LinkedHashMap(2);
//        m.put("type", "fax");
//        m.put("number", "212 555-1234");
//
//        // adding map to list
//        ja.add(m);
//
//        // putting phoneNumbers to JSONObject
//        jo.put("phoneNumbers", ja);

        // writing JSON to file:"JSONExample.json" in cwd
        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();

        jsonObj = jo;
    }

    public void readDynamicWebserviceJsonFile() throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        String type = (String) jo.get("type");
        String ref = (String) jo.get("ref");

        System.out.println(type);
        System.out.println(ref);

        // getting Request
        Map request = ((Map)jo.get("request"));

        // iterating address Map
        Iterator<Map.Entry> itr1 = request.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
    }

    public boolean validateJson(JSONObject jsonObj) throws Exception {
        List<RequestParam> typeParams =  new DAL().selectAllParams((String) jsonObj.get("type"));
        List<String> requestParams = new ArrayList<>();

        Map request = ((Map)jsonObj.get("request"));
        Iterator<Map.Entry> itr1 = request.entrySet().iterator();

        // Validate mandatory params
        while(itr1.hasNext())
        {
            Map.Entry pair = itr1.next();

            //Add keys to a separate list
            requestParams.add((String)pair.getKey());

            //each param existed in the type params
            if(!typeParams.contains(pair.getKey()))
            {
                System.out.println("The param is not belong the type");
                return false;
            }
        }

        for(RequestParam param : typeParams)
        {
            if(param.isMandatory() && !requestParams.contains(param.getParam_name()))
            {
                System.out.println("An mandatory param is missing!");
                return false;
            }
        }

        return true;
    }
}



