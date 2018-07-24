package certyficate.datalogger;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import certyficate.datalogger.type.Rotronic;
import certyficate.files.PathCreator;
import certyficate.property.SheetData;

public class ReferenceFiles {	
	private static final String NON_FILE = "brak pliku wzorca dla punktu: ";
	private static final String OPENING = "otwieranie: ";
	private String path;
	
	private String[] fileList;
	
	private File file;
	
	private Pattern probe;
	private Pattern datePattern;
	
	private Logger logger;

	public ReferenceFiles(Rotronic rotronic) {
		logger = rotronic;
		probe = Pattern.compile(SheetData.probeSerialNumber, Pattern.CASE_INSENSITIVE);
		path = PathCreator.probeDataPath();
        fileList = new File(path).list();
	}

	public File findFile(int currentPoint) {
		file = null;
		setDatePattern(currentPoint);
		searchTheFiles();
		return file;
	}
	
	private void setDatePattern(int currentPoint) {
		String date = logger.getCalibrationPoints().get(currentPoint).getPatternDate();
		datePattern = Pattern.compile(date, Pattern.CASE_INSENSITIVE);
	}

	private void searchTheFiles() {
		int size = fileList.length;
		for(int i = 0; i < size; i++) {
			checkFileName(fileList[i]);
		}
	}

	private void checkFileName(String fileName) {
		if(checkName(fileName)) {
			setFile(fileName);
		}
	}

	private void setFile(String fileName) {
		openFileCommunicate(fileName);
		String filePath = getFilePath(fileName);
		file = new File(filePath);
	}

	private void openFileCommunicate(String fileName) {
		StringBuilder build = new StringBuilder(OPENING);
		build.append(fileName);
		System.out.println(build);
	}

	private boolean checkName(String fileName) {
		Matcher matcherDate = datePattern.matcher(fileName);
        Matcher matcherProbe = probe.matcher(fileName);
        return matcherDate.find() && matcherProbe.find();
	}
	
	private String getFilePath(String fileName) {
		StringBuilder filePath = new StringBuilder(path);
		filePath.append(fileName);
		return filePath.toString();
	}

	public StringBuilder nonFile(int currentPoint) {
		StringBuilder filePath = new StringBuilder(NON_FILE);
		filePath.append(currentPoint);
		return filePath;
	}
}
