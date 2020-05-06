package WorldBuilder;

import java.math.BigDecimal;

/**
 * Utilities to be used to convert distance units anc calculate travel speeds for the game
 */
public class SpaceTravel {
    /**Amount of meters in a AU**/
    private static BigDecimal meters = new BigDecimal(149597870691.0);
    /**Value of one G in meters/sec^2**/
    private static double g = 9.8;
    /**enum for the accepted distances to be used**/
    public enum distUnits {Meter,AU,ly,ls}
    /**enum for the units of time used**/
    public enum timeUnits {Days,Hours,Minutes,Seconds}

    /**
     * Takes an inputted distance, speed (in Gs), and method of travel and calculates the time it would take to travel there.
     * @param distance double
     * @param speed double
     * @param dUnit enum
     * @param tUnits enum
     * @param sling boolean
     * @return double
     */
    public static double TimeTo(double distance,double speed, distUnits dUnit,timeUnits tUnits,boolean sling){
        double time;
        if (!sling) {
            double tConvert = 1;
            double dConvert;
            switch (tUnits){
                case Days:
                    tConvert = 86400;
                    break;
                case Hours:
                    tConvert = 3600;
                    break;
                case Minutes:
                    tConvert = 60;
            }
            switch (dUnit){
                case AU:
                    dConvert = meters.doubleValue()*distance;
                    break;
                case ls:
                    //1au=498.66ls
                    dConvert = (meters.doubleValue()/498.66)*distance;
                    break;
                default:
                    dConvert = meters.doubleValue();
            }
            time = Math.sqrt((2 * dConvert) / (speed*g))/tConvert;
        } else {
            time = distance*6;
        }
        return time;
    }

    /**
     * converts AU distance to LightSeconds
     * @param au double
     * @return double
     */
    public static double AUtoLS(double au){
        return au*498.66;
    }

    /**
     * Converts Light seconds to AU
     * @param ls double
     * @return double
     */
    public static double LStoAU(double ls){
        return ls/498.66;
    }

}
