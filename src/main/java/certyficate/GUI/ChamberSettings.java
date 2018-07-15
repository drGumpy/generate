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
		setChamberPanel();
	}

	private void setChamberPanel() {
		setButtonGroup();
		setPointsBox();
		setPanel();
		updateCalibrationData();		
	}

	private void setButtonGroup() {
		setButtons();
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(huminidity);
		buttons.add(temperature);
	}

	private void setButtons() {
		setHuminidityButton();
		setTemperaturButton();
	}

	private void setHuminidityButton() {
		huminidity = new JRadioButton(HUMINIDITY_LABEL);
		huminidity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calibrationType = CalibrationType.HUMINIDITY;
				setNewParametrs(CUSTOM_HUMINIDITY_POINT);
			}
		});
	}
	
	private void setTemperaturButton() {
		temperature = new JRadioButton(TEMPERATURE_LABEL); 
		temperature.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				calibrationType = CalibrationType.TEMPERATURE;
				setNewParametrs(CUSTOM_TEMPERATURE_POINT);
			}
		});
		calibrationType = CalibrationType.TEMPERATURE;
		temperature.setSelected(true);
	}
	
	private void setNewParametrs(int pointsNumber) {
		points = pointsNumber;
		pointsBox.setSelectedIndex(pointsNumber - 1);
		updateCalibrationData();
	}
	
	private void setPointsBox() {
		pointsBox = new JComboBox<Integer>();
		for(int i = 1; i <= MAXIMUM_CALIBRATION_POINTS; i++)
			pointsBox.addItem(i);
		pointsBox.setSelectedIndex(2);
		pointsBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				points = (Integer) pointsBox.getSelectedItem();
				updateCalibrationData();
			}
		});
	}
	
	private void setPanel() {
		setBorder(new TitledBorder(PANEL_TITLE));
		addElements();
	}
	
	private void addElements() {
		add(pointsBox);	
		add(temperature);
		add(huminidity);
	}
	
	private void updateCalibrationData() {
		CalibrationData.calibrationType = calibrationType;
		CalibrationData.calibrationPoints = points;
	}
}
