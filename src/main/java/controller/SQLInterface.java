package controller;

import javafx.scene.control.Alert;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class SQLInterface {

    private static int counter = 0;
    private static Person activeUser;

    public static void init() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    // checks to see all tables exist
    // in the database - if not, create them
    public static void checkDatabase() {
        Connection c = null;
        boolean createLogin = true;
        boolean createWaterSource = true;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                if (rs.getString(3).equals("User")) {
                    createLogin = false;
                } else if (rs.getString(3).equals("WaterSource")) {
                    createWaterSource = false;
                }
            }
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        if (createLogin) {
            createLoginTable();
        }
        if (createWaterSource) {
            createWaterSourceTable();
        }
    }

    public static void createLoginTable() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE User("
                    + "Username VARCHAR(32) PRIMARY KEY,"
                    + "Password VARCHAR(32) NOT NULL,"
                    + "Title VARCHAR(32),"
                    + "Name VARCHAR(32),"
                    + "Email VARCHAR(32),"
                    + "AddressLine1 VARCHAR(32),"
                    + "AddressLine2 VARCHAR(32),"
                    + "AddressLine3 VARCHAR(32),"
                    + "UserType INT"
                    + ")";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static void createWaterSourceTable() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE WaterSource("
                    + "ReportNumber INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "User VARCHAR(32),"
                    + "Date CHARACTER(10),"
                    + "Hour INTEGER,"
                    + "Minute INTEGER,"
                    + "Latitude REAL,"
                    + "Longitude REAL,"
                    + "Type INTEGER,"
                    + "Condition INTEGER"
                    + ")";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    // checks to see if the username is unique
    // if so, create a new entry in user table
    // more specifications and such can be added later
    // returns true on success; false on failure
    public static boolean createLogin(String username, String password, String name, int usertype) {
        if (duplicateUN(username)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists in system");
            alert.showAndWait();
            return false;
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
                Statement stmt = c.createStatement();
                String sql = String.format("INSERT INTO User(Username,Password, Name, Title, Email, AddressLine1, AddressLine2, AddressLine3, Usertype) VALUES('%s','%s', '%s', 'Duelmaster', '', '', '', '', '%s')",
                        username, password, name, usertype);
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        return true;
    }

    public static boolean createWaterSourceReport(WaterSourceReport report) {
        boolean success = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("INSERT INTO WaterSource(User, Date, Hour, Minute, Latitude, Longitude, Type, Condition) VALUES('%s','%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                report.getName(), report.getDate().toString(), report.getHour(), report.getMinute(), report.getLatitude(), report.getLongitude(), report.getType(), report.getCondition());
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            success = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return success;
    }

    public static ArrayList<WaterSourceReport> getAllReportsInSystem() {
        ArrayList<WaterSourceReport> collection = new ArrayList<WaterSourceReport>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM WaterSource");
            while (rs.next()) {
                WaterSourceReport temp = new WaterSourceReport();
                temp.setReport_id(rs.getInt(1));
                temp.setName(rs.getString(2));
                temp.setDate(LocalDate.parse(rs.getString(3)));
                temp.setHour(rs.getInt(4));
                temp.setMinute(rs.getInt(5));
                temp.setLatitude(rs.getInt(6));
                temp.setLongitude(rs.getInt(7));
                temp.setType(WaterSourceType.values()[rs.getInt(8)]);
                temp.setCondition(WaterSourceCondition.values()[rs.getInt(9)]);
                collection.add(temp);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return collection;
    }

    //  see above method, checks for duplicate username
    private static boolean duplicateUN(String username) {
        boolean dup = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT Username FROM User WHERE Username = '%s'", username));
            while (rs.next()) {
                dup = true;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return dup;
    }

    public static boolean authenticate(String username, String password) {
        boolean founduser = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("Select * FROM User WHERE Username = '%s' AND Password = '%s'",
                    username, password);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                founduser = true;
                activeUser = new Person();
                activeUser.homeAddress = new HomeAddress();
                activeUser.username = rs.getString(1);
                activeUser.password = rs.getString(2);
                activeUser.title = rs.getString(3);
                activeUser.name = rs.getString(4);
                activeUser.email = rs.getString(5);
                activeUser.homeAddress.line1 = rs.getString(6);
                activeUser.homeAddress.line2 = rs.getString(7);
                activeUser.homeAddress.line3 = rs.getString(8);
                activeUser.type = UserType.values()[rs.getInt(9)];
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return founduser;
    }

    public static void updateUser(Person user) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("UPDATE User SET "
                    + "Title = '" + user.title + "', "
                    + "Name = '" + user.name + "', "
                    + "Email = '" + user.email + "', "
                    + "AddressLine1 = '" + user.homeAddress.line1 + "', "
                    + "AddressLine2 = '" + user.homeAddress.line2 + "', "
                    + "AddressLine3 = '" + user.homeAddress.line3 + "' "
                    + "WHERE Username = '" + user.username + "'");
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static Person getUser() {
        return activeUser;
    }
}
