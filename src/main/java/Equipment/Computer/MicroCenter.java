package Equipment.Computer;

import ArchivesBuilder.SQLite;

import java.sql.*;
import java.util.ArrayList;

public class MicroCenter {

    private static final String databaseName = "equipment";


    public static ArrayList<ComputerModule> getPart(String type) {
        ArrayList<ComputerModule> modules = new ArrayList<>();
        boolean hasShock = false;
        boolean hasSec = false;
        try {
            String sql = "SELECT * FROM computer WHERE type='" + type + "'";
            Connection conn = SQLite.connect(databaseName);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ComputerModule temp;
                String feature = rs.getString(1);
                if (feature.equalsIgnoreCase("Security")) {
                    hasSec = true;
                } else if (feature.equalsIgnoreCase("Shock Grid")) {
                    hasShock = true;
                } else {
                    temp = new ComputerModule(feature,
                            rs.getString(2),
                            rs.getString(3));
                    modules.add(temp);
                }
            }
            rs.close();
            stmt.close();
            if (hasSec) getSec(modules, conn);
            if (hasShock) getShock(modules, conn);
            conn.close();
        } catch (SQLException e) {
            System.out.println("MICROCENTER getModules error");
            System.out.println(e.getMessage());
        }
        return modules;
    }

    private static void getShock(ArrayList<ComputerModule> modules, Connection conn) throws SQLException {
        String sql = "SELECT * FROM computer_shock";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            ShockModule temp = new ShockModule("Shock Grid " + rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    "Upgrades");
            modules.add(temp);
        }
        rs.close();
        stmt.close();
    }

    private static void getSec(ArrayList<ComputerModule> modules, Connection conn) throws SQLException {
        String sql = "SELECT * FROM computer_security";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            SecurityModule temp = new SecurityModule("Shock Grid " + rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    "Countermeasures");
            modules.add(temp);
        }
        rs.close();
        stmt.close();
    }

}
