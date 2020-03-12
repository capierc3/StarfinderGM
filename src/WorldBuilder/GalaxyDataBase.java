package WorldBuilder;

import ArchivesBuilder.SQLite;
import Equipment.Armor.ArmorUpgrade;
import Equipment.Armor.Shields;
import Equipment.Equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

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
        SQLite.Build(dbName);
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


}
