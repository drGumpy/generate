package certyficate.equipment.calculation;

import java.util.Arrays;

public class CalculateRh extends Calculate {
	
	protected double[] findCorrection() {
		double[] correction = checkPoint();
		if(correction == null)
			correction = calculateCorrection();
		return correction;
	}	

	private double[] checkPoint() {
		double[] correction;
		correction = checkTemperature();
		if(correction == null)
			correction = checkHuminidity();
		return correction;
	}

	private double[] checkTemperature() {
		double[] correction = null;
		for(int i = 0; i < 2; i++)
			if(pointsInRange[i].value == point[0]){
				pointsInRange = temperatureSubArray(i);
				correction = calculateCorrection(
						pointsInRange);
			}
		return correction;
	}

	private DataProbe[] temperatureSubArray(int index) {
		int startSubArray = 2 * index;
		DataProbe[] array = Arrays.copyOfRange(pointsInRange, 
				startSubArray,  startSubArray + 1);
		return array;
	}

	private double[] checkHuminidity() {
		double[] correction = null;
		for(int i = 0; i < 2; i++)
			if(pointsInRange[2 * i].value == point[2]){
				setHuminiditySubArray(i);
				correction = calculateCorrectionRh(
						pointsInRange);
			}
		return correction;
	}

	private void setHuminiditySubArray(int index) {
		pointsInRange = new DataProbe[] {
				pointsInRange[index],
				pointsInRange[index + 2]
		};
	}

	private double[] calculateCorrectionRh(DataProbe[] points) {
		LineCreator creator = new LineCreator(points);
		return creator.findCorrectionRh(point[1]);
	}

	private double[] calculateCorrection() {
		int[][] subPoints = setSubPoints();
		DataProbe[] points = new DataProbe[2];
		for(int i = 0; i < 2; i++)
			points[i] = setPoint(i, subPoints[i]);
		return calculateCorrectionRh(points);
	}
	
	private DataProbe setPoint(int index, int[] point) {
		Calculate calculate = new Calculate();
		DataProbe[] checkPoint = temperatureSubArray(index);
		return calculate.findPoint(checkPoint, point);
	}

	private int[][] setSubPoints() {
		int[][] subPoints = new int[2][2];
		for(int i = 0; i < 2; i++) {
			subPoints[i][0] = point[0];
			subPoints[i][1] = (int) pointsInRange[i].valueRh;
		}
		return subPoints;
	}
}
