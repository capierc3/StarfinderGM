package ships;

import static ArchivesBuilder.SQLite.connect;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;


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

    //TODO BAD FIX! based on import order to database!
    public static ArrayList<ShipWeapon> getWeaponsByType(String wType) {
        try {
            ArrayList<ArrayList<String>> weaponsStrings = getItemStrings(ShipTables.weapons.toString());
            ArrayList<ShipWeapon> weapons = new ArrayList<>();
            int start;
            switch (wType) {
                case "Light":
                    start = 0;
                    break;
                case "Heavy":
                    start = 1;
                    break;
                case "Capital":
                    start = 2;
                    break;
                default:
                    start = 3;
                    break;
            }
            int pos = 0;
            boolean sw = true;
            for (ArrayList<String> weaponsString : weaponsStrings) {
                boolean temp = weaponsString.get(1).equalsIgnoreCase("Direct-Fire");
                if (!sw && temp) {
                   pos++;
                }
                sw = temp;
                if (pos == start) {
                    weapons.add(new ShipWeapon(weaponsString));
                } else if (pos > start) {
                    return weapons;
                }
            }
            return weapons;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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


    private StarShip ship;
    private ArrayList<Part> shoppingCart;
    private int pcu;
    private int bp;

//TODO: Ship building and shopping cart
    /**
     * Starts a new ship builder
     * @param ship Starship object
     */
    public void newBuild(StarShip ship) {
        this.ship = ship;
        shoppingCart = new ArrayList<>();
        bp = Integer.parseInt(ship.getTier().getBuildPoints());
        pcu = 0;
    }

    public void buyPart(Part part) {
        shoppingCart.add(part);
        try {
            bp -= Integer.parseInt(part.cost.replaceAll(" ",""));
        } catch (NumberFormatException e) {
            if (!part.cost.contains("—")) {
                if (part.cost.contains( "x size category")) {
                    bp -= findSizePrice(part.cost);
                } else {
                    System.out.println("DEBUG: (COST BUY) " + part.cost);
                }
            }
        }
        if (part instanceof PowerCore) {
            pcu += Integer.parseInt(part.pcu);
        } else {
            try {
                pcu -= Integer.parseInt(part.pcu);
            } catch (NumberFormatException e) {
                if (part.pcu != null && !part.pcu.contains("-")) {
                    System.out.println("DEBUG: (PCU BUY) " + part.pcu);
                }
            }
        }
    }

    public void returnPart(Part part) {
        shoppingCart.remove(part);
        if (part instanceof PowerCore) {
            pcu -= Integer.parseInt(part.pcu);
        } else {
            try {
                pcu += Integer.parseInt(part.pcu);
            } catch (NumberFormatException e) {
                if (part.pcu != null && !part.pcu.contains("-")) {
                    System.out.println("DEBUG: (PCU RETURN) " + part.pcu);
                }
            }
        }
        try {
            bp += Integer.parseInt(part.cost.replaceAll(" ",""));
        } catch (NumberFormatException e) {
            if (!part.cost.contains("—")) {
                if (part.cost.contains( "x size category")) {
                    bp += findSizePrice(part.cost);
                } else {
                    System.out.println("DEBUG: (COST RETURN) " + part.cost);
                }
            }
        }

    }

    public int getBuildPoints() {
        return bp;
    }

    public int getPcuLeft() {
        return pcu;
    }

    public ArrayList<Part> getCart() {
        return shoppingCart;
    }

    public int findSizePrice(String cost) {
        int times = Integer.parseInt(cost.split("x")[0].replaceAll("[^0-9]",""));
        switch (ship.getFrame().getSize()) {
            case " Tiny":
                return times;
            case " Small":
                return times * 2;
            case " Medium":
                return times * 3;
            case " Large":
                return times * 4;
            case " Huge":
                return times * 5;
            case " Gargantuan":
                return times * 6;
            case " Colossal":
                return times * 7;
            case " Supercolossal":
                return times * 8;
            default:
                return 0;
        }
    }

}
