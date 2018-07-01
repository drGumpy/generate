package certyficate.GUI.infrared;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PyrometrPanel extends JPanel {
	private final static String DEFAULT_EMISSIVITY_VALLUE = "0,97";
	private final static String DEFAULT_DISTANCE_VALLUE = "200"; 
	
	private JRadioButton set = new JRadioButton();
	private JRadioButton copy = new JRadioButton();
	
	private JTextField emissivity;
	private JTextField distance;
	private JTextField[] referenceValue;
	
	private JComboBox<String>[] blackBodyChoose;
	
	private JPanel _device(final int num){
		JPanel jp = new JPanel();
		jp.setMinimumSize(new Dimension(250, 200));
		jp.setLayout(new GridBagLayout());
		final int[][] array= data.get(num).point;	
		blackBodyChoose[num]= new JComboBox[array[0].length];
		referenceValue[num]= new JTextField[array[0].length];	
		GridBagConstraints c= new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=2;
		set[num] = new JRadioButton("aktywny");
		set[num].setSelected(true);
		set[num].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean selected = set[num].isSelected();
				copy[num].setEnabled(selected);
				emissivity[num].setEditable(selected);
				distance[num].setEditable(selected);
				for(int j=0; j<array[0].length; j++){
					blackBodyChoose[num][j].setEnabled(selected);
					referenceValue[num][j].setEnabled(selected);
				}
			}
		});
		jp.add(set[num], c);
		
		c.gridy=1;
		c.gridwidth=2;
		copy[num] = new JRadioButton("zastosuj dla wszystkich");		
		copy[num].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean selected = true;
				if(copy[num].isSelected()){
					selected = false;
				}
				if(!selected){
					int n= copy.length;
					String em = emissivity[num].getText();
					String dis = distance[num].getText();
					for(int i=0; i<n; i++){
						if(set[i].isSelected()
								&& _compareArray(data.get(i).point, data.get(num).point)){
							copy[i].setSelected(true);
							emissivity[i].setEditable(selected);
							emissivity[i].setText(em);
							distance[i].setEditable(selected);
							distance[i].setText(dis);
							for(int j=0; j<array[0].length; j++){ //
								blackBodyChoose[i][j].setSelectedItem(
										blackBodyChoose[num][j].getSelectedItem());
								blackBodyChoose[i][j].setEnabled(selected);
								referenceValue[i][j].setText(
										referenceValue[num][j].getText());
								referenceValue[i][j].setEnabled(selected);	
							}
						}
					}
				}else{
					emissivity[num].setEditable(selected);
					distance[num].setEditable(selected);
					for(int j=0; j<array[0].length; j++){
						blackBodyChoose[num][j].setEnabled(selected);
						referenceValue[num][j].setEnabled(selected);
					}
				}
			}
		});
		jp.add(copy[num], c);
		
		c.gridwidth=1;
		c.gridy=2;
		JLabel modelT = new JLabel();
		modelT.setText("model");
		jp.add(modelT, c);
		JTextField model = new JTextField(10);
		model.setEditable(false);
		model.setText(data.get(num).device.model);
		c.gridx=1;
		jp.add(model, c);
		
		c.gridx=0;
		c.gridy=3;
		JLabel serialT = new JLabel();
		serialT.setText("numer seryjny");
		jp.add(serialT, c);
		JTextField serial = new JTextField(10);
		serial.setEditable(false);
		serial.setText(data.get(num).deviceSerialNumber);
		c.gridx=1;
		jp.add(serial, c);

		c.gridx=0;
		c.gridy=4;
		JLabel emissivityT = new JLabel();
		emissivityT.setText("emisyjność");
		jp.add(emissivityT, c);
		emissivity[num] = new JTextField(10);
		emissivity[num].setEditable(true);
		emissivity[num].setText("0,97");
		c.gridx=1;
		emissivity[num].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				double data = _valideEmissivity(emissivity[num].getText());
				emissivity[num].setText(data+"");
			}
		});
		jp.add(emissivity[num], c);
		
		c.gridx=0;
		c.gridy=5;
		JLabel distanceT = new JLabel();
		distanceT.setText("odlegległość");
		jp.add(distanceT, c);
		distance[num] = new JTextField(10);
		distance[num].setEditable(true);
		distance[num].setText("200");
		c.gridx=1;
		distance[num].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int data = _valideDistance(distance[num].getText());
				distance[num].setText(data+"");
			}
		});
		jp.add(distance[num], c);
		
		c.gridx=0;
		c.gridy=6;
		c.gridwidth=2;
		jp.add(_pointData(num), c);
		
		jp.setBorder(new TitledBorder(data.get(num).declarant.name));
		return jp;
	}
}
