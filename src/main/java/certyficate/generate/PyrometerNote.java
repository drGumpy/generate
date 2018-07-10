package certyficate.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.calculation.DataCalculation;
import certyficate.dataContainer.CertificateValue;
import certyficate.entitys.Certificate;
import certyficate.equipment.calculation.DataProbe;
import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class PyrometerNote {
	private static final String NOTE_FILE = "z_T.ods";
	private static final String NOTE_SHEET = "Wyniki wzorcowania";
	
	private static final int POINT_GAP = 32;
	
	private static Sheet sheet;
	
	private static List<CertificateValue> calibrationData;
	
	private static int calibrationPointCount;
	
	private static Certificate certificate;
	
	private static DataProbe[] reference;
	
	private static String[] environment;
	
	public static void setNoteAndCertificate(Certificate certificateData) throws IOException {
		setCalibrationData(certificateData);
		createSheet();
		PyrometerCertificate.setCertificate(certificate, calibrationData);
	}
	
	private static void setCalibrationData(Certificate certificateData) {
		certificate = certificateData;
		reference = CalibrationData.probe;
		environment = CertificateText.getEnviromentData();
	}

	private static void createSheet() throws FileNotFoundException, IOException {
		setSheet();
		setSheetData();
		safeFile();
	}

	private static void setSheet() throws IOException {
		String pathToNote = PathCreator.filePath(NOTE_FILE);
		File noteFile = new File(pathToNote);
		sheet = SpreadSheet.createFromFile(noteFile).getSheet(NOTE_SHEET);
	}
	
	private static void setSheetData() {
		calibrationData = new ArrayList<CertificateValue>();
		calibrationPointCount = 0;
		for(int i = 0; i< CalibrationData.calibrationPoints; i++) { 
			checkData(i);
		}
	}

	private static void checkData(int index) {
		boolean haveData = certificate.measurmets[index].haveMeasurments;
		haveData &= reference[index].question;
		haveData &= reference[index].value 
				!= certificate.point[calibrationPointCount][0];
        if(haveData) {
        	setPointData(index);
        }	
	}
	
	private static void setPointData(int index) {
		int line = calibrationPointCount * POINT_GAP + 3;
		CertificateValue pointValue;
		setConstantValue(line);
		setMeasurmentValue(line, index);
		pointValue = setCalibrationBudget(line, index);
		setPointValue(line, pointValue);
		calibrationPointCount++;
	}

	private static void setConstantValue(int line) {
		sheet.setValueAt(certificate.numberOfCalibration, 3 , line);
        sheet.setValueAt(certificate.calibrationCode, 8 , line);
        sheet.setValueAt(certificate.calibrationDate, 13 , line);
        sheet.setValueAt(environment[0], 3 , line+1);
        sheet.setValueAt(environment[1], 7 , line+1);
        sheet.setValueAt(certificate.device.type, 3 , line+3);
        sheet.setValueAt(certificate.device.model, 3 , line+6);
        sheet.setValueAt(certificate.deviceSerialNumber, 3 , line+9);
        sheet.setValueAt(certificate.device.producent, 3 , line+11);
        sheet.setValueAt(certificate.device.resolution[0], 3 , line+13);
	}

	private static void setMeasurmentValue(int line, int index) {
		double referenceValue =
				certificate.pyrometr.reference[calibrationPointCount];
		for(int i=0; i<10; i++){
			sheet.setValueAt(referenceValue,
					1 , line+17+i);
			sheet.setValueAt(certificate.measurmets[index].data[i][0],
					3 , line+17+i);
		}
	}
	
	private static CertificateValue setCalibrationBudget(int line, int index) {
		double[] uncerinity = findUncerinity(index, 0);
		setUncerinity(uncerinity, line);
		sheet.setValueAt(certificate.measurmets[index].average[0], 7 , line+5);
        sheet.setValueAt(certificate.pyrometr.reference[calibrationPointCount],
        		7 , line+7);
        sheet.setValueAt(reference[index].correction, 7 , line+9);
        sheet.setValueAt(certificate.device.resolution[0], 9 , line+6);
        sheet.setValueAt(reference[index].uncertainty, 9, line+9);
        sheet.setValueAt(reference[index].drift, 9, line+10);
        sheet.setValueAt(certificate.pyrometr.blackBodyError[calibrationPointCount],
        		9, line+11);
        return setCertificateValue(index, uncerinity);
	}

	private static double[] findUncerinity(int index, int parametrIndex) {
		double[] uncerinity = new double[7];
        uncerinity[0] = certificate.measurmets[index].standardDeviation[parametrIndex];
        uncerinity[1] = Double.parseDouble(certificate.device.resolution[parametrIndex]) / Math.sqrt(3);
        uncerinity[2] = 0;
        uncerinity[3] = 0.1 / Math.sqrt(3);
        uncerinity[4] = reference[index].getUncertainty(parametrIndex) / 2;
        uncerinity[5] = reference[index].getDrift(parametrIndex) / Math.sqrt(3);
        uncerinity[6] = certificate.pyrometr.blackBodyError[calibrationPointCount] / 2;
		return uncerinity;
	}
	
	private static void setUncerinity(double[] uncerinity, int line) {
		for(int i = 0; i < uncerinity.length; i++){
            sheet.setValueAt(uncerinity[i], 13, line+5+i);
        }
	}
	
	private static CertificateValue setCertificateValue(int index, double[] uncerinities) {
		CertificateValue pointValue = new CertificateValue();
		double uncerinity =DataCalculation.uncertainty(uncerinities);
        double round = DataCalculation.findRound(2*uncerinity, Double.parseDouble(certificate.device.resolution[0]));
        double pt=DataCalculation.round_d(certificate.pyrometr.reference[calibrationPointCount]+
        		reference[index].correction,round);
        double div =DataCalculation.round_d(certificate.measurmets[index].average[0], round);
        pointValue.probeT= DataCalculation.round(pt,round).replace(".", ",");
        pointValue.deviceT = DataCalculation.round(div,round).replace(".", ",");
        pointValue.errorT = DataCalculation.round(div-pt,round).replace(".", ",");
        pointValue.uncertaintyT = DataCalculation.round(2*uncerinity,round).replace(".", ",");
		return pointValue;
	}
	
	private static void setPointValue(int line, CertificateValue pointValue) {
		sheet.setValueAt(pointValue.probeT, 5, line+17);
		sheet.setValueAt(pointValue.deviceT, 7, line+17);
		sheet.setValueAt(pointValue.errorT, 9, line+17);
		sheet.setValueAt(pointValue.uncertaintyT, 13, line+17);
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
		StringBuilder bulid = new StringBuilder(certificate.numberOfCalibration);
		bulid.append("_");
		bulid.append(certificate.device.model);
		bulid.append(".ods");
		return bulid.toString();
	}
}
