package certyficate.GUI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EnvironmentListener implements PropertyChangeListener{
	private double MIN_TEMPERATURE = 17;
	private double MAX_TEMPERATURE = 27;
	private double MIN_HUMINIDITY = 20;
	private double MAX_HUMINIDITY = 80;
	
	private double minTemperature;
	private double maxTemperature;
	private double minHuminidity;
	private double maxHuminidity;
	
	EnvironmentListener() {
		setData();
	}
	
	private void setData() {
		minTemperature = EnvironmentPanel.DEFAULT_TEMPERATURE;
		maxTemperature = EnvironmentPanel.DEFAULT_TEMPERATURE;
		minHuminidity = EnvironmentPanel.DEFAULT_HUMINIDITY;
		maxHuminidity = EnvironmentPanel.DEFAULT_HUMINIDITY;
	}

	public void propertyChange(PropertyChangeEvent e) {
		Object text = e.getSource();
		int index = findIndex(text);
		checkParametr(index);
	}

	private void checkParametr(int index) {
		double parametr = EnvironmentPanel.getParametr(index);
		switch(index) {
		case 0: minTemperature = parametr;
				break;
		case 1: maxTemperature = parametr;
				break;
		case 2: minHuminidity = parametr;
				break;
		default: maxHuminidity = parametr;
		}
		if(index < 2)
			checkTemperature();
		else
			checkHuminidity();
			
	}

	private int findIndex(Object text) {
		int index = 0;
		for(int i = 0; i < EnvironmentPanel.PARAMETS_NUMBER; i++) {
			if(EnvironmentPanel.environment[i].equals(text))
				index = i;
		}
		return index;
	}
	
	private void checkTemperature() {
		if(minTemperature > maxTemperature) {
			maxTemperature += minTemperature;
			minTemperature = maxTemperature - minTemperature;
			maxTemperature -= minTemperature;
		}
		if(minTemperature < MIN_TEMPERATURE) {
			minTemperature = MIN_TEMPERATURE;
		}
		if(maxTemperature > MAX_TEMPERATURE) {
			maxTemperature = MAX_TEMPERATURE;
		}
		EnvironmentPanel.environment[0].setValue(minTemperature);
		EnvironmentPanel.environment[1].setValue(maxTemperature);
	}

	private void checkHuminidity() {
		if(minHuminidity > maxHuminidity) {
			maxHuminidity += minHuminidity;
			minHuminidity = maxHuminidity - minHuminidity;
			maxHuminidity -= minHuminidity;
		}
		if(minHuminidity < MIN_HUMINIDITY) {
			minHuminidity = MIN_HUMINIDITY;
		}
		if(maxHuminidity > MAX_HUMINIDITY) {
			maxHuminidity = MAX_HUMINIDITY;
		}
		EnvironmentPanel.environment[2].setValue(minHuminidity);
		EnvironmentPanel.environment[3].setValue(maxHuminidity);
	}
}