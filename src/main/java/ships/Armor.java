package ships;

public class Armor extends Part {

    private String bonusAc;
    private String special;

    public Armor(String line) {
        super(line);
        bonusAc = line.split("\\?")[1];
        special = line.split("\\?")[2];
        cost = line.split("\\?")[3];
        pcu = "-";
    }

    public String getBonusAc() {
        return bonusAc;
    }

    public String getSpecial() {
        return special;
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nBonus to AC: "+ bonusAc+
                "\nSpecial: "+special+
                toStringBTM();
    }
}
