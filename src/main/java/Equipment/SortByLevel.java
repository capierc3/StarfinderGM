package Equipment;

import java.util.Comparator;

public class SortByLevel implements Comparator<Equipment> {

    public int compare(Equipment a, Equipment b){
        String aString = a.getLevel().replace(",","");
        String bString = b.getLevel().replace(",","");
        Integer alvl = Integer.parseInt(aString);
        Integer blvl = Integer.parseInt(bString);
        return alvl.compareTo(blvl);
    }
}
