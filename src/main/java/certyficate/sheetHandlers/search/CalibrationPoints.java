package certyficate.sheetHandlers.search;

import certyficate.dataContainer.CalibrationType;
import certyficate.entitys.Certificate;
import certyficate.property.CalibrationData;

public class CalibrationPoints {
	private final static int[][] TEMPERATURE_STANDARD_POINT = new int[][] {{-25},{0},{25}}; 
	private final static int[][] HUMINIDITY_STANDARD_POINT 
		 = new int[][] {{15, 50}, {25, 30}, {25, 50}, {25, 70}, {35, 50}};
	private final static int[][] INFRARED_STANDARD_POINT = new int[][] {{25},{90},{180}};
	
	private final static char START_POINT = '(';
	private final static char END_POINT = ')';
	private final static char START_HUMINIDITY_POINT = '[';
	private final static char END_HUMINIDITY_POINT = ']';
	
	private final static String POINT_SEPARATOR = ", ";

	static int[][] point(){
		int[][] point;
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

	public static void setPoint(Certificate order) {
		String code = order.calibrationCode;
		order.point = findPoints(code);
	}

	private static int[][] findPoints(String code) {
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

	private static int[][] setPointsArray(String[] points) {
		int numberOfPoints = points.length;
		int[][] point = new int[numberOfPoints][];
		for(int i = 0; i < numberOfPoints; i++) 
			point[i] = getPoint(points[i]);
		return point;
	}

	private static int[] getPoint(String point) {
		int[] pointArray;
		if(CalibrationData.calibrationType == CalibrationType.HUMINIDITY)
			pointArray = findRhPoint(point);
		else
			pointArray = findPoint(point);
		return pointArray;
	}

	private static int[] findRhPoint(String point) {
		int[] pointArray;
		int start = point.indexOf(START_HUMINIDITY_POINT);
		int end = point.indexOf(END_HUMINIDITY_POINT);
		if(start != -1) {
			point = point.substring(start, end);
			pointArray = setRhPoint(point);
		} else
			pointArray = findPoint(point);
		return pointArray;
	}

	private static int[] setRhPoint(String point) {
		int[] pointArray = new int[2];
		String[] points = point.split(POINT_SEPARATOR);
		for(int i = 0; i < 2; i++)
			pointArray[i] = findValue(points[i]);
		return pointArray;
	}

	private static int[] findPoint(String point) {
		int[] pointArray = new int[2];
		pointArray[0] = findValue(point);
		return pointArray;
	}
	
	private static int findValue(String point) {
		String nonNumber = "[^\\d.]";
		point = point.replaceAll(nonNumber, "");
		return Integer.parseInt(point);
	}
}
