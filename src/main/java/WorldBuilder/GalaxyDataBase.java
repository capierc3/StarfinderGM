package WorldBuilder;

import ArchivesBuilder.SQLite;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static ArchivesBuilder.SQLite.connect;

/**
 * Class used to build and maintain the galaxy database.
 */
public class GalaxyDataBase {

    /**Database name and Table names to be used*/
    public static final String dbName = "Galaxy";
    public static final String[] tables = {"Sectors","Systems","Stars","Planets","Bodies"};

    /**
     * Used to build the database if a new database is needed.
     */
    public GalaxyDataBase(){
        File db = new File("Galaxy.db");
        if (db.exists()) db.delete();
        SQLite.build(dbName);
        SQLite.createTable(dbName,tables[0], buildTableSQL(new Sector()));
        SQLite.createTable(dbName,tables[1], buildTableSQL(new StarSystem()));
        SQLite.createTable(dbName,tables[2], buildTableSQL(new Star()));
        SQLite.createTable(dbName,tables[3], buildTableSQL(new Planet()));
        SQLite.createTable(dbName,tables[4],buildTableSQL(new Asteroid()));
    }

    /**
     * Method to take the item used in the table to quickly create a table with its keys and table name
     * @param item the Galaxy Database Item that needs a table created for it.
     * @return SQL string for the create table.
     */
    private String buildTableSQL(GalaxyDataBaseItem item){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(item.getTableNames()).append("(");
        for (int i = 0; i < item.getKeys().length ; i++) {
            sb.append(item.getKeys()[i]);
            if (i == 0) {
                sb.append(" TEXT NOT NULL, ");
            } else if (i == item.getKeys().length - 1) {
                sb.append(" TEXT)");
            } else {
                sb.append(" TEXT, ");
            }
        }
        return sb.toString();
    }

    /**
     * Adds a new entry to the database. If its an sector or system it adds the entries within to the database.
     * @param entry GalaxyDataBaseItem to be added.
     */
    public static void addEntry(GalaxyDataBaseItem entry){
        ArrayList<String> sqls = new ArrayList<>();
        sqls.add(entry.getSQLInsert());
        SQLite.AddRecord(dbName,sqls,entry.getTableNames());
        System.out.println(entry.getTableNames()+": "+entry.getName()+" added");
        if (entry instanceof Sector) {
            Sector sector = (Sector) entry;
            for (int i = 0; i < sector.getGrid().length; i++) {
                for (int j = 0; j < sector.getGrid()[i].length; j++) {
                    if (sector.getGrid()[i][j] != null) {
                        addEntry(sector.getGrid()[i][j]);
                    }
                }
            }
        } else if (entry instanceof StarSystem) {
            StarSystem system = (StarSystem) entry;
            for (Star s:system.getStars()) {
                addEntry(s);
            }
            for (Body b:system.getOrderSystem()){
                addEntry(b);
            }
        }
    }

    /**
     * Searches the database for the name of the item then fills the item with the found values.
     * @param item Item to be filled
     * @param name name of item to be filled
     * @throws SQLException
     */
    public static void readEntry(GalaxyDataBaseItem item, String name) throws SQLException {
        String[] values = new String[item.getKeys().length];
        String sql = "SELECT * FROM "+ item.getTableNames();
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        boolean found = false;
        while (rs.next() || !found){
            if (rs.getString("Name").equalsIgnoreCase(name)){
                for (int i = 0; i <item.getKeys().length ; i++) {
                    values[i]= rs.getString(item.getKeys()[i]);
                    found = true;
                }
            }
        }
        if (values[0] != null) item.readSQL(values);
        rs.close();
        stmt.close();
        conn.close();
    }

    public static ArrayList<StarSystem> findNearBySystems(StarSystem system,int range) throws SQLException {
        ArrayList<StarSystem> systems = new ArrayList<>();
        StarSystem s = new StarSystem();
        String[] values = new String[system.getKeys().length];
        String sql = "SELECT * FROM "+system.getTableNames();
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        double x1 = system.getX();
        double y1 = system.getY();
        double z1 = system.getZ();
        while (rs.next()){
            double x2 = Double.parseDouble(rs.getString("X"));
            double y2 = Double.parseDouble(rs.getString("Y"));
            double z2 = Double.parseDouble(rs.getString("Z"));
            double dist = Math.pow((x2-x1),2)+Math.pow((y2-y1),2)+Math.pow((z2-z1),2);
            dist = Math.sqrt(dist);
            if (dist<=15){
                for (int i = 0; i <system.getKeys().length ; i++) {
                    values[i]= rs.getString(system.getKeys()[i]);
                }
                s.readSQL(values);
                systems.add(s);
                s = new StarSystem();
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return systems;
    }
    public static StarSystem findSystem(String name) throws SQLException {
        StarSystem s = new StarSystem();
        String[] values = new String[s.getKeys().length];
        String sql = "SELECT * FROM "+s.getTableNames();
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            if (rs.getString("Name").equalsIgnoreCase(name)){
                for (int i = 0; i <s.getKeys().length ; i++) {
                    values[i]= rs.getString(s.getKeys()[i]);
                }
            }
        }
        s.readSQL(values);
        rs.close();
        stmt.close();
        conn.close();
        return s;
    }
    public static Star[] findStar(String name, int amount) throws SQLException {
        Star item = new Star();
        String[] values = new String[item.getKeys().length];
        Star[] items = new Star[amount];
        String sql = "SELECT * FROM "+item.getTableNames();
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int j = 0;
        while (rs.next() && j<amount){
            if (rs.getString("System_Name").equalsIgnoreCase(name)){
                for (int i = 0; i <item.getKeys().length ; i++) {
                    values[i]= rs.getString(item.getKeys()[i]);
                }
                item.readSQL(values);
                items[j] = item;
                j++;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return items;
    }
    public static ArrayList<Planet> findPlanets(String name, int amount) throws SQLException {
        Planet item = new Planet();
        String[] values = new String[item.getKeys().length];
        ArrayList<Planet> items = new ArrayList<>();
        String sql = "SELECT * FROM "+item.getTableNames();
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()||amount>0){
            if (rs.getString("System_Name").contains(name)){
                for (int i = 0; i <item.getKeys().length ; i++) {
                    values[i]= rs.getString(item.getKeys()[i]);
                }
                item.readSQL(values);
                items.add(item);
                amount--;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return items;
    }
    public static ArrayList<Body> findBodies(String name, int amount) throws SQLException {
        Body item = new Asteroid();
        String[] values = new String[item.getKeys().length];
        ArrayList<Body> items = new ArrayList<>();
        String sql = "SELECT * FROM "+item.getTableNames();
        Connection conn = connect(dbName);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()||amount>0){
            if (rs.getString("System_Name").contains(name)){
                for (int i = 0; i <item.getKeys().length ; i++) {
                    values[i]= rs.getString(item.getKeys()[i]);
                }
                item.readSQL(values);
                items.add(item);
                amount--;
            }
        }
        rs.close();
        stmt.close();
        conn.close();
        return items;
    }


}
