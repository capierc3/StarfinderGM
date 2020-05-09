package ships;

import java.sql.*;
import java.util.ArrayList;

import static ArchivesBuilder.SQLite.connect;

/**
 * Class that holds the takes the information on the ship parts from the database and stores them.
 *
 * @author Chase
 */
public class ShipYardNew {

    private static final String databaseName = "ships";

    /**
     * returns the names of the items in the table.
     * @param tableName String of the table to read from
     * @return ArrayList of strings
     * @throws SQLException e
     */
    public static ArrayList<String> getNames(String tableName) throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        Connection conn = connect(databaseName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            names.add(rs.getString(1));
        }
        rs.close();
        stmt.close();
        conn.close();
        return names;
    }

    /**
     * returns a list of all the parts of the inputted types from the database.
     * @param part New object of the wanted part type.
     * @return List of parts
     */
    public static ArrayList<Part> getParts(Part part) {
        try {
            if (part instanceof Armor) {
                return getArmors();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<Part> getArmors() throws SQLException {
        ArrayList<ArrayList<String>> armorValues = getItemStrings("armor");
        ArrayList<Part> armors = new ArrayList<>();
        for (ArrayList<String> list : armorValues) {
            armors.add(new Armor(list));
        }
        return armors;
    }

    private static ArrayList<ArrayList<String>> getItemStrings(String tableName) throws SQLException {
        ArrayList<ArrayList<String>> items = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        Connection conn = connect(databaseName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        while (rs.next()) {
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                values.add(rs.getString(i + 1));
            }
            items.add(values);
            values = new ArrayList<>();
        }
        rs.close();
        stmt.close();
        conn.close();
        return items;
    }
}
