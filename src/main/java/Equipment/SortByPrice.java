package Equipment;

import java.util.Comparator;

public class SortByPrice implements Comparator<Equipment> {

    public int compare(Equipment a, Equipment b){
        String aPriceString = a.getPrice().replace(",","");
        String bPriceString = b.getPrice().replace(",","");
        Integer aPrice = Integer.parseInt(aPriceString);
        Integer bPrice = Integer.parseInt(bPriceString);
        return aPrice.compareTo(bPrice);
    }
}
