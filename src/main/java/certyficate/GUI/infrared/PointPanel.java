package certyficate.GUI.infrared;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import certyficate.dataContainer.IRData;
import certyficate.entitys.Certificate;

@SuppressWarnings("serial")
public class PointPanel extends JPanel {
	private final static String RADIATOR = "radiator";
	private int numberOfParametrs;
	
	private Certificate certificate;
	
	private JComboBox<String>[] blackBodyChoose;
	
	private JFormattedTextField[] referenceValue;
	
	private GridBagConstraints constrain = new GridBagConstraints();
	
	public void setEditability(boolean active) {
		for(int i = 0; i < numberOfParametrs; i++) {
			blackBodyChoose[i].setEditable(active);
			referenceValue[i].setEditable(active);
		}
	}
	
	PointPanel(Certificate certificate) {
		this.certificate = certificate;
		setPanelData();
		addPoints();
	}

	private void setPanelData() {
		setLayout(new GridBagLayout());
		setPanelElements();
		constrain.anchor = GridBagConstraints.PAGE_START;
		constrain.fill = GridBagConstraints.HORIZONTAL;
	}

	@SuppressWarnings("unchecked")
	private void setPanelElements() {
		numberOfParametrs = certificate.point.length;
		blackBodyChoose = new JComboBox[numberOfParametrs];
		referenceValue = new JFormattedTextField[numberOfParametrs];
	}

	private void addPoints() {
		for(int i = 0; i < numberOfParametrs; i++) {
			addPoint(i);
			constrain.gridy++;
		}
	}

	private void addPoint(int index) {
		int point = certificate.point[index][0];
		addLabel(point);
		setComboBox(index);
		setTextField(index);
	}

	private void addLabel(int point) {
		JLabel pointLabel = new JLabel();
		String label = new StringBuilder(point).toString();
		pointLabel.setText(label);
		constrain.gridx = 0;
		add(pointLabel, constrain);
	}
	

	private void setComboBox(int index) {
		blackBodyChoose[index] = new JComboBox<String>();
		setComboBoxValue(index);		
	}

	private void setComboBoxValue(int index) {
		int numberOfBlackBody = BlackBodyData.blackBody.length;
		for(int i = 0; i < numberOfBlackBody; i++) 
			blackBodyChoose[index].addItem(BlackBodyData.blackBody[i]);
		blackBodyChoose[index].setSelectedIndex((
				index + 1) / numberOfBlackBody);
		addComboBox(index);
	}

	private void addComboBox(int index) {
		checkValue(index);
		constrain.gridx = 1;
		add(blackBodyChoose[index], constrain);
	}

	private void checkValue(int index) {
		if (certificate.point[index][0] == 25) {
			blackBodyChoose[index].addItem(RADIATOR);
			blackBodyChoose[index].setSelectedItem(RADIATOR);
		}
	}
	
	private void setTextField(int index) {
		referenceValue[index] = createTextField(index);
		constrain.gridx = 2;
		add(referenceValue[index], constrain);
	}

	private JFormattedTextField createTextField(int index) {
		JFormattedTextField textField = new JFormattedTextField(
				new DecimalFormat("#0.0"));
		textField.setValue(certificate.point[index][0]);
		return textField;
	}

	public void findPointsData(IRData data) {
		data.setNumberOfParametrs(numberOfParametrs);
		for(int i = 0; i < numberOfParametrs; i++) {
			data.blackBodyName[i] = getBlackBodyName(i);
			data.reference[i] = getReferenceValue(i);
		}
	}

	private String getBlackBodyName(int index) {
		Object blackBody = blackBodyChoose[index].getSelectedItem();
		String blackBodyName = blackBody.toString();
		return blackBodyName;
	}
	
	private double getReferenceValue(int index) {
		String fieldValue = referenceValue[index].getText();
		double value = Double.parseDouble(fieldValue);
		return value;
	}

	public void setData(IRData pyrometerData) {
		for(int i = 0; i < numberOfParametrs; i++) {
			setData(pyrometerData, i);
		}
	}

	private void setData(IRData pyrometerData, int index) {
		blackBodyChoose[index].setSelectedItem(
				pyrometerData.blackBodyName[index]);
		referenceValue[index].setValue(pyrometerData.reference[index]);
	}

	public void setBlackBodyData() {
		// TODO Auto-generated method stub
		
	}
}
