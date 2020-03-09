package Character;

import Equipment.Armor.GearHouse;
import Equipment.Equipment;
import ArchivesBuilder.HardCodedData;
import dice.Dice;

import java.util.ArrayList;

public class NPCBasic extends SFCharacter {

    private double CR;
    private type NPCType;
    private String[] mainArray;
    private String[] attackArray;
    private graft NPCGraft;
    private String graftTraits;
    private String graftAdjustments;


    public enum type {COMBATANT,EXPERT,SPELLCASTER}
    public enum graft {ANIMAL, HUMAN, UNDEAD, VERMIN, ANDROID}

    public NPCBasic(){
        this(new Dice(27).Roll());
    }
    public NPCBasic(double CR){
        this(CR,randomType(),randomGraft());
    }
    public NPCBasic(double CR,type NPCType, graft NPCgraft){
        this.CR = CR;
        this.NPCType = NPCType;
        this.NPCGraft = NPCgraft;
        this.race = this.NPCGraft.name();
        findArrays();
        findGraftInfo();
    }


    //helpers
    private static type randomType(){
        switch (new Dice(3).Roll()){
            case 1:
                return type.SPELLCASTER;
            case 2:
                return type.EXPERT;
            default:
                return type.COMBATANT;
        }
    }
    private static graft randomGraft(){
        switch (new Dice(5).Roll()){
            case 1: return graft.ANDROID;
            case 2: return graft.ANIMAL;
            case 3: return graft.VERMIN;
            case 4: return graft.UNDEAD;
            default: return graft.HUMAN;
        }
    }
    private String[] findArrays(){
        String fullMainString;
        String[] fullMainArray;
        String fullAttackString;
        String[] fullAttackArray;
        if (this.NPCType.equals(type.COMBATANT)) {
            fullMainString = HardCodedData.getCombatantMain();
            fullAttackString = HardCodedData.getCombatantAttack();
        } else if (NPCType.equals(type.EXPERT)) {
            fullMainString = HardCodedData.getExpertMain();
            fullAttackString = HardCodedData.getExpertAttack();
        } else {
            fullMainString = HardCodedData.getSpellCasterMain();
            fullAttackString = HardCodedData.getSpellCasterAttack();
        }
        fullMainArray = fullMainString.split("\n");
        fullAttackArray = fullAttackString.split("\n");
        if (this.CR>=1){
            this.mainArray = fullMainArray[(int)CR+1].split("\\?");
            this.attackArray = fullAttackArray[(int)CR+1].split("\\?");
        } else if (this.CR == 1.0/2){
            this.mainArray =  fullMainArray[0].split("\\?");
            this.attackArray = fullAttackArray[0].split("\\?");
        } else if (this.CR == 1.0/3){
            this.mainArray = fullMainArray[1].split("\\?");
            this.attackArray = fullAttackArray[1].split("\\?");
        }
        return new String[0];
    }
    private void findGraftInfo(){
        switch (this.NPCGraft){
            case ANDROID:
                graftTraits = "darkvision 60 ft., low-light vision,if the NPC is of the android race, it also gains the " +
                        "constructed, flat affect, and upgrade slot racial traits.";
                graftAdjustments = "–2 to all saving throws, +1 to attack rolls.";
            case HUMAN:
                graftTraits =  "None; if the NPC is of the human race, it gains an additional special ability of any type " +
                        "and an additional good skill.";
                graftAdjustments = "+2 to one type of saving throw.";
            case UNDEAD:
                graftTraits = "Darkvision 60 ft., undead immunities, unliving; set Constitution modifier to —.";
                graftAdjustments = "+2 to Will saving throws";
            case ANIMAL:
                graftTraits = "Low-light vision; set Intelligence modifier to –4 or –5.";
                graftAdjustments = "+2 to Fortitude and Reflex saving throws.";
                break;
            case VERMIN:
                graftTraits = " Darkvision 60 ft., mindless; set Intelligence modifier to —";
                graftAdjustments = "+2 to Fortitude saving throws.";
        }
    }
    //TODO Add spellcaster weapons and expert weapons, add type of style to better pick weapons and armor combo
//    private void randomWeapons(){
//        ArrayList<Equipment> weapons;
//        if (this.NPCType.equals(type.COMBATANT)) {
//            switch (new Dice(3).Roll()){
//                //two small arm fighter
//                case 1:
//                    weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getSmallArm());
//                    this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                    this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                    break;
//                case 2:
//                    //Small & Long
//                    weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getSmallArm());
//                    this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                    weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getLongArm());
//                    this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                    break;
//                case 3:
//                    //Melee & other
//                    weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getBasicOneHandedMelee());
//                    weapons.addAll(armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getAdvancedOneHandedMelee()));
//                    this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                    switch (new Dice(3).Roll()){
//                        case 1:
//                            weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getSmallArm());
//                            this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                            break;
//                        case 2:
//                            weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getLongArm());
//                            this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                            break;
//                        case 3:
//                            weapons = armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getAdvancedTwoHandedMelee());
//                            weapons.addAll(armory.getWeaponsByLevelandType(this.CR,this.CR+1,armory.getBasicTwoHandedMelee()));
//                            this.weapons.add(weapons.get(new Dice(weapons.size()).Roll()-1));
//                            break;
//                    }
//                    break;
//            }
//        }
//    }
    private void randomArmor(){
        GearHouse gearHouse = new GearHouse();
        ArrayList<Equipment> armor = gearHouse.getArmorByLevelAndType(this.CR,this.CR+1,gearHouse.getLightArmor());
        this.armor.add(armor.get(new Dice(armor.size()).Roll()-1));
    }
    //getters
    public String[] getMainArray(){return mainArray;}
    public String MainArrayDisplay(){
        StringBuilder sb = new StringBuilder();
        String[] header = {"CR","EAC","KAC","Fort","Ref","Will","Hit Points","Ability DC","Base Spell Dc",
                "Ability Score Modifiers","Special Abilities","Master Skills","Good Skills"};
        for (int i = 0; i < header.length; i++) {
            if (i>0)sb.append("\n");
            sb.append(header[i])
                    .append(": ")
                    .append(mainArray[i]);
        }
        return sb.toString();
    }
    public String AttackArrayDisplay(){
        String[] header = {"CR","Attack Bonus High","Attack Bonus Low","Ranged Damage Energy","Ranged Damage Kinetic",
        "Melee Standard","Melee Three Attacks","Melee Four Attacks"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < header.length; i++) {
            if (i>0)sb.append("\n");
            sb.append(header[i])
                    .append(": ")
                    .append(attackArray[i]);
        }
        return sb.toString();
    }
    public String getType(){return NPCType.name();}
    public String getGraftTraits() {
        return graftTraits;
    }
    public String getGraftAdjustments() {
        return graftAdjustments;
    }
}
