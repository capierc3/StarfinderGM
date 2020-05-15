package ships;

import java.util.ArrayList;

public class ShipTier implements ShipComponent {

    private String tier;
    private String buildPoints;
    private String special;

    public ShipTier(String[] values) {
        tier = values[0];
        buildPoints = values[1];
        special = values[2];
    }

    @Override
    public String toString() {
        return tier;
    }

    @Override
    public String getTableName() {
        return null;
    }

    public String getTier() {
        return tier;
    }

    public String getBuildPoints() {
        return buildPoints;
    }

    public String getSpecial() {
        return special;
    }

    public static ArrayList<ShipTier> getTiers() {
        String[][] values = {
                {"1/4","25","—"},
                {"1/3","30","—"},
                {"1/2","40","—"},
                {"1","55","—"},
                {"2","75","—"},
                {"3","95","—"},
                {"4","115","HP increase"},
                {"5","135","—"},
                {"6","155","—"},
                {"7","180","—"},
                {"8","205","HP increase"},
                {"9","230","—"},
                {"10","270","—"},
                {"11","310","—"},
                {"12","350","HP increase"},
                {"13","400","—"},
                {"14","450","—"},
                {"15","500","—"},
                {"16","600","HP increase"},
                {"17","700","—"},
                {"18","800","—"},
                {"19","900","—"},
                {"20","1,000","HP increase"},
        };
        ArrayList<ShipTier> tiers = new ArrayList<>();
        for (String[] valueList : values) {
            tiers.add(new ShipTier(valueList));
        }
        return tiers;
    }
}
