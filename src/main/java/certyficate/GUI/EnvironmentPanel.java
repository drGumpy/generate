package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import certyficate.GUI.listeners.EnvironmentListener;

@SuppressWarnings("serial")
public class EnvironmentPanel extends JPanel {
	public static final double DEFAULT_TEMPERATURE = 22D;
	public static final double DEFAULT_HUMINIDITY = 45D;
	
	public static final int PARAMETS_NUMBER = 4;
	
	private static final int WIDTH = 300;
	private static final int HIGHT = 50;
	
	private static int changeIndex;
	
	private static final String[] LABEL_TEXT  = {"t min","t max","Rh min","Rh max"};
	
	private static final String NUMBER_FORMAT = "#0.000";
	private static final String PANEL_TITLE = "Warunki środowiskowe";
	
	private static double[] enviromentCondition;
	
	private static JFormattedTextField[] environment;
	
	private NumberFormat numbersFormat;
	
	private GridBagConstraints constrain;

	EnvironmentPanel(){
		setSettings();
		setEviromentsValue();
		addFields();
		addListener();
	}

	public static double[] getEnviromentCondition() {
		return enviromentCondition;
	}
	
	public static void updateCondition() {
		for(int i = 0; i < PARAMETS_NUMBER; i++) {
			enviromentCondition[i] = getParametr(i);
		}
	}
	
	public static double getCondition(int index) {
		return enviromentCondition[index];
	}
	
	public static String getText(int index) {
		return environment[index].getValue().toString();
	}
	
	public static void setCondition(double minTemperature, int index) {
		changeIndex = index;
		enviromentCondition[index] = minTemperature;
		environment[index].setValue(minTemperature);
	}
	
	public static boolean checkChange(JFormattedTextField field) {
		boolean isChange = true;
		if(changeIndex != -1) {
			isChange = !field.equals(environment[changeIndex]);
		}
		return isChange;
	}
	
	public static void resetIndex() {
		changeIndex = -1;
	}
	
	private static double getParametr(int index) {
		return Double.parseDouble(environment[index].getValue().toString());
	}

	private void setSettings() {
		setPanelSettings();
		setNumbersFormat();
		setLayoutSettings();
	}

	private void setPanelSettings() {
		Dimension size = new Dimension(WIDTH, HIGHT);
		setPreferredSize(size);
		setBorder(new TitledBorder(PANEL_TITLE));
	}

	private void setNumbersFormat() {
		numbersFormat = new DecimalFormat(NUMBER_FORMAT);;
		numbersFormat.setMinimumFractionDigits(3);
	}
	
	private void setLayoutSettings() {
		setLayout(new GridBagLayout());
		constrain = new GridBagConstraints();
		constrain.fill = GridBagConstraints.HORIZONTAL;
		constrain.ipadx = 10;
	}
	
	private void setEviromentsValue() {
		setEnviromentCondition();
		setEviromentsTextField();
	}
	
	private void setEnviromentCondition() {
		enviromentCondition = new double[4];
		enviromentCondition[0] = DEFAULT_TEMPERATURE;
		enviromentCondition[1] = DEFAULT_TEMPERATURE;
		enviromentCondition[2] = DEFAULT_HUMINIDITY;
		enviromentCondition[3] = DEFAULT_HUMINIDITY;
	}

	private void setEviromentsTextField() {
		environment = new JFormattedTextField[PARAMETS_NUMBER];
		for(int i = 0; i < PARAMETS_NUMBER ; i++) {
			setEvironmetField(i);
		}
	}
	
	private void setEvironmetField(int index) {
		environment[index] = new JFormattedTextField(numbersFormat);
		environment[index].setValue(enviromentCondition[index]);
		environment[index].setName(LABEL_TEXT[index]);
		environment[index].setColumns(5);
		environment[index].addPropertyChangeListener(new EnvironmentListener());
	}

	private void addFields() {
	    for(int i = 0; i < PARAMETS_NUMBER ; i++) {
	    	addField(i);
	    }	
	}
	
	private void addField(int index) {
		constrain.gridx = (index * 2) % PARAMETS_NUMBER;
    	constrain.gridy = index / 2;
    	add(environment[index], constrain);
    	addLabel(index);
	}

	private void addLabel(int index) {
		JLabel label= new JLabel();
    	label.setText(LABEL_TEXT[index]);
    	constrain.gridx++;
    	add(label, constrain);		
	}

	private void addListener() {
		for(int i = 0; i < PARAMETS_NUMBER ; i++) {
	    	environment[i].addPropertyChangeListener(new EnvironmentListener());
		}	
	}
}
