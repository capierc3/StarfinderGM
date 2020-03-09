package Character;


import ArchivesBuilder.HardCodedData;
import dice.Dice;


import java.util.*;

public class NPCHero extends SFCharacter {

    private final String theme;
    private final String $class;
    private int[] abilityScores;
    private HashMap<String,Integer> skills;
    private Integer initiative;
    private Integer stamina;
    private Integer[] saves;
    private Integer[] attacks;

    public NPCHero(){
        skills = emptySkillList();
        theme = newTheme();
        $class = newClass();
        abilityScores = newAbilityScores($class);
        initiative = getMod(abilityScores[1]);


    }

    //helper methods
    private HashMap<String,Integer> emptySkillList(){
        String[] skillsData = HardCodedData.getSkills();
        HashMap<String,Integer> skills = new HashMap<>();
        for (String s : skillsData) {
            String[] split = s.split(":");
            skills.put(split[0],0);
        }
        return skills;
    }
    private String newTheme(){
        String[] themes = HardCodedData.getThemes();
        Dice die = new Dice(themes.length);
        return themes[die.Roll()-1].split(":")[0];
    }
    private String newRace(){
        Dice d100 = new Dice(100);
        if (d100.Roll()>80){
            return "Android";
        } else return "Human";
    }
    private String newClass(){
        String[] classes = HardCodedData.getClasses();
        Dice dice = new Dice(classes.length);
        return classes[dice.Roll()-1].split(":")[0];
    }
    private int[] newAbilityScores(String $class){
        int str=0,dex=0,con=0,Int=0,wis=0,cha=0;
        Dice d6 = new Dice(6);
        LinkedList<Integer> rolls = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            int[] rollAmounts = {d6.Roll(),d6.Roll(),d6.Roll(),d6.Roll()};
            Arrays.sort(rollAmounts);
            rolls.add(rollAmounts[1]+rollAmounts[2]+rollAmounts[3]);
        }
        Collections.sort(rolls);
        String classMain = "";
        for (String s: HardCodedData.getClasses()) {
            if (s.contains($class)){
                classMain = s.split(":")[2];
                break;
            }
        }
        if (classMain.equalsIgnoreCase("Cha")){
            cha = rolls.removeLast();
        } else if (classMain.equalsIgnoreCase("Dex")){
            dex = rolls.removeLast();
        } else if (classMain.equalsIgnoreCase("Wis")){
            wis = rolls.removeLast();
        } else if (classMain.equalsIgnoreCase("Int")){
            Int = rolls.removeLast();
        } else if (classMain.equalsIgnoreCase("Str,Dex")){
            if (d6.Roll()>3){
                str = rolls.removeLast();
            } else {
                dex = rolls.removeLast();
            }
        }
        int[] finalStats = {str,dex,con,Int,wis,cha};
        Collections.shuffle(rolls);
        for (int i = 0; i < finalStats.length; i++) {
            if (finalStats[i] == 0) {
                finalStats[i] = rolls.remove();
            }
        }
        return finalStats;
    }

    //getters
    public HashMap<String, Integer> getSkills() {
        return skills;
    }
    public String getTheme(){return theme;}
    public String get$class() {
        return $class;
    }
    public int[] getAbilityScores() {
        return abilityScores;
    }
    public int getMod(int ability){
        int mod = 0;
        int x;
        if (ability<10){
            x= 10-ability;
            mod = (int) (-1*(Math.ceil(x/2.0)));
        } else if (ability<20){
            mod = (ability-10)/2;
        } else {
            x = ability-20;
            switch (x){
                case 0: case 1:
                    mod = 5;
                    break;
                case 2: case 3:
                    mod = 6;
                    break;
                case 4: case 5:
                    mod = 7;
                    break;
                case 6:
                    mod = 8;
                    break;
            }
        }
        return mod;
    }
    public Integer getStamina() {
        return stamina;
    }
    public Integer getInitiative() {
        return initiative;
    }
    public Integer[] getSaves() {
        return saves;
    }
    public Integer[] getAttacks() {
        return attacks;
    }
}
