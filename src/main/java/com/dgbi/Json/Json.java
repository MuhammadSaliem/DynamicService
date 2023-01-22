package com.dgbi.Json;

import com.dgbi.Models.Request;
import com.dgbi.Models.RequestParam;
import com.dgbi.DAL.DAL;
import com.dgbi.Engine.Engine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Json {


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

    public JSONObject readDynamicWebserviceJsonFile(String dir) throws IOException, ParseException {
        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader(dir));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        return jo;
    }

    public boolean validateJson(JSONObject jsonObj) throws Exception {

        List<RequestParam> paramList =  new DAL().selectAllParams((String) jsonObj.get("type"));

        // params from DB
        List<String> typeParams = new DAL().getParamsNames(paramList);

        // params from request
        List<String> requestParams = new ArrayList<>();

        Map request = ((Map)jsonObj.get("request"));
        Iterator<Map.Entry> itr1 = request.entrySet().iterator();

        // Validate type params
        while(itr1.hasNext())
        {
            Map.Entry pair = itr1.next();

            //Add keys to a separate list
            requestParams.add((String)pair.getKey());

            //validate that each request param belongs to the type params
            if(!typeParams.contains(pair.getKey()))
            {
                System.out.println(String.format("The param \"%s\" does not belong to type \"%s\"", pair.getKey().toString(), (String)jsonObj.get("type")));
                return false;
            }
        }

        // Validate mandatory params
        for(RequestParam param : paramList)
        {
            if(param.isMandatory() && !requestParams.contains(param.getParam_name()))
            {
                System.out.println("An mandatory param is missing!");
                return false;
            }
        }

        return true;
    }

    public Request convertJsonToRequestObject(JSONObject obj)
    {
        Request req = new Request();
        req.setRef((String)obj.get("ref"));
        req.setType((String)obj.get("type"));

        String requestParams = ((Map) obj.get("request")).toString();
        req.setRequestParams(requestParams);

        return req;
    }

    public String generateQueryString(JSONObject request)
    {
        JSONObject obj = new JSONObject();
        obj.put("username", "Ahmed Elmixicy");
        obj.put("password", "202010");

        Map req = ((Map)request.get("request"));
        obj.put("request", req);

        System.out.println("QueryString ... \n" + obj.toString());
        return obj.toString();
    }

    public void processRequest(JSONObject obj) throws Exception {

        // Cheak if ref already existed
        String refId = ((String) obj.get("ref"));
        Request request = new DAL().selectRequest(refId);

        if(request != null)
        {
            System.out.println("request already existed!");
            System.out.println("Terminate ...");
            return;
        }

        // validate Json
        if(validateJson(obj))
        {
            String queryString = generateQueryString(obj);
            new Engine().engineMethod(queryString);

            Request req = convertJsonToRequestObject(obj);
            new DAL().InsertRequest(req);
        }
        else
            System.out.println("Json file is not valid");
    }
}



