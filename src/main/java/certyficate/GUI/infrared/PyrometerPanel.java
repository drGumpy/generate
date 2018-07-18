package certyficate.GUI.infrared;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import certyficate.entitys.Order;
import certyficate.property.DataCalculation;

@SuppressWarnings("serial")
public class PyrometerPanel extends JPanel {
	private final static String ACTIVE = "aktywny";
	private final static String APPLY_FOR_ALL = "zastosuj dla wszystkich";
	private final static String MODEL = "model";
	private final static String SERIAL_NUMBER = "numer seryjny";
	private final static String EMISSIVITY = "emisyjność";
	private final static String EMISSIVITY_FORMAT = "#0.00";
	private final static String DISTANCE = "odlegległość";
	
	private final static double DEFAULT_EMISSIVITY_VALLUE = 0.97;
	private final static double DEFAULT_DISTANCE_VALLUE = 200;
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;
	private static final int FIELD_SIZE = 10;
	
	private JRadioButton active;
	private JRadioButton copy;
	
	private JFormattedTextField emissivity;
	private JFormattedTextField distance;
	
	private InfraredParametrs owner;
	
	private PointPanel pointPanel;
	
	private Order certificate;
	
	private GridBagConstraints constrain;
	
	public IRData getPyrometerData() {
		IRData data = new IRData();
		data.emissivity = DataCalculation.getDouble(emissivity.getText());
		data.distance = Integer.parseInt(distance.getText());
		pointPanel.findPointsData(data);
		return data;
	}
	
	public PyrometerPanel(Order certificate, InfraredParametrs infraredParametrs) {
		owner = infraredParametrs;
		this.certificate = certificate;
		setBorder(new TitledBorder(certificate.declarant.name));
		pointPanel = new PointPanel(certificate);
		setPanel();
	}

	private void setPanel() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		constrain = new GridBagConstraints();	
		setMinimumSize(size);
		setLayout(new GridBagLayout());
		setPanelElements();
	}

	private void setPanelElements() {
		addButtons();
		addDeviceFields();
		addDataFields();
		addPointFields();
	}

	private void addButtons() {
		addActiveButton();
		addCopyButton();
		constrain.gridwidth = 1;
	}

	private void addActiveButton() {
		constrain.gridwidth = 2;
		active = new JRadioButton(ACTIVE);
		add(active, constrain);
		active.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				setElementsEditability(active.isSelected());
			}
		});
	}
	
	private void setElementsEditability(boolean active) {
		emissivity.setEditable(active);
		distance.setEditable(active);
		pointPanel.setEditability(active);
	}

	private void addCopyButton() {
		copy = new JRadioButton(APPLY_FOR_ALL);
		constrain.gridy = 1;
		add(copy, constrain);
		copy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				checkAndSetElements(active.isSelected());
			}
		});
	}
	
	private void checkAndSetElements(boolean selected) {
		if(selected) {
			setDataToPanels();
		} else {
			setElementsEditability(true);
		}
	}
	
	private void setDataToPanels() {
		IRData data = findDataFormElements();
		owner.setIRData(data);
	}

	private IRData findDataFormElements() {
		IRData data = getPyrometerData();
		data.point = certificate.point;
		return data;
	}
	
	private void addDeviceFields() {
		addModelField();
		addSerialField();
	}
	
	private void addModelField() {
		addLabel(MODEL);
		addField(certificate.device.model);
	}
	
	private void addSerialField() {
		addLabel(SERIAL_NUMBER);
		addField(certificate.deviceSerialNumber);
	}
	
	private void addLabel(String name) {
		JLabel label = new JLabel(name);
		constrain.gridy++;
		constrain.gridx = 0;
		add(label, constrain);	
	}
	
	private void addField(String text) {
		JTextField field = new JTextField(10);
		field.setEditable(false);
		field.setText(text);
		constrain.gridx = 1;
		add(field, constrain);	
	}

	private void addDataFields() {
		addEmissivityField();
		addDistanceField();
		addPointFields();
	}
	
	private void addEmissivityField() {
		addLabel(EMISSIVITY);
		emissivity = new JFormattedTextField(new DecimalFormat(EMISSIVITY_FORMAT));
		emissivity.setValue(DEFAULT_EMISSIVITY_VALLUE);
		emissivity.setColumns(FIELD_SIZE);
		constrain.gridx = 1;
		add(emissivity, constrain);
	}

	private void addDistanceField() {
		addLabel(DISTANCE);
		distance = new JFormattedTextField(
				NumberFormat.getIntegerInstance());
		distance.setValue(DEFAULT_DISTANCE_VALLUE);
		distance.setColumns(FIELD_SIZE);
		constrain.gridx = 1;
		add(distance, constrain);
	}

	private void addPointFields() {
		constrain.gridy++;
		constrain.gridx = 0;
		constrain.gridwidth = 2;
		add(pointPanel, constrain);
	}

	public void checkData(IRData pyrometerData) {
		if(pyrometerData.checkPoint(certificate)) {
			setData(pyrometerData);
			setElementsEditability(false);
		}		
	}

	private void setData(IRData pyrometerData) {
		emissivity.setValue(pyrometerData.emissivity);
		distance.setValue(pyrometerData.distance);
		pointPanel.setData(pyrometerData);		
	}

	public void setPyrometerData() {
		IRData data = getPyrometerData();
		pointPanel.setBlackBodyError(data);
		certificate.pyrometr = data;		
	}
}
