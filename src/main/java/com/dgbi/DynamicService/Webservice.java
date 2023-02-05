package com.dgbi.DynamicService;

import com.dgbi.service.Service;
import com.dgbi.Engine.PartyExtractor;
import com.dgbi.Models.Request;
import com.dgbi.Models.RequestParam;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Webservice {

    private static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    // refactoring
    public boolean validateJson(JSONObject jsonObj) throws Exception {


        // validate type
        if(!new Service().isTypeExisted((String) jsonObj.get("type")))
        {
            displayErrorMessageToConsole(String.format("Type \"%s\" is not exited!", (String) jsonObj.get("type")));
            throw new IllegalArgumentException(String.format("Type \"%s\" is not exited!", (String) jsonObj.get("type")));
        }

        List<RequestParam> paramList =  new Service().selectAllParams((String) jsonObj.get("type"));

        // params from DB
        List<String> typeParams = new Service().getParamsNames(paramList);

        // params from request
        List<String> requestParams = new ArrayList<>();

        Map request = ((Map)jsonObj.get("request"));
        Iterator<Map.Entry> itr1 = request.entrySet().iterator();

//        List<String> foreignParams = new ArrayList<>();

        // Validate type params
        while(itr1.hasNext())
        {
            Map.Entry pair = itr1.next();

            //Add keys to a separate list
            requestParams.add((String)pair.getKey());

            //validate that each request param belongs to the type params
//            if(!typeParams.contains(pair.getKey()))
//                foreignParams.add((String)pair.getKey());

        }

//        if(!foreignParams.isEmpty())
//        {
//            displayErrorMessageToConsole(String.format("The %s "+ (foreignParams.size() > 1 ? "parameters do" : "parameter does") +" not belong to type \"%s\"", foreignParams.toString(), (String)jsonObj.get("type")));
//            return false;
//        }


        List<String> missingParams = new ArrayList<>();

        // Validate mandatory params
        for(RequestParam param : paramList)
        {
            if(param.isMandatory() && !requestParams.contains(param.getParam_name()))
            {
                missingParams.add(param.getParam_name());
            }
        }

        if(missingParams.size() > 0)
        {
            displayErrorMessageToConsole("The following mandatory parameters are missing " + missingParams.toString());
            throw new Exception("The following mandatory parameters are missing " + missingParams.toString());
        }

        return true;
    }

    public Request convertJsonToRequestObject(JSONObject obj)
    {
        Request req = new Request();
        req.setReference((String)obj.get("ref"));
        req.setType((String)obj.get("type"));

        String requestParams = ((Map) obj.get("request")).toString();
        req.setRequestParams(requestParams);

        return req;
    }

    public String generateQueryString(JSONObject request)
    {
        JSONObject obj = new JSONObject();

        Map req = ((Map)request.get("request"));
        obj.put("request", req);

        //System.out.println("\nQueryString ... \n" + obj.toString());
        return obj.toString();
    }

    public void processRequest(JSONObject obj) throws Exception {

        // Cheak if ref already existed
        String refId = ((String) obj.get("ref"));
        Request request = new Service().selectRequest(refId);

        if(request != null)
        {
//            System.out.println("request already existed!");
            displayErrorMessageToConsole("Reference key already existed!");
            throw new NullPointerException("Reference key already existed!");
        }

        // validate Json
        if(validateJson(obj))
        {
            displaySuccessMessageToConsole("Request validated successfully");
            String queryString = generateQueryString(obj);
            /*
                - type
                - queryString
                - either to get any user for this type from the db
                - if type is trade use: trade_user
                - if type is core use: core_user
                - call the engine function
            */
            String source_username = new Service().selectSourceUsername(((String) obj.get("type")));
            String password = "secret";

            System.out.println("QueryString: " + queryString + "\n");
            new PartyExtractor().extractParties(source_username, password , queryString);

            Request req = convertJsonToRequestObject(obj);
            new Service().InsertRequest(req);

            displaySuccessMessageToConsole("Processed request successfully");
        }
        else
            displayErrorMessageToConsole("The request is not valid");

    }

    private void displayErrorMessageToConsole(String text)
    {
        System.out.println(ANSI_RED + text + ANSI_RESET);
    }

    private void displaySuccessMessageToConsole(String text)
    {
        System.out.println(ANSI_GREEN + text + ANSI_RESET);
    }
}
