package certyficate.equipment.calculation;

class LineCreator {
	private double value;
	private double valueDifference;
	
	private double correction;
	private double correctionDifference;
	
	private double RhCorrection;
	private double RhCorrectionDifference;
	
	private DataProbe[] data;
	
	LineCreator(DataProbe[] data) {
		this.data = data;
		correction = data[0].correction;
		correctionDifference = correction - data[1].correction;
		RhCorrection = data[0].correctionRh;
		RhCorrectionDifference = RhCorrection - data[1].correctionRh;		
	}
	
	double[] findCorrection(double point) {
		value = data[0].value;
		valueDifference = value - data[1].value;
		return getCorrections(point);
	}
	
	double[] findCorrectionRh(double point) {
		value = data[0].valueRh;
		valueDifference = value - data[1].valueRh;
		return getCorrections(point);
	}
	
	private double[] getCorrections(double point) {
		double[] correction = new double[2];
		correction[0] = getCorrection(point);
		correction[1] = getHumidityCorrection(point);
		return correction;
	}

	private double getCorrection(double point) {
		double a = correctionDifference / valueDifference;
		double b = correction - value * a;
		StraightLine line = new StraightLine(a, b);
		return line.findPointValue(point);
	}

	private double getHumidityCorrection(double point) {
		double a = RhCorrectionDifference / valueDifference;
		double b = RhCorrection - value * a;
		StraightLine line = new StraightLine(a, b);
		return line.findPointValue(point);
	}
}
