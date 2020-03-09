package Equipment.Augmentations;

import Equipment.Equipment;
import Equipment.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SurgeryCenter {

    private ArrayList<Augmentations> augmentations;

    public SurgeryCenter(){
        try{
            this . augmentations = findAugmentations();
        } catch (FileNotFoundException e){
            System.out.println("SurgeryCenter txt not found.");
        }
        augmentations.add(new Augmentations("MK1 +2?3?1,400?Synaptic Accelerator?"));
        augmentations.add(new Augmentations("MK2 +4?7?6,500?Synaptic Accelerator?"));
        augmentations.add(new Augmentations("MK3 +6?14?75,000?Synaptic Accelerator?"));
    }

    private ArrayList<Augmentations> findAugmentations() throws FileNotFoundException {
        Scanner in = new Scanner(new File("txtFiles/augmentations.txt"));
        ArrayList<Augmentations> augs = new ArrayList<>();
        String line;
        while (in.hasNext()) {
            line = in.nextLine();
            if (line.equalsIgnoreCase("3")) {
                line = in.nextLine();
                while (!line.equalsIgnoreCase("")) {
                    augs.add(new Augmentations(line));
                    line = in.nextLine();
                }
                return augs;
            }
        }
        return augs;
    }
    public ArrayList<Equipment> getAugmentationsByLevelAndType(double low, double high, ArrayList<Equipment> list){
        list.sort(new SortByLevel());
        ArrayList<Equipment> equipment = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLevelInt()<=high && list.get(i).getLevelInt()>=low){
                equipment.add(list.get(i));
            } else if (list.get(i).getLevelInt()>high) {
                return equipment;
            }
        }
        return equipment;
    }
    public ArrayList<Augmentations> getAugmentations() {
        return augmentations;
    }
}
