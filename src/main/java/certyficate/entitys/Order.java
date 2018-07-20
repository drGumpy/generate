package certyficate.entitys;

import certyficate.GUI.infrared.IRData;
import certyficate.sheetHandlers.search.measurments.Point;

public class Order{
	private double[][] point;
	
	private int channelNumber;
	
	private String[] probeSerial;
	
	private String probeSerialNumber;
	private String calibrationDate;
	private String calibrationCode;
	private String numberOfCalibration;
	private String deviceSerialNumber;
	
	private Client user;
	private Client declarant;
	
	private Device device;
	
	private Probe probe;
	
	private Point[] measurmets; 
	
	private IRData pyrometr;
	
	public Order() {
		user = new Client();
		declarant = new Client();
		device = new Device();
		probe = new Probe();
	}
	
	public void setPoint(double[][] point) {
		this.point = point;
	}
	
	public double[][] getPoint() {
		return point;
	}
	
	public double[] getPoint(int index) {
		return point[index];
	}
	
	public double getPoint(int index, int index2) {
		return point[index][index2];
	}
	
	public int getPointLength() {
		return point.length;
	}
	
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
	
	public int getChannelNumber() {
		return channelNumber;
	}
	
	public void setProbeSerial(String[] probeSerial) {
		this.probeSerial = probeSerial;
	}
	
	public String[] getProbeSerial(){
		return probeSerial;
	}
	
	public String getProbeSerial(int index){
		return probeSerial[index];
	}
	
	public int getProbeSerialLength(){
		return probeSerial.length;
	}

	public void setProbeSerialNumber(String probeSerialNumber) {
		this.probeSerialNumber = probeSerialNumber;
	}
	
	public String getProbeSerialNumber() {
		return probeSerialNumber;
	}
	
	public void setCalibrationDate(String calibrationDate) {
		this.calibrationDate = calibrationDate;
	}
	
	public String getCalibrationDate() {
		return calibrationDate;
	}
	
	public void setCalibrationCode(String calibrationCode) {
		this.calibrationCode = calibrationCode;
	}
	
	public String getCalibrationCode() {
		return calibrationCode;
	}
	
	public void setNumberOfCalibration(String numberOfCalibration) {
		this.numberOfCalibration = numberOfCalibration;
	}
	
	public String getNumberOfCalibration() {
		return numberOfCalibration;
	}
	
	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}
	
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}
	
	public void setUser(Client user) {
		this.user = user;
	}
	
	public Client getUser() {
		return user;
	}
	
	public void setDeclarant(Client declarant) {
		this.declarant = declarant;
	}
	
	public Client getDeclarant() {
		return declarant;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public void setProbe(Probe probe) {
		this.probe = probe;
	}

	public Probe getProbe() {
		return probe;
	}
	
	public void setMeasurmet(Point[] measurmets) {
		this.measurmets = measurmets;
	}
	
	public Point[] getMeasurmets() {
		return measurmets;
	}
	
	public Point getMeasurments(int index) {
		return measurmets[index];
	}

	
	public void setPyrometrData(IRData pyrometr) {
		this.pyrometr = pyrometr;
	}
	
	public IRData getPyrometrData() {
		return pyrometr;
	}
}

