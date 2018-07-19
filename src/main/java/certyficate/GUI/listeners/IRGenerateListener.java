package certyficate.GUI.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import certyficate.GUI.EnvironmentData;
import certyficate.GUI.infrared.InfraredPanel;
import certyficate.GUI.infrared.InfraredParametrs;
import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.generate.Generate;
import certyficate.property.CalibrationData;
import certyficate.property.CalibrationType;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.search.measurments.MeasurementsData;
import certyficate.sheetHandlers.search.order.CertificateData;

public class IRGenerateListener implements ActionListener {	
	private static final int MAXIMUM_CALIBRATION_POINTS = 6;
	
	private static final String ERROR = "Generate file error";
	
	private InfraredPanel infraredPanel;
	
	public IRGenerateListener(InfraredPanel infraredPanel) {
		this.infraredPanel = infraredPanel;
	}
	
	public void actionPerformed(ActionEvent e) {
		CalibrationData.calibrationType = CalibrationType.INFRARED;
		SheetData.setInfrared();
		getFilesData();
		generateCalibrationDocuments();
		infraredPanel.close();
	}

	private void getFilesData()  {
		try {
			findCalibrationData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void findCalibrationData() throws IOException {
		findCertificateData();
		findReferenceData();
		EnvironmentData.setEnvirometsData();
	}

	private void findCertificateData() throws IOException {
		getCalibrationData();
		findMeasurementsData();
		new InfraredParametrs(infraredPanel.getConsole());
	}
	
	private void getCalibrationData() {
		CertificateData.findOrdersData();
	}
	
	private void findMeasurementsData() throws IOException {
		CalibrationData.calibrationPoints 
			= MAXIMUM_CALIBRATION_POINTS;
		MeasurementsData.findMeasurementsData();
	}
	
	private void findReferenceData() throws IOException {
		CalibrationData.probe 
			= EquipmentParameters.findProbe(EquipmentType.INFRARED_REFERENCE);
	}	

	private void generateCalibrationDocuments() {
		try {
			Generate.generateDocuments();
		} catch (IOException e) {
			System.out.println(ERROR);
			e.printStackTrace();
		}
	}
}
