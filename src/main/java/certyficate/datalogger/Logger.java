package certyficate.datalogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

import certyficate.files.ReaderCreator;
import certyficate.sheetHandlers.CalibrationPoint;

public abstract class Logger {
	//TODO reorganizing messages
	private static final String NON_DATA_INFROMATION = "No data for point ";
	private static final String GET_DATA = "pobieranie danych dla punktu: ";
	private static final String TIME = " z godziny: ";
	private static final String END_OF_FILE = "End of File"; 
	
	private static final int MEASUREMENTS_POINTS = 10;
	
	private File file;
	
	private BufferedReader reader;
	
	protected boolean Rh;
	
	protected int nonDataLine;
	
	protected List<CalibrationPoint> calibrationPoints;
	
	protected PointData[][] data;
	
	protected int currentPoint;
	protected int deviceNumber;
	
	public Logger(boolean RH){
		this.Rh = RH;
	}
	
	public void setFile(File file) {
		this.file= file;		
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
			
		}
	}

	private void findPoints() throws IOException {
    	reader = ReaderCreator.getReader(file);
    	removeNonDataLine();
    	findPointsData();
    	reader.close();
    }

	private void removeNonDataLine() throws IOException {
		for(int i = 0; i < nonDataLine; i++) {
			reader.readLine();
		}
	}

	private void findPointsData() throws IOException {
		try {
			findPoint();
		} catch (IOException e) {
			System.out.println(noDataInformation());
			throw e;
		}
	}
	
	private StringBuilder noDataInformation() {
		StringBuilder build = new StringBuilder(NON_DATA_INFROMATION);
		build.append(currentPoint);
		return build;
	} 

	private void findPoint() throws IOException {
		String line= reader.readLine();
		while(checkLine(line)) {
			checkPoint(line);
			line = reader.readLine();
		}
	}

	private boolean checkLine(String line) {
		return line != null && currentPoint < calibrationPoints.size();
	}
	
	private void checkPoint(String line) throws IOException {
		CalibrationPoint point = calibrationPoints.get(currentPoint);
		PointData pointData = divide(line);
		if(pointData.equalsData(point)) {
			setPointsData(pointData);
		}
	}

	private void setPointsData(PointData pointData) throws IOException {
		insertPointDataMessage(pointData.getTime());
		data[currentPoint][0] = pointData;
		setPointData();
		currentPoint++;
	}

	private void insertPointDataMessage(String time) {
		StringBuilder build = new StringBuilder(GET_DATA);
		build.append(currentPoint + 1);
		build.append(TIME);
		build.append(time);
		System.out.println(build);
	}

	private void setPointData() throws IOException {
		for(int i = 1; i < MEASUREMENTS_POINTS; i++) {
			setLine(i);
		}
		checkFileData();
	}

	protected void checkFileData() {}

	private void setLine(int index) throws IOException {
		if(currentPoint < calibrationPoints.size()) {
			data[currentPoint][index] = setLine();
		} 
	}

	private PointData setLine() throws IOException {
		String line = reader.readLine();
		checkLineData(line);
		return divide(line);
	}
	
	private void checkLineData(String line) throws IOException {
		if(line == null) {
			System.out.println(END_OF_FILE);
			throw new IOException();
		}
	}

	protected abstract PointData divide(String nextLine);
}
