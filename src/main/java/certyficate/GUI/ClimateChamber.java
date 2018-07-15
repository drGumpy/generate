package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.generate.Generate;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.insert.PutData;
import certyficate.sheetHandlers.search.CertificateData;
import certyficate.sheetHandlers.search.MeasurementsData;

@SuppressWarnings("serial")
public class ClimateChamber extends JPanel {
	public static final String LOGGER_BUTTON = "dane z rejestratorów";
	public static final String GENERATION_BUTTON = "generuj świadetwa";
	
	public static final int WIDTH = 200;
	public static final int HIGHT = 23;
	
	static ChamberSettings settings;
	
	private Console console;
	
	GridBagConstraints constrain = new GridBagConstraints();
	
	public ClimateChamber(Console console) {
		this.console = console;
		setPanelSettings();
		setPanelElements();
	}


	private void setPanelSettings() {
		 setLayout(new GridBagLayout());
		 constrain = new GridBagConstraints();
		 constrain.anchor = GridBagConstraints.PAGE_START;
		 constrain.ipadx = 10;
	}
	
	private void setPanelElements() {
		setChamberSettings();
		setLoggerButton();
		setGenerationButton();
	}


	private void setChamberSettings() {
		ChamberSettings settings = new ChamberSettings();
		constrain.gridwidth = 2;
        add(settings, constrain);
	}


	private void setLoggerButton() {
		JButton dataLogger = new JButton(LOGGER_BUTTON);
		dataLogger.setMinimumSize(new Dimension(WIDTH, HIGHT));
		dataLogger.addActionListener(new DataLoggerListener());
        constrain.gridwidth = 1;
        constrain.gridy = 1;
        add(dataLogger, constrain);
	}
	
	private class DataLoggerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSheetData();
			PutData.insertReferenceAndLoggersData();
		}
	}
	
	private void setSheetData() {
		SheetData.setChamberData(CalibrationData.calibrationType);
	}
	
	private void setGenerationButton() {
		JButton generate= new JButton(GENERATION_BUTTON);
		generate.setMinimumSize(new Dimension(WIDTH, HIGHT));
		generate.addActionListener(new GenerateListener());
		constrain.gridx = 1;
        add(generate, constrain);
	}

	private class GenerateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSheetData();
			getFilesData();
			generateCalibrationDocuments();
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
			findChamberData();
			EnvironmentData.setEnvirometsData();
		}

		private void findMeasurmentsData() throws IOException {
			getCalibrationData();
			findMeasurementsData();
		}
		
		private void getCalibrationData() {
			CertificateData.findOrdersData();
		}
		
		private void findMeasurementsData() throws IOException {
			MeasurementsData.findMeasurementsData();
			MeasurementsData.findProbeData();
		}
		
		private void findReferenceData() throws IOException {
			EquipmentType probe = EquipmentType.setReferenceType();
			CalibrationData.probe = EquipmentParameters.find(probe);
		}
		
		private void findChamberData() throws IOException {
			EquipmentType chamber = EquipmentType.setChamber();
			CalibrationData.chamber = EquipmentParameters.find(chamber);
		}

		private void generateCalibrationDocuments() {
			try {
				Generate.generateDocuments();
			} catch (IOException e) {
				System.out.println("Generate file error");
				e.printStackTrace();
			}
		}
	}
}
