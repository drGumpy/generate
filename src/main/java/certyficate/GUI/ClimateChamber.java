package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.GUI.listeners.DataLoggerListener;
import certyficate.GUI.listeners.GenerateListener;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;

@SuppressWarnings("serial")
public class ClimateChamber extends JPanel {
	public static final String LOGGER_BUTTON = "dane z rejestratorów";
	public static final String GENERATION_BUTTON = "generuj świadetwa";
	
	public static final int WIDTH = 200;
	public static final int HIGHT = 23;
	
	private Console console;
	
	private GridBagConstraints constrain;
	
	public ClimateChamber(Console console) {
		this.console = console;
		setPanelSettings();
		setPanelElements();
	}
	
	public void setSheetData() {
		SheetData.setChamberData(CalibrationData.calibrationType);
	}

	public void close() {
		console.close();
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
		dataLogger.addActionListener(new DataLoggerListener(this));
        constrain.gridwidth = 1;
        constrain.gridy = 1;
        add(dataLogger, constrain);
	}
	
	private void setGenerationButton() {
		JButton generate= new JButton(GENERATION_BUTTON);
		generate.setMinimumSize(new Dimension(WIDTH, HIGHT));
		generate.addActionListener(new GenerateListener(this));
		constrain.gridx = 1;
        add(generate, constrain);
	}
}
