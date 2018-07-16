package certyficate.GUI.infrared;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import certyficate.entitys.Order;

@SuppressWarnings("serial")
public class PointPanel extends JPanel {
	private int numberOfParametrs;
	
	private Order certificate;
	
	private JComboBox<String>[] blackBodyChoose;
	
	private JFormattedTextField[] referenceValue;
	
	private GridBagConstraints constrain;
	
	public void setEditability(boolean active) {
		for(int i = 0; i < numberOfParametrs; i++) {
			blackBodyChoose[i].setEditable(active);
			referenceValue[i].setEditable(active);
		}
	}
	
	PointPanel(Order certificate) {
		this.certificate = certificate;
		setPanelData();
		addPoints();
	}

	private void setPanelData() {
		setLayout(new GridBagLayout());
		setPanelElements();
		constrain = new GridBagConstraints();
		constrain.anchor = GridBagConstraints.PAGE_START;
		constrain.fill = GridBagConstraints.HORIZONTAL;
		setSize();
	}

	private void setSize() {
		int WIDTH = 300;
		int HIGHT = 400;
		setSize 
	}

	@SuppressWarnings("unchecked")
	private void setPanelElements() {
		numberOfParametrs = certificate.point.length;
		System.out.println(numberOfParametrs);
		blackBodyChoose = new JComboBox[numberOfParametrs];
		referenceValue = new JFormattedTextField[numberOfParametrs];
	}

	private void addPoints() {
		for(int i = 0; i < numberOfParametrs; i++) {
			addPoint(i);
			constrain.gridy += 2;
		}
	}

	private void addPoint(int index) {
		double point = certificate.point[index][0];
		addLabel((int) point);
		setComboBox(index);
		setTextField(index);
	}

	private void addLabel(int point) {
		JLabel pointLabel = new JLabel();
		String label = Integer.toString(point);
		pointLabel.setText(label);
		constrain.gridx = 0;
		add(pointLabel, constrain);
	}
	

	private void setComboBox(int index) {
		blackBodyChoose[index] = 
				BlackBodyData.setComboBox(certificate.point[index][0], index);
		addComboBox(index);		
	}

	private void addComboBox(int index) {
		constrain.gridx = 1;
		add(blackBodyChoose[index], constrain);
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

	public void setBlackBodyError(IRData data) {
		double[] blackBodyError = new double[numberOfParametrs];
		for(int i = 0; i < numberOfParametrs; i++) {
			blackBodyError[i] = 
					BlackBodyData.getBlackBodyError(getBlackBodyName(i), certificate.point[i]);
		}
		data.blackBodyError = blackBodyError;
	}
}
