package certyficate.datalogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.datalogger.type.*;
import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

public class LoggersFinder {
	private static final String[] LOGGER_TYPE = {"Hobo", "DT-172", "testo", "RC", "Lascar", "EBI"};
	private static final String[] EXTENSION = {".txt",".csv",".csv",".txt",".txt",".csv"};
	private static String[] loggerPath;
	
	private static List<Logger> loggers;
	
	private static boolean Rh;
	
	public static List<Logger> findLoggersFile(Sheet sheet) {
		setSettings();
		setLoggers(sheet);
		return loggers;
	}

	private static void setSettings() {
		loggers = new ArrayList<Logger>();
		Rh = SheetData.Rh;
		setLoggerPaths();
	}

	private static void setLoggerPaths() {
		loggerPath = new String[LOGGER_TYPE.length];
		for(int i = 0; i < LOGGER_TYPE.length; i++) {
			loggerPath[i] = PathCreator.setLoggerPath(LOGGER_TYPE[i]);
		}
	}

	private static void setLoggers(Sheet sheet) {
		for(int i = 0; i < SheetData.numberOfDevices; i++) {
			checkDevice(sheet, i);
		}
	}

	private static void checkDevice(Sheet sheet, int index) {
		int line = SheetData.startRow + CalibrationData.numberOfParameters * index;
		String deviceName = sheet.getValueAt(1, line).toString();
		Logger logger = checkFiles(deviceName);
		addLogger(logger, index);
	}

	private static Logger checkFiles(String deviceName) {
		Logger logger = null;
		for(int i = 0; i < LOGGER_TYPE.length; i++) {
			File file = setFile(deviceName, i);
			if(file.exists()) {
				logger = findLogger(file, i);
				break;
			}
		}
		logger.setSerialNumber(deviceName);
		return logger;
	}

	private static File setFile(String deviceName, int index) {
		return new File(setFilePath(deviceName, index));
	}
	
	private static String setFilePath(String deviceName, int index) {
		StringBuilder build = new StringBuilder(loggerPath[index]);
		build.append(deviceName);
		build.append(EXTENSION[index]);
		return build.toString();
	}
	
	private static Logger findLogger(File file, int index) {
		Logger logger = findDataType(index);
		try {
			logger.setFile(file);
		} catch (IOException e) {
			System.out.println("file error");
		}
		return logger;
	}

	private static Logger findDataType(int index) {
		Logger logger;
        switch(index) {
            case 0:{
                logger = new Onset(Rh);
                break;
            }
            case 1:{
                logger = new CEM(Rh);
                break;
            }
            case 2:{
                logger = new Testo(Rh);
                break;
            }
            case 3:{
                logger = new RC(Rh);
                break;
            }
            case 4:{
                logger = new Lascar(Rh);
                break;
            }
            default: logger = new EBI(Rh);
        }
		return logger;
	}
	
	private static void addLogger(Logger logger, int index) {
		if(logger != null) {
			logger.setDeviceNumber(index);
			loggers.add(logger);
		}
	}
}
