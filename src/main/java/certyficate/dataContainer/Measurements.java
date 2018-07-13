package certyficate.dataContainer;

import certyficate.entitys.Order;

public class Measurements{
	public int num;
	public Point[] measurmets;
	public String name;
	
	public Measurements(int numberOfPoint){
		measurmets = new Point[numberOfPoint];
	}

	public boolean match(Order certificate) {
		boolean answer = false;
		if(name != null && name.equals(certificate.deviceSerialNumber)) {
			answer = true;
		}
		return answer;
	}
}