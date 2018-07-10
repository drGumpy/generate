package certyficate.property;

import certyficate.dataContainer.CalibrationType;

public class SheetData {
	public static String sheetName;
	public static String probeSerialNumber;
	
	public static boolean Rh;
	
	public static int dateColumn;
	public static int timeColumn;
	public static int pointGap;
	public static int startRow;
	public static int numberOfDevices;

	public static void setChamberData(CalibrationType calibrationType) {
		if(calibrationType == CalibrationType.HUMINIDITY){
			Rh = true;
			dateColumn = 34;
			timeColumn = 3;
			pointGap = 45;
			startRow = 10;
			CalibrationData.numberOfParameters = 2;
			sheetName = "Zapiska Temp & RH";
			probeSerialNumber = "61602551"; // "20055774";
	        numberOfDevices = 20;
		}else{
			setTemperatureSettings();
			probeSerialNumber = "20098288"; // "20068251";
		}
	}
	
	public static void setInfrared() {
		setTemperatureSettings();
		probeSerialNumber = "12030011";
	}
	
	private static void setTemperatureSettings() {
		dateColumn = 33;
		timeColumn = 2;
		pointGap = 34;
		startRow = 9;
		sheetName = "Zapiska temp.";
		numberOfDevices = 30;
	}
}
