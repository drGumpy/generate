package certyficate.sheetHandlers.search.measurments;

public class PointCalculation {
	final private static int MEASUREMENTS_POINTS = 10;
	
	private static Point point;
	
	public static Point setFalse() {
		Point point = new Point(1);
		point.haveMeasurments = false;
		return point;
	}
	
	public static void calculate(Point currentPoint) {
		point = currentPoint;
		calculateResults();
	}

	private static void calculateResults() {
		for(int i = 0; i < point.numberOfParamters; i++) {
			setResults(i);
		}
	}

	private static void setResults(int index) {
		point.average[index] = calculaleAverage(index);
		point.standardDeviation[index] = calculateStandardDeviation(index);
	}

	private static double calculaleAverage(int index) {
		double average = calculaleSum(index);
		average /= MEASUREMENTS_POINTS;
		return average;
	}
	
	private static double calculaleSum(int index) {
		double sum = 0;
		for(int i = 0; i < MEASUREMENTS_POINTS; i++) {
			sum += point.data[index][i];
		}
		return sum;
	}

	private static double calculateStandardDeviation(int index) {
        double standardDeviation = sumOfSquares(index);
        standardDeviation /= MEASUREMENTS_POINTS;
        standardDeviation /= (MEASUREMENTS_POINTS - 1);
        return Math.sqrt(standardDeviation);
	}

	private static double sumOfSquares(int index) {
		double sum = 0;
		for(int i = 0; i < MEASUREMENTS_POINTS; i++) {
			sum += square(index, i);
		}
		return sum;
	}

	private static double square(int index, int i) {
		double number = point.data[index][i] - point.average[index];
		return Math.pow(number, 2);
	}
}
