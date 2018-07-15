package certyficate.generate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;

//metody stowane w programie
public class DataCalculation {
    public static double uncertainty(double[] number){
        double sum= 0;
        for(int i=0; i<number.length; i++)
            sum += Math.pow(number[i], 2.0);
        sum = Math.sqrt(sum);
        return sum;
    }
    
    //TODO find better method
    public static double findRound(double uncertainty, double resolution){
        double round = checkLogarithm(uncertainty, resolution);
        if(round == Double.NaN) {
        	round = setByResolution(resolution);
        }
        return round;
    }

	private static double checkLogarithm(double uncertainty, double resolution) {
    	double round = Double.NaN;
    	double uncertaintyLog = Math.log10(uncertainty),
                 resolutionLog = Math.log10(resolution);
         if(uncertaintyLog > 0 && resolutionLog < 0) {
        	 round = checkUncertaintyLog(uncertaintyLog);
         } else if((int)uncertaintyLog - 1 > resolutionLog) {
        	 round = Math.pow(10, ((int)uncertaintyLog - 2));
         }
		return round;
	}

	private static double checkUncertaintyLog(double uncertaintyLog) {
		double round = 0.1;
		if(uncertaintyLog <= 1) {
			round = Math.pow(10, ((int)uncertaintyLog - 1));
		}
		return round;
	}
	
    private static double setByResolution(double resolution) {
    	double resolutionLog = Math.log10(resolution);
    	double round;
        if(resolutionLog < 0){
        	round = setRound(resolution, resolutionLog - 1);
        }else{
        	round = setRound(resolution, resolutionLog);
        }
		return round;
	}

	private static double setRound(double resolution, double resolutionLog) {
		double power = Math.pow(10, ((int)resolutionLog - 1));
		return Math.round(Math.round(resolution / power)) * power;
	}

    public static double roundTonumber(double value, double round){
        double newValue = checkRound(value, round);
        if(newValue != Double.NaN) {
        	newValue = roundValue(value, round);
        }
        return newValue;
    }

	private static double checkRound(double value, double round) {
		double newValue = Double.NaN;
		if(round == 1){
			newValue = Math.round(value);
		}
		return newValue;
	}
	
	private static double roundValue(double value, double round) {
		double newValue = Math.round(value/round) * round;
		double roundLogarithm = Math.log10(round);
		if(roundLogarithm % 1 == 0)
			roundLogarithm--;
		roundLogarithm *= -1;
		return roundToPlace(newValue, (int) roundLogarithm);
	}

	public static double roundToPlace(double value, int places) {
        if(places < 0) 
        	throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public static String round(double value, double round){
    	String roundText = checkNumber(value, round);
    	if(roundText != null) {
    		roundText = setRoundText(value, round);
    	}
        double d= Math.log10(round);
        int places;
        if(d%1!=0)
            places=-1*(int)d+1;
        else
            places=-1*(int)d;
        double s=Math.round(value/round);
        s=s*round;
        String format = "%."+places+"f";
        return String.format(format, s);
    }

	private static String checkNumber(double value, double round) {
    	String roundText = null;
    	if(round>=1){
    		roundText = Integer.toString((int)Math.round(value));
        }
		return roundText;
	}
	
    private static String setRoundText(double value, double round) {
    	double roundLogarithm = Math.log10(round);
    	double rounded = Math.round(value/round) * round;
    	int places = -1 * (int)roundLogarithm;
    	if((roundLogarithm % 1) != 0) {
    		places++;
    	}
		return setString(rounded, places);
	}

	private static String setString(double rounded, int places) {
		NumberFormat format = new DecimalFormat();
		format.setMaximumFractionDigits(places);
        return format.format(rounded);
	}

	public static String time (String time, int d){
        LocalTime newTime= LocalTime.parse(time);
        newTime=newTime.plusMinutes(d);
        return newTime.toString();
    }
}