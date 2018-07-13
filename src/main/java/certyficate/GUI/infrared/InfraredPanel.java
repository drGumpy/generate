package certyficate.GUI.infrared;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.GUI.Console;
import certyficate.calculation.EnvironmentData;
import certyficate.dataContainer.CalibrationType;
import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.generate.Generate;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.insert.PutDate;
import certyficate.sheetHandlers.search.CertificateData;
import certyficate.sheetHandlers.search.MeasurementsData;

@SuppressWarnings("serial")
public class InfraredPanel extends JPanel {
	public static final String BUTTON_LABEL = "generuj świadetwa";
	
	public static final int WIDTH = 200;
	public static final int HIGHT = 23;
	
	private JButton generate;
	
	private Console console;
	
	public InfraredPanel(Console console) {
		this.console = console;
		setButtons();
	}

	private void setButtons() {
		setCalibrationDataButton();
		add(generate);
	}

	private void setCalibrationDataButton() {
		Dimension size = new Dimension(WIDTH, HIGHT);
		setSize(size);
		generate = new JButton(BUTTON_LABEL);
		generate.setMinimumSize(size);
		generate.addActionListener(new IRGenerateListener());
	}
	
	private class IRGenerateListener implements ActionListener {
		final private static int MAXIMUM_CALIBRATION_POINTS = 6;
		
		public void actionPerformed(ActionEvent e) {
			CalibrationData.calibrationType = CalibrationType.INFRARED;
			SheetData.setInfrared();
			getFilesData();
			generateCalibrationDocuments();
			PutDate.calibrationDate();
			console.close();
		}

		private void getFilesData()  {
			try {
				findCalibrationData();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		private void findCalibrationData() throws IOException {
			findMeasurmentsData();
			findReferenceData();
			EnvironmentData.setEnvirometsData();
		}

		private void findMeasurmentsData() throws IOException {
			getCalibrationData();
			findMeasurementsData();
			new InfraredParametrs(console);
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
				= EquipmentParameters.find(EquipmentType.INFRARED_REFERENCE);
		}	

		private void generateCalibrationDocuments() {
			Generate.generateDocuments();
		}
	}
}
