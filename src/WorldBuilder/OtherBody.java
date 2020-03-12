package WorldBuilder;

/**
 * Class to be used as a Holder for Bodies in the system That aren't main types or needed of own class
 * @author Chase Pierce
 * @version 1.0
 */
public class OtherBody extends Body {
    /**
     * Constructor that sets the name and type of the body.
     * @param type String
     * @param name String
     */
    public OtherBody(String type,String name){
        this.type = type;
        this.name = name+"'s "+type;
        systemName = name;
    }

    @Override
    public String getTableNames() {
        return tableName;
    }

    @Override
    public String[] getKeys() {
        return keys;
    }
}
