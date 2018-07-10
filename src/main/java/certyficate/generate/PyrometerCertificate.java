package certyficate.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.dataContainer.CertificateValue;
import certyficate.entitys.Certificate;
import certyficate.entitys.Client;
import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class PyrometerCertificate {
	private static final String FILE_NAME = "sw_IR.ods";
	private static final String SHEET_NAME = "Świadectwo wzorcowania";
	
	private static final int NUMBER_OF_PAGES = 2;
	private static final int COMMENTS_COLUMN = 4;
	private static final int DATE_COLUMN = 8;
	private static final int NUMBER_COLUMN = 22;
	private static final int INFORMATION_COLUMN = 12;
	
	private static Sheet sheet;
	
	static Certificate certificate;
	
	static List<CertificateValue> pointData;
	
	static String[] environment;
	
	public static void setCertificate(Certificate certificateData,
			List<CertificateValue> calibration) throws IOException {
		setCalibrationData(certificateData, calibration);
		setDataSheet();
	}
	
	private static void setCalibrationData(Certificate certificateData,
			List<CertificateValue> calibration) {
		certificate = certificateData;
		pointData = calibration;
		environment = CertificateText.getEnvironmentText();
	}

	private static void setDataSheet() throws IOException {
		setSheet();
		setSheetData();
		safeFile();
	}

	private static void setSheet() throws IOException {
		String pathToNote = PathCreator.filePath(FILE_NAME);
		File noteFile = new File(pathToNote);
		sheet = SpreadSheet.createFromFile(noteFile).getSheet(SHEET_NAME);
	}


	private static void setSheetData() {
		setDateAndNumber();
		setCalibrationInformation();
		setMeasurmentData();
	}

	private static void setDateAndNumber() {
		int pageGap = 57;
		for(int i =0; i < NUMBER_OF_PAGES; i++) {
			int informationLine = 13 + i * pageGap;
			sheet.setValueAt(new Date(), DATE_COLUMN , informationLine);
	        sheet.setValueAt(certificate.numberOfCalibration,
	        		NUMBER_COLUMN , informationLine);
		}
	}

	private static void setCalibrationInformation() {
		setDeviceData(16);
		setClientsData();
		setEnvironmentData(30);
		setCalibrationDate(33);
		setComments(94);
	}

	private static void setDeviceData(int line) {
		String description = CertificateText.setDescription(certificate);
		sheet.setValueAt(description, INFORMATION_COLUMN , line);		
	}
	
	private static void setClientsData() {
		setClient(certificate.declarant, 20);
		setClient(certificate.user, 23);		
	}


	private static void setClient(Client client, int line) {
		String adres = CertificateText.getAdres(client);
		sheet.setValueAt(client.name, INFORMATION_COLUMN , line);
        sheet.setValueAt(adres, INFORMATION_COLUMN , line);
	}

	private static void setEnvironmentData(int line) {
		for(int i =0; i < environment.length; i++) {
			sheet.setValueAt(environment[i],
					INFORMATION_COLUMN , line + i);
		}
	}
	
	private static void setCalibrationDate(int line) {
		sheet.setValueAt(certificate.calibrationDate,
				INFORMATION_COLUMN , line);		
	}
	
	private static void setMeasurmentData() {
		int line = 84;
		for(CertificateValue data: pointData) {
			setData(data, line);
			line += 2;
			if(line == 90)
				break;
		}
	}

	private static void setData(CertificateValue data, int line) {
		sheet.setValueAt(data.probeT, 3, line);
    	sheet.setValueAt(data.deviceT, 12, line);
    	sheet.setValueAt(data.errorT, 21, line);
    	sheet.setValueAt(data.uncertaintyT, 30, line);
	}
	
	private static void setComments(int line) {
		String distance = CertificateText.getDistance(certificate.pyrometr);
		String emissivity = CertificateText.getEmissivity(certificate.pyrometr);
		sheet.setValueAt("UWAGI:", COMMENTS_COLUMN, line);
		sheet.setValueAt(distance, COMMENTS_COLUMN, line + 1);
		sheet.setValueAt(emissivity, COMMENTS_COLUMN, line + 2);		
	}

	private static void safeFile() throws FileNotFoundException, IOException {
		File certificateFile = certificateFile();
		sheet.getSpreadSheet().saveAs(certificateFile);
	}

	private static File certificateFile() {
		File certificates = CalibrationData.certificate;
		String fileName = setFileName();
		File file = new File(certificates, fileName);
		return file;
	}

	private static String setFileName() {
		StringBuilder bulid = new StringBuilder(certificate.numberOfCalibration);
		bulid.append("_");
		bulid.append(certificate.declarant.name);
		bulid.append(".ods");
		return bulid.toString();
	}
}
