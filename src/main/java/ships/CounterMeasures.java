package ships;

public class CounterMeasures extends Part {

    private String bonus;

    public CounterMeasures(String line){
        String[] split = line.split("\\?");
        bonus = split[1];
        partType = "Counter Measures";
    }
    public CounterMeasures(int none){
        super(none);
        partType = "Counter Measures";
        bonus = "-";
    }

    public String getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nBonus To TL: "+bonus+
                toStringBTM();
    }
}
