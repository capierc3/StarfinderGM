package ArchivesBuilder;

import Equipment.Armor.Shields;
import Equipment.Equipment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArchivesToSql {
    private Scanner in;
    private String line;

    /**
     * main method that creates a new DataBase and loads in the tables and values provided in the Archives folder.
     */
    public ArchivesToSql() {
        SQLite.Build();
        addTables();
        try {
            FillTable("Weapons");
            FillTable("Armor");
            FillTable("Augmentations");
            FillTable("Other Items");
            FillTable("Equipment");
            addAndFillMisc();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the tables and values to the database.
     */
    private void addTables() {
        //Weapon Table
        String weaponTable = "CREATE TABLE Weapons" +
                "(Name                 TEXT NOT NULL," +
                " Type                 TEXT," +
                " Category             TEXT," +
                " Level                TEXT," +
                " Price                TEXT," +
                " Damage               TEXT," +
                " Range                TEXT," +
                " Critical             TEXT," +
                " Capacity             TEXT," +
                " Usage                TEXT," +
                " Bulk                 TEXT," +
                " Special              TEXT," +
                " ID                   TEXT)";
        SQLite.createTable("Weapons", weaponTable);
        //Armor table
        String armorTable = "CREATE TABLE Armor" +
                "(Name                TEXT NOT NULL," +
                " Type                TEXT," +
                " Level               TEXT," +
                " Price               TEXT," +
                " EAC_Bonus           TEXT," +
                " KAC_Bonus           TEXT," +
                " Maximum_Dex_Bonus   TEXT," +
                " Armor_Check_Penalty TEXT," +
                " Speed_Adjustment    TEXT," +
                " Upgrade_Slots       TEXT," +
                " Bulk                TEXT," +
                " ID                  TEXT)";
        SQLite.createTable("Armor", armorTable);
        //Augmentation table
        String augTable = "CREATE TABLE Augmentations" +
                "(Name               TEXT NOT NULL," +
                " Type               TEXT," +
                " Level              TEXT," +
                " Price              TEXT," +
                " System             TEXT," +
                " Ability            TEXT," +
                " ID                 TEXT)";
        SQLite.createTable("Augmentations", augTable);
        //Other Items table
        String itemsTable = "CREATE TABLE Items"+
                "(Name               TEXT NOT NULL," +
                " Type               TEXT," +
                " Category           TEXT," +
                " Level              TEXT," +
                " Price              TEXT," +
                " Bulk               TEXT," +
                " ID                 TEXT)";
        SQLite.createTable("Items",itemsTable);
    }

    /**
     * Creates arrays for the files saved in Archives/Weapons to be used in the fillWeaponTable method
     *
     * @throws FileNotFoundException thrown if file is not found from fillWeaponTable method.
     */
    private void FillTable(String folder) throws FileNotFoundException {
        //Find Files
        File folderMain = new File("Archives/" + folder);
        File[] folderFiles = folderMain.listFiles();
        assert folderFiles != null;
        //Get type names
        String[] typeBases = new String[folderFiles.length];
        for (int i = 0; i < folderFiles.length; i++) {
            typeBases[i] = folderFiles[i].getName().replace(".txt", "") + ":";
        }
        //Make id Codes
        String[] idCode = new String[folderFiles.length];
        for (int i = 0; i < folderFiles.length; i++) {
            String[] name = typeBases[i].split(" ");
            StringBuilder sb = new StringBuilder();
            for (String s : name) {
                sb.append(s.charAt(0));
            }
            idCode[i] = sb.toString();
        }
        //Add to database
        for (int i = 0; i < folderFiles.length; i++) {
            if (folder.equalsIgnoreCase("Weapons")) {
                fillWeaponTable(folderFiles[i], typeBases[i], idCode[i]);
            } else if (folder.equalsIgnoreCase("Armor")) {
                fillArmorTable(folderFiles[i], typeBases[i], idCode[i]);
            } else if (folder.equalsIgnoreCase("Augmentations")) {
                fillAugTable(folderFiles[i], typeBases[i], idCode[i]);
            } else if (folder.equalsIgnoreCase("Other Items")){
                fillOtherTable(folderFiles[i],typeBases[i],idCode[i]);
            } else if (folder.equalsIgnoreCase("Equipment")){
                fillEquipmentTables(folderFiles[i],typeBases[i],idCode[i],new Shields());
            }
            System.out.println(typeBases[i] + " Added");
        }
    }

    /**
     * Reads the file in Archives/Weapons and inserts the information into the weapons table in the database.
     *
     * @param file     file that contains the weapon information
     * @param typeBase name of weapon type
     * @param id       ID code to use for logging
     * @throws FileNotFoundException if file is not found
     */
    private void fillWeaponTable(File file, String typeBase, String id) throws FileNotFoundException {
        int[] itemBoos = new int[11];
        in = new Scanner(file);
        int i = 0;

        ArrayList<String> sqls = new ArrayList<>();
        String type = "";
        boolean lineOne = true;
        while (in.hasNext()) {
            line = in.nextLine();
            if (!line.equalsIgnoreCase("")) {
                if (line.contains(":::")) {
                    type = line.replace(":::", "");
                    type = type.replace(" Weapons", "");
                } else if (line.contains("<>")) {
                    String[] booArray = line.replace("<>", "").split("\\?");
                    String[] entries = {"Name", "Category", "Level", "Price", "Damage", "Range", "Critical", "Capacity", "Usage", "Bulk", "Special"};
                    int booSpot = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (booArray[booSpot].equalsIgnoreCase(entries[j])) {
                            itemBoos[j] = 1;
                            booSpot++;
                        } else itemBoos[j] = 0;
                    }
                } else if (!line.contains("~")) {
                    String[] tempItem = line.split("\\?");
                    String[] item = new String[itemBoos.length];
                    int tLoc = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (itemBoos[j] == 1) {
                            item[j] = tempItem[tLoc];
                            tLoc++;
                        } else {
                            item[j] = "-";
                        }
                    }
                    String idTemp = id + i;
                    String sql = " INSERT INTO Weapons" + "(Name,Type,Category,Level,Price,Damage,Range,Critical,Capacity,Usage,Bulk,Special,ID)" +
                            "VALUES ('" + item[0] + "','" + typeBase + type + "','" + item[1] + "','" + item[2] + "','" + item[3] + "','" + item[4] + "','" + item[5] + "','"
                            + item[6] + "','" + item[7] + "','" + item[8] + "','" + item[9] + "','" + item[10] + "','" + idTemp + "');";
                    sqls.add(sql);
                    i++;
                }
            }
        }
        SQLite.AddRecord(sqls, "Weapons");
    }

    /**
     * Reads the file in Archives/Armor and inserts the information into the armor table in the database.
     *
     * @param file     file that contains the armor information
     * @param typeBase name of armor type
     * @param id       ID code to use for logging
     * @throws FileNotFoundException if a file isn't found
     */
    private void fillArmorTable(File file, String typeBase, String id) throws FileNotFoundException {
        int[] itemBoos = new int[10];
        in = new Scanner(file);
        int i = 0;
        ArrayList<String> sqls = new ArrayList<>();
        String type = "";
        while (in.hasNext()) {
            line = in.nextLine();
            if (!line.equalsIgnoreCase("")) {
                if (line.contains(":::")) {
                    type = line.replace(":::", "");
                } else if (line.contains("<>")) {
                    String[] booArray = line.replace("<>", "").split("\\?");
                    String[] entries = {"Name", "Level", "Price", "EAC_Bonus", "KAC_Bonus", "Maximum_Dex_Bonus", "Armor_Check_Penalty", "Speed_Adjustment", "Upgrade_Slots", "Bulk"};
                    int booSpot = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (booArray.length == 3) {
                            itemBoos = new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 0};
                        } else {
                            if (booArray[booSpot].equalsIgnoreCase(entries[j])) {
                                itemBoos[j] = 1;
                                booSpot++;
                            } else {
                                itemBoos[j] = 0;
                            }
                        }
                    }
                } else if (!line.contains("~")) {
                    String[] tempItem = line.split("\\?");
                    String[] item = new String[itemBoos.length];
                    int tLoc = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (itemBoos[j] == 1) {
                            item[j] = tempItem[tLoc];
                            tLoc++;
                        } else {
                            item[j] = "-";
                        }
                        if (item[j].contains("'")) {
                            item[j] = item[j].replace("'", "+");
                        }
                    }
                    String idTemp = id + i;
                    String sql = " INSERT INTO Armor" + "(Name,Type,Level,Price,EAC_Bonus,KAC_Bonus,Maximum_Dex_Bonus,Armor_Check_Penalty,Speed_Adjustment,Upgrade_Slots,Bulk,ID)" +
                            "VALUES ('" + item[0] + "','" + typeBase + type + "','" + item[1] + "','" + item[2] + "','" + item[3] + "','" + item[4] + "','" + item[5] + "','"
                            + item[6] + "','" + item[7] + "','" + item[8] + "','" + item[9] + "','" + idTemp + "');";
                    sqls.add(sql);
                    i++;
                }
            }
        }
        SQLite.AddRecord(sqls, "Armor");
    }

    /**
     * Reads the file in Archives/Augmentation and inserts the information into the augmentation table in the database.
     *
     * @param file     file that contains the augmentation information
     * @param typeBase name of augmentation type
     * @param id       ID code to use for logging
     * @throws FileNotFoundException if a file isn't found
     */
    private void fillAugTable(File file, String typeBase, String id) throws FileNotFoundException {
        int[] itemBoos = new int[5];
        in = new Scanner(file);
        int i = 0;
        ArrayList<String> sqls = new ArrayList<>();
        String type = "";
        while (in.hasNext()) {
            line = in.nextLine();
            if (!line.equalsIgnoreCase("")) {
                if (line.contains(":::")) {
                    type = line.replace(":::", "");
                } else if (line.contains("<>")) {
                    String[] booArray = line.replace("<>", "").split("\\?");
                    String[] entries = {"Name", "Level", "Price", "System", "Ability"};
                    int booSpot = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (booSpot < booArray.length && booArray[booSpot].equalsIgnoreCase(entries[j])) {
                            itemBoos[j] = 1;
                            booSpot++;
                        } else {
                            itemBoos[j] = 0;
                        }
                    }
                } else if (!line.contains("~")) {
                    String[] tempItem = line.split("\\?");
                    String[] item = new String[itemBoos.length];
                    int tLoc = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (itemBoos[j] == 1) {
                            item[j] = tempItem[tLoc];
                            tLoc++;
                        } else {
                            item[j] = "-";
                        }
                        if (item[j].contains("'")) {
                            item[j] = item[j].replace("'", "+");
                        }
                    }
                    String idTemp = id + i;
                    String sql = " INSERT INTO Augmentations" + "(Name,Type,Level,Price,System,Ability,ID)" +
                            "VALUES ('" + item[0] + "','" + typeBase + type + "','" + item[1] + "','" + item[2] + "','" + item[3] + "','" + item[4] + "','" + idTemp + "');";
                    sqls.add(sql);
                    i++;
                }
            }
        }
        SQLite.AddRecord(sqls,"Armor");
    }

    /**
     * Reads the file in Archives/Other Items and inserts the information into the items table in the database.
     *
     * @param file     file that contains the item information
     * @param typeBase name of item type
     * @param id       ID code to use for logging
     * @throws FileNotFoundException if a file isn't found
     */
    private void fillOtherTable(File file, String typeBase, String id) throws FileNotFoundException {
        int[] itemBoos = new int[5];
        in = new Scanner(file);
        int i = 0;
        ArrayList<String> sqls = new ArrayList<>();
        String type = "";
        while (in.hasNext()) {
            line = in.nextLine();
            if (!line.equalsIgnoreCase("")) {
                if (line.contains(":::")) {
                    type = line.replace(":::", "");
                } else if (line.contains("<>")) {
                    String[] booArray = line.replace("<>", "").split("\\?");
                    String[] entries = {"Name","Category","Level","Price","Bulk"};
                    int booSpot = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (booSpot < booArray.length && booArray[booSpot].equalsIgnoreCase(entries[j])) {
                            itemBoos[j] = 1;
                            booSpot++;
                        } else {
                            itemBoos[j] = 0;
                        }
                    }
                } else if (!line.contains("~")) {
                    String[] tempItem = line.split("\\?");
                    String[] item = new String[itemBoos.length];
                    int tLoc = 0;
                    for (int j = 0; j < itemBoos.length; j++) {
                        if (itemBoos[j] == 1) {
                            if (tempItem.length<=tLoc){
                                item[j] = "-";
                            } else {
                                item[j] = tempItem[tLoc];
                                tLoc++;
                            }
                        } else {
                            item[j] = "-";
                        }
                        if (item[j].contains("'")) {
                            item[j] = item[j].replace("'", "+");
                        }
                    }
                    String idTemp = id + i;
                    String sql = " INSERT INTO Items" + "(Name,Type,Category,Level,Price,Bulk,ID)" +
                            "VALUES ('" + item[0] + "','" + typeBase + type + "','" + item[1] + "','" + item[2] + "','" + item[3] + "','" + item[4] + "','" + idTemp + "');";
                    sqls.add(sql);
                    i++;
                }
            }
        }
        SQLite.AddRecord(sqls,"Items");
    }

    /**
     * Creates and fills tables for all the files in the Misc folder in Archives.
     * @throws FileNotFoundException if a file isn't found
     */
    private void addAndFillMisc() throws FileNotFoundException {
        //Find Files
        File miscFolder = new File("Archives/Misc");
        File[] miscFiles = miscFolder.listFiles();
        assert miscFiles != null;
        for (File file:miscFiles) {
            String tableName = file.getName().replace(".txt","").replace(" ","_");
            in = new Scanner(file);
            int i = 0;
            ArrayList<String> sqls = new ArrayList<>();
            String[] tableValues;
            StringBuilder sbInsert = new StringBuilder();
            while (in.hasNext()){
                line = in.nextLine();
                if (!line.equalsIgnoreCase("")){
                    if (line.contains("~")){
                        //no need for now, Skip
                    } else if (line.contains("<>")){
                        //Create Table
                        tableValues = line.replace("<>","").split("\\?");
                        StringBuilder sb = new StringBuilder();
                        sb.append("CREATE TABLE ")
                                .append(tableName)
                                .append("(");
                        sbInsert.append("(");
                        for (String s:tableValues){
                            sbInsert.append(s).append(",");
                            sb.append(s).append("  TEXT,");
                        }
                        sb.append("ID    TEXT)");
                        sbInsert.append("ID)");
                        SQLite.createTable(tableName, sb.toString());
                    } else if (line.contains(":::")){
                        //TODO add type to table, then add a type if exist based on headers
                    } else {
                        //Add values
                        String[] item = line.split("\\?");
                        StringBuilder sql = new StringBuilder();
                        sql.append("INSERT INTO ").append(tableName).append(" ").append(sbInsert.toString()).append("VALUES ('");
                        for (String s:item){
                            if (s.contains("'")) s=s.replace("'","+");
                            sql.append(s).append("','");
                        }
                        sql.append(i).append("');");
                        sqls.add(sql.toString());
                        i++;
                    }
                }
            }
            SQLite.AddRecord(sqls,tableName);
        }
    }

    private void fillEquipmentTables(File file, String typeBase, String id, Equipment e) throws FileNotFoundException {
        String[] tableValues = e.getKeys();
        StringBuilder sbTable = new StringBuilder();
        int idStop = 0;
        sbTable.append("CREATE TABLE ")
                .append(e.getTableName())
                .append("(");
        for (String s:tableValues){
            if (idStop != tableValues.length-1) sbTable.append(s).append("  TEXT,");
            idStop++;
        }
        sbTable.append("ID    TEXT)");

        SQLite.createTable(e.getTableName(), sbTable.toString());
        in = new Scanner(file);
        int i = 0;
        ArrayList<String> sqls = new ArrayList<>();
        String type = "-";
        while (in.hasNext()) {
            line = in.nextLine();
            if (!line.equalsIgnoreCase("")) {
                if (line.contains(":::")) {
                    type = line.replace(":::", "");
                } else if (line.contains("<>")) {

                } else if (!line.contains("~")) {
                    String[] itemTemp = line.split("\\?");
                    String[] item = new String[itemTemp.length+1];
                    int k = 0;
                    for (int j = 0; j < item.length; j++) {
                        if (j!=1){
                            item[j] = itemTemp[k];
                            k++;
                        } else item[j] = type;
                    }
                    String idTemp = id + i;
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    for (int j = 0; j < e.getKeys().length; j++) {
                        sb.append(e.getKeys()[j]);
                        if (j!=e.getKeys().length-1){
                            sb.append(",");
                            sb2.append(item[j]).append("','");
                        }
                    }
                    String sql = " INSERT INTO "+e.getTableName() + "("+sb.toString()+")" +
                            "VALUES ('" + sb2.toString() + idTemp + "');";
                    sqls.add(sql);
                    i++;
                }
            }
        }
        SQLite.AddRecord(sqls, e.getTableName());
    }









}

