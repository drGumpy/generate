package certyficate.property;

import java.io.File;
import java.util.List;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.entitys.Order;
import certyficate.equipment.calculation.DataProbe;
import certyficate.sheetHandlers.CalibrationPoint;
import certyficate.sheetHandlers.search.Measurements;

public class CalibrationData {
	final public static int MEASUREMENTS_POINTS = 10;
	public static int calibrationPoints;
	public static int numberOfParameters = 1;
	
	public static String referenceSerial;
	
	public static List<Order> orders;
	
	public static List<Measurements> devices;
	
	public static List<CalibrationPoint> point;
	
	public static DataProbe[] probe;
	public static DataProbe[] chamber;
	
	public static Measurements patern;
	
	public static CalibrationType calibrationType;
	
	public static SpreadSheet spreadSheet;
	
	public static double[] eniromentData;
	
	public static File sheet;
	public static File certificate;
	public static File notes;
}
