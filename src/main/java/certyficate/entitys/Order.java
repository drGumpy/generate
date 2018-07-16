package certyficate.entitys;

import certyficate.GUI.infrared.IRData;
import certyficate.sheetHandlers.search.measurments.Point;

public class Order{
	public double[][] point;
	
	public int channelNumber;
	
	public String[] probeSerial;
	
	public String probeSerialNumber;
	public String calibrationDate;
	public String calibrationCode;
	public String numberOfCalibration;
	public String deviceSerialNumber;
	
	public Client user;
	public Client declarant;
	
	public Device device;
	
	public Probe probe;
	
	public Point[] measurmets; 
	
	public IRData pyrometr;
}

