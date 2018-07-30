package certyficate.equipment.calculation;

import certyficate.property.CalibrationData;

public class Calculate {
	protected static final int TEMPERATURE_INDEX = 0;
	
	protected DataProbe[] pointsInRange;
	
	protected double[] point;
	
	public DataProbe findPoint(DataProbe[] pointsInRange, double[] point) {
		this.point = point;
		this.pointsInRange = pointsInRange;
		return getDataPoint(point);
	}
	
	protected DataProbe getDataPoint(double[] point) {
    	DataProbe data  = setCorrections(point);
    	setData(data);
		return data;
	}

	protected DataProbe setCorrections(double[] point) {
    	DataProbe data  = new DataProbe(point);
    	estimateCorection(data);
		return data;
	}

	protected void estimateCorection(DataProbe data) {
		double[] correction = findCorrection();
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) {
			data.setCorrection(correction[i], i);
		}
	}

	protected double[] findCorrection() {
		return calculateCorrection(pointsInRange);
	}
	
	protected double[] calculateCorrection(DataProbe[] points) {
		LineCreator creator = new LineCreator(points);
		return creator.findCorrection(point[TEMPERATURE_INDEX], TEMPERATURE_INDEX);
	}
	

	protected void setData(DataProbe data) {
    	data.setDrift(pointsInRange[TEMPERATURE_INDEX]);
    	findUncertainty(data);	
	}

	protected void findUncertainty(DataProbe data) {
		double[] uncertainty = maxUncertainty();
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) {
			data.setUncertainty(uncertainty[i], i);
		}
	}
	
    private double[] maxUncertainty() {
		double[] uncertainty = new double[CalibrationData.numberOfParameters];
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) {
			uncertainty[i] = maxUncertainty(i);
		}
		return uncertainty;
	}
    
	private double maxUncertainty(int index) {
		double uncertainty = 0D;
		for(int i = 0; i < pointsInRange.length; i++) {
			double pointUncertainty = pointsInRange[i].getUncertainty(index);
			uncertainty = Math.max(uncertainty, pointUncertainty);
		}
		return uncertainty;
	}
}
