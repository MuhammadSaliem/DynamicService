package com.dgbi.DAL;


import com.dgbi.Models.Request;
import com.dgbi.Models.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DAL {
    //    private final String connectionString = "jdbc:sqlserver://DESKTOP-2I3LGMF;databaseName=testDB;encrypt=true;trustServerCertificate=true;";
    private final String connectionString = "jdbc:sqlserver://localhost; databaseName=testDB; encrypt=true; trustServerCertificate=true;";
    private final String user = "sa";
    private String password = "P@ssw0rd";

    public Connection getConnection() {

        try {
            Connection connection = DriverManager.getConnection(connectionString, user, password);
            return connection;

        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeSelectQuery(String query) {
        ResultSet rs = null;
        List<RequestParam> params = null;

        try {
            Statement stmt = getConnection().createStatement();
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }

        return rs;
    }

    public boolean executeInsertQuery(String query) {
        try {
            Statement stmt = getConnection().createStatement();
            return stmt.execute(query);
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return false;
    }
}
