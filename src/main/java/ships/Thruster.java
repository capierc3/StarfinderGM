package ships;

public class Thruster extends Part {

    private String size;
    private String speed;
    private String pilotingMod;

    public Thruster(String line){
        String[] split = line.split("\\?");
        size = split[1];
        speed = split[2];
        pilotingMod = split[3];
        partType = "Thruster";
    }

    public String getSize() {
        return size;
    }
    public Integer getPilotMod(){
        if (pilotingMod.contains("+")){
            return Integer.parseInt(pilotingMod.substring(1));
        } else return -1*Integer.parseInt(pilotingMod.substring(1));
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nSize: "+size+
                "\nSpeed: "+speed+
                "\nPiloting Modifier: "+pilotingMod+
                toStringBTM();
    }
}
