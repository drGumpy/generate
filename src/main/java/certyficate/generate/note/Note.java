package certyficate.generate.note;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.calculation.DataCalculation;
import certyficate.dataContainer.CertificateValue;
import certyficate.entitys.Order;
import certyficate.entitys.Probe;
import certyficate.entitys.Device;
import certyficate.equipment.calculation.DataProbe;
import certyficate.files.PathCreator;
import certyficate.generate.CertificateText;
import certyficate.generate.certificate.Certificate;
import certyficate.property.CalibrationData;

public abstract class Note {
	private static final String NOTE_SHEET = "Wyniki wzorcowania";
	private static final String DEFAULT_NUMBER_SEPRATOR = ".";
	private static final String CUSTOM_NUMBER_SEPRATOR = ",";
	
	private static final int POINT_GAP = 32;
	
	private static String noteFile;
	
	protected static Sheet sheet;
	
	private static List<CertificateValue> calibrationData;
	
	protected static int calibrationPointCount;
	
	protected static double round;
	
	protected static Order order;
	
	protected static Certificate certificate;
	
	protected static DataProbe[] reference;
	
	private static String[] environment;
	
	public void setNoteAndCertificate(Order orderData) throws IOException {
		setCalibrationData(orderData);
		createSheet();
		setCertificate();
		certificate.setCertificate(order, calibrationData);
	}

	private static void setCalibrationData(Order orderData) {
		order = orderData;
		environment = CertificateText.getEnviromentData();
		setEquipmentData();
	}

	private static void setEquipmentData() {
		reference = CalibrationData.probe;		
	}

	private void createSheet() throws FileNotFoundException, IOException {
		setSheet();
		setSheetData();
		safeFile();
	}

	private void setSheet() throws IOException {
		String pathToNote = PathCreator.filePath(noteFile);
		File noteFile = new File(pathToNote);
		sheet = SpreadSheet.createFromFile(noteFile).getSheet(NOTE_SHEET);
	}
	
	private void setSheetData() {
		calibrationData = new ArrayList<CertificateValue>();
		calibrationPointCount = 0;
		for(int i = 0; i< CalibrationData.calibrationPoints; i++) { 
			checkData(i);
		}
	}

	private void checkData(int index) {
		boolean haveData = order.measurmets[index].haveMeasurments;
		haveData &= reference[index].question;
		haveData &= reference[index].value 
				!= order.point[calibrationPointCount][0];
        if(haveData) {
        	setPointData(index);
        }	
	}
	
	private void setPointData(int index) {
		int line = calibrationPointCount * POINT_GAP + 3;
		CertificateValue pointValue;
		setConstantValue(line);
		setMeasurmentValue(line, index);
		pointValue = setCalibrationBudget(line, index);
		setPointValue(line, pointValue);
		calibrationPointCount++;
	}

	private void setConstantValue(int line) {
        setCertificate(line);
        setEnvironment(line + 1);
        setDevice(order.device, line);
        if(order.probe != null) {
        	setProbe(order.probe ,line);
        	serProbeSerial(line);
        }
	}

	private void setCertificate(int line) {
		sheet.setValueAt(order.numberOfCalibration, 3, line);
        sheet.setValueAt(order.calibrationCode, 8, line);
        sheet.setValueAt(order.calibrationDate, 13, line);
        sheet.setValueAt(order.deviceSerialNumber, 3, line + 9);
	}

	private void setEnvironment(int environmentLine) {
		sheet.setValueAt(environment[0], 3, environmentLine);
        sheet.setValueAt(environment[1], 7, environmentLine);
	}

	private void setDevice(Device device, int line) {
		int deviceColumn = 3;
        sheet.setValueAt(device.type, deviceColumn, line + 3);
        sheet.setValueAt(device.model, deviceColumn, line + 6);
        sheet.setValueAt(device.producent, deviceColumn, line + 11);
        setResolution(device.resolution, line + 13);
	}

	protected abstract void setResolution(String[] resolution, int line);

	private static void setProbe(Probe probe, int line) {
		int probeColumn = 4;
		sheet.setValueAt(probe.model, probeColumn, line + 6);
		sheet.setValueAt(probe.producent, probeColumn, line + 11);
	}
	
	protected static void serProbeSerial(int line) {
		sheet.setValueAt(order.probeSerial[0], 4 , line+9);
		//TODO set numbers of Probe
	}

	protected abstract void setMeasurmentValue(int line, int index);
	
	protected abstract CertificateValue setCalibrationBudget(int line, int index);
	
	protected String setNumber(double number) {
		String rounded = DataCalculation.round(number, round);
		return rounded.replace(DEFAULT_NUMBER_SEPRATOR, CUSTOM_NUMBER_SEPRATOR);
	}

	protected void setPointValue(int line, CertificateValue pointValue) {
		sheet.setValueAt(pointValue.probeT, 5, line+17);
		sheet.setValueAt(pointValue.deviceT, 7, line+17);
		sheet.setValueAt(pointValue.errorT, 9, line+17);
		sheet.setValueAt(pointValue.uncertaintyT, 13, line+17);
		addPointValue(pointValue);
	}
	
	protected void addPointValue(CertificateValue pointValue) {
		calibrationData.add(pointValue);		
	}

	private static void safeFile() throws FileNotFoundException, IOException {
		File noteFile = noteFile();
		sheet.getSpreadSheet().saveAs(noteFile);
	}

	private static File noteFile() {
		File notes = CalibrationData.notes;
		String fileName = setFileName();
		File note = new File(notes, fileName);
		return note;
	}

	private static String setFileName() {
		StringBuilder bulid = new StringBuilder(order.numberOfCalibration);
		bulid.append("_");
		bulid.append(order.device.model);
		bulid.append(".ods");
		return bulid.toString();
	}
	
	protected abstract void setCertificate();
}
