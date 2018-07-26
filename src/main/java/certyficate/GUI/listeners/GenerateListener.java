package certyficate.GUI.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import certyficate.GUI.ClimateChamber;
import certyficate.GUI.EnvironmentData;
import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.generate.Generate;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.search.measurments.MeasurementsData;
import certyficate.sheetHandlers.search.order.CertificateData;

public class GenerateListener implements ActionListener {
	ClimateChamber climateChamber;
	
	private static final String ERROR = "Generate file error";
	
	public GenerateListener(ClimateChamber climateChamber) {
		this.climateChamber = climateChamber;
	}
	
	public void actionPerformed(ActionEvent e) {
		SheetData.setChamberData(CalibrationData.calibrationType);
		climateChamber.setSheetData();
		getFilesData();
		generateCalibrationDocuments();
		climateChamber.close();
	}
	
	private void getFilesData()  {
		try {
			findCalibrationData();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private void findCalibrationData() throws IOException {
		getCalibrationData();
		findReferenceData();
		findChamberData();
		EnvironmentData.setEnvirometsData();
	}

	private void getCalibrationData() throws IOException {
		getOrdersData();
		findMeasurementsData();
	}
	
	private void getOrdersData() {
		CertificateData.findOrdersData();
	}
	
	private void findMeasurementsData() throws IOException {
		MeasurementsData.findMeasurementsData();
		MeasurementsData.findProbeData();
	}
	
	private void findReferenceData() throws IOException {
		EquipmentType probe = EquipmentType.setReferenceType();
		CalibrationData.probe = EquipmentParameters.findProbe(probe);
	}
	
	private void findChamberData() throws IOException {
		CalibrationData.chamber = EquipmentParameters.findChamber();
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
