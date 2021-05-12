import Equipment.Computer.MicroCenter;
import Equipment.EquipmentDatabaseBuilder;


import static runables.Test.buildComputer;

public class TestingClass {

    public static void main(String[] args) {

        MicroCenter microCenter = new MicroCenter();
        String name = "My PC";
        buildComputer(microCenter,name);


    }



}
