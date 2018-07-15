package certyficate.generate.certificate;

import certyficate.generate.CertificateValue;

public class HuminidityCertificate extends Certificate {
	protected static int dateColumn = 9;
	protected static int numberColumn = 24;
	protected static int informactionColumn = 13;
	protected static int numberOfData = 5;
	
	@Override
	protected void setTemplateData() {
		templateName = "sw_Rh.ods";		
	}
	
	@Override
	protected void setMeasurmentData() {
		setMeasurmentData(84);		
	}
	
	@Override
	protected void setData(CertificateValue data, int line) {
		sheet.setValueAt(data.probeT, 3, line);
        sheet.setValueAt(data.probeRh, 8, line);
        sheet.setValueAt(data.deviceT, 13, line);
        sheet.setValueAt(data.deviceRh, 18, line);
        sheet.setValueAt(data.errorT, 23, line);
        sheet.setValueAt(data.errorRh, 28, line);
        sheet.setValueAt(data.uncertaintyT, 33, line);
        sheet.setValueAt(data.uncertaintyRh, 38, line);
	}
}
