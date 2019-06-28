package sample.DataBaseConnection;


import java.sql.*;

public class DataBaseConnection {

    public static Connection start() {

        Connection connection = null;
        String USER = "****";
        String PASS = "****";
        String DB_URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        System.out.println("Start JDBC program");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Connecting to database");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            System.err.println(e);
        }
        return connection;

    }

}
