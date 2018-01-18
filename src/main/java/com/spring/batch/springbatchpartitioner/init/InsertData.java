package com.spring.batch.springbatchpartitioner.init;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class InsertData {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/test";
    private static final String DB_USER = "test";
    private static final String DB_PASSWORD = "test";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USERS (ID INTEGER,USER_LOGIN VARCHAR(22), USER_PASS VARCHAR(22),AGE VARCHAR(22));";


    public void insertDataIntoUserTable() throws Exception {

        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();

            statement = dbConnection.createStatement();
            statement.addBatch(CREATE_TABLE);
            statement.executeBatch();

            Random generator = new Random();

            for (int i = 1; i <= 1000000; i++) {

                String insertTableSQL = "INSERT INTO USERS (ID, USER_LOGIN, USER_PASS, AGE) VALUES (':id',':name',':pass',':age')";

                insertTableSQL = insertTableSQL.replaceAll(":id", String.valueOf(i + generator.nextInt(1000000)));
                insertTableSQL = insertTableSQL.replaceAll(":name", "user_" + UUID.randomUUID().toString());
                insertTableSQL = insertTableSQL.replaceAll(":pass", "pass_" + UUID.randomUUID().toString());

                insertTableSQL = insertTableSQL.replaceAll(":age", String.valueOf(generator.nextInt(1000000)));

                System.out.println(insertTableSQL);

                statement.addBatch(insertTableSQL);

            }

            statement.executeBatch();

            System.out.println("Record is inserted into USER table!");

        } catch (BatchUpdateException e) {

            System.out.println(e.getMessage());
            System.out.println(e.getNextException());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

}