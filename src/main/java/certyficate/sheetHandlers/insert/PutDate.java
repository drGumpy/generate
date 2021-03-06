package certyficate.sheetHandlers.insert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.property.CalibrationData;
 
public class PutDate {
	private static final String SHEET_NAME = "Zlecenia";
	private static final String ERROR = "calibration file error";
	
	private static final int DATE_COLUMN = 2;
	
    private static Sheet sheet;
    
    private static File file;
    
    private static List<String> done;
    
    public static void calibrationDate(List<String> doneOrder) {
    	done = doneOrder;
    	setDoneOrder();
    }
    
    private static void setDoneOrder() {
    	try {
			setCalibrationDate();
		} catch (IOException e) {
			System.out.println(ERROR);
			e.printStackTrace();
		}
	}

	private static void setCalibrationDate() throws IOException {
    	setSheet();
		setDate();
		safeFile();
	}

	private static void setSheet() throws IOException {
		file = CalibrationData.sheet;
		sheet = SpreadSheet.createFromFile(file).getSheet(SHEET_NAME);
	}

	private static void setDate() {
		Date date = new Date();
		for(String number: done) {
			setDate(number, date);
		}
	}

	private static void setDate(String number, Date date) {
		int calibrationNumber = Integer.parseInt(number);
		sheet.setValueAt(date, DATE_COLUMN, calibrationNumber);
	}
	
    private static void safeFile() throws FileNotFoundException, IOException {
    	sheet.getSpreadSheet().saveAs(file);		
	}   
}
