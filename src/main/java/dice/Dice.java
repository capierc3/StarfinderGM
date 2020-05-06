package dice;

import java.util.Random;

public class Dice {

    private final int d;
    private final double dD;
    private int roll;
    private double dRoll;

    public Dice(int dSize){
        this.d=dSize;
        this.dD=dSize;
    }
    public Dice(double dSize){
        this.dD = dSize;
        this.d = (int) dSize;
    }

    public int Roll(){
        Random r = new Random();
        int low = 1;
        int high = this.d+1;
        this.roll = r.nextInt(high - low)+low;
        return this.roll;
    }
    public double Roll(int x){
        Random r = new Random();
        double low = 1.0;
        double high = this.dD+1.0;
        this.dRoll = low + (high - low) * r.nextDouble();
        return this.dRoll;
    }

    public int getRoll() {
        if (roll==0){
            Roll();
        }
        return roll;
    }

    public static int Roller(int times, int sides){
        Dice die = new Dice(sides);
        int value = 0;
        for (int i = 0; i < times; i++) {
            value = value + die.Roll();
        }
        return value;
    }
    public static double Roller(int times,double sides){
        Dice die = new Dice(sides);
        double value = 0;
        for (int i = 0; i < times; i++) {
            value = value + die.Roll(1);
        }
        return value;
    }
}
