package certyficate.datalogger;

import java.io.File;
import java.util.StringTokenizer;

import certyficate.dataContainer.PointData;

public class Rotronic extends Logger {
	//protected static final String LINE_DATA_SEPARATOR = ";";
	protected static final String DATE_FORMAT = "MM.dd.yyyy HH:mm:ss";
	protected static final String NUMBER_SEPARATOR = ",";
	
	protected int nonDataLine = 47;
	
	protected int deviceNumber = -1;
	
	private ReferenceFiles files;

	
	public Rotronic(boolean RH) {
		super(RH);
		files = new ReferenceFiles(this);
	}
	
	protected void setData() {
		super.setData();
		findFile();
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

	@Override
	protected PointData divide(String line) {
		PointData point = new PointData();
		StringTokenizer st = new StringTokenizer(line);
        String[] num = {st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken()};
        String date = setDate(num[1]);
        point.setDate(date, DATE_FORMAT);
        point.setTemperature(num[3], NUMBER_SEPARATOR);
        point.setHumidity(num[2], NUMBER_SEPARATOR);
        return point;
	}
	
	private String setDate(String time) {
		StringBuilder build = new StringBuilder(
				calibrationPoints.get(currentPoint).getDate());
		build.append(" ");
		build.append(time);
		return build.toString();
	}
	
	protected void checkPoints(String line) {
		if(currentPoint + 1 < calibrationPoints.size()) {
			checkNextPointDate();
			checkNextPoint();
		}
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
