package certyficate.dataContainer;

import certyficate.entitys.Order;
import certyficate.generate.*;

public class IRData {
	public double[] blackBodyError;
	public double[] reference;
	
	public double emissivity;
	
	public double[][] point;
	
	public int distance;
	
	public String[] blackBodyName;
	
	public String toString(){
		return String.format(DisplayedText.distanceIR, distance);
	}

	public void setNumberOfParametrs(int numberOfParametrs) {
		blackBodyError = new double[numberOfParametrs];
		reference = new double[numberOfParametrs];
		blackBodyName = new String[numberOfParametrs];
	}

	public boolean checkPoint(Order certificate) {
		boolean answer;
		if(point != null && point.equals(certificate.point));
			answer = true;
		return answer;
	}
}
