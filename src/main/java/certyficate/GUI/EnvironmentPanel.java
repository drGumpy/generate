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

@SuppressWarnings("serial")
public class EnvironmentPanel extends JPanel {
	protected static final double DEFAULT_TEMPERATURE = 22.000;
	protected static final double DEFAULT_HUMINIDITY = 45.000;
	
	public static final int WIDTH = 400;
	public static final int HIGHT = 80;
	
	protected static final int PARAMETS_NUMBER = 4;
	
	protected String[] LABEL_TEXT  = {"t min","t max","Rh min","Rh max"};
	
	private String PANEL_TITLE = "Warunki środowiskowe";
	
	protected static JFormattedTextField[] environment;
	
	private NumberFormat numbersFormat;
	
	private GridBagConstraints constrain;
	
	private EnvironmentListener listener;

	EnvironmentPanel(){
		setPanelSettings();
		setEviromentsTextField();
		addFields();
		addListener();
	}

	private void setPanelSettings() {
		Dimension size = new Dimension(WIDTH, HIGHT);
		setPreferredSize(size);
		setBorder(new TitledBorder(PANEL_TITLE));
		setLayout(new GridBagLayout());
		setNumbersFormat();
		setLayoutSettings();
	}

	private void setLayoutSettings() {
		constrain = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		constrain.fill = GridBagConstraints.HORIZONTAL;
		constrain.ipadx = 10;
	}

	private void setNumbersFormat() {
		numbersFormat = new DecimalFormat("#0.000");;
		numbersFormat.setMinimumFractionDigits(3);
	}
	
	private void setEviromentsTextField() {
		environment = new JFormattedTextField[PARAMETS_NUMBER];
		listener = new EnvironmentListener(this);
		for(int i = 0; i < PARAMETS_NUMBER ; i++) {
			setEvironmetField(i);
		}
	}
	
	private void setEvironmetField(int index) {
		environment[index] = new JFormattedTextField(numbersFormat);
		environment[index].setName(LABEL_TEXT[index]);
		if(index<2) {
			environment[index].setValue(new Double(DEFAULT_TEMPERATURE));
		} else {
			environment[index].setValue(new Double(DEFAULT_HUMINIDITY));
		}
		environment[index].setColumns(5);
		environment[index].addPropertyChangeListener(listener);
	}

	private void addFields() {
	    for(int i = 0; i < PARAMETS_NUMBER ; i++) {
	    	constrain.gridx = (i * 2) % PARAMETS_NUMBER;
	    	constrain.gridy = i / 2;
	    	this.add(environment[i], constrain);
	    	addLabel(i);
	    }	
	}
	
	private void addLabel(int index) {
		JLabel label= new JLabel();
    	label.setText(LABEL_TEXT[index]);
    	constrain.gridx++;
    	this.add(label, constrain);		
	}

	private void addListener() {
		for(int i = 0; i < PARAMETS_NUMBER ; i++) {
	    	environment[i].addPropertyChangeListener(listener);
		}	
	}
	
	public static double[] getEnviromentCondition() {
		double[] enviromentCondition = new double[PARAMETS_NUMBER];
		for(int i = 0; i < PARAMETS_NUMBER; i++)
			enviromentCondition[i] = getParametr(i);
		return enviromentCondition;
	}
	
	protected static double getParametr(int index) {
		double parametr = Double.parseDouble(environment[index]
				.getValue().toString());
		return parametr;
	}
}
