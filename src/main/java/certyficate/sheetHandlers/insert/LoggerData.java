package certyficate.sheetHandlers.insert;

import java.util.List;

import certyficate.dataContainer.CalibrationPoint;
import certyficate.dataContainer.Point;
import certyficate.dataContainer.datalogger.Logger;

public class LoggerData {
	private static List<CalibrationPoint> points;
	
	private Point[] loggerData;
	
	private Logger logger;
	
	public LoggerData(List<CalibrationPoint> calibrationPoints) {
		points = calibrationPoints;
	}

	public Point[] getData(Logger logger) {
		loggerData = new Point[points.size()];
		this.logger = logger;
		findPoints();
		return loggerData;
	}

	private void findPoints() {
		// TODO Auto-generated method stub
		
	}

	private void setFile() {
		// TODO Auto-generated method stub
		
	}

}
