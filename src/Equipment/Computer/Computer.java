package Equipment.Computer;

import Equipment.Equipment;
import java.util.ArrayList;

public class Computer implements Equipment {

    private String tier;
    private ArrayList<ComputerModule> modules;
    private String price;
    private Integer hackDC;

    public Computer(String line){
        String[] split = line.split("\\?");
        tier = split[0];
        price = split[1];
        String baseDC = split[2];
        modules = new ArrayList<>();
        hackDC = Integer.parseInt(baseDC);
    }

    public void addModule(ComputerModule module){
        modules.add(module);
        if (module.getClass().equals(SecurityModule.class)){
            SecurityModule mod = (SecurityModule) module;
            hackDC = hackDC+Integer.parseInt(mod.getDcIncrease().replace("+",""));
        }
    }

    public ArrayList<ComputerModule> getModules() {
        return modules;
    }

    public String getPrice() {
        return price;
    }
    public String getLevel() {
        return tier;
    }
    public String getType() {
        return "Computer";
    }
    public String toString(){
        return "Tier: "+tier+
                "\nHack DC: "+hackDC+
                "\nPrice: "+price+
                "\nAmount of Modules: "+modules.size();
    }
    public Integer getLevelInt() {
        return Integer.parseInt(tier);
    }
    public Integer getPriceInt() {
        return Integer.parseInt(price);
    }

    @Override
    public void readSQL(String[] values) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String[] getKeys() {
        return new String[0];
    }


}


