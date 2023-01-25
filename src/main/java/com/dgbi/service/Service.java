package com.dgbi.service;

import com.dgbi.DAL.DAL;
import com.dgbi.Models.Request;
import com.dgbi.Models.RequestParam;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


// refactoring
public class Service {

    DAL dal;

    public Service(){
        dal = new DAL();
    }
    public List<RequestParam> selectAllParams(String type) {
        String query = String.format("SELECT request_type, param_name, isMandatory, param_type FROM Parameters WHERE request_type = '%s'", type);
        List<RequestParam> params = null;

        try {
            ResultSet rs = dal.executeSelectQuery(query);
            params = new ArrayList<RequestParam>();

            while (rs.next()) {
                RequestParam param = new RequestParam();
                param.setType(rs.getString("request_type"));
                param.setParam_name(rs.getString("param_name"));
                param.setMandatory(rs.getBoolean("isMandatory"));
                param.setParam_type(rs.getString("param_type"));
                params.add(param);
            }
            //System.out.println("Done.");
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return params;
    }


    public List<RequestParam> selectRequestParams(List<String> keys) {

        // generate sql conditional list
        String listOfKeys = generateSqlList(keys);

        String query = String.format("SELECT param_name, param_type  FROM Parameters WHERE param_name in %s", listOfKeys);
        List<RequestParam> params = null;

        try {
            ResultSet rs = dal.executeSelectQuery(query);
            params = new ArrayList<RequestParam>();

            while (rs.next()) {
                RequestParam param = new RequestParam();
                param.setParam_name(rs.getString("param_name"));
                param.setParam_type(rs.getString("param_type"));
                params.add(param);
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }

        return params;
    }

    public String generateSqlList(List<String> list) {
        StringBuilder sqlList = new StringBuilder("(");

        for (int i = 0; i < list.size(); i++) {
            sqlList.append("'" + list.get(i) + "'");

            if (i != list.size() - 1)
                sqlList.append(", ");
        }

        sqlList.append(")");

        return sqlList.toString();
    }


    public Request selectRequest(String refId) {

        String query = String.format("SELECT * FROM Request WHERE reference = '%s'", refId);
        Request request = null;

        try {
            ResultSet rs = dal.executeSelectQuery(query);

            // if result set is not empty
            if (rs.next()) {
                request = new Request();

                request.setReference(rs.getString("reference"));
                request.setType(rs.getString("type"));
                request.setRequestParams(rs.getString("request"));
            }

//                System.out.println("Select request record");
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return request;
    }

    public boolean InsertRequest(Request request) {
        String query = String.format("INSERT INTO REQUEST VALUES('%s', '%s', '%s')", request.getReference(), request.getType(), request.getRequestParams());

        try {
            return dal.executeInsertQuery(query);
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getParamsNames(List<RequestParam> params) {
        List<String> paramList = new ArrayList<>();

        for (RequestParam param : params) {
            paramList.add(param.getParam_name());
        }
        return paramList;
    }


    public String selectSourceUsername(String type) {
        String query = String.format("SELECT source_username FROM Request_Sources WHERE source_type = '%s'", type);
        String sourceUsername = null;

        try {
            ResultSet rs = dal.executeSelectQuery(query);

            // if result set is not empty
            if (rs.next()) {
                sourceUsername = rs.getString("source_username").toString();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return sourceUsername;
    }

    public String selectSourceType(String sourceUsername) {
        String query = String.format("SELECT source_type FROM Request_Sources WHERE source_username = '%s'", sourceUsername);
        String sourceType = null;

        try {
            ResultSet rs = dal.executeSelectQuery(query);

            // if result set is not empty
            if (rs.next()) {
                sourceType = rs.getString("source_username").toString();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return sourceType;
    }

    public boolean isTypeExisted(String typeName) {
        String query = String.format("SELECT param_name FROM Parameters WHERE request_type = '%s'", typeName);
        String sourceType = null;

        try {
            ResultSet rs = dal.executeSelectQuery(query);

            // if result set is not empty
            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return false;
    }
}
