package Equipment.Computer;

import java.util.ArrayList;

public class Computer {

    private int tier;
    private ArrayList<ComputerModule> modules;
    private int price;
    private Integer hackDC;

    public Computer(int tierNum){
        tier = tierNum;
        price = getTier(tier)[0];
        hackDC = getTier(tier)[1];
        modules = new ArrayList<>();
    }

    public void addModule(ComputerModule module){
        modules.add(module);
        String price = module.getPrice();
        String value = price.split(" ")[0];
        if (value.contains("%")) {
            //TODO
        } else if (value.equalsIgnoreCase("Varies")) {
            //TODO
        } else {
            this.price += Integer.parseInt(value);
        }
    }

    public ArrayList<ComputerModule> getModules() {
        return modules;
    }

    public int getPrice() {
        return price;
    }
    public int getLevel() {
        return tier;
    }
    public String toString(){
        return "Tier: "+tier+
                "\nHack DC: "+hackDC+
                "\nPrice: "+price+
                "\nAmount of Modules: "+modules.size();
    }

    public static int[] getTier(int tierNum) {
        int[] price = {50, 250, 1250, 5000, 10000, 20000, 40000, 80000, 160000, 320000};
        int[] dc = {17,21,25,29,33,37,41,45,49,53};
        return new int[] {price[tierNum - 1], dc[tierNum - 1]};
    }


}


