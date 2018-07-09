package certyficate.GUI.infrared;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import certyficate.dataContainer.IRData;
import certyficate.entitys.*;
import certyficate.property.CalibrationData;

@SuppressWarnings("serial")
public class InfraredParametrs extends JDialog {
	public static final String PANEL_NAME = "dane o badanych pirometrach";
	public static final String BUTTON_LABEL = "zatwierdź";
	
	public static final int WIDTH= 700;
	public static final int HIGHT = 600;
	public static final int DEVICES_PER_LINE = 3;
	
	private JButton accept;
	
	private PyrometerPanel[] pyrometers;
	
	private List<Certificate> data;
	
	private int devicesPerWindow = 2 * DEVICES_PER_LINE;
	private int numberOfOrders;
	
	private GridBagConstraints constrain;
	
	public InfraredParametrs(Frame owner){
		super(owner, true);
		findPanelData();
		setPanelSettings();
		setWindow();
	}

	private void findPanelData() {
		data = CalibrationData.orders;
		numberOfOrders = data.size();
		checkMaxCalibrationPoints();		
	}

	private void checkMaxCalibrationPoints() {
		int max = findMaxCalibrationPoints();
		if(max > 3) {
			devicesPerWindow = DEVICES_PER_LINE;
		}
	}

	private int findMaxCalibrationPoints() {
		int max = 0;
		for(Certificate calibration: data) {
			int calibrationPoints = calibration.point.length;
			max = Math.max(max, calibrationPoints);
		}
		return max;
	}

	private void setPanelSettings() {
		Dimension size = new Dimension(WIDTH, HIGHT);
		pyrometers = new PyrometerPanel[data.size()];
		accept = getAcceptButton();
		setSize(size);
		setTitle(PANEL_NAME);
	}
	
	private void setWindow() {
		if(numberOfOrders > devicesPerWindow) {
			setTabbedPane();
		} else {
			setFirstPanel();
		}
	}

	private void setTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		for(int i = 0; i < numberOfOrders; i += devicesPerWindow){
			String TabName = setName(i);
			tabbedPane.addTab(TabName, getPanel(i));
		}
		add(tabbedPane);
	}

	private String setName(int index) {
		StringBuilder bulid = new StringBuilder();
		int endOfName = Math.min(index + devicesPerWindow, numberOfOrders);
		bulid.append(index + 1);
		bulid.append(" ÷ ");
		bulid.append(endOfName);
		return bulid.toString();
	}

	private void setFirstPanel() {
		add(getPanel(0));		
	}

	private JPanel getPanel(int index) {
		int lastObjectInPanel = Math.min(index + devicesPerWindow, numberOfOrders);
		JPanel panel = new JPanel();
		constrain = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		for(int i = index; i < lastObjectInPanel; i++){
			setPyrometrPanel(panel, i);
		}
		constrain.gridy++;
		constrain.gridx = 0;
		constrain.gridwidth = 3;
		panel.add(accept, constrain);
		return panel;
	}

	private void setPyrometrPanel(JPanel panel, int index) {
		Certificate certificate = data.get(index);
		pyrometers[index] = new PyrometerPanel(certificate, this);
		constrain.gridy = index / DEVICES_PER_LINE;
		constrain.gridx = index % DEVICES_PER_LINE;
		panel.add(pyrometers[index], constrain);
	}
	
	private JButton getAcceptButton() {
		JButton accept = new JButton(BUTTON_LABEL);
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				setData();
				close();
			}
		});
		return accept;
	}
	
	private void setData() {
		for(PyrometerPanel pyrometer: pyrometers) {
			pyrometer.setPyrometerData();
		}
	}
	
	private void close(){
		this.dispose();
	}

	public void setIRData(IRData pyrometerData) {
		for(PyrometerPanel pyrometer: pyrometers) {
			pyrometer.checkData(pyrometerData);
		}
	}
}

