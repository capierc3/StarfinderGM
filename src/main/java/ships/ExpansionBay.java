package ships;

import java.util.ArrayList;

public class ExpansionBay extends Part {

    private String description;

    public ExpansionBay() {}

    public ExpansionBay(ArrayList<String> list) {
        name = list.get(0);
        pcu = list.get(1);
        cost = list.get(2);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return toStringTop() + toStringBTM();
    }

    @Override
    public String getTableName() {
        return "expansion";
    }
}
