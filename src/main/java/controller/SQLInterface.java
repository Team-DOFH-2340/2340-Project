package controller;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/** Gatekeeper to the database. */
public class SQLInterface {
    private static SQLInterface instance;
    private SQLInterface() {
        init();
    }
    public static SQLInterface getInstance() {
        if (null == instance) {
            instance = new SQLInterface();
        }
        return instance;
    }
    /**
     * Initializes a connection with the database.
     */
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /**
     * Checks to see all tables exist in the database. If not, create them
     */
    public void checkDatabase() {
        Connection c;
        boolean createLogin = true;
        boolean createWaterSource = true;
        boolean createWaterQuality = true;
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
                } else if (rs.getString(3).equals("WaterQuality")) {
                    createWaterQuality = false;
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
        if (createWaterQuality) {
            createWaterQualityTable();
        }
    }

    private void createLoginTable() {
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

    private void createWaterSourceTable() {
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
                    + "Latitude NUMERIC,"
                    + "Longitude NUMERIC,"
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

    private void createWaterQualityTable() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE WaterQuality("
                    + "ReportNumber INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "User VARCHAR(32),"
                    + "Date CHARACTER(10),"
                    + "Hour INTEGER,"
                    + "Minute INTEGER,"
                    + "Latitude NUMERIC,"
                    + "Longitude NUMERIC,"
                    + "VirusPPM NUMERIC,"
                    + "ContaminantPPM NUMERIC,"
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

    /**
     * Checks to see if the username is unique. If so, create a new entry in user table
     *
     * @param username username of the user
     * @param password password for the user
     * @param name     Name of the user
     * @param usertype type of user to create
     * @return true on successful user creation; false on failure
     */
    public boolean createLogin(String username, String password, String name, int usertype) {
        // TODO Refactor to take a Person object?
        if (duplicateUN(username)) {
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

    /**
     * Puts a source report in the database.
     *
     * @param report the report to put in the database.
     * @return If the report was successfully put into the database.
     */
    public boolean createWaterSourceReport(WaterSourceReport report) {
        boolean success = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("INSERT INTO WaterSource(User, Date, Hour, Minute, Latitude, Longitude, Type, Condition) VALUES('%s','%s', '%d', '%d', '%f', '%f', '%d', '%d')",
                    report.getReportedBy(), report.getDate().toString(), report.getHour(), report.getMinute(), report.getLatitude(), report.getLongitude(), report.getType().ordinal(), report.getCondition().ordinal());
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

    /**
     * Puts a source report in the database.
     *
     * @param report the report to put in the database.
     * @return If the report was successfully put into the database.
     */
    public boolean createWaterQualityReport(WaterQualityReport report) {
        boolean success = false;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("INSERT INTO WaterQuality(User, Date, Hour, Minute, Latitude, Longitude, VirusPPM, ContaminantPPM, Condition) VALUES('%s','%s', '%d', '%d', '%f', '%f', '%f', '%f', '%d')",
                    report.getReportedBy(), report.getDate().toString(), report.getHour(), report.getMinute(), report.getLatitude(), report.getLongitude(), report.getVirusPPM(), report.getContaminantPPM(), report.getCondition().ordinal());
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

    /** @return All reports in database. */
    public Collection<Report> getAllReportsInSystem() {
        Collection<Report> working = new HashSet<>();
        working.addAll(getAllQualityReportsInSysten());
        working.addAll(getAllSourceReportsInSystem());
        return working;
    }
    /**
     * @return All of the waterSourceReports in the database.
     */
    public Collection<WaterSourceReport> getAllSourceReportsInSystem() {
        ArrayList<WaterSourceReport> collection = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM WaterSource");
            while (rs.next()) {
                collection.add(new WaterSourceReport(rs.getInt(1), rs.getString(2), LocalDate.parse(rs.getString(3)),
                        rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7), WaterSourceType.values()[rs.getInt(8)],
                        WaterSourceCondition.values()[rs.getInt(9)]));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return collection;
    }

    /**
     * @return All of the waterqualityReports in the database.
     */
    public Collection<WaterQualityReport> getAllQualityReportsInSysten() {
        ArrayList<WaterQualityReport> collection = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM WaterQuality");
            while (rs.next()) {
                WaterQualityReport temp = new WaterQualityReport();
                temp.setReport_id(rs.getInt(1));
                temp.setReportedBy(rs.getString(2));
                temp.setDate(LocalDate.parse(rs.getString(3)));
                temp.setHour(rs.getInt(4));
                temp.setMinute(rs.getInt(5));
                temp.setLatitude(rs.getDouble(6));
                temp.setLongitude(rs.getDouble(7));
                temp.setVirusPPM(rs.getDouble(8));
                temp.setContaminantPPM(rs.getDouble(9));
                temp.setCondition(Condition.values()[rs.getInt(10)]);
                collection.add(temp);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return collection;
    }

    /**
     * @return All of the users in the database.
     */
    public Collection<Person> getAllUsersInSystem() {
        ArrayList<Person> collection = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                Person temp = new Person();
                temp.setUsername(rs.getString(1));
                temp.setTitle(rs.getString(3));
                temp.setName(rs.getString(4));
                temp.setEmail(rs.getString(5));
                temp.setHomeAddress(new HomeAddress());
                temp.getHomeAddress().setLine1(rs.getString(6));
                temp.getHomeAddress().setLine2(rs.getString(7));
                temp.getHomeAddress().setLine3(rs.getString(8));
                temp.setType(UserType.values()[rs.getInt(9)]);
                collection.add(temp);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return collection;
    }

    /**
     * @param username to check
     * @return if username was already found in database
     */
    private boolean duplicateUN(String username) {
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

    /**
     * @param username to check
     * @param password to check
     * @return Person class holding all credentials about that user, null if not found
     */
    public Person authenticate(String username, String password) {
        Person activeUser = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("Select * FROM User WHERE Username = '%s' AND Password = '%s'",
                    username, password);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                activeUser = new Person();
                activeUser.setHomeAddress(new HomeAddress());
                activeUser.setUsername(rs.getString(1));
                activeUser.setTitle(rs.getString(3));
                activeUser.setName(rs.getString(4));
                activeUser.setEmail(rs.getString(5));
                activeUser.getHomeAddress().setLine1(rs.getString(6));
                activeUser.getHomeAddress().setLine2(rs.getString(7));
                activeUser.getHomeAddress().setLine3(rs.getString(8));
                activeUser.setType(UserType.values()[rs.getInt(9)]);
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return activeUser;
    }

    /**
     * @param user The user to update in the database.
     */
    public void updateUser(Person user) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = "UPDATE User SET "
                    + "Title = '" + user.getTitle() + "', "
                    + "Name = '" + user.getName() + "', "
                    + "Email = '" + user.getEmail() + "', "
                    + "AddressLine1 = '" + user.getHomeAddress().getLine1() + "', "
                    + "AddressLine2 = '" + user.getHomeAddress().getLine2() + "', "
                    + "AddressLine3 = '" + user.getHomeAddress().getLine3() + "' "
                    + "WHERE Username = '" + user.getUsername() + "'";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void deleteReport(String table, int report_id) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("DELETE from %s where ReportNumber=%d;", table, report_id);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void deleteUser(String username) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
            Statement stmt = c.createStatement();
            String sql = String.format("DELETE from User where Username ='%s';", username);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
