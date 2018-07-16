package certyficate.files;

import java.io.File;

public class PathCreator {
	private static final StringBuilder PATH_TO_DESCOP = pathToDescop();
	
	private static final String PROPERTY_KEY = "user.home";
	private static final String SHEETNAME = "Laboratorium.ods";
	private static final String DESCOP = "Descop";
	private static final String CERTIFICATE = "Świadectwa wzorcowania";
	private static final String NOTE = "Zapiski";
	private static final String ARCHIVES = "Wyniki wzorcowań";
	private static final String DESCOP_FILE = "Laboratorium";
	private static final String FILE_EXTENSION = ".txt";
	private static final String TEMPLATE_FILE = "generacja";
	private static final String REFERENCE_FILES = "Wyniki z wzorca";
	private static final String DOCUMENTS = "Documents";
	private static final String DATALOGERS = "rejestratory";
	
	private static StringBuilder pathToDescop() {
		StringBuilder path = userPath();
		path.append(withSeparator(DESCOP));
		return path;
	}
	
	private static StringBuilder userPath() {
		return withSeparator(System.getProperty(PROPERTY_KEY));
	}

	private static StringBuilder withSeparator(String expression) {
		StringBuilder path = new StringBuilder(expression);
		path.append(File.separator);
		return path;
	}
	
	public static String certificatesPath() {
		StringBuilder path = new StringBuilder();
		path.append(archivesPath());
		path.append(withSeparator(CERTIFICATE));
		return path.toString();
	}

	public static String notePath() {
		StringBuilder path = new StringBuilder();
		path.append(archivesPath());
		path.append(withSeparator(NOTE));
		return path.toString();
	}
	
	private static StringBuilder archivesPath(){
		StringBuilder path = new StringBuilder(folderPath());
		path.append(withSeparator(ARCHIVES));
		return path;
	}
	
	private static StringBuilder folderPath(){
		StringBuilder path = new StringBuilder(PATH_TO_DESCOP);
		path.append(withSeparator(DESCOP_FILE));
		return path;
	}
	
	public static String filePath(String fileName) {
		StringBuilder path = new StringBuilder(filePath());
		path.append(withSeparator(fileName));
		return path.toString();
	}
	
	public static String txtFilePath(String fileName) {
		StringBuilder path = new StringBuilder(filePath(fileName));
		path.append(FILE_EXTENSION);
		return path.toString();
	}
	
	public static StringBuilder filePath() {
		StringBuilder path = new StringBuilder(folderPath());
		path.append(withSeparator(TEMPLATE_FILE));
		return path;
	}
		
	public static String sheetPath() {
		StringBuilder path = new StringBuilder(PATH_TO_DESCOP);
		path.append(SHEETNAME);
		return path.toString();
	}

	public static String setLoggerPath(String loggerType) {
		StringBuilder path = loggerPath();
		path.append(withSeparator(loggerType));;
		return path.toString();
	}
	
	private static StringBuilder loggerPath() {
		StringBuilder path = userPath();
		path.append(withSeparator(DOCUMENTS));
		path.append(withSeparator(DATALOGERS));
		return path;
	}

	public static String probeDataPath() {
		StringBuilder path = new StringBuilder(withSeparator("P:"));
		path.append(withSeparator(DESCOP_FILE));
		path.append(withSeparator(REFERENCE_FILES));
		return path.toString();
	}
	
}
