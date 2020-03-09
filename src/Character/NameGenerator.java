package Character;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NameGenerator {

    public static String getFName(String gender){
        if (gender.equalsIgnoreCase("Male")){
            return getMale();
        } else return getFemale();
    }

    public static String getLName(){
        File inputFile = new File("txtFiles/last-name.txt");
        String name;
        Random rand = new Random();
        ArrayList<String> lNames = new ArrayList<>();
        try{
            Scanner input = new Scanner(inputFile);
            while(input.hasNextLine()){
                name = input.nextLine();
                lNames.add(name);
            }
        } catch (Exception e) {
            System.out.println("File was not found");
        }
        name = lNames.get(rand.nextInt(lNames.size()));


        return name;
    }

    private static String getMale(){
        File inputFile = new File("txtFiles/male-first.txt");
        String name= "Chase";
        Random rand = new Random();
        ArrayList<String> fNames = new ArrayList<>();
        try{
            Scanner input = new Scanner(inputFile);
            while(input.hasNextLine()){
                name = input.nextLine();
                fNames.add(name);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        name = fNames.get(rand.nextInt(fNames.size()));
        String fName = name.replaceAll("\\s+$", "");
        return fName;
    }

    private static String getFemale(){
        File inputFile = new File("txtFiles/female-first.txt");
        String name;
        Random rand = new Random();
        ArrayList<String> fNames = new ArrayList<>();
        try{
            Scanner input = new Scanner(inputFile);
            while(input.hasNextLine()){
                name = input.nextLine();
                fNames.add(name);
            }
        } catch (Exception e) {
            System.out.println("no file...");
        }
        name = fNames.get(rand.nextInt(fNames.size()));
        String fName = name.replaceAll("\\s+$", "");
        return fName;
    }


}
