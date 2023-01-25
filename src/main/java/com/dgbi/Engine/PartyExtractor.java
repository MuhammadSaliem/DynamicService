package com.dgbi.Engine;

import com.dgbi.service.Service;
import com.dgbi.Models.Party;
import com.dgbi.Models.RequestParam;
import com.dgbi.service.Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;
import java.util.*;

public class PartyExtractor {

    // refactoring
    public List<Party> extractParties(String username, String password, String queryString) throws ParseException, SQLException {

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(queryString);
        List<Party> parties = new ArrayList<>();

        // getting request obj from json
        Map request = ((Map) jsonObj.get("request"));

        List<String> paramKeys = new ArrayList<>();

        // iterating request Map
        Iterator<Map.Entry> itr1 = request.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            paramKeys.add(pair.getKey().toString());
        }

        List<RequestParam> params = new Service().selectRequestParams(paramKeys);

        itr1 = request.entrySet().iterator();

        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();


            // optimization
            String type = params.stream()
                    .filter(p -> p.getParam_name().equals(pair.getKey().toString()))
                    .findFirst()
                    .orElse(null)
                    .getParam_type();

            parties.add(new Party(type, pair.getValue().toString()));
        }

        System.out.println(String.format("username: '%s' password: '%s'", username, password));
        displayParties(parties);
        return parties;
    }

    public void func(String username, String password, String queryString) {
        /*
            - username tells which module to use
            - queryString contains only param_name and value
            - param_type decides the type of party to be generated

            - party {type : value}

            - Engine is fully ignorant about the request processor
            - get the module/type using the username
            - get the parameters of the module/type
            - create the list of the parties as per their types.

            type = getType(username);
            params = getTypeParams(type)

            params(parameter_name, parameter_type) & request(parameter_name, parameter_value)
            for each parameter in request
                . get type
                . build party


            param_type:
                1- name
                2- country
                3- identity
                4- bic
                5- occupation
                6- goods
                7- ports


         */
    }

    public void displayParties(List<Party> parties) {
        System.out.println("parties {type : value} ...");
        for (int i = 0; i < parties.size(); i++) {
            System.out.println(String.format("\t%d- {%s : \"%s\"}",i+1 , parties.get(i).getType(), parties.get(i).getValue()));
        }
    }
}
