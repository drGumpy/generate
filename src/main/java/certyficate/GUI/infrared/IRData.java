package certyficate.GUI.infrared;

import certyficate.entitys.Order;

public class IRData {
	public double[][] point;
	public double[] blackBodyError;
	public double[] reference;
	
	public double emissivity;
	
	public int distance;
	
	public boolean active;
	
	public String[] blackBodyName;

	public void setNumberOfParametrs(int numberOfParametrs) {
		blackBodyError = new double[numberOfParametrs];
		reference = new double[numberOfParametrs];
		blackBodyName = new String[numberOfParametrs];
	}

	public boolean checkPoint(Order certificate) {
		return point != null && point.equals(certificate.getPoint());
	}
}
