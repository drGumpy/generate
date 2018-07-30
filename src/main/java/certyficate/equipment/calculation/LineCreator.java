package certyficate.equipment.calculation;

import certyficate.property.CalibrationData;

class LineCreator {
	private int size;
	
	private double value;
	private double valueDifference;
	
	private double[] correction;
	private double[] correctionDifference;
	
	private DataProbe[] data;
	
	LineCreator(DataProbe[] data) {
		this.data = data;
		setSize();
		setCorrection();
	}
	
	double[] findCorrection(double point, int index) {
		value = data[0].getValue(index);
		valueDifference = value - data[1].getValue(index);
		return getCorrections(point);
	}

	private void setSize() {
		size = CalibrationData.numberOfParameters;
		correction = new double[size];
		correctionDifference = new double[size];
	}
	
	private void setCorrection() {
		for(int i = 0; i < size; i++) {
			correction[i] = data[0].getCorrection(i);
			correctionDifference[i] = correction[i] - data[1].getCorrection(i);
		}
	}
	
	private double[] getCorrections(double point) {
		double[] correction = new double[size];
		for(int i = 0; i < size; i++) {
			correction[i] = getCorrection(point, i);
		}
		return correction;
	}

	private double getCorrection(double point, int index) {
		double a = correctionDifference[index] / valueDifference;
		double b = correction[index] - value * a;
		StraightLine line = new StraightLine(a, b);
		return line.findPointValue(point);
	}
}
