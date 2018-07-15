package certyficate.sheetHandlers.search;

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class MeasurementResults {
	final private static int MEASUREMENTS_POINTS = 10;
	final private static String NON_DATA = "-";
	final private static String EMPTY_CELL = "";
	
	private static int calibrationPoints;
	
	private static List<Measurements> devices;
	
	private static Sheet sheet;
	
	static List<Measurements> findMeasurmentData() {
		setProperties();
		findDevicesData();
		return devices;
	}	

	private static void setProperties() {
		sheet = MeasurementsData.sheet;
		calibrationPoints = CalibrationData.calibrationPoints;
	}

	private static void findDevicesData() {
		int line = SheetData.startRow;
		devices = new ArrayList<Measurements>();
		for(int i = 0; i <= SheetData.numberOfDevices; i++){
			checkAndAddDevice(line);
            line += CalibrationData.numberOfParameters;
        }
	}

	private static void checkAndAddDevice(int line) {
		String name = sheet.getValueAt(1,line).toString();
		if(!EMPTY_CELL.equals(name)) {
			Measurements device = findDeviceResults(line);
			device.name = name;
			devices.add(device);
		}
	}

	static Measurements findDeviceResults(int line) {
		Measurements device = 
        		new Measurements(calibrationPoints);
        for(int i = 0; i < calibrationPoints; i++){
        	device.measurmets[i] = findPoint(line);
        	line += SheetData.pointGap;
        }            
        return device;
	}

	private static Point findPoint(int line) {
		Point point;
        try{
        	point = findPointData(line);
        	PointCalculation.calculate(point);
        }catch(NumberFormatException e) {
        	point = Point.setFalse();;
        }
		return point;
	}

	private static Point findPointData(int line)
			throws NumberFormatException {
		Point point = new Point(calibrationPoints);
		for(int i = 0; i < calibrationPoints; i++) {
			point.data[i] = getMeasurmentData(line);
			line++;
		}
		return point;
	}
	
	private static double[] getMeasurmentData(int line)
			throws NumberFormatException {
		double[] mesurmentData = new double[MEASUREMENTS_POINTS];
		int column = SheetData.timeColumn;
		for(int i = 0; i < MEASUREMENTS_POINTS; i++) {
			mesurmentData[i] = getData(column, line);
			column += 3;
		}
		return mesurmentData;
	}

	private static double getData(int column, int line)
			throws NumberFormatException {
		double number;
		String integer = sheet.getValueAt(column,line).toString();
		if(NON_DATA.equals(integer)) {
			number = Double.NaN;
		} else {
			String decimal = sheet.getValueAt(column,line).toString();
			number = createDouble(integer, decimal);
		}
		return number;
	}

	private static double createDouble(String integer, String decimal)
			throws NumberFormatException {
		String number = createNumber(integer, decimal);
		double newNumber = Double.parseDouble(number);
		return newNumber;
	}

	private static String createNumber(String integer, String decimal) {
		StringBuilder bulider = new StringBuilder(integer);
		bulider.append(".");
		bulider.append(decimal);
		return bulider.toString();
	}
}
