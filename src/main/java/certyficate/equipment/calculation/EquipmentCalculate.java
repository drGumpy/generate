package certyficate.equipment.calculation;

import certyficate.property.CalibrationData;

public class EquipmentCalculate extends Calculate {
	@Override
	protected DataProbe getDataPoint(double[] point) {
    	DataProbe data  = setCorrections(point);
    	findUncertainty(data);
		return data;
	}
	
	protected double[] findCorrection() {
		return findMaxCorrection(pointsInRange);
	}

	private double[] findMaxCorrection(DataProbe[] pointsInRange) {
		double[] correction = new double[CalibrationData.numberOfParameters];
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) {
			correction[i] = maxCorrection(i);
		}
		return correction;
	}
	
	private double maxCorrection(int index) {
		double correction = 0D;
		for(int i = 0; i < pointsInRange.length; i++) {
			double pointCorrection = pointsInRange[i].getCorrection(index);
			correction = Math.max(correction, pointCorrection);
		}
		return correction;
	}
}
