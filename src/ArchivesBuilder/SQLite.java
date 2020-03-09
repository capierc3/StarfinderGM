package ArchivesBuilder;

import Equipment.Equipment;

import java.sql.*;
import java.util.ArrayList;

public class SQLite {

    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:database"+".db";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            //System.out.println("Connection established.");
        } catch (SQLException e) {
            //System.out.println("1 "+e.getMessage());
        } catch (ClassNotFoundException e) {
            //System.out.println("2");
            e.printStackTrace();
        }
        return conn;
    }

    public static void Read( Equipment equipment, String table, String name, String[] keys) throws SQLException {
        String[] values;
        values = new String[keys.length];
        String sql = "SELECT * FROM "+table;
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            if (rs.getString("Name").equalsIgnoreCase(name)){
                for (int i = 0; i <keys.length ; i++) {
                    values[i]= rs.getString(keys[i]);
                }
            }
        }
        if (values[0] != null) equipment.readSQL(values);
        rs.close();
        stmt.close();
        conn.close();
    }
    public static ArrayList<String> GetNamesByType(String table, String type) throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT * FROM "+table;
        Connection conn = connect();
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
    public static ArrayList<String> GetNamesByLevel(String table, int levelLow, int levelHigh) throws SQLException {


        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT * FROM "+table;
        Connection conn = connect();
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
    public static ArrayList<String> getTableNames() throws SQLException {
        ArrayList<String> tables = new ArrayList<>();
        Connection conn = connect();
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

    public static void Build(){

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (Exception e){
            System.out.println(e.getClass().getName()+": "+e.getMessage());
        }
        System.out.println("Database Created");
    }

    public static void createTable(String tableName,String sql){

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");

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

    public static void AddRecord(ArrayList<String> sqls, String tableName){

        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
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
