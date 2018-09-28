package certyficate.generate.note;

import certyficate.entitys.Device;
import certyficate.generate.CertificateValue;
import certyficate.generate.certificate.HuminidityCertificate;
import certyficate.property.DataCalculation;

public class HuminidityNote extends TemperatureNote {
	private static final String NOTE_FILE = "z_Rh.ods";
	
	private static final int NUMBER_OF_DATA = 5;
	
	public HuminidityNote() {
		super();
	}
	
	@Override
	protected void setData() {
		noteFile = NOTE_FILE;
		numberOfData = NUMBER_OF_DATA;
	}

	@Override
	protected void setResolution(Device device, int line) {
		super.setResolution(device, line);
		sheet.setValueAt(device.getResolution(1), 4, line);	
	}
	
	@Override
	protected CertificateValue findPointValue(int line, int index) {
		CertificateValue certificateValue = super.findPointValue(line, index);
		setCalibrationBudgetRh(certificateValue, line, index);
		return certificateValue;
	}
	
	@Override
	protected boolean havePointData(int index) {
		return super.havePointData(index) 
				&& (reference[index].getValue(1) == order.getPoint(calibrationPointCount, 1));
	}
	
	protected void setUncerinity(double[] uncerinity, int line) {
		for(int i = 0; i < uncerinity.length; i++){
            sheet.setValueAt(checkValue(uncerinity[i]), 13, line + 5 + i);
        }
	}
	
	@Override
	protected void setPointValue(int line, CertificateValue pointValue) {
		setPointValue(line + 14, pointValue, 0);
		setPointValue(line + 27, pointValue, 1);
		addPointValue(pointValue);
	}
	
	private void setPointValue(int line, CertificateValue pointValue, int paramertIndex) {
		sheet.setValueAt(pointValue.getProbe(paramertIndex), 5, line);
		sheet.setValueAt(pointValue.getDevice(paramertIndex), 7, line);
		sheet.setValueAt(pointValue.getError(paramertIndex), 9, line);
		sheet.setValueAt(pointValue.getUncertainty(paramertIndex), 13, line);
	}

	@Override
	protected void setCertificate() {
		certificate = new HuminidityCertificate();
	}

	private void setCalibrationBudgetRh(CertificateValue certificateValue,
			int line, int index) {
		double[] uncerinity = findUncerinity(index, 1);
		line += 13;
		setUncerinity(uncerinity, line);
		sheet.setValueAt(checkValue(order.getMeasurments(index).average[1]), 7 , line + 5);
        sheet.setValueAt(referenceValue.measurmets[index].average[1],
        		7 , line + 7);
        sheet.setValueAt(reference[index].getCorrection(1), 7 , line +  9);
        sheet.setValueAt(order.getDevice().getResolution(1), 9 , line + 6);
        sheet.setValueAt(reference[index].getUncertainty(1), 9, line + 9);
        sheet.setValueAt(reference[index].getDrift(1), 9, line + 10);
        sheet.setValueAt(chamber[index].getCorrection(1), 9, line + 11);
        sheet.setValueAt(chamber[index].getUncertainty(1), 9, line + 12);
        setCertificateValueRh(certificateValue, index, uncerinity);
	}
	
	private void setCertificateValueRh(CertificateValue certificateValue,
			int index, double[] uncerinities) {
		double uncerinity = findUncerinityAndRound(uncerinities, 1);
        double referenceData = DataCalculation.roundTonumber(
        		referenceValue.measurmets[index].average[1]
        		+ reference[index].getCorrection(1), round);
        double deviceValue = DataCalculation.roundTonumber(order.getMeasurments(index).average[1], round);
        System.out.println(order.getMeasurments(index).average[1]);
        System.out.println(deviceValue);
        certificateValue.setProbe(setNumber(referenceData), 1);
        certificateValue.setDevice(setNumber(deviceValue), 1);
        certificateValue.setError(setNumber(deviceValue - referenceData), 1);
        certificateValue.setUncertainty(setNumber(2 * uncerinity), 1);
	}
}