package ships;

public class ExpansionBay extends Part {

    private String description;

    public ExpansionBay(String line){
        super(line);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return toStringTop()+toStringBTM();
    }
}
