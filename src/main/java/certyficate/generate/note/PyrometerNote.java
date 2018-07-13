package certyficate.generate.note;

import certyficate.calculation.DataCalculation;
import certyficate.dataContainer.CertificateValue;
import certyficate.generate.certificate.PyrometerCertificate;

public class PyrometerNote extends Note {
	protected static String noteFile = "z_T.ods";
	
	@Override
	protected void setResolution(String[] resolution, int line) {
		sheet.setValueAt(resolution[0], 3, line);
	}

	@Override
	protected void setMeasurmentValue(int line, int index) {
		double referenceValue =	order.pyrometr.reference[calibrationPointCount];
		for(int i = 0; i < 10; i++){
			sheet.setValueAt(referenceValue, 1, line+17+i);
			sheet.setValueAt(order.measurmets[index].data[i][0], 3, line + 17 + i);
		}
	}
	
	@Override
	protected CertificateValue setCalibrationBudget(int line, int index) {
		double[] uncerinity = findUncerinity(index, 0);
		setUncerinity(uncerinity, line);
		sheet.setValueAt(order.measurmets[index].average[0], 7 , line+5);
        sheet.setValueAt(order.pyrometr.reference[calibrationPointCount],
        		7 , line+7);
        sheet.setValueAt(reference[index].correction, 7 , line+9);
        sheet.setValueAt(order.device.resolution[0], 9 , line+6);
        sheet.setValueAt(reference[index].uncertainty, 9, line+9);
        sheet.setValueAt(reference[index].drift, 9, line+10);
        sheet.setValueAt(order.pyrometr.blackBodyError[calibrationPointCount],
        		9, line+11);
        return setCertificateValue(index, uncerinity);
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
            sheet.setValueAt(uncerinity[i], 13, line+5+i);
        }
	}
	
	private CertificateValue setCertificateValue(int index, double[] uncerinities) {
		CertificateValue pointValue = new CertificateValue();
		double uncerinity = findUncerinityAndRound(uncerinities);
        double referenceValue = DataCalculation.round_d(order.pyrometr.reference[calibrationPointCount] 
        		+ reference[index].correction,round);
        double deviceValue =DataCalculation.round_d(order.measurmets[index].average[0], round);
        pointValue.probeT = setNumber(referenceValue);
        pointValue.deviceT = setNumber(deviceValue);
        pointValue.errorT = setNumber(deviceValue - referenceValue);
        pointValue.uncertaintyT = setNumber(2 * uncerinity);
		return pointValue;
	}

	protected double findUncerinityAndRound(double[] uncerinities) {
		double uncerinity =DataCalculation.uncertainty(uncerinities);
		round = DataCalculation.findRound(2*uncerinity, 
				Double.parseDouble(order.device.resolution[0]));
		return uncerinity;
	}

	@Override
	protected void setCertificate() {
		certificate = new PyrometerCertificate();
	}
}
