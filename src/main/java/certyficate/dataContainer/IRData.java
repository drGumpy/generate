package certyficate.dataContainer;

import certyficate.generate.*;

public class IRData {
	public double[] blackBodyError;
	public double[] reference;
	
	public double emissivity;
	
	public String[] blackBodyName;
	
	public int distance;
	
	public String toString(){
		return String.format(DisplayedText.distanceIR, distance);
	}
}
