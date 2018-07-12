package certyficate.sheetHandlers.insert;

import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.dataContainer.CalibrationPoint;
import certyficate.dataContainer.PointData;
import certyficate.datalogger.Logger;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class DataInsert {
	private static final int MEASUREMENTS_POINTS = 10;
	private int devicePosition;
	
	private Sheet sheet;
	
	private List<CalibrationPoint> points;
	
	private boolean Rh;
	
	private PointData[][] data;

	public DataInsert(Sheet sheet) {
		this.sheet = sheet;
		Rh = SheetData.Rh;
	}

	public void setPoints(List<CalibrationPoint> points) {
		this.points = points;
	}

	public void putLoggerData(Logger logger) {
		setLoggerData(logger);
		insertData();
	}

	private void setLoggerData(Logger logger) {
		data = logger.getPointData();
		devicePosition = logger.getDeviceNumber();
	}
	
	private void insertData() {
		int length = data.length;
		for(int i = 0; i < length; i++) {
			insetPointData(i);
		}
	}

	private void insetPointData(int index) {
		int line = setLine(index);
		insetLine(data[index], line);
	}

	private void insetLine(PointData[] data, int line) {
		int column = SheetData.startRow;
		for(int i = 0; i < MEASUREMENTS_POINTS; i++) {
			insertPoint(data[i], column, line);
			column += 3;
		}
	}

	private void insertPoint(PointData data, int column, int line) {
		setPoint(data.getTemperature(), column, line);
		if(Rh) {
			setPoint(data.getHumidity(), column, line + 1);
		}
	}

	private void setPoint(String[] array, int column, int line) {
		sheet.setValueAt(array[0], column, line);
		sheet.setValueAt(array[1], column + 2, line);
	}

	private int setLine(int index) {
		int line = SheetData.startRow;
		line += CalibrationData.numberOfParameters * devicePosition;
		line += SheetData.pointGap * points.get(index).number;
		return line;
	}
}
