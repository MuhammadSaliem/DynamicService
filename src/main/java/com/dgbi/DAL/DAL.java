package com.dgbi.DAL;


import com.dgbi.Models.Request;
import com.dgbi.Models.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAL {
    private final String connectionString = "jdbc:sqlserver://localhost;databaseName=testDB;encrypt=true;trustServerCertificate=true;";
    private final String user = "sa";
    private String password = "P@ssw0rd";
    ResultSet rs;
    public static Connection connection;

    public DAL() throws SQLException {
        connection = createConnection();
        System.out.println("Constructor - >" + getConnection().isClosed());
    }

    public Connection createConnection()
    {
        try {
            System.out.print("Connecting to SQL Server ... ");

            try (Connection connection = DriverManager.getConnection(connectionString, user, password);

            )  {
                this.connection = connection;
                System.out.println(getConnection().isClosed());
                return connection;
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return null;
    }

    public List<RequestParam> selectAllParams(String type){
        String query = String.format("SELECT request_type, param_name, isMandatory FROM Paramaters WHERE request_type = '%s'", type);
        List<RequestParam> params = null;

        try {
            System.out.print("Connecting to SQL Server ... ");

            try (Connection connection = DriverManager.getConnection(connectionString, user, password);
                 Statement stmt = connection.createStatement();

            )  {

                rs = stmt.executeQuery(query);
                params = new ArrayList<RequestParam>();

                while(rs.next())
                {
                    RequestParam param = new RequestParam();
                    param.setType(rs.getString("request_type"));
                    param.setParam_name(rs.getString("param_name"));
                    param.setMandatory(rs.getBoolean("isMandatory"));
                    params.add(param);
                }
                System.out.println("Done.");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return params;
    }





    public Request selectRequest(String refId){
        String query = String.format("SELECT * FROM Request WHERE reference = '%s'", refId);
        Request request = null;

        try {
            System.out.print("Connecting to SQL Server ... ");

            try (Connection connection = DriverManager.getConnection(connectionString, user, password);
                 Statement stmt = connection.createStatement();

            )  {

                rs = stmt.executeQuery(query);


                // if result set is not empty
                if(rs.next())
                {
                    request = new Request();

                    request.setRef(rs.getString("reference"));
                    request.setType(rs.getString("type"));
                    request.setRequestParams(rs.getString("request"));
                }

                System.out.println("Select request record");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return request;
    }

    public void InsertRequest(Request request){
        String query = String.format("INSERT INTO REQUEST VALUES('%s', '%s', '%s')", request.getRef(), request.getType(), request.getRequestParams());


        try {
            System.out.print("Connecting to SQL Server ... ");

            try (Connection connection = DriverManager.getConnection(connectionString, user, password);
                 Statement stmt = connection.createStatement();

            )  {

                stmt.execute(query);
                System.out.println("Done.");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }

    }

    public List<String> getParamsNames(List<RequestParam> params)
    {
        List<String> paramList = new ArrayList<>();

        for (RequestParam param : params)
        {
            paramList.add(param.getParam_name());
        }
        return paramList;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }




}
