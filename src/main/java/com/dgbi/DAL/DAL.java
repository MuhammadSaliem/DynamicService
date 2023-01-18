package com.dgbi.DAL;


import com.dgbi.BL.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAL {
    String ConnectionString = "jdbc:sqlserver://localhost;databaseName=testDB;encrypt=true;trustServerCertificate=true;";
    String user = "sa";
    String password = "P@ssw0rd";
    ResultSet rs;


    private Connection connectionString;

    public DAL(){
        connectionString = getConnectionString();
    }

    public Connection getConnectionString()
    {
        try {
            System.out.print("Connecting to SQL Server ... ");

            try (Connection connection = DriverManager.getConnection(ConnectionString, user, password);

            )  {
                System.out.println("Done.");
                return connection;
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return null;
    }
    public List<RequestParam> selectAllParams(String type){
        String query = String.format("SELECT request_type, param_name, isMandatory FROM Request WHERE request_type = '%s'", type);
        List<RequestParam> params = null;

        try {
            System.out.print("Connecting to SQL Server ... ");

            try (Connection connection = DriverManager.getConnection(ConnectionString, user, password);
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



    private int ConvertBooleanToBit(boolean x)
    {
        if(x)
            return 1;
        else
            return 0;
    }
}
