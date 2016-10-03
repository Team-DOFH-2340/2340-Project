package controller;

import model.HomeAddress;
import model.Person;

import java.sql.*;

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
        boolean createData = true;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                if (rs.getString(3).equals("User")) {
                    createLogin = false;
                } else if (rs.getString(3).equals("Data")) {
                    createData = false;
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
        if (createData) {
            // TODO: implement CreateDataTable();
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
                    + "User_id INT NOT NULL"
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
    public static boolean createLogin(String username, String password) {
        if (duplicateUN(username)) {
            return false;
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
                Statement stmt = c.createStatement();
                String sql = String.format("INSERT INTO User(Username,Password, User_id) VALUES('%s','%s',0)",
                        username, password);
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
