package certyficate.generate.certificate;

import certyficate.generate.CertificateValue;

public class PyrometerCertificate extends Certificate {
	protected static final int MEASURMENT_LINE = 84;
	
	protected static int dateColumn = 8;
	protected static int numberColumn = 22;
	protected static int informactionColumn = 12;
	protected static int numberOfData = 3;	
	protected static int commentsLine = 95;
	
	public PyrometerCertificate() {
		super();
	}

	@Override
	protected void setMeasurmentData() {
		setMeasurmentData(MEASURMENT_LINE);
	}
	
	protected void setData(CertificateValue data, int line) {
		sheet.setValueAt(data.probeT, 3, line);
    	sheet.setValueAt(data.deviceT, 12, line);
    	sheet.setValueAt(data.errorT, 21, line);
    	sheet.setValueAt(data.uncertaintyT, 30, line);
	}
}
