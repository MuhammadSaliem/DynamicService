package com.dgbi;

import com.dgbi.DAL.DAL;
import com.dgbi.json.JsonFile;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        new DAL().selectAllParams("Core");

        JsonFile json = new JsonFile();
        json.readDynamicWebserviceJsonFile();
        System.out.println(json.validateJson());

//        new JsonFile().createJsonFile();
//        new JsonFile().readJsonFile();
    }
}