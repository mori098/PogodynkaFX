package weatherapp.model.utils;

import java.sql.*;

public class DatabaseConnector {
    private static DatabaseConnector ourInstance = new DatabaseConnector();

    public static DatabaseConnector getInstance() {
        return ourInstance;
    }

    private static final String SQL_LINK = "jdbc:mysql://5.135.218.27:3306/mateusz?characterEncoding=utf-8";
    private static final String SQL_USER = "mateusz";
    private static final String SQL_PASS = "realm098";
    private static final String SQL_CLASS = "com.mysql.jdbc.Driver";


    private Connection connection;


    private DatabaseConnector() {
        connect();
        System.out.println("Połączono");
    }

    private void connect() {
        try {
            Class.forName(SQL_CLASS).newInstance();
            connection = DriverManager.getConnection(SQL_LINK, SQL_USER, SQL_PASS);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public PreparedStatement getNewPreparedStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Statement getNewStatement(){
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

