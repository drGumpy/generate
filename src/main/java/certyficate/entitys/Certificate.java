package certyficate.entitys;

import certyficate.dataContainer.IRData;
import certyficate.dataContainer.Point;

public class Certificate{
	public int[][] point;
	
	public int channelNumber;
	
	public String[] probeSerial =null;
	
	public String probeSerialNumber;
	public String calibrationDate;
	public String calibrationCode;
	public String numberOfCalibration;
	public String deviceSerialNumber;
	
	public Client user= new Client();
	public Client declarant= new Client();
	
	public Device device = new Device();
	
	public Probe probe= new Probe();
	
	public Point[] measurmets; 
	
	public IRData pyrometr;
}

