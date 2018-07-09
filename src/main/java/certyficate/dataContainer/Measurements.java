package certyficate.dataContainer;

import certyficate.entitys.Certificate;

public class Measurements{
	public int num;
	public Point[] measurmets;
	public String name;
	
	public Measurements(int numberOfPoint){
		measurmets = new Point[numberOfPoint];
	}

	public boolean match(Certificate certificate) {
		boolean answer = false;
		if(name != null && name.equals(certificate.deviceSerialNumber)) {
			answer = true;
		}
		return answer;
	}
}