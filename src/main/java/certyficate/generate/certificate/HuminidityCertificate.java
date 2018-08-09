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
		setData(data, line, 0);
		setData(data, line, 1);
	}

	private void setData(CertificateValue data, int line, int paramertIndex) {
		int correction = 5 * paramertIndex;
		sheet.setValueAt(data.getProbe(paramertIndex), 3 + correction, line);
    	sheet.setValueAt(data.getDevice(paramertIndex), 13 + correction, line);
    	sheet.setValueAt(data.getError(paramertIndex), 23 + correction, line);
    	sheet.setValueAt(data.getUncertainty(paramertIndex), 33 + correction, line);
	}
}
