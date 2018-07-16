package certyficate.GUI.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;

import certyficate.GUI.EnvironmentPanel;

public class EnvironmentListener implements PropertyChangeListener{
	private double MIN_TEMPERATURE = 17;
	private double MAX_TEMPERATURE = 27;
	private double MIN_HUMINIDITY = 20;
	private double MAX_HUMINIDITY = 80;
	
	private double minTemperature;
	private double maxTemperature;
	private double minHuminidity;
	private double maxHuminidity;

	public void propertyChange(PropertyChangeEvent e) {
		JFormattedTextField field = (JFormattedTextField) e.getSource();
		String text = field.getValue().toString();
		int index = findIndex(text);
		EnvironmentPanel.updateCondition();
		checkParametr(index);
	}
	
	private int findIndex(Object text) {
		int index = 0;
		for(int i = 0; i < EnvironmentPanel.PARAMETS_NUMBER; i++) {
			if(EnvironmentPanel.getText(i).equals(text)) {
				index = i;
			}
		}
		return index;
	}

	private void checkParametr(int index) {
		if(index < 2) {
			checkTemperature();
		} else {
			checkHuminidity();
		}
	}
	
	private void checkTemperature() {
		getTemperature();
		checkTemperatureValue();
		checkMinTemperature();
		checkMaxTemperature();
		setNewTemperature();
	}

	private void getTemperature() {
		minTemperature = EnvironmentPanel.getCondition(0);
		maxTemperature = EnvironmentPanel.getCondition(1);
	}

	private void checkTemperatureValue() {
		if(minTemperature > maxTemperature) {
			maxTemperature += minTemperature;
			minTemperature = maxTemperature - minTemperature;
			maxTemperature -= minTemperature;
		}
	}

	private void checkMinTemperature() {
		if(minTemperature < MIN_TEMPERATURE) {
			minTemperature = MIN_TEMPERATURE;
		}
	}

	private void checkMaxTemperature() {
		if(maxTemperature > MAX_TEMPERATURE) {
			maxTemperature = MAX_TEMPERATURE;
		}
	}

	private void setNewTemperature() {
		EnvironmentPanel.setCondition(minTemperature, 0);
		EnvironmentPanel.setCondition(maxTemperature, 1);	
	}

	private void checkHuminidity() {
		getHuminidity();
		checkHuminidityValue();
		checkMinHuminidity();
		checkMaxHuminidity();
		setNewHuminidity();
	}

	private void getHuminidity() {
		minHuminidity = EnvironmentPanel.getCondition(2);
		maxHuminidity = EnvironmentPanel.getCondition(3);
	}

	private void checkHuminidityValue() {
		if(minHuminidity > maxHuminidity) {
			maxHuminidity += minHuminidity;
			minHuminidity = maxHuminidity - minHuminidity;
			maxHuminidity -= minHuminidity;
		}
	}

	private void checkMinHuminidity() {
		if(minHuminidity < MIN_HUMINIDITY) {
			minHuminidity = MIN_HUMINIDITY;
		}
	}

	private void checkMaxHuminidity() {
		if(maxHuminidity > MAX_HUMINIDITY) {
			maxHuminidity = MAX_HUMINIDITY;
		}
	}

	private void setNewHuminidity() {
		EnvironmentPanel.environment[2].setValue(minHuminidity);
		EnvironmentPanel.environment[3].setValue(maxHuminidity);
	}
}