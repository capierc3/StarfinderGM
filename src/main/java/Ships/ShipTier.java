package Ships;

public class ShipTier implements ShipComponent {

    private String tier;
    private String buildPoints;
    private String special;

    public ShipTier(String line){
        String[] split = line.split("\\?");
        tier = split[0];
        buildPoints = split[1];
        special = split[2];
    }

    @Override
    public String toString() {
        return tier;
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
}
