package Equipment;

public interface Equipment {


    String getPrice();
    String getLevel();
    String getType();
    String toString();
    Integer getLevelInt();
    Integer getPriceInt();
    void readSQL(String[] values);
    String getName();
    String getTableName();
    String[] getKeys();



}
