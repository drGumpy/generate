package certyficate.generate.certificate;

import certyficate.generate.CertificateValue;

public class HuminidityCertificate extends Certificate {
	private static final int MEASURMENT_LINE = 84;
	
	public HuminidityCertificate() {
		super();
	}
	
	@Override
	protected void setTemplateData() {
		dateColumn = 9;
		numberColumn = 24;
		informactionColumn = 13;
		numberOfData = 5;
	}
	
	@Override
	protected void setMeasurmentData() {
		setMeasurmentData(MEASURMENT_LINE);		
	}
	
	@Override
	protected void setData(CertificateValue data, int line) {
		sheet.setValueAt(data.getProbe(0), 3, line);
		sheet.setValueAt(data.getProbe(1), 8, line);
    	sheet.setValueAt(data.getDevice(0), 13, line);
    	sheet.setValueAt(data.getDevice(1), 18, line);
    	sheet.setValueAt(data.getError(0), 23, line);
    	sheet.setValueAt(data.getError(1), 28, line);
    	sheet.setValueAt(data.getUncertainty(0), 33, line);
    	sheet.setValueAt(data.getUncertainty(1), 38, line);
	}
}
