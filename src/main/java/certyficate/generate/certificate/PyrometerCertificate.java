package certyficate.generate.certificate;

import certyficate.generate.CertificateValue;

public class PyrometerCertificate extends Certificate {
	protected static final int MEASURMENT_LINE = 84;
	
	public PyrometerCertificate() {
		super();
	}
	
	@Override
	protected void setTemplateData() {
		dateColumn = 8;
		numberColumn = 22;
		informactionColumn = 12;
		numberOfData = 3;	
		commentsLine = 95;
	}

	@Override
	protected void setMeasurmentData() {
		setMeasurmentData(MEASURMENT_LINE);
	}
	
	protected void setData(CertificateValue data, int line) {
		sheet.setValueAt(data.getProbe(0), 3, line);
    	sheet.setValueAt(data.getDevice(0), 12, line);
    	sheet.setValueAt(data.getError(0), 21, line);
    	sheet.setValueAt(data.getUncertainty(0), 30, line);
	}
}
