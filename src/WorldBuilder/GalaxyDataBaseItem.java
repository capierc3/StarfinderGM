package WorldBuilder;

public interface GalaxyDataBaseItem {

    String getTableNames();
    String[] getKeys();
    String getSQLInsert();
    String getName();
    void readSQL(String[] values);



}
