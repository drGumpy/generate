package certyficate.generate.note;

import certyficate.generate.CertificateValue;
import certyficate.generate.certificate.PyrometerCertificate;
import certyficate.property.DataCalculation;

public class PyrometerNote extends Note {
	private static final String NOTE_FILE = "z_T.ods";
	
	private static final int NUMBER_OF_DATA = 3;

	public PyrometerNote() {
		super();
	}
	
	@Override
	protected void setData() {
		noteFile = NOTE_FILE;
		numberOfData = NUMBER_OF_DATA;
	}
	
	@Override
	protected void setResolution(String[] resolution, int line) {
		sheet.setValueAt(resolution[0], DEVICE_COLUMN, line);
	}

	@Override
	protected void setValue(int line, int index, int point) {
		double referenceValue =	order.pyrometr.reference[calibrationPointCount];
		sheet.setValueAt(referenceValue, 1, line);
		sheet.setValueAt(order.measurmets[index].data[0][point], 3, line);
	}

	@Override
	protected CertificateValue findPointValue(int line, int index) {
		double[] uncerinity = findUncerinity(index, 0);
		setUncerinity(uncerinity, line);
		return setCertificateValue(index, uncerinity);
	}
	
	@Override
	protected void setCalibrationBudget(int line, int index) {
		super.setCalibrationBudget(line, index);
		sheet.setValueAt(order.pyrometr.reference[calibrationPointCount],
        		7 , line + 7);
        sheet.setValueAt(order.pyrometr.blackBodyError[calibrationPointCount],
        		9, line+11);
	}

	private double[] findUncerinity(int index, int parametrIndex) {
		double[] uncerinity = new double[7];
        uncerinity[0] = order.measurmets[index].standardDeviation[parametrIndex];
        uncerinity[1] = Double.parseDouble(order.device.resolution[parametrIndex]) 
        		/ Math.sqrt(3);
        uncerinity[2] = 0;
        uncerinity[3] = 0.1 / Math.sqrt(3);
        uncerinity[4] = reference[index].getUncertainty(parametrIndex) / 2;
        uncerinity[5] = reference[index].getDrift(parametrIndex) / Math.sqrt(3);
        uncerinity[6] = order.pyrometr.blackBodyError[calibrationPointCount] / 2;
		return uncerinity;
	}
	
	private void setUncerinity(double[] uncerinity, int line) {
		for(int i = 0; i < uncerinity.length; i++){
            sheet.setValueAt(uncerinity[i], 13, line + 5 + i);
        }
	}
	
	private CertificateValue setCertificateValue(int index, double[] uncerinities) {
		CertificateValue pointValue = new CertificateValue();
		double uncerinity = findUncerinityAndRound(uncerinities);
        double referenceValue = DataCalculation.roundTonumber(order.pyrometr.reference[calibrationPointCount] 
        		+ reference[index].correction,round);
        double deviceValue = DataCalculation.roundTonumber(order.measurmets[index].average[0], round);
        pointValue.probeT = setNumber(referenceValue);
        pointValue.deviceT = setNumber(deviceValue);
        pointValue.errorT = setNumber(deviceValue - referenceValue);
        pointValue.uncertaintyT = setNumber(2 * uncerinity);
		return pointValue;
	}

	@Override
	protected void setCertificate() {
		certificate = new PyrometerCertificate();
	}
}
