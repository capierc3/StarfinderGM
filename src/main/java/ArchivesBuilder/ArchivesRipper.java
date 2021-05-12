package ArchivesBuilder;

import java.io.IOException;

public class ArchivesRipper {
    /**
     * String[][] of the hard coded urls from Archives of Nethys and the file names to save them as.
     */
    //DONE
    final String[][] weaponPages = {
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=AdvMelee","Advanced Melee"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=BasicMelee","Basic Melee"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Grenade","Grenade"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Heavy","Heavy"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Longarms","Long Arms"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=SmallArms","Small Arms"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Sniper","Snipers"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Special","Special"}};
    //DONE
    final String[][] armorPages = {
            {"https://www.aonsrd.com/Armor.aspx?Category=Light","Light"},
            {"https://www.aonsrd.com/Armor.aspx?Category=Heavy","Heavy"},
            {"https://www.aonsrd.com/PoweredArmor.aspx?ItemName=All","Powered"},
    };
    //DONE
    final String[][] augmentationsPages = {
            {"https://www.aonsrd.com/Biotech.aspx?ItemName=All&Family=None","Biotech"},
            {"https://www.aonsrd.com/Cybernetics.aspx?ItemName=All&Family=None","Cybernetics"},
            {"https://www.aonsrd.com/Magitech.aspx?ItemName=All&Family=None","Magitech"},
            {"https://www.aonsrd.com/Necrografts.aspx?ItemName=All&Family=None","Necrografts"},
            {"https://www.aonsrd.com/PersonalUpgrades.aspx?ItemName=All&Family=None","Personal Upgrades"}
    };
    //DONE
    final String[][] otherItemsPages = {
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Food and Drinks","Food and Drink"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Lodgings","Lodgings"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Medicinals","Medicinals"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Personal Items","Personal Items"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Professional Services","Processional Services"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Recharging Stations","Recharging Stations"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Trade Goods","Trade Goods"},
            {"https://www.aonsrd.com/OtherItems.aspx?Category=Transportation","Transportation"}

    };
    //DONE
    final String [][] computerPages = {
            {"https://www.aonsrd.com/ComputerMods.aspx?ItemName=All&Family=None","Computers"},
            {"https://aonsrd.com/ComputerMods.aspx?ItemName=Security&Family=None", "Security"},
            {"https://aonsrd.com/ComputerMods.aspx?ItemName=Shock%20Grid&Family=None", "Shock"}
    };
    //DONE (SOME SKIPPED)
    final String[][] miscPages = {
            {"https://www.aonsrd.com/WeaponAccessories.aspx?ItemName=All&Family=None","Weapon Accessories"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Ammo","Ammunition"},
            {"https://www.aonsrd.com/WeaponFusions.aspx?ItemName=All","Weapon Fusions"},
            {"https://www.aonsrd.com/Weapons.aspx?Proficiency=Solarian","Solarian Weapon Crystals"},
            {"https://www.aonsrd.com/Companions.aspx","Creature Companions"}, //skipped
            {"https://www.aonsrd.com/HybridItems.aspx?ItemName=All&Family=None","Hybrid Items"},
            {"https://www.aonsrd.com/MagicItems.aspx?ItemName=All&Family=None","Magic Items"},
            {"https://www.aonsrd.com/TechItems.aspx?ItemName=All&Family=None","Technological Items"},
            {"https://www.aonsrd.com/SpecialMaterials.aspx?ItemName=All&Family=None","Special Materials"},
            {"https://www.aonsrd.com/Aliens.aspx?Letter=All","Aliens"}, //skipped
            {"https://www.aonsrd.com/Feats.aspx?Category=","Feats"}, //skipped
            {"https://www.aonsrd.com/LandVehicles.aspx?ItemName=All","Land Vehicles"}
    };
    final String[][] rulePages = {
            {"https://www.aonsrd.com/WeaponCriticals.aspx?ItemName=All","Critical Hit Effects"},
            {"https://www.aonsrd.com/WeaponProperties.aspx?ItemName=All","Special Properties"},
            {"https://www.aonsrd.com/Traps.aspx?Name=All","Traps"},
            {"https://www.aonsrd.com/Manufacturers.aspx?ItemName=All","Manufactures"},
            {"https://www.aonsrd.com/Skills.aspx?ItemName=All","Skills"},
            {"https://www.aonsrd.com/Starship_WeaponProperties.aspx?ItemName=All","Ship Weapon Properties"}
    };
    final String[][] afflictionsPages = {
            {"https://www.aonsrd.com/Corruptions.aspx?ItemName=All","Corruptions"},
            {"https://www.aonsrd.com/Afflictions.aspx?Category=Curse","Curses"},
            {"https://www.aonsrd.com/Afflictions.aspx?Category=Disease","Diseases"},
            {"https://www.aonsrd.com/Afflictions.aspx?Category=Drug","Drugs"},
            {"https://www.aonsrd.com/Afflictions.aspx?Category=Poison","Poisons"}
    };
    final String[][] spellPages = {
            {"https://www.aonsrd.com/Spells.aspx?Class=Mystic","Mystic"},
            {"https://www.aonsrd.com/Spells.aspx?Class=Technomancer","Technomancer"}
    };
    //SHIP DB DONE
    final String[][] vehiclePages = {
            {"https://www.aonsrd.com/Starship_Armor.aspx","Armor"},
            {"https://www.aonsrd.com/Starship_BaseFrames.aspx?ItemName=All","Base Frames"},
            {"https://www.aonsrd.com/Starship_Computers.aspx","Computers"},
            {"https://www.aonsrd.com/Starship_CrewQuarters.aspx?ItemName=All","Crew Quarters"},
            {"https://www.aonsrd.com/Starship_DefCounters.aspx","Countermeasures"},
            {"https://www.aonsrd.com/Starship_DriftEngines.aspx","Drift Engine"},
            {"https://www.aonsrd.com/Starship_ExpBays.aspx?ItemName=All&Family=None","Expansion Bays"},
            {"https://www.aonsrd.com/Starship_OtherSystems.aspx?ItemName=All","Other Systems"},
            {"https://www.aonsrd.com/Starship_PowerCores.aspx","Power Cores"},
            {"https://www.aonsrd.com/Starship_Security.aspx?ItemName=All&Family=None","Security"},
            {"https://www.aonsrd.com/Starship_Sensors.aspx","Sensors"},
            {"https://www.aonsrd.com/Starship_Shields.aspx","Shields"},
            {"https://www.aonsrd.com/Starship_Thrusters.aspx","Thrusters"},
            {"https://www.aonsrd.com/Starship_Weapons.aspx","Weapons"},
    };
    //DONE
    final String[][] stillNeed = {
            {"https://www.aonsrd.com/Shields.aspx","Shields"},
            {"https://www.aonsrd.com/ArmorUpgrades.aspx?ItemName=All&Family=None","Armor Upgrades"}
    };


    /**
     * Rips the saved urls that are hardcoded in the final String[][] to text files in the Archives folder.
     */
    public ArchivesRipper() {
        String[][][] site = {
            weaponPages, armorPages, augmentationsPages,
            otherItemsPages, computerPages, miscPages, rulePages, afflictionsPages,
            spellPages, vehiclePages
        };
        String[] folders = {
            "Weapons","Armor","Augmentations",
            "Other Items","Computers","Misc","Rules","Afflictions",
            "Spells","Vehicles"};
        for (int i = 0; i < site.length; i++) {
            for (int j = 0; j < site[i].length; j++) {
                ripper(site[i][j][0],folders[i],site[i][j][1],1);
            }
        }
        String[][][] siteType2 = {stillNeed};
        String[] type2Folders = {"random"};
        for (int i = 0; i < siteType2.length; i++) {
            for (int j = 0; j < siteType2[i].length; j++) {
                ripper(siteType2[i][j][0],type2Folders[i],siteType2[i][j][1],1);
            }
        }
    }

    /**
     * runs the URLRipper and catches any thrown errors.
     * @param url url of page to rip
     * @param folder destination folder
     * @param fileName destination file name
     */
    private static void ripper(String url,String folder, String fileName,int type) {
        try {
            if (type == 1) {
                URLRipper.tablesLayout(url, folder, fileName);
            } else if (type == 2) {
                URLRipper.textLayout(url,folder,fileName);
            }
            System.out.println(folder + "/" + fileName + ": Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
