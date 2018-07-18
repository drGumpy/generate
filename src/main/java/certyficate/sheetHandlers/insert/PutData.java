package certyficate.sheetHandlers.insert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.CalibrationPoint;
import certyficate.datalogger.*;
import certyficate.datalogger.type.Rotronic;
    
public class PutData {
	private static final String ERROR = "Calibration sheet error";
	
	private static Sheet sheet;    
    
    private static int numberOfPoints;
    
    private static File file;
        
    private static List<Logger> loggers;
    private static List<CalibrationPoint> points;
    
    private static DataInsert insert;
        
    public static void insertReferenceAndLoggersData() {
    	try {
			setData();
		} catch (IOException e) {
			System.out.println(ERROR);
			e.printStackTrace();
		}
    }
    
    private static void setData() throws IOException {
    	updateSettings();
		setCalibrationPoints();
		setProbeData();
		setLoggerData();
	}

	private static void updateSettings() throws IOException {
		numberOfPoints = CalibrationData.calibrationPoints;
		file = CalibrationData.sheet; 
		setSheet();
	}

	private static void setSheet() throws IOException {
		sheet = SpreadSheet.createFromFile(file).getSheet(SheetData.sheetName);
		insert = new DataInsert(sheet);
	}
	
	private static void setCalibrationPoints() {
		points = new ArrayList<CalibrationPoint>();
		findCalibrationPoints();
		Collections.sort(points, CalibrationPoint.comparator);
		insert.setPoints(points);
	}
	
	private static void findCalibrationPoints() {
		for (int i = 0; i < numberOfPoints; i++){
			addPoint(i);
		}
	}

	private static void addPoint(int index) {
		int line = 6 + SheetData.pointGap * index;
		CalibrationPoint point = findPoint(line);
		point.setPointNumber(index);
		points.add(point);
	}


	private static CalibrationPoint findPoint(int line) {
		CalibrationPoint point = new CalibrationPoint();
		point.setDate(sheet.getValueAt(SheetData.dateColumn, line).toString());
		point.setTime(sheet.getValueAt(SheetData.timeColumn, line).toString());
		return point;
	}
	
	private static void setProbeData() {
		Logger reference = new Rotronic(SheetData.Rh);
		insertLoggerData(reference);	
	}

	private static void setLoggerData() {
		findLoggers();
		insertLoggersData();
	}

	private static void findLoggers() {
		loggers = LoggersFinder.findLoggersFile(sheet);
	}
	
	private static void insertLoggersData() {
		for(Logger logger: loggers) {
			insertLoggerData(logger);
		}
	}

	private static void insertLoggerData(Logger logger) {
		logger.getData(points);
		insert.putLoggerData(logger);
	}
}