package certyficate.dataContainer;

import certyficate.entitys.Certificate;
import certyficate.generate.*;

public class IRData {
	public double[] blackBodyError;
	public double[] reference;
	
	public double emissivity;
	
	public int[][] point;
	
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

	public boolean checkPoint(Certificate certificate) {
		boolean answer;
		if(point != null && point.equals(certificate.point));
			answer = true;
		return answer;
	}
}
