package certyficate.GUI.infrared;

import certyficate.entitys.Order;

public class IRData {
	public double[] blackBodyError;
	public double[] reference;
	
	public double emissivity;
	
	public double[][] point;
	
	public int distance;
	
	public String[] blackBodyName;

	public void setNumberOfParametrs(int numberOfParametrs) {
		blackBodyError = new double[numberOfParametrs];
		reference = new double[numberOfParametrs];
		blackBodyName = new String[numberOfParametrs];
	}

	public boolean checkPoint(Order certificate) {
		boolean answer;
		if(point != null && point.equals(certificate.getPoint()));
			answer = true;
		return answer;
	}
}
