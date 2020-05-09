package ships;

public class ShipWeapon extends Part {

    private String range;
    private String speed;
    private String dmg;
    private String special;
    private String type;

    public ShipWeapon(String type, String line){

        String[] split = line.split("\\?");
        range = split[1];
        speed = split[2];
        dmg = split[3];
        pcu = split[4];
        cost = split[5];
        special = split[6];
        this.type = type;
        partType = type+" Weapon";
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nType: "+type+
                "\nRange: "+range+
                "\nSpeed: "+speed+
                "\nSpecial: "+special+
                toStringBTM();
    }
}
