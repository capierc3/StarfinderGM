package ships;

import java.util.ArrayList;

public class CounterMeasures extends Part {

    private String bonus;

    public CounterMeasures(){

    }

    public CounterMeasures(ArrayList<String> list) {
        name = list.get(0);
        bonus = list.get(1);
        pcu = list.get(2);
        cost = list.get(3);
    }

    public String getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return toStringTop()
                + "\nBonus To TL: " + bonus
                + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "countermeasures";
    }
}
