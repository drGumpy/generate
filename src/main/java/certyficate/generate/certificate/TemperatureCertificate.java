package certyficate.generate.certificate;

import certyficate.generate.CertificateText;

public class TemperatureCertificate extends PyrometerCertificate {
	private static final int CHANNEL_LINE_CORECTION = 8;
	
	private int channelNumber;
	
	public TemperatureCertificate() {
		super();
	}

	@Override
	protected void setMeasurmentData() {
		channelNumber = 0;
		setMeasurment(MEASURMENT_LINE);
		if(numberOfChannel > 1) {
			int line = MEASURMENT_LINE + numberOfData * POINT_GAP + 9;
			setMeasurment(line);
		}
	}

	private void setMeasurment(int measurmentLine) {
		setChannel(measurmentLine);
		setMeasurmentData(measurmentLine);
		channelNumber++;
	}

	private void setChannel(int measurmentLine) {
		int line = measurmentLine - CHANNEL_LINE_CORECTION;
		String channelText = CertificateText.getChannelText(certificate, channelNumber);
		sheet.setValueAt(channelText, 3, line);
	}
}
