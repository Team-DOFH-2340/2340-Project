package controller;

import java.sql.*;

public class SQLInterface {

    private static int counter = 0;

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
                    + "Password VARCHAR(32) NOT NULL,	"
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
                String sql = "INSERT INTO User("
                        + "Username, "
                        + "Password,"
                        + "User_id)"
                        + "VALUES("
                        + "'" + username + "',"
                        + "'" + password + "',"
                        + "0)";
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
            ResultSet rs = stmt.executeQuery("SELECT Username FROM User WHERE Username = " + "'" + username + "'");
            while (rs.next()) {
                dup = true;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Is a duplicate? " + dup);
        return dup;
    }

    public static boolean authenticate(String username, String password) {
        boolean founduser = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = "Select User_id FROM User "
                    + "WHERE "
                    + "Username = '" + username + "' "
                    + "AND "
                    + "Password = '" + password + "'";
            System.out.println("[" + sql + "]");
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                founduser = true;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return founduser;
    }
}
