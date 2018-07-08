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

import certyficate.dataContainer.IRData;
import certyficate.entitys.Certificate;

@SuppressWarnings("serial")
public class PyrometerPanel extends JPanel {
	private final static String DEFAULT_EMISSIVITY_VALLUE = "0,97";
	private final static String DEFAULT_DISTANCE_VALLUE = "200";
	private final static String ACTIVE = "aktywny";
	private final static String APPLY_FOR_ALL = "zastosuj dla wszystkich";
	
	public static final int HIGHT = 250;
	public static final int WIDTH = 200;
	
	private JRadioButton active;
	private JRadioButton copy;
	
	private JFormattedTextField emissivity;
	private JFormattedTextField distance;
	
	private InfraredParametrs owner;
	
	private PointPanel pointPanel;
	
	private Certificate certificate;
	
	private GridBagConstraints constrain;
	
	public void setElementsEditability(boolean active) {
		emissivity.setEditable(active);
		distance.setEditable(active);
		pointPanel.setEditability(active);
	}
	
	public PyrometerPanel(Certificate certificate, InfraredParametrs infraredParametrs) {
		owner = infraredParametrs;
		this.certificate = certificate;
		pointPanel = new PointPanel(certificate);
		setPanel();
	}

	private void setPanel() {
		Dimension size = new Dimension(HIGHT, WIDTH);
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
	}

	private void addActiveButton() {
		constrain.gridx = 0;
		constrain.gridy = 0;
		constrain.gridwidth = 2;
		active = new JRadioButton(ACTIVE);
		add(active, constrain);
		active.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				setElementsEditability(active.isSelected());
			}
		});
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
			IRData data = findDataFormElements();
			owner.setIRData(data);
		} else 
			setElementsEditability(true);
	}

	private IRData findDataFormElements() {
		IRData data = getPyrometerData();
		data.point = certificate.point;
		return data;
	}
	
	public IRData getPyrometerData() {
		IRData data = new IRData();
		data.emissivity = Double.parseDouble(
				emissivity.getText());
		data.distance = Integer.parseInt(
				distance.getText());
		pointPanel.findPointsData(data);
		return data;
	}
	
	private void addDeviceFields() {
		addModelField();
		addSerialField();
	}
	
	private void addModelField() {
		addLabel("model");
		addField(certificate.device.model);
	}
	
	private void addSerialField() {
		addLabel("numer seryjny");
		addField(certificate.deviceSerialNumber);
	}
	
	private void addLabel(String name) {
		JLabel label = new JLabel("model");
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
		addLabel("emisyjność");
		emissivity = new JFormattedTextField(new DecimalFormat("#0.00"));
		emissivity.setValue(DEFAULT_EMISSIVITY_VALLUE);
		constrain.gridx = 1;
		add(emissivity, constrain);
	}

	private void addDistanceField() {
		addLabel("emisyjność");
		distance = new JFormattedTextField(
				NumberFormat.getIntegerInstance());
		distance.setValue(DEFAULT_DISTANCE_VALLUE);
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
		pointPanel.setBlackBodyData();
		certificate.pyrometr = data;		
	}
}
