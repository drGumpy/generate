package certyficate.datalogger;

import java.io.File;

import certyficate.dataContainer.PointData;

public class Rotronic extends Logger {
	protected static final String LINE_DATA_SEPARATOR = "\t";
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected static final String NUMBER_SEPARATOR = ",";
	
	protected int nonDataLine = 47;
	
	protected int deviceNumber = -1;
	
	private ReferenceFiles files;

	public Rotronic(boolean RH) {
		super(RH);
		files = new ReferenceFiles(this);
	}
	
	@Override
	protected void setData() {
		super.setData();
		findFile();
	}

	@Override
	protected PointData divide(String line) {
		PointData point = new PointData();
        String[] data = line.split(LINE_DATA_SEPARATOR);
        String date = setDate(data[0], data[1]);
        point.setDate(date, DATE_FORMAT);
        point.setTemperature(data[3], NUMBER_SEPARATOR);
        point.setHumidity(data[2], NUMBER_SEPARATOR);
        return point;
	}
	
	@Override
	protected void checkPoints(String line) {
		if(currentPoint + 1 < calibrationPoints.size()) {
			checkNextPointDate();
			checkNextPoint();
		}
	}
	
	private String setDate(String data, String time) {
		StringBuilder build = new StringBuilder(data);
		build.append(" ");
		build.append(time);
		return build.toString();
	}
	
	private void findFile() {
		File file = files.findFile(currentPoint);
		if(file == null) {
			findNext();
		} else {
			setFile(file);
		}
	}

	private void findNext() {
		currentPoint++;
		System.out.println(files.nonFile(currentPoint));
		findFile();
	}

	private void checkNextPointDate() {
		if(nextPointDate()) {
			data[currentPoint + 1] = data[currentPoint];
			currentPoint++;
		}
	}

	private boolean nextPointDate() {
		return calibrationPoints.get(currentPoint).
				equalDate(calibrationPoints.get(currentPoint + 1));
	}
	
	private void checkNextPoint() {
		if(!nextPointDay()) {
			currentPoint++;
			findFile();
			findDataPoints();
		}
	}

	private boolean nextPointDay() {
		return calibrationPoints.get(currentPoint).
				equalDay(calibrationPoints.get(currentPoint + 1));
	}
}
