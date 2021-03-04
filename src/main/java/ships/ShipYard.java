package ships;

import static ArchivesBuilder.SQLite.connect;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;


/**
 * Class that holds the takes the information on the ship parts from the database and stores them.
 *
 * @author Chase
 */
public class ShipYard {

    private static final String databaseName = "ships";
    public enum ShipTables {
        armor,
        baseframes,
        computers,
        cores,
        countermeasures,
        drift,
        expansion,
        other,
        properties,
        quarters,
        security,
        sensors,
        shields,
        thrusters,
        weapons
    }

    private StarShip ship;

    /**
     * returns the names of the items in the table.
     * @param tableName String of the table to read from
     * @return ArrayList of strings
     * @throws SQLException e
     */
    public static ArrayList<String> getNames(String tableName) {
        try {
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
        } catch (SQLException e) {
            System.out.println("getNames() error");
            return new ArrayList<>();
        }
    }

    /**
     * returns a list of all the parts of the inputted types from the database.
     * @param part New object of the wanted part type.
     * @return List of parts
     */
    public static ArrayList<Part> getParts(Part part) {
        ArrayList<ArrayList<String>> partValues;
        ArrayList<Part> parts = new ArrayList<>();
        try {
            partValues = getItemStrings(part.getTableName());
            for (ArrayList<String> list : partValues) {
                parts.add(part.getClass().getConstructor(ArrayList.class).newInstance(list));
            }
        } catch (SQLException | NoSuchMethodException
                | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        return parts;
    }

    /**
     * TODO
     * @return
     */
    public static ArrayList<Makes> getMakes() {
        return new ArrayList<>();
    }

    /**
     * Returns a list of different lists of strings for each row in the table.
     * @param tableName String of the table to be pulled from
     * @return ArrayList of ArrayLists of strings.
     * @throws SQLException e
     */
    private static ArrayList<ArrayList<String>> getItemStrings(String tableName)
            throws SQLException {
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


//TODO: Ship building and shopping cart
    /**
     * Starts a new ship builder
     * @param ship Starship object
     */
    public void newBuild(StarShip ship) {
        this.ship = ship;
    }

    public void buyPart(Part part) {

    }

    public void returnPart(Part part) {

    }

    public int getBuildPoints() {
        return 0;
    }

    public int getPcuLeft() {
        return 0;
    }

    public ArrayList<Part> getCart() {
        return new ArrayList<>();
    }

}
