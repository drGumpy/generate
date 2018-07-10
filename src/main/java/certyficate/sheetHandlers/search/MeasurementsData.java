package certyficate.sheetHandlers.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
 
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.dataContainer.*;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class MeasurementsData {
	private static ArrayList<CalibrationPoint> points 
		= new ArrayList<CalibrationPoint>();
    
	static Sheet sheet;
    
	private static int calibrationPoints 
		= CalibrationData.calibrationPoints;

    public static void findProbeData() {
    	int line = SheetData.startRow - CalibrationData.numberOfParameters;
    	Measurements reference = MeasurementResults.findDeviceResults(line);
    	CalibrationData.patern = reference;
	}
	
	public static void findMeasurementsData() throws IOException {
		setSheet();
		getCalibtationPoints();
		MeasurementResults.findMeasurmentData();
	}
	
	private static void setSheet() throws IOException {
		File file = CalibrationData.sheet;
		sheet = SpreadSheet.createFromFile(file).getSheet(SheetData.sheetName);
	}
	
	private static void getCalibtationPoints() {
		int line = 6;
		for(int i = 0; i < calibrationPoints; i++){
			addCalibrationPoint(line);
			line += SheetData.pointGap;
		}
	}

	private static void addCalibrationPoint(int line) {
		CalibrationPoint point = new CalibrationPoint();
		point.time = sheet.getValueAt(SheetData.timeColumn, line)
				.toString();
		point.date = sheet.getValueAt(SheetData.dateColumn, line)
				.toString();
		point.number = points.size();
		point.point = getPoint(line);
		points.add(point);
	}

	private static double[] getPoint(int line) {
		double[] point = new double[CalibrationData.numberOfParameters];
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) 
			point[i] = Double.parseDouble(sheet.getValueAt(1 - i, line)
					.toString());
		return point;
	}
}
 

