package ships;

import java.util.ArrayList;

public class ShipScales implements ShipComponent {

    private String size;
    private String length;
    private String weight;
    private String modifier;

    public ShipScales(String[] values) {
        size = values[0];
        length = values[1];
        weight = values[2];
        modifier = values[3];
    }

    public String toString() {
        return "Size: " + size
                + "\nLength: " + length
                + "\nWeight: " + weight
                + "\nAC & TL Modifier: " + modifier;
    }

    @Override
    public String getTableName() {
        return null;
    }

    public static ArrayList<ShipScales> getScales() {
        String[][] valueList = {
                {"Tiny","20–60 ft.","3–20 tons","+2"},
                {"Small","60 – 120 ft.","20 – 40 tons","+1"},
                {"Medium","120 – 300 ft.","40 – 150 tons","+0"},
                {"Large","300 – 800 ft.","150 – 420 tons","–1"},
                {"Huge","800 – 2,000 ft.","420 – 1,200 tons","–2"},
                {"Gargantuan","2,000 – 15,000 ft.","1,200 – 8,000 tons","–4"},
                {"Colossal","Over 15,000 ft.","Over 8,000 tons","–8"}
        };
        ArrayList<ShipScales> values = new ArrayList<>();
        for (String[] value : valueList) {
            values.add(new ShipScales(value));
        }
        return values;
    }

}
