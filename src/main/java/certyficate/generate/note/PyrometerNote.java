package certyficate.generate.note;

import certyficate.entitys.Device;
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
	protected void setResolution(Device device, int line) {
		sheet.setValueAt(device.getResolution(0), DEVICE_COLUMN, line);
	}

	@Override
	protected void setValue(int line, int index, int point) {
		double referenceValue =	order.getPyrometrData().reference[calibrationPointCount];
		sheet.setValueAt(referenceValue, 1, line);
		sheet.setValueAt(order.getMeasurments(index).data[0][point], 3, line);
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
		sheet.setValueAt(order.getPyrometrData().reference[calibrationPointCount],
        		7 , line + 7);
        sheet.setValueAt(order.getPyrometrData().blackBodyError[calibrationPointCount],
        		9, line+11);
	}

	@Override
	protected void setCertificate() {
		certificate = new PyrometerCertificate();
	}

	private double[] findUncerinity(int index, int parametrIndex) {
		double[] uncerinity = new double[7];
        uncerinity[0] = order.getMeasurments(index).standardDeviation[parametrIndex];
        uncerinity[1] = Double.parseDouble(order.getDevice().getResolution(parametrIndex)) 
        		/ Math.sqrt(3);
        uncerinity[2] = 0;
        uncerinity[3] = 0.1 / Math.sqrt(3);
        uncerinity[4] = reference[index].getUncertainty(parametrIndex) / 2;
        uncerinity[5] = reference[index].getDrift(parametrIndex) / Math.sqrt(3);
        uncerinity[6] = order.getPyrometrData().blackBodyError[calibrationPointCount] / 2;
		return uncerinity;
	}
	
	private void setUncerinity(double[] uncerinity, int line) {
		for(int i = 0; i < uncerinity.length; i++){
            sheet.setValueAt(uncerinity[i], 13, line + 5 + i);
        }
	}
	
	private CertificateValue setCertificateValue(int index, double[] uncerinities) {
		CertificateValue pointValue = new CertificateValue();
		double uncerinity = findUncerinityAndRound(uncerinities, 0);
        double referenceValue = DataCalculation.roundTonumber(order.getPyrometrData().reference[calibrationPointCount] 
        		+ reference[index].getCorrection(0), round);
        double deviceValue = DataCalculation.roundTonumber(order.getMeasurments(index).average[0], round);
        pointValue.setProbe(setNumber(referenceValue), 0);
        pointValue.setDevice(setNumber(deviceValue), 0);
        pointValue.setError(setNumber(deviceValue - referenceValue), 0);
        pointValue.setUncertainty(setNumber(2 * uncerinity), 0);
		return pointValue;
	}
}