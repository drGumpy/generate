package certyficate.generate.certificate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.entitys.Client;
import certyficate.entitys.Order;
import certyficate.files.PathCreator;
import certyficate.generate.CertificateText;
import certyficate.generate.CertificateValue;
import certyficate.property.CalibrationData;

public abstract class Certificate {
	private static final String SHEET_NAME = "Świadectwo wzorcowania";
	private static final String COMMENT = "UWAGI:";
	
	private static final int NUMBER_OF_PAGES = 2;
	private static final int COMMENTS_COLUMN = 4;
	private static final int PAGE_GAP = 57;
	private static final int POINT_GAP = 2;
	
	protected static int dateColumn;
	protected static int numberColumn;
	protected static int informactionColumn;
	protected static int numberOfData;
	protected static int commentsLine;
	
	private int currentPoint;
	
	protected String templateName;
	
	protected Sheet sheet;
	
	protected Order certificate;
	
	private List<CertificateValue> pointData;
	
	protected String[] comments;
	private String[] environment;
	
	public Certificate() {
		environment = CertificateText.getEnvironmentText();
	}
	
	public void setCertificate(Order certificateData,
			List<CertificateValue> calibration) throws IOException {
		setCalibrationData(certificateData, calibration);
		setDataSheet();
	}
	
	private void setCalibrationData(Order certificateData,
			List<CertificateValue> calibration) {
		certificate = certificateData;
		pointData = calibration;
		currentPoint = 0;
		setTemplateData();
	}

	protected abstract void setTemplateData();

	private void setDataSheet() throws IOException {
		setSheet();
		setSheetData();
		safeFile();
	}

	private void setSheet() throws IOException {
		String pathToNote = PathCreator.filePath(templateName);
		File noteFile = new File(pathToNote);
		sheet = SpreadSheet.createFromFile(noteFile).getSheet(SHEET_NAME);
	}


	private void setSheetData() {
		setDateAndNumber();
		setCalibrationInformation();
		setMeasurmentData();
	}

	private void setDateAndNumber() {
		for(int i = 0; i < NUMBER_OF_PAGES; i++) {
			int line = 13 + i * PAGE_GAP;
			setDateAndNumber(line);
		}
	}

	private void setDateAndNumber(int line) {
		sheet.setValueAt(new Date(), dateColumn , line);
        sheet.setValueAt(certificate.numberOfCalibration,
        		numberColumn , line);
		
	}

	private void setCalibrationInformation() {
		setDeviceData(16);
		setClientsData();
		setEnvironmentData(30);
		setCalibrationDate(33);
		insertComments(94);
	}

	private void setDeviceData(int line) {
		String description = CertificateText.setDescription(certificate);
		sheet.setValueAt(description, informactionColumn , line);		
	}
	
	private void setClientsData() {
		setClient(certificate.declarant, 20);
		setClient(certificate.user, 23);		
	}


	private void setClient(Client client, int line) {
		String adres = CertificateText.getAdres(client);
		sheet.setValueAt(client.name, informactionColumn , line);
        sheet.setValueAt(adres, informactionColumn , line);
	}

	private void setEnvironmentData(int line) {
		for(int i = 0; i < environment.length; i++) {
			sheet.setValueAt(environment[i],
					informactionColumn , line + i);
		}
	}
	
	private void setCalibrationDate(int line) {
		sheet.setValueAt(certificate.calibrationDate,
				informactionColumn , line);		
	}
	
	protected abstract void setMeasurmentData();
	
	protected void setMeasurmentData(int line) {
		int endData = setEndData();
		for(; currentPoint > endData; currentPoint++) {
			setData(pointData.get(currentPoint), line);
			line += POINT_GAP;
		}
	}

	private int setEndData() {
		int endData = currentPoint + numberOfData;
		return Math.min(endData, pointData.size());
	}

	protected abstract void setData(CertificateValue data, int line);
	
	private void insertComments(int line) {
		if(comments != null) {
			insertCommentsCell();
			insertComments();
		}
	}

	private void insertComments() {
		int length = comments.length;
		for(int i = 0; i < length; i++) {
			sheet.setValueAt(comments[i], COMMENTS_COLUMN, commentsLine + i);
		}
	}

	private void insertCommentsCell() {
		sheet.setValueAt(COMMENT, COMMENTS_COLUMN, commentsLine -1);
	}

	private void safeFile() throws FileNotFoundException, IOException {
		File certificateFile = certificateFile();
		sheet.getSpreadSheet().saveAs(certificateFile);
	}

	private File certificateFile() {
		File certificates = CalibrationData.certificate;
		String fileName = setFileName();
		File file = new File(certificates, fileName);
		return file;
	}

	private String setFileName() {
		StringBuilder bulid = new StringBuilder(certificate.numberOfCalibration);
		bulid.append("_");
		bulid.append(certificate.declarant.name);
		bulid.append(".ods");
		return bulid.toString();
	}

}
