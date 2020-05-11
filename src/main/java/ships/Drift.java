package ships;

import java.util.ArrayList;

public class Drift extends Part {

    private String rating;
    private String minPCU;
    private String maxSize;

    public Drift(ArrayList<String> list) {
        name = list.get(0);
        rating = list.get(1);
        minPCU = list.get(2);
        maxSize = list.get(3);
        cost = list.get(4);
    }

    public Drift() {

    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nRating: " + rating
                + "\nMin PCU: " + minPCU
                + "\nMax Siz: " + maxSize
                + "\nCost: " + cost;
    }

    @Override
    public String getTableName() {
        return "drift";
    }
}
