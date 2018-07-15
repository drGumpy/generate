package certyficate.datalogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import certyficate.files.ReaderCreator;
import certyficate.sheetHandlers.CalibrationPoint;

public abstract class Logger {
	private static final int MEASUREMENTS_POINTS = 10;
	private File file;
	
	protected boolean Rh;
	
	protected int nonDataLine;
	
	protected List<CalibrationPoint> calibrationPoints;
	
	protected PointData[][] data;
	
	protected int currentPoint;
	protected int deviceNumber;
	
	public Logger(boolean RH){
		this.Rh = RH;
	}
	
	public PointData[][] getPointData() {
		return data;
	}
	
	public int getDeviceNumber() {
		return deviceNumber;
	}
	
	public List<CalibrationPoint> getCalibrationPoints() {
		return calibrationPoints;
	}
	
	public void setFile(File file) {
		this.file= file;		
	}

	public void setDeviceNumber(int index) {
		deviceNumber = index;
	}
	
	public void getData(List<CalibrationPoint> calibrationPoints) {
		this.calibrationPoints = calibrationPoints;
		setData();
		findDataPoints();
	}
	
	protected void setData() {
		data = new PointData[calibrationPoints.size()][MEASUREMENTS_POINTS];
		currentPoint = 0;
	}
	
	protected void findDataPoints() {
		try {
			findPoints();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void findPoints() throws IOException {
    	BufferedReader reader = ReaderCreator.getReader(file);
    	removeNonDataLine(reader);
    	getPoint(reader);
    	reader.close();
    }

	private void removeNonDataLine(BufferedReader reader) throws IOException {
		for(int i = 0; i < nonDataLine; i++) {
			reader.readLine();
		}
	}
	
	private void getPoint(BufferedReader reader) throws IOException {
		int arrayLength = calibrationPoints.size();
		for(; currentPoint < arrayLength; currentPoint++) {
			findPoint(reader);
		}
	}

	private void findPoint(BufferedReader reader) throws IOException {
		CalibrationPoint point = calibrationPoints.get(currentPoint);
		try {
			findPoint(reader, point);
		} catch (IOException e) {
			System.out.println("No data for point " + currentPoint);
			throw e;
		}
	}

	private void findPoint(BufferedReader reader, CalibrationPoint point) 
			throws IOException {
		String line;
		while((line = reader.readLine()) != null) {
			PointData pointData = divide(line);
			if(pointData.equalsData(point)) {
				data[currentPoint][0] = pointData;
				setPointData(reader);
				break;
			}
		}
		checkPoints(line);
	}

	private void setPointData(BufferedReader reader) throws IOException {
		for(int i = 1; i < MEASUREMENTS_POINTS; i++) {
			data[currentPoint][i] = setLine(reader.readLine());
		}
		
	}

	private PointData setLine(String line) throws IOException {
		if(line == null) {
			throw new IOException();
		}
		return divide(line);
	}
	
	protected void checkPoints(String line) throws IOException {
		if(line == null) {
			System.out.println("End of File");
			throw new IOException();
		}
	}

	protected abstract PointData divide(String nextLine);
}
