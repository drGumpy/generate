package certyficate.sheetHandlers.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.dataContainer.*;
import certyficate.entitys.Order;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class MeasurementsData {
	private static List<CalibrationPoint> points;
    
	static Sheet sheet;
	
	private static List<Measurements> devices;

    public static void findProbeData() {
    	int line = SheetData.startRow - CalibrationData.numberOfParameters;
    	Measurements reference = MeasurementResults.findDeviceResults(line);
    	CalibrationData.patern = reference;
	}
	
	public static void findMeasurementsData() throws IOException {
		setSheet();
		getCalibtationPoints();
		findMeasurments();
	}
	
	private static void findMeasurments() {
		devices = MeasurementResults.findMeasurmentData();
		addMeasurmentsToOrders();
	}

	private static void setSheet() throws IOException {
		File file = CalibrationData.sheet;
		sheet = SpreadSheet.createFromFile(file).getSheet(SheetData.sheetName);
	}
	
	private static void getCalibtationPoints() {
		points = new ArrayList<CalibrationPoint>();
		int line = 6;
		for(int i = 0; i < CalibrationData.calibrationPoints; i++){
			addCalibrationPoint(line);
			line += SheetData.pointGap;
		}
		CalibrationData.point = points;
	}

	private static void addCalibrationPoint(int line) {
		CalibrationPoint point = new CalibrationPoint();
		point.setTime(sheet.getValueAt(SheetData.timeColumn, line)
				.toString());
		point.setDate(sheet.getValueAt(SheetData.dateColumn, line)
				.toString());
		point.set(points.size());
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
	
	private static void addMeasurmentsToOrders() {
		for(Order order: CalibrationData.orders) {
			findAndAddMeasurmentsData(order);
			checkMeasurmentsData(order);
		}
	}

	private static void findAndAddMeasurmentsData(Order order) {
		for(Measurements device: devices) {
			check(device, order);
		}
	}

	private static void check(Measurements device, Order order) {
		if(device.match(order)) {
			order.measurmets = device.measurmets;
		}
	}
	
	private static void checkMeasurmentsData(Order order) {
		if(order.measurmets == null) {
			CalibrationData.orders.remove(order);
		}
	}
}
 

