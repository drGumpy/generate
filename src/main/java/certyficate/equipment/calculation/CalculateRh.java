package certyficate.equipment.calculation;

import java.util.Arrays;

public class CalculateRh extends Calculate {
	protected static final int HUMINIDITY_INDEX = 1;
	
	protected double[] findCorrection() {
		double[] correction = checkPoint();
		if(correction == null) {
			correction = calculateCorrection();
		}
		return correction;
	}	

	private double[] checkPoint() {
		double[] correction;
		correction = checkTemperature();
		if(correction == null) {
			correction = checkHuminidity();
		}
		return correction;
	}

	private double[] checkTemperature() {
		double[] correction = null;
		for(int i = 0; i < 2; i++) {
			if(pointsInRange[i].getValue(TEMPERATURE_INDEX) 
					== point[TEMPERATURE_INDEX]){
				pointsInRange = temperatureSubArray(i);
				correction = calculateCorrection(
						pointsInRange);
			}
		}
		return correction;
	}

	private DataProbe[] temperatureSubArray(int index) {
		int startSubArray = 2 * index;
		return Arrays.copyOfRange(pointsInRange, 
				startSubArray,  startSubArray + 2);
	}

	private double[] checkHuminidity() {
		double[] correction = null;
		for(int i = 0; i < 2; i++) {
			if(pointsInRange[2 * i].getValue(HUMINIDITY_INDEX) 
					== point[HUMINIDITY_INDEX]){
				pointsInRange = setHuminiditySubArray(i);
				correction = calculateCorrectionRh(
						pointsInRange);
			}
		}
		return correction;
	}

	private DataProbe[] setHuminiditySubArray(int index) {
		return new DataProbe[] {
				pointsInRange[index],
				pointsInRange[index + 2]
		};
	}

	private double[] calculateCorrectionRh(DataProbe[] points) {
		LineCreator creator = new LineCreator(points);
		return creator.findCorrection(point[HUMINIDITY_INDEX], HUMINIDITY_INDEX);
	}

	private double[] calculateCorrection() {
		double[][] subPoints = setSubPoints();
		DataProbe[] points = new DataProbe[2];
		for(int i = 0; i < 2; i++) {
			points[i] = setPoint(i, subPoints[i]);
		}
		return calculateCorrectionRh(points);
	}
	
	private DataProbe setPoint(int index, double[] point) {
		Calculate calculate = new Calculate();
		DataProbe[] checkPoint = setHuminiditySubArray(index);
		return calculate.findPoint(checkPoint, point);
	}

	private double[][] setSubPoints() {
		double[][] subPoints = new double[2][2];
		for(int i = 0; i < 2; i++) {
			subPoints[i][TEMPERATURE_INDEX] = point[TEMPERATURE_INDEX];
			subPoints[i][HUMINIDITY_INDEX] 
					= pointsInRange[i].getValue(HUMINIDITY_INDEX);
		}
		return subPoints;
	}
}
