package certyficate.sheetHandlers.search.order;

import certyficate.entitys.Order;
import certyficate.property.CalibrationData;
import certyficate.property.CalibrationType;

public class CalibrationPoints {
	private final static double[][] TEMPERATURE_STANDARD_POINT = new double[][] {{-25},{0},{25}}; 
	private final static double[][] HUMINIDITY_STANDARD_POINT 
		 = new double[][] {{15, 50}, {25, 30}, {25, 50}, {25, 70}, {35, 50}};
	private final static double[][] INFRARED_STANDARD_POINT = new double[][] {{25},{90},{180}};
	
	private final static char START_POINT = '(';
	private final static char END_POINT = ')';
	private final static char START_HUMINIDITY_POINT = '[';
	private final static char END_HUMINIDITY_POINT = ']';
	
	private final static String POINT_SEPARATOR = ", ";
	private final static String NON_NUMBER = "[^\\d.]";
	private final static String EMPTY_STRING = "";
	

	static double[][] point(){
		double[][] point;
		switch (CalibrationData.calibrationType) {
		case HUMINIDITY:
			point = HUMINIDITY_STANDARD_POINT;
			break;
		case INFRARED:
			point = INFRARED_STANDARD_POINT;
			break;		
		default:
			point = TEMPERATURE_STANDARD_POINT;
			break;
		}
		return point;
	}

	public static void setPoint(Order order) {
		String code = order.getCalibrationCode();
		order.setPoint(findPoints(code));
	}

	private static double[][] findPoints(String code) {
		String[] points = getPoints(code);
		return setPointsArray(points);
	}

	private static String[] getPoints(String code) {
		code = deleteUnnecessaryPart(code);
		return code.split(POINT_SEPARATOR);
	}

	private static String deleteUnnecessaryPart(String code) {
		int start = code.indexOf(START_POINT);
		int end = code.indexOf(END_POINT);
		return code.substring(start, end);
	}

	private static double[][] setPointsArray(String[] points) {
		int numberOfPoints = points.length;
		double[][] point = new double[numberOfPoints][];
		for(int i = 0; i < numberOfPoints; i++) 
			point[i] = getPoint(points[i]);
		return point;
	}

	private static double[] getPoint(String point) {
		double[] pointArray;
		if(CalibrationData.calibrationType == CalibrationType.HUMINIDITY)
			pointArray = findRhPoint(point);
		else
			pointArray = findPoint(point);
		return pointArray;
	}

	private static double[] findRhPoint(String point) {
		double[] pointArray;
		int start = point.indexOf(START_HUMINIDITY_POINT);
		int end = point.indexOf(END_HUMINIDITY_POINT);
		if(start != -1) {
			point = point.substring(start, end);
			pointArray = setRhPoint(point);
		} else {
			pointArray = findPoint(point);
		}
		return pointArray;
	}

	private static double[] setRhPoint(String point) {
		double[] pointArray = new double[2];
		String[] points = point.split(POINT_SEPARATOR);
		for(int i = 0; i < 2; i++) {
			pointArray[i] = findValue(points[i]);
		}
		return pointArray;
	}

	private static double[] findPoint(String point) {
		double[] pointArray = new double[2];
		pointArray[0] = findValue(point);
		return pointArray;
	}
	
	private static int findValue(String point) {
		point = point.replaceAll(NON_NUMBER, EMPTY_STRING);
		return Integer.parseInt(point);
	}
}
