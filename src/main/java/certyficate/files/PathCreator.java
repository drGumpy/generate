package certyficate.files;

import java.io.File;

public class PathCreator {
	private static StringBuilder PATH_TO_DESCOP = pathToDescop();
	private static String SHEETNAME = "Laboratorium.ods";
	
	private static StringBuilder pathToDescop() {
		StringBuilder path = userPath();
		path.append(withSeparator("Descop"));
		return path;
	}
	
	private static StringBuilder userPath() {
		return withSeparator(System.getProperty("user.home"));
	}

	private static StringBuilder withSeparator(String expression) {
		StringBuilder path = new StringBuilder(expression);
		path.append(File.separator);
		return path;
	}
	
	public static String certificatesPath() {
		StringBuilder path = new StringBuilder();
		path.append(archiwumPath());
		path.append(withSeparator("Świadectwa wzorcowania"));
		return path.toString();
	}

	public static String notePath() {
		StringBuilder path = new StringBuilder();
		path.append(archiwumPath());
		path.append(withSeparator("Zapiski"));
		return path.toString();
	}
	
	private static StringBuilder archiwumPath(){
		StringBuilder path = new StringBuilder(folderPath());
		path.append(withSeparator("Wyniki wzorcowań"));
		return path;
	}
	
	private static StringBuilder folderPath(){
		StringBuilder path = new StringBuilder(PATH_TO_DESCOP);
		path.append(withSeparator("Laboratorium"));
		return path;
	}
	
	public static String filePath(String fileName) {
		StringBuilder path = new StringBuilder(filePath());
		path.append(withSeparator(fileName));
		return path.toString();
	}
	
	public static String txtFilePath(String fileName) {
		StringBuilder path = new StringBuilder(filePath(fileName));
		path.append(".txt");
		return path.toString();
	}
	
	public static StringBuilder filePath() {
		StringBuilder path = new StringBuilder(folderPath());
		path.append(withSeparator("generacja"));
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
		path.append(withSeparator("Documents"));
		path.append(withSeparator("rejestratory"));
		return path;
	}

	public static String probeDataPath() {
		StringBuilder path = new StringBuilder(withSeparator("P:"));
		path.append(withSeparator("Laboratorium"));
		path.append(withSeparator("Wyniki z wzorca"));
		return path.toString();
	}
	
}
