package Equipment.Computer;

public class ShockModule extends ComputerModule {

    private String dc;
    private String damage;

    public ShockModule(String feature, String dc, String dmg, String price, String type) {
        super(feature, price, type);
        this.dc = dc;
        this.damage = dmg;

    }

    public String toString() {
        return "Type: " + type +
                "\nFeature: " + feature +
                "\nPrice: " + price +
                "\nDC: " + dc +
                "\nDamage: " + damage;
    }
}
