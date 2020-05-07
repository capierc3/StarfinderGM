package ships;

public class PowerCore extends Part {

    private String size;

    public PowerCore(String line) {
        super(line);
        String[] split = line.split("\\?");
        name = split[0];
        size = split[1];
        partType = "Power Core";
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return toStringTop()+
                "\nSize: "+size+
                toStringBTM();
    }
}
