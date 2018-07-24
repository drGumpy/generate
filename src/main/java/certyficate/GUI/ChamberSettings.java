package certyficate.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import certyficate.property.CalibrationData;
import certyficate.property.CalibrationType;

@SuppressWarnings("serial")
public class ChamberSettings extends JPanel {
	private static final String PANEL_TITLE =
			"ilość punktów pomiarowych i rodzaj wzorcowania";
	private static final String TEMPERATURE_LABEL = "temperatura";
	private static final String HUMINIDITY_LABEL = "temperatura i wilgotność";
	
	private static final int MAXIMUM_CALIBRATION_POINTS = 6;
	private static final int CUSTOM_TEMPERATURE_POINT = 3;
	private static final int CUSTOM_HUMINIDITY_POINT = 5;
	
	private AbstractButton temperature; 
	private AbstractButton huminidity;
	
	private JComboBox<Integer> pointsBox;
	
	static int points = 3;
	
	static CalibrationType calibrationType;
	
	public ChamberSettings() {
		setPanelSettings();
		setChamberPanel();
	}

	private void setPanelSettings() {
		points = 3;
		calibrationType = CalibrationType.TEMPERATURE;
		setBorder(new TitledBorder(PANEL_TITLE));
	}

	private void setChamberPanel() {
		setPointsBox();
		setButtonGroup();
		updateCalibrationData();		
	}
	
	private void setPointsBox() {
		pointsBox = new JComboBox<Integer>();
		setCalibrationPoints();
		addPointBoxListener();
		add(pointsBox);
	}
	
	private void setCalibrationPoints() {
		for(int i = 1; i <= MAXIMUM_CALIBRATION_POINTS; i++) {
			pointsBox.addItem(i);
		}
		pointsBox.setSelectedIndex(2);
	}

	private void addPointBoxListener() {
		pointsBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				points = (Integer) pointsBox.getSelectedItem();
				updateCalibrationData();
			}
		});
	}

	private void setButtonGroup() {
		ButtonGroup buttons = new ButtonGroup();
		temperature = new JRadioButton(TEMPERATURE_LABEL);
		huminidity = new JRadioButton(HUMINIDITY_LABEL);
		buttons.add(huminidity);
		buttons.add(temperature);
		setButtons();
	}

	private void setButtons() {
		setTemperaturButton();
		setHuminidityButton();
	}

	private void setTemperaturButton() {
		temperature.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calibrationType = CalibrationType.TEMPERATURE;
				setNewParametrs(CUSTOM_TEMPERATURE_POINT);
			}
		});
		calibrationType = CalibrationType.TEMPERATURE;
		temperature.setSelected(true);
		add(temperature);
	}
	

	private void setHuminidityButton() {
		huminidity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calibrationType = CalibrationType.HUMINIDITY;
				setNewParametrs(CUSTOM_HUMINIDITY_POINT);
			}
		});
		add(huminidity);
	}
	
	private void setNewParametrs(int pointsNumber) {
		points = pointsNumber;
		pointsBox.setSelectedIndex(pointsNumber - 1);
		updateCalibrationData();
	}

	private void updateCalibrationData() {
		CalibrationData.calibrationType = calibrationType;
		CalibrationData.calibrationPoints = points;
	}
}
