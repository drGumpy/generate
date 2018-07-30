package certyficate.generate;

import certyficate.property.CalibrationData;

public class CertificateValue {
	private String[] probe;
	private String[] device;
	private String[] error;
	private String[] uncertainty;

	public CertificateValue() {
		int size = CalibrationData.numberOfParameters;
		probe = new String[size];
		device = new String[size];
		error = new String[size];
		uncertainty = new String[size];
	}
	
	public void setProbe(String probe, int index) {
		this.probe[index] = probe; 
	}
	
	public String getProbe(int index) {
		return probe[index];
	}
	
	public void setDevice(String device, int index) {
		this.device[index] = device; 
	}
	
	public String getDevice(int index) {
		return device[index];
	}
	
	public void setError(String error, int index) {
		this.error[index] = error; 
	}
	
	public String getError(int index) {
		return error[index];
	}
	
	public void setUncertainty(String uncertainty, int index) {
		this.uncertainty[index] = uncertainty; 
	}
	
	public String getUncertainty(int index) {
		return uncertainty[index];
	}
}
