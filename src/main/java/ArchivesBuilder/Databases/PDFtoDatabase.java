//package com.company.utilities.Databases;
//
//import com.company.model.Weapons;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class PDFtoDatabase {
//
//    public static void WeaponsPdfToSQL(){
//
//        SQLite.Build();
//        SQLite.createTable("Pistols");
//        SQLite.createTable("Rifles");
//        SQLite.createTable("Snipers");
//        SQLite.createTable("Antimateriel");
//        SQLite.createTable("Submachine");
//        SQLite.createTable("Shotguns");
//        SQLite.createTable("Machine");
//        SQLite.createTable("Launchers");
//        SQLite.createTable("Grenades");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(10,53),"Pistols");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(54,83),"Rifles");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(84,107),"Snipers");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(108,115),"Antimateriel");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(116,139),"Submachine");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(140,157),"Shotguns");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(158,173),"Machine");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(174,182),"Launchers");
//        SQLite.NewWeaponsRecords(PDFtoDatabase.WeaponsPDF(-25,-25),"Grenades");
//
//    }
//
//    private static ArrayList<Weapons> WeaponsPDF(int start, int end){
//
//        String pdfText;
//        try {
//            pdfText = PDFScrape.run(start,end);
//        } catch (IOException e) {
//            e.printStackTrace();
//            pdfText = "";
//        }
//        String[] text = pdfText.split("\\r?\\n");
//        ArrayList<ArrayList<String>> weapons;
//        ArrayList<Weapons> weaponArray;
//        if (start>=174) {
//            weapons = getNadeLaunchersStrings(text);
//            weaponArray = getWeapons(weapons);
//        } else if(start<0) {
//            weaponArray = nades();
//        } else {
//                weapons = getWeaponsStrings(text);
//                weaponArray = getWeapons(weapons);
//                weaponArray.addAll(getWeaponsFromTables(text));
//        }
//        removeJunk(weaponArray);
//        //OutPrintArray(weaponArray);
//        return weaponArray;
//    }
//
//    private static ArrayList<ArrayList<String>> getWeaponsStrings(String[] text){
//        ArrayList<ArrayList<String>> weapons = new ArrayList<>();
//        int a = 0;
//        for (int i = 0; i <text.length ; i++) {
//            if (text[i].contains("[ACAAIC+Conques-Demi]")){
//                ArrayList<String> weapon = new ArrayList<>();
//                if (text[i+1].contains("[ACABCO+Agenda-BoldCondensed]")) {
//                    weapon.add(text[i]);
//                    int count = 0;
//                    int lineAmount = 6;
//                    int line = i + 1;
//                    Boolean rules = false;
//                    boolean wanted = false;
//                    String mod = "";
//                    while (count < lineAmount && line < (text.length - 1)) {
//                        if (text[line].contains("[ACABCO+Agenda-BoldCondensed]")) {
//                            if (text[line].contains("d20 Modern Rules:")) {
//                                lineAmount = 7;
//                                //System.out.println("rules"+ line);
//                                String stop = "";
//                                int f = line;
//                                while (!stop.contains("[")) {
//                                    mod = mod + text[f] + " ";
//                                    stop = text[f + 1];
//                                    f++;
//                                }
//                                rules = true;
//                            }
//                            //System.out.println(line+" While loop");
//                            if (line == i + 1) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Damage:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Critical:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Damage Type:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Range Increment:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Rate of Fire:")) wanted = true;
//                            //System.out.println(line+" While loop");
//                            if (rules) {
//                                weapon.add(mod);
//                                rules = false;
//                            } else if (wanted) {
//                                weapon.add(text[line]);
//                                wanted = false;
//                                count++;
//                            }
//                        }
//                        line++;
//                    }
//                } else if (text[i+1].contains("[ACABED+Agenda-LightCondensed]")) {
//
//                } else {
//                    weapon.add(text[i+1]);
//                    int count = 0;
//                    int lineAmount = 6;
//                    int line = i+2;
//                    String mod = "";
//                    boolean rules = false;
//                    boolean wanted = false;
//                    while (count<lineAmount && line<(text.length-1)){
//                        if (text[line].contains("[ACABCO+Agenda-BoldCondensed]")){
//                            if (text[line].contains("d20 Modern Rules:")){
//                                lineAmount = 7;
//                                //System.out.println("rules"+ line);
//                                String stop = "";
//                                int f = line;
//                                while (!stop.contains("[")){
//                                    mod = mod + text[f]+" ";
//                                    stop = text[f+1];
//                                    f++;
//                                }
//                                rules = true;
//                            }
//                            if (line== i+2)wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Damage:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Critical:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Damage Type:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Range Increment:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Rate of Fire:")) wanted = true;
//                            //System.out.println(line+" While loop");
//                            if (rules){
//                                weapon.add(mod);
//                                rules = false;
//                            } else if (wanted){
//                                weapon.add(text[line]);
//                                wanted = false;
//                                count++;
//                            }
//
//                        }
//                        line++;
//                    }
//                }
//                weapons.add(weapon);
//            }
//            //System.out.println(a + ": " + text[i]);
//            a++;
//        }
//        return weapons;
//    }
//
//    private static ArrayList<Weapons> getWeapons(ArrayList<ArrayList<String>> weapons){
//        ArrayList<Weapons> weaponArray = new ArrayList<>();
//        for (int j = 0; j <weapons.size(); j++) {
//            Weapons weapon = new Weapons();
//            //System.out.println("lines: "+weapons.get(j).size());
//            int q = 0;
//            for (String s : weapons.get(j)) {
//                //System.out.println(s);
//                q++;
//                if (s.contains("[ACAAIC+Conques-Demi]")) {
//                    s = s.replace("[ACAAIC+Conques-Demi]", "");
//                }
//                if (!s.contains("[")) {
//                    //System.out.println("Name: " + s);
//                    if (s.contains("(")){
//                        s = s.replace("(","~");
//                        String[] sA = s.split("~");
//                        weapon.setName(sA[0]);
//                        weapon.setType(sA[1].replace(")",""));
//                    } else {
//                        //System.out.println("Name: "+s);
//                        weapon.setName(s);
//                    }
//                }
//                if (q == 2 && !s.contains("d20 Modern Rules:")&&!s.contains("Damage:")&&!s.contains("Critical:")){
//                    s = s.replace("[ACABCO+Agenda-BoldCondensed]","");
//                    weapon.setType(s);
//                }
//                if (s.contains("d20 Modern Rules:")){
//                    s = s.replace("[ACABCO+Agenda-BoldCondensed]d20 Modern Rules:[ACABED+Agenda-LightCondensed]","");
//                    s = s.replace("[ACABCO+Agenda-BoldCondensed]d20 Modern Rules: [ACABED+Agenda-LightCondensed]","");
//                    weapon.setMod(s);
//                }
//
//                if (s.contains("[ACABCO+Agenda-BoldCondensed]")&& !s.contains("Rules:")) {
//                    s = s.replace("[ACABCO+Agenda-BoldCondensed]", "");
//                    s = s.replace("[ACABED+Agenda-LightCondensed]", "");
//                    String[] p = s.split(" ");
//                    for (int i = 0; i < p.length; i++) {
//                        //System.out.println(p[i]);
//                        if (p[i].contains("Damage:")) {
//                            //System.out.println("Damage: " + p[i + 1]);
//                            if (p[i+1].contains("See")){
//                                weapon.setDamage("See Grenade");
//                            } else {
//                                weapon.setDamage(p[i + 1]);
//                            }
//                        } else if (p[i].contains("Magazine:")) {
//                            //System.out.println("Magazine: " + p[i + 1] + " " + p[i + 2]);
//                            if (p[i+1].contains("Linked")&& !p[i+1].contains(",")){
//                                weapon.setMagazine(p[i+1]);
//                            } else if (p[i+1].contains(",")|| ((i+2)<=p.length)&& p[i+2].contains(",")) {
//                                weapon.setMagazine(p[i + 1] + " " + p[i + 2]+" "+p[i+3]);
//                            } else {
//                                weapon.setMagazine(p[i + 1] + " " + p[i + 2]);
//                            }
//                        } else if (p[i].contains("Critical:")) {
//                            //System.out.println("Critical: " + p[i + 1]);
//                            weapon.setCrit(p[i + 1]);
//                        } else if (p[i].contains("Size:")) {
//                            //System.out.println("Size: " + p[i + 1]);
//                            weapon.setSize(p[i + 1]);
//                        } else if (p[i].contains("Type:")) {
//                            //System.out.println("Damage Type: " + p[i + 1]);
//                            weapon.setDamageType(p[i + 1]);
//                        } else if (p[i].contains("Weight:")){
//                            //System.out.println("Weight: " + p[i + 1]);
//                            weapon.setWeight(p[i + 1]);
//                        } else if (p[i].contains("Increment:")) {
//                            //System.out.println("Range: " + p[i + 1]);
//                            weapon.setRangeIncrement(p[i + 1]);
//                        } else if (p[i].contains("Fire:")) {
//                            if (p[i + 1].contains(",")) {
//                                //System.out.println("Rate of Fire: " + p[i + 1] + p[i + 2]);
//                                weapon.setRateOfFire(p[i + 1] + p[i + 2]);
//                            } else {
//                                //System.out.println("Rate of Fire: " + p[i + 1]);
//                                weapon.setRateOfFire(p[i + 1]);
//                            }
//                        } else if (p[i].contains("DC:")) {
//                            //System.out.println("Rarity: " + p[i + 1]);
//                            weapon.setRarity(p[i + 1]);
//                        }
//                    }
//                }
//            }
//            weaponArray.add(weapon);
//        }
//        //OutPrintArray(weaponArray);
//        return weaponArray;
//    }
//
//    private static void removeJunk(ArrayList<Weapons> weaponArray){
//
//        for (Iterator<Weapons> it = weaponArray.iterator(); it.hasNext();){
//            Weapons w = it.next();
//            if (w.getName() == null){
//                it.remove();
//            } else if (w.getType() == null) {
//                it.remove();
//            } else {
//                boolean hasLetter = false;
//                String name = w.getName();
//                for (int j = 0; j < name.length(); j++) {
//                    if (!Character.isDigit(name.charAt(j))) {
//                        hasLetter = true;
//                    }
//                }
//                if (!hasLetter) {
//                    it.remove();
//                }
//            }
//        }
//
//    }
//
//    private static ArrayList<Weapons> getWeaponsFromTables(String[] text){
//        //for (String s:text) {
//        //    System.out.println(s);
//        //}
//        ArrayList<Weapons> weapons = new ArrayList<>();
//        for (int i = 0; i <text.length ; i++) {
//            if (text[i].contains("[ACAAIA+CachetBook]TABLE")){
//                ArrayList<ArrayList<String>> tables = new ArrayList<>();
//                int line = i+1;
//                boolean inTable = false;
//                boolean end = false;
//                ArrayList<String> table = new ArrayList<>();
//                while (!end) {
//                    if (text[line].contains("[ACABED+Agenda-LightCondensed]")) {
//                        inTable = true;
//                    }
//                    if (inTable) {
//                        table.add(text[line]);
//                    }
//                    if (inTable && text[line + 1].contains("[") ) {
//                        inTable = false;
//                        end = true;
//                    }
//                    if (!inTable && text[line + 1].contains("[ACABCO+Agenda-BoldCondensed]1[ACABED+Agenda-LightCondensed]")){
//                        inTable = true;
//                        end = false;
//                    }
//                    line++;
//                }
//                if (table.size()>0){
//                    weapons.addAll(tableToWeapon(table));
//                }
//            }
//        }
//        return weapons;
//    }
//
//    private static ArrayList<Weapons> tableToWeapon(ArrayList<String> table){
//
//        ArrayList<Weapons> weapons = new ArrayList<>();
//        //int z = 0;
//        //System.out.println("size: "+table.size());
//        //for (String s: table){
//        //    System.out.println(z+": "+s);
//        //    z++;
//        //}
//        int a = 0;
//        boolean endMod = false;
//        ///////////////
//        for (String s:table) {
//            //System.out.println(s);
//            if (endMod && a>=table.size()-1){
//                break;
//            }
//            if (!s.contains("(")){
//                break;
//            }
//            Weapons weapon = new Weapons();
//            s = s.replace("[ACABED+Agenda-LightCondensed]","");
//            if (s.contains(" [ACABCO+Agenda-BoldCondensed]1")){
//                String mod = table.get(table.size()-1);
//                mod = mod.substring(2);
//                weapon.setMod(mod);
//                endMod = true;
//            }
//            s = s.replace(" [ACABCO+Agenda-BoldCondensed]1","");
//            //if (a==0)System.out.println("OutPrint: " +s);
//            String name = "", crit,dam, type = "",range,fire,mag,size,weight,dc;
//            boolean firstP = false;
//            boolean lastP = false;
//            for (int i = 0; i <s.length() ; i++) {
//                if (s.charAt(i)=='('){
//                    firstP = true;
//                }
//                if (s.charAt(i)==')'){
//                    lastP = true;
//                }
//                if (!firstP&&!lastP){
//                    name = name + s.charAt(i);
//                }
//                if (firstP&&!lastP){
//                    type = type + s.charAt(i);
//                }
//            }
//            s = s.replace(name,"");
//            s = s.replace(type,"");
//            String[] sA = s.split(" ");
//            dam = sA[1];
//            crit = sA[2];
//            range = sA[4];
//            fire = sA[6];
//            if (fire.contains(",")){
//                fire = fire+" "+sA[7];
//                if (sA[8].contains("Linked")){
//                    mag = sA[8];
//                    size = sA[9];
//                    weight = sA[10];
//                    dc = sA[12];
//                }else {
//                    mag = sA[8] + " " + sA[9];
//                    size = sA[10];
//                    weight = sA[11];
//                    dc = sA[13];
//                }
//            } else {
//                if (sA[7].contains("Linked")){
//                    mag = sA[7];
//                    size = sA[8];
//                    weight = sA[9];
//                    dc = sA[11];
//                }else {
//                    mag = sA[7] + " " + sA[8];
//                    size = sA[9];
//                    weight = sA[10];
//                    dc = sA[12];
//                }
//            }
//            /*
//            int b = 0;
//            for (String s1:sA){
//                if (a == 3) System.out.println(b+": "+s1);
//                b++;
//            }
//            */
//            a++;
//            type = type.replace("(","");
//            type = type.replace(")","");
//            weapon.setName(name);
//            weapon.setCrit(crit);
//            weapon.setType(type);
//            weapon.setDamage(dam);
//            weapon.setRangeIncrement(range);
//            weapon.setRateOfFire(fire);
//            weapon.setMagazine(mag);
//            weapon.setSize(size);
//            weapon.setWeight(weight);
//            weapon.setRarity(dc);
//            weapons.add(weapon);
//        }
//        //OutPrintArray(weapons);
//        return weapons;
//    }
//
//    private static ArrayList<ArrayList<String>> getNadeLaunchersStrings(String[] text){
//        ArrayList<ArrayList<String>> weapons = new ArrayList<>();
//        int a = 0;
//        for (int i = 0; i <text.length ; i++) {
//            if (text[i].contains("[ACAAIC+Conques-Demi]")){
//                ArrayList<String> weapon = new ArrayList<>();
//                if (text[i+1].contains("[ACABCO+Agenda-BoldCondensed]")) {
//                    weapon.add(text[i]);
//                    int count = 0;
//                    int lineAmount = 5;
//                    int line = i + 1;
//                    Boolean rules = false;
//                    boolean wanted = false;
//                    String mod = "";
//                    while (count < lineAmount && line < (text.length - 1)) {
//                        if (text[line].contains("[ACABCO+Agenda-BoldCondensed]")) {
//                            if (text[line].contains("d20 Modern Rules:")) {
//                                lineAmount = 6;
//                                //System.out.println("rules"+ line);
//                                String stop = "";
//                                int f = line;
//                                while (!stop.contains("[")) {
//                                    mod = mod + text[f] + " ";
//                                    stop = text[f + 1];
//                                    f++;
//                                }
//                                rules = true;
//                            }
//                            //System.out.println(line+" While loop");
//                            if (line == i + 1) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Damage:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Range Increment:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Rate of Fire:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Magazine:")) wanted = true;
//                            //System.out.println(line+" While loop");
//                            if (rules) {
//                                weapon.add(mod);
//                                rules = false;
//                            } else if (wanted) {
//                                weapon.add(text[line]);
//                                wanted = false;
//                                count++;
//                            }
//                        }
//                        line++;
//                    }
//                } else if (text[i+1].contains("[ACABED+Agenda-LightCondensed]")) {
//
//                } else {
//                    weapon.add(text[i+1]);
//                    int count = 0;
//                    int lineAmount = 5;
//                    int line = i+2;
//                    String mod = "";
//                    boolean rules = false;
//                    boolean wanted = false;
//                    while (count<lineAmount && line<(text.length-1)){
//                        if (text[line].contains("[ACABCO+Agenda-BoldCondensed]")){
//                            if (text[line].contains("d20 Modern Rules:")){
//                                lineAmount = 6;
//                                //System.out.println("rules"+ line);
//                                String stop = "";
//                                int f = line;
//                                while (!stop.contains("[")){
//                                    mod = mod + text[f]+" ";
//                                    stop = text[f+1];
//                                    f++;
//                                }
//                                rules = true;
//                            }
//                            if (line== i+2)wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Damage:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Range Increment:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Rate of Fire:")) wanted = true;
//                            if (text[line].contains("[ACABCO+Agenda-BoldCondensed]Magazine:")) wanted = true;
//                            //System.out.println(line+" While loop");
//                            if (rules){
//                                weapon.add(mod);
//                                rules = false;
//                            } else if (wanted){
//                                weapon.add(text[line]);
//                                wanted = false;
//                                count++;
//                            }
//
//                        }
//                        line++;
//                    }
//                }
//                weapons.add(weapon);
//            }
//            //System.out.println(a + ": " + text[i]);
//            a++;
//        }
//        return weapons;
//    }
//
//    private static ArrayList<Weapons> nades(){
//
//        ArrayList<Weapons> nadeList = new ArrayList<>();
//        String nades =
//                "20mm burst shell~3d6~—~Slashing~5 ft.~14~Tiny~0.5 lb.~18~1\n" +
//                "30mm HEAT grenade~3d6~—~Slashing~10 ft.~16~Tiny~1 lb.~19~2\n" +
//                "35mm incendiary cartridge~2d6~—~Fire~20 ft.~12~Tiny~1 lb.~15~3\n" +
//                "37mm rubber bullet~3d6 nonlethal~20~Bludgeoning~—~—~Tiny~1 lb.~15~0\n" +
//                "37mm tear gas shell~Special~—~—~Special~—~Tiny~1 lb.~15~1\n" +
//                "40mm shotgun/”Beehive” round~4d10~20~Ballistic~—~—~Tiny~1 lb.~14~4\n" +
//                "40mm fragmentation grenade~3d6~—~Slashing~10 ft.~15~Tiny~1 lb.~16~0\n" +
//                "40mm smoke grenade~—~—~—~Special~—~Tiny~1 lb.~10~5\n" +
//                "40mm white phosphorus grenade~2d6~—~Fire~20 ft.~15~Tiny~1 lb.~16~3\n" +
//                "43mm flare grenade~—~—~—~Special~—~Tiny~1 lb.~10~6\n" +
//                "43mm flash bomb~3d6~—~Bludgeoning~20 ft.~16~Tiny~1 lb.~19~0\n" +
//                "43mm fragmentation grenade~3d6~—~Slashing~10 ft.~15~Tiny~1 lb.~16~0\n" +
//                "40mm smoke grenade~—~—~—~Special~—~Tiny~1 lb.~10~5\n" +
//                "76.2mm HE shell~4d6~—~Slashing~30 ft.~16~Small~2 lb.~19~0";
//        String mods =
//                "See the tear gas grenade in Chapter Four of the d20 Modern Roleplaying Game for additional rules.\n" +
//                        "This projectile ignores 5 points of hardness when fi red at an object or structure.\n" +
//                        "See the white phosphorus grenade in Chapter Four of the d20 Modern Roleplaying Game for rules on additional damage in " +
//                        "subsequent rounds, smoke, and catching fi re.\n" +
//                        "This is a 40mm bundle of darts with a range increment of 20 feet. Like shotguns, reduce the damage of this projectile by 1 point for " +
//                        "every range increment of the attack.\n" +
//                        "See the smoke grenade in Chapter Four of the d20 Modern Roleplaying Game for additional rules.\n" +
//                        "The flare provides light equivalent to daylight in a 60-foot radius for 1 minute. Individuals using nightvision goggles within the fl are’s " +
//                        "radius must succeed on a Fortitude save (DC 15) or be blinded for 2d6 rounds.";
//        String[] modSplit = mods.split("\n");
//        String[] nadesSplit = nades.split("\n");
//        for (String s:nadesSplit){
//            String[] nadeSplit = s.split("~");
//            Weapons nade = new Weapons();
//            int a = 0;
//            for (String q:nadeSplit) {
//                switch (a){
//                    case 0:
//                        nade.setName(q);
//                        break;
//                    case 1:
//                        nade.setDamage(q);
//                        break;
//                    case 2:
//                        nade.setCrit(q);
//                        break;
//                    case 3:
//                        nade.setType(q);
//                        break;
//                    case 4:
//                        nade.setMagazine(q);
//                        break;
//                    case 5:
//                        nade.setRateOfFire(q);
//                        break;
//                    case 6:
//                        nade.setSize(q);
//                        break;
//                    case 7:
//                        nade.setWeight(q);
//                        break;
//                    case 8:
//                        nade.setRarity(q);
//                        break;
//                    case 9:
//                        nade.setMod(q);
//                        break;
//                }
//                a++;
//            }
//            switch (nade.getMod()){
//                case "0":
//                    nade.setMod("");
//                    break;
//                case "1":
//                    nade.setMod(modSplit[0]);
//                    break;
//                case "2":
//                    nade.setMod(modSplit[1]);
//                    break;
//                case "3":
//                    nade.setMod(modSplit[2]);
//                    break;
//                case "4":
//                    nade.setMod(modSplit[3]);
//                    break;
//                case "5":
//                    nade.setMod(modSplit[4]);
//                    break;
//                case "6":
//                    nade.setMod(modSplit[5]);
//                    break;
//            }
//            nadeList.add(nade);
//        }
//        /*
//        System.out.println("//////////");
//        for (Weapons n:nadeList){
//            System.out.println("Name: "+n.getName());
//            System.out.println("Damage: "+n.getDamage());
//            System.out.println("Critical: "+n.getCrit());
//            System.out.println("Damage Type: "+n.getType());
//            System.out.println("Blast Radius: "+n.getMagazine());
//            System.out.println("Reflex Save: "+n.getRateOfFire());
//            System.out.println("Size: "+n.getSize());
//            System.out.println("Weight: "+n.getWeight());
//            System.out.println("Rarity: "+n.getRarity());
//            System.out.println("Mod: "+n.getMod());
//            System.out.println("///////////////");
//
//        }
//        */
//        return nadeList;
//    }
//
//    private static void OutPrintArray(ArrayList<Weapons> weapons){
//
//        System.out.println("///////////////////");
//        for (Weapons w:weapons) {
//            System.out.println("Name: " + w.getName());
//            System.out.println("Type: " + w.getType());
//            System.out.println("Damage: " + w.getDamage());
//            System.out.println("Magazine: " + w.getMagazine());
//            System.out.println("Critical: " + w.getCrit());
//            System.out.println("Size: " + w.getSize());
//            System.out.println("Range: " + w.getRangeIncrement());
//            System.out.println("Fire: " + w.getRateOfFire());
//            System.out.println("Rarity: " + w.getRarity());
//            System.out.println("Mod: " + w.getMod());
//            System.out.println("/////////////////////");
//        }
//
//    }
//}
