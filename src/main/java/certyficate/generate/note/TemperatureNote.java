package certyficate.generate.note;


import certyficate.entitys.Device;
import certyficate.equipment.calculation.DataProbe;
import certyficate.generate.CertificateValue;
import certyficate.generate.certificate.TemperatureCertificate;
import certyficate.property.CalibrationData;
import certyficate.property.DataCalculation;
import certyficate.sheetHandlers.search.measurments.Measurements;

public class TemperatureNote extends Note {
	private static final String NOTE_FILE = "z_T.ods";
	
	private static final int NUMBER_OF_DATA = 3;
	
	protected Measurements referenceValue;
	
	protected DataProbe[] chamber;
	
	public TemperatureNote() {
		super();
		setDeviceData();
	}
	
	@Override
	protected void setData() {
		noteFile = NOTE_FILE;
		numberOfData = NUMBER_OF_DATA;
	}

	private void setDeviceData() {
		referenceValue = CalibrationData.patern;
		chamber = CalibrationData.chamber;
	}

	@Override
	protected void setResolution(Device device, int line) {
		sheet.setValueAt(device.getResolution(0), 3, line);
	}


	@Override
	protected void setValue(int line, int index, int point) {
		String time = CalibrationData.point.get(index).getTime();
		sheet.setValueAt(DataCalculation.time(time, point), 0, line);
		sheet.setValueAt(referenceValue.measurmets[index].data[0][point], 
				1, line);
		sheet.setValueAt(order.getMeasurments(index).data[0][point], 
				3, line);
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
		sheet.setValueAt(referenceValue.measurmets[index].average[0],
        		7 , line + 7);
        sheet.setValueAt(chamber[index].correction, 9, line + 11);
        sheet.setValueAt(chamber[index].uncertainty, 9, line + 12);
	}

	protected double[] findUncerinity(int index, int parametrIndex) {
		double[] uncerinity = new double[8];
        uncerinity[0] = order.getMeasurments(index).standardDeviation[parametrIndex];
        uncerinity[1] = Double.parseDouble(order.getDevice().getResolution(parametrIndex)) 
        		/ Math.sqrt(3);
        uncerinity[2] = referenceValue.measurmets[index].standardDeviation[parametrIndex];
        uncerinity[3] = 0.01 / Math.sqrt(3);
        uncerinity[4] = reference[index].getUncertainty(parametrIndex) / 2;
        uncerinity[5] = reference[index].getDrift(parametrIndex) / Math.sqrt(3);
        uncerinity[6] = chamber[index].correction / Math.sqrt(3);
        uncerinity[7] = chamber[index].uncertainty / 2;
		return uncerinity;
	}
	
	protected void setUncerinity(double[] uncerinity, int line) {
		for(int i = 0; i < uncerinity.length; i++){
            sheet.setValueAt(uncerinity[i], 13, line + 5 + i);
        }
	}
	
	private CertificateValue setCertificateValue(int index, double[] uncerinities) {
		CertificateValue pointValue = new CertificateValue();
		double uncerinity = findUncerinityAndRound(uncerinities);
        double referenceData = DataCalculation.roundTonumber(
        		referenceValue.measurmets[index].average[0]
        		+ reference[index].correction, round);
        double deviceValue =DataCalculation.roundTonumber(order.getMeasurments(index).average[0], round);
        pointValue.probeT = setNumber(referenceData);
        pointValue.deviceT = setNumber(deviceValue);
        pointValue.errorT = setNumber(deviceValue - referenceData);
        pointValue.uncertaintyT = setNumber(2 * uncerinity);
		return pointValue;
	}

	@Override
	protected void setCertificate() {
		certificate = new TemperatureCertificate();
	}

}
