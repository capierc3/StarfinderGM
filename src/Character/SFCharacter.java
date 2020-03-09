package Character;

import Equipment.Armor.Armor;
import Equipment.Augmentations.Augmentations;
import Equipment.Equipment;
import Equipment.Weapons.Weapon;
import dice.Dice;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SFCharacter {

    enum Attitudes {HELPFUL, FRIENDLY, INDIFFERENT, UNFRIENDLY, HOSTILE}
    private String gender;
    private String fName;
    private String lName;
    private String  attitude;
    protected String race;
    protected Integer EAC;
    protected Integer KAC;
    protected Integer HP;
    protected ArrayList<Equipment> weapons;
    protected ArrayList<Equipment> items;
    protected ArrayList<Equipment> armor;
    protected HashMap<Augmentations.systems,Augmentations> augmentations;


    public SFCharacter(){
        this.gender = GetGender();
        this.fName = NameGenerator.getFName(this.gender);
        this.lName = NameGenerator.getLName();
        this.race = newRace();
        this.EAC = 10;
        this.KAC = 10;
        this.HP = 4;
        this.attitude = newAttitude();
        this.weapons = new ArrayList<Equipment>();
        this.items = new ArrayList<Equipment>();
        this.armor = new ArrayList<Equipment>();
        this.augmentations = new HashMap<>();
    }
    //getters
    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }
    public String getGender() {
        return gender;
    }
    public String getRace(){return race;}
    public Integer getKAC() {
        return KAC;
    }
    public Integer getEAC() {
        return EAC;
    }
    public Integer getHP() {
        return HP;
    }
    public String getAttitude() {
        return attitude;
    }

    public ArrayList<Equipment> getWeapons() {
        return weapons;
    }
    public ArrayList<Equipment> getItems() {
        return items;
    }
    public ArrayList<Equipment> getArmor() {
        return armor;
    }
    public HashMap<Augmentations.systems,Augmentations> getAugmentations(){
        return augmentations;
    }
    public void addAugmentations(Augmentations.systems system,Augmentations aug){
        augmentations.put(system,aug);
    }
    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }
    public void addItem(Equipment equipment){items.add(equipment);}
    public void addArmor(Armor armor){this.armor.add(armor);}

    //Helpers
    private String GetGender(){
        Dice d100 = new Dice(100);
        String gender = "random";
        int num = d100.Roll();
        if(num >=50){
            gender = "Male";
        } else {
            gender = "Female";
        }
        return gender;
    }
    private String newRace(){
        Dice d100 = new Dice(100);
        if (d100.Roll()>80){
            return "Android";
        } else return "Human";
    }
    private String newAttitude(){
        Dice d100 = new Dice(100);
        int roll = d100.Roll();
        if (roll>90){
            return Attitudes.HOSTILE.name();
        } else if (roll>70){
            return Attitudes.UNFRIENDLY.name();
        } else if (roll>35){
            return Attitudes.INDIFFERENT.name();
        } else if (roll>15){
            return Attitudes.FRIENDLY.name();
        } else {
            return Attitudes.HELPFUL.name();
        }
    }


}
