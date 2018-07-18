package certyficate.generate.note;


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
	protected void setResolution(String[] resolution, int line) {
		super.setResolution(resolution, line);
		sheet.setValueAt(resolution[1], 4, line);	
	}
	
	@Override
	protected void setValue(int line, int index, int point) {
		super.setValue(line, index, point);
		sheet.setValueAt(referenceValue.measurmets[index].data[point][1], 
				2, line);
		sheet.setValueAt(order.measurmets[index].data[point][1], 
				4, line);
	}
	
	@Override
	protected CertificateValue findPointValue(int line, int index) {
		CertificateValue certificateValue = super.findPointValue(line, index);
		setCalibrationBudgetRh(certificateValue, line, index);
		return certificateValue;
	}

	private void setCalibrationBudgetRh(CertificateValue certificateValue,
			int line, int index) {
		double[] uncerinity = findUncerinityRh(index, 1);
		line += 13;
		setUncerinity(uncerinity, line);
		sheet.setValueAt(order.measurmets[index].average[1], 7 , line + 5);
        sheet.setValueAt(referenceValue.measurmets[index].average[1],
        		7 , line + 7);
        sheet.setValueAt(reference[index].correctionRh, 7 , line +  9);
        sheet.setValueAt(order.device.resolution[1], 9 , line + 6);
        sheet.setValueAt(reference[index].uncertaintyRh, 9, line + 9);
        sheet.setValueAt(reference[index].drift, 9, line + 10);
        sheet.setValueAt(chamber[index].correctionRh, 9, line + 11);
        sheet.setValueAt(chamber[index].uncertaintyRh, 9, line + 12);
        setCertificateValueRh(certificateValue, index, uncerinity);
	}

	private double[] findUncerinityRh(int index, int parametrIndex) {
		double[] uncerinity = super.findUncerinity(index, parametrIndex);
        uncerinity[6] = chamber[index].correctionRh / Math.sqrt(3);
        uncerinity[7] = chamber[index].uncertaintyRh / 2;
		return uncerinity;
	}

	protected void setUncerinity(double[] uncerinity, int line) {
		for(int i = 0; i < uncerinity.length; i++){
            sheet.setValueAt(uncerinity[i], 13, line + 5 + i);
        }
	}
	
	private void setCertificateValueRh(CertificateValue certificateValue,
			int index, double[] uncerinities) {
		double uncerinity = findUncerinityAndRound(uncerinities);
        double referenceData = DataCalculation.roundTonumber(
        		referenceValue.measurmets[index].average[1]
        		+ reference[index].correctionRh, round);
        double deviceValue =DataCalculation.roundTonumber(order.measurmets[index].average[0], round);
        certificateValue.probeRh = setNumber(referenceData);
        certificateValue.deviceRh = setNumber(deviceValue);
        certificateValue.errorRh = setNumber(deviceValue - referenceData);
        certificateValue.uncertaintyRh = setNumber(2 * uncerinity);
	}
	
	@Override
	protected void setPointValue(int line, CertificateValue pointValue) {
		sheet.setValueAt(pointValue.probeT, 5, line + 14);
		sheet.setValueAt(pointValue.deviceT, 7, line + 14);
		sheet.setValueAt(pointValue.errorT, 9, line + 14);
		sheet.setValueAt(pointValue.uncertaintyT, 13, line + 14);
		sheet.setValueAt(pointValue.probeRh, 5, line + 27);
		sheet.setValueAt(pointValue.deviceRh, 7, line + 27);
		sheet.setValueAt(pointValue.errorRh, 9, line + 27);
		sheet.setValueAt(pointValue.uncertaintyRh, 13, line + 27);
		addPointValue(pointValue);
	}
	
	@Override
	protected void setCertificate() {
		certificate = new HuminidityCertificate();
	}
}
