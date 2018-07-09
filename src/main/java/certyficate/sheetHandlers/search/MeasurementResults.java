package certyficate.sheetHandlers.search;

import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.calculation.PointCalculation;
import certyficate.dataContainer.Measurements;
import certyficate.dataContainer.Point;
import certyficate.entitys.Certificate;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class MeasurementResults {
	final private static int MEASUREMENTS_POINTS = 10;
	private static int calibrationPoints;
	
	private static List<Measurements> devices;
	
	private static Sheet sheet;
	
	static void findMeasurmentData() {
		setProperties();
		findDevicesData();
		addMeasurmentsToOrders();
		CalibrationData.devices = devices;
	}	

	private static void setProperties() {
		sheet = MeasurementsData.sheet;
		calibrationPoints = CalibrationData.calibrationPoints;
	}

	private static void findDevicesData() {
		int line = SheetData.START_ROW;
		devices = new ArrayList<Measurements>();
		for(int i = 0; i <= SheetData.NUMBER_OF_DEVICES; i++){
			checkAndAddDevice(line);
            line += SheetData.NUMBER_OF_PARAMETERS;
        }
	}

	private static void checkAndAddDevice(int line) {
		String name = sheet.getValueAt(1,line).toString();
		if(!name.equals("")) {
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
        	line+=SheetData.POINT_GAP;
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
		int column = SheetData.TIME_COLUMN;
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
		if("-".equals(integer)) {
			number = -1;
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
	
	private static void addMeasurmentsToOrders() {
		for(Certificate order: CalibrationData.orders) {
			findAndAddMeasurmentsData(order);
			checkMeasurmentsData(order);
		}
	}

	private static void findAndAddMeasurmentsData(Certificate order) {
		for(Measurements device: devices) {
			check(device, order);
		}
	}

	private static void check(Measurements device, Certificate order) {
		if(device.match(order)) {
			order.measurmets = device.measurmets;
		}
	}
	
	private static void checkMeasurmentsData(Certificate order) {
		if(order.measurmets == null) {
			CalibrationData.orders.remove(order);
		}
	}
}
