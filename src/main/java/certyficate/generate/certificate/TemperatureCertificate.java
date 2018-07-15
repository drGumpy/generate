package certyficate.generate.certificate;

import certyficate.generate.CertificateValue;

public class TemperatureCertificate extends Certificate {
	private static int numberOfChannel = 1;
	
	protected static int dateColumn = 8;
	protected static int numberColumn = 22;
	protected static int informactionColumn = 12;
	protected static int numberOfData = 3;	
	protected static int commentsLine = 95;
	
	public TemperatureCertificate() {
		super();
	}
	
	@Override
	protected void setTemplateData() {
		templateName = "sw_T.ods";
		// TODO template finder, comment set, channel set
		
	}

	@Override
	protected void setMeasurmentData() {
		setMeasurmentData(84);
		if(numberOfChannel > 1) {
			setMeasurmentData(99);
		}
	}

	@Override
	protected void setData(CertificateValue data, int line) {
		sheet.setValueAt(data.probeT, 3, line);
    	sheet.setValueAt(data.deviceT, 12, line);
    	sheet.setValueAt(data.errorT, 21, line);
    	sheet.setValueAt(data.uncertaintyT, 30, line);
	}

}
