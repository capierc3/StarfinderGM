package ArchivesBuilder;

import Equipment.Equipment;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class that holds all the information and logic to create and edit SQLite databases.
 */
public class SQLite {
    /**
     * Method used to connect to the SQLite database
     * @return the connection to the database
     */
    public static Connection connect(String dbName) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:"+dbName+".db";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Read method for database.db sets the values of an inputted equipment to the matching entry in the database.
     * @param equipment equipment object to be filled
     * @param name name of the equipment
     * @throws SQLException
     */
    public static void Read(Equipment equipment, String name) throws SQLException {
        String[] values;
        values = new String[equipment.getKeys().length];
        String sql = "SELECT * FROM "+ equipment.getTableName();
        Connection conn = connect("database");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            if (rs.getString("Name").equalsIgnoreCase(name)){
                for (int i = 0; i <equipment.getKeys().length ; i++) {
                    values[i]= rs.getString(equipment.getKeys()[i]);
                }
            }
        }
        if (values[0] != null) equipment.readSQL(values);
        rs.close();
        stmt.close();
        conn.close();
    }

    public static ArrayList<String> GetNamesByType(String dbName,String table, String type) throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT * FROM "+table;
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            if (rs.getString("Type").equalsIgnoreCase(type)){
                names.add(rs.getString("Name"));
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return names;
    }
    public static ArrayList<String> GetNamesByLevel(String dbName,String table, int levelLow, int levelHigh) throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT * FROM "+table;
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            int itemLevel;
            if (rs.getString("Level").equalsIgnoreCase("—")){
                itemLevel = 0;
            } else itemLevel = Integer.parseInt(rs.getString("Level"));
            if (itemLevel<=levelHigh && itemLevel>=levelLow){
                names.add(rs.getString("Name"));
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return names;
    }
    public static ArrayList<String> getTableNames(String dbName) throws SQLException {
        ArrayList<String> tables = new ArrayList<>();
        Connection conn = connect(dbName);
        DatabaseMetaData md = null;
        try {
            md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                tables.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        return tables;
    }

    /**
     * Creates the main database file.
     * @param dbName name of database to be created
     */
    public static void Build(String dbName){

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
        } catch (Exception e){
            System.out.println(e.getClass().getName()+": "+e.getMessage());
        }
        System.out.println(dbName+" Created");
    }

    /**
     * Creates the tables inside the database file
     * @param tableName Name of table
     * @param sql the sql string to create the table.
     */
    public static void createTable(String dbName, String tableName,String sql){

        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e){
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.out.println(tableName+" ERROR");
        }
        System.out.println(tableName+" table created");
    }

    /**
     * Adds an item to a database.
     * @param dbName the database to add the entry to
     * @param sqls an array of sqls to add
     * @param tableName the table that the sqls need to be added to
     */
    public static void AddRecord(String dbName, ArrayList<String> sqls, String tableName){

        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            for (int i = 0; i <sqls.size() ; i++) {
                String sql = sqls.get(i);
                stmt.executeUpdate(sql);
            }
            stmt.close();
            c.commit();
            c.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.out.println(tableName+" ERROR");
        }
    }






}
