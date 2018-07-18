package certyficate.generate.note;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.entitys.Order;
import certyficate.entitys.Probe;
import certyficate.equipment.calculation.DataProbe;
import certyficate.entitys.Device;
import certyficate.files.PathCreator;
import certyficate.generate.CertificateText;
import certyficate.generate.CertificateValue;
import certyficate.generate.certificate.Certificate;
import certyficate.property.CalibrationData;
import certyficate.property.DataCalculation;

//TODO remove T/Rh poll
public abstract class Note {
	private static final String NOTE_SHEET = "Wyniki wzorcowania";
	private static final String DEFAULT_NUMBER_SEPRATOR = "\\.";
	private static final String CUSTOM_NUMBER_SEPRATOR = ",";
	
	private static final int POINT_GAP = 32;
	private static final int PROBE_COLUMN = 4;
	protected static final int DEVICE_COLUMN = 3;
	
	protected String noteFile;
	
	protected static Sheet sheet;
	
	private static List<CertificateValue> calibrationData;
	
	protected static int calibrationPointCount;
	protected int numberOfData;
	
	protected static double round;
	
	protected static Order order;
	
	protected static Certificate certificate;
	
	protected static DataProbe[] reference;
	
	private static String[] environment;
	
	public Note() {
		setCalibrationData();
		setCertificate();
		setData();
	}

	public void setNoteAndCertificate(Order orderData) throws IOException {
		setCalibrationData(orderData);
		createNote();
		certificate.setCertificate(order, calibrationData);
	}
	
	private void setCalibrationData() {
		environment = CertificateText.getEnviromentData();
		setEquipmentData();
	}
	
	private static void setEquipmentData() {
		reference = CalibrationData.probe;		
	}
	
	protected abstract void setCertificate();
	
	protected abstract void setData();

	private static void setCalibrationData(Order orderData) {
		order = orderData;
	}

	private void createNote() throws FileNotFoundException, IOException {
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
		for(int i = 0; i < CalibrationData.calibrationPoints; i++) { 
			checkData(i);
		}
	}

	private void checkData(int index) {
		if(calibrationPointCount < order.point.length) {
			checkPointData(index);
		}
	}
	
	private void checkPointData(int index) {
		if(havePointData(index)) {
        	setPointData(index);
        }
	}

	private boolean havePointData(int index) {
		boolean haveData = order.measurmets[index].haveMeasurments;
		haveData &= reference[index].question;
		haveData &= reference[index].value == order.point[calibrationPointCount][0];
		return haveData;
	}

	private void setPointData(int index) {
		int line = calibrationPointCount * POINT_GAP + 3;
		CertificateValue pointValue = findPointValue(line, index);
		setConstantValue(line);
		setMeasurmentValue(line + 17, index);
		setCalibrationBudget(line, index);
		setPointValue(line, pointValue);
		calibrationPointCount++;
	}

	protected abstract CertificateValue findPointValue(int line, int index);

	private void setConstantValue(int line) {
        setCertificate(line);
        setEnvironment(line + 1);
        setDevice(order.device, line);
        if(order.probe != null) {
        	setProbeData(line);
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
        sheet.setValueAt(device.type, DEVICE_COLUMN, line + 3);
        sheet.setValueAt(device.model, DEVICE_COLUMN, line + 6);
        sheet.setValueAt(device.producent, DEVICE_COLUMN, line + 11);
        setChannel(device.channel, line + 8);
        setResolution(device.resolution, line + 13);
	}

	private void setChannel(String[] channel, int line) {
		int channelNumber = calibrationPointCount / numberOfData;
		if(channelNumber < channel.length) {
			sheet.setValueAt(channel[channelNumber], DEVICE_COLUMN , line);
		}
	}

	protected abstract void setResolution(String[] resolution, int line);

	private void setProbeData(int line) {
		setProbe(order.probe ,line);
    	serProbeSerial(order.probeSerial, line + 9);
	}
	
	private static void setProbe(Probe probe, int line) {
		sheet.setValueAt(probe.model, PROBE_COLUMN, line + 6);
		sheet.setValueAt(probe.producent, PROBE_COLUMN, line + 11);
	}
	
	protected void serProbeSerial(String[] probeSerial, int line) {
		int probeNumber = calibrationPointCount / numberOfData;
		if(probeNumber < probeSerial.length) {
			sheet.setValueAt(probeSerial[probeNumber], PROBE_COLUMN , line);
		}
	}

	private void setMeasurmentValue(int line, int index) {
		for(int i = 0; i < CalibrationData.MEASUREMENTS_POINTS; i++) {
			setValue(line + i, index, i);
		}
	}
	
	protected abstract void setValue(int line, int index, int i);

	protected void setCalibrationBudget(int line, int index) {
		sheet.setValueAt(order.measurmets[index].average[0], 7 , line + 5);
        sheet.setValueAt(order.pyrometr.reference[calibrationPointCount],
        		7 , line + 7);
        sheet.setValueAt(reference[index].correction, 7 , line + 9);
        sheet.setValueAt(order.device.resolution[0], 9 , line + 6);
        sheet.setValueAt(reference[index].uncertainty, 9, line + 9);
        sheet.setValueAt(reference[index].drift, 9, line + 10);
	}
	
	protected double findUncerinityAndRound(double[] uncerinities) {
		double uncerinity = DataCalculation.uncertainty(uncerinities);
		round = DataCalculation.findRound(2 * uncerinity, 
				Double.parseDouble(order.device.resolution[0]));
		return DataCalculation.roundTonumber(uncerinity, round);
	}
	
	protected String setNumber(double number) {
		String rounded = DataCalculation.round(number, round);
		return rounded.replaceAll(DEFAULT_NUMBER_SEPRATOR, CUSTOM_NUMBER_SEPRATOR);
	}

	protected void setPointValue(int line, CertificateValue pointValue) {
		sheet.setValueAt(pointValue.probeT, 5, line + 17);
		sheet.setValueAt(pointValue.deviceT, 7, line + 17);
		sheet.setValueAt(pointValue.errorT, 9, line + 17);
		sheet.setValueAt(pointValue.uncertaintyT, 13, line + 17);
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
}
