package certyficate.GUI.infrared;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import certyficate.equipment.type.BlackBodyGenerator;
import certyficate.files.PathCreator;

public class BlackBodyData {
	private static final int RADIATOR_VALUE = 25;
	private static final String RADIATOR = "radiator";
	
	private static String[] blackBodys = {"10000236", "10000220"};

	private static Map<String, BlackBodyGenerator> blackBodyData;
	
	public static void setBlackBodyGenerators() {
		blackBodyData = new HashMap<String, BlackBodyGenerator>();
		for(String blackBody: blackBodys) {
			findAndSetBlackBody(blackBody);
		}
	}

	private static void findAndSetBlackBody(String blackBody) {
		try {
			setBlackBody(blackBody);
		} catch (IOException e) {
			System.out.println("non blackBody file: " + blackBody);
			e.printStackTrace();
		}
	}

	private static void setBlackBody(String blackBody) throws IOException {
		String path = PathCreator.txtFilePath(blackBody);
		BlackBodyGenerator generator = new BlackBodyGenerator(path);
		blackBodyData.put(blackBody, generator);
	}

	public static JComboBox<String> setComboBox(double point, int index) {
		JComboBox<String> comboBox = new JComboBox<String>();
		setBlackBodyName(comboBox);
		if(point == RADIATOR_VALUE) {
			comboBox.addItem(RADIATOR);
			comboBox.setSelectedItem(RADIATOR);
		} else {
			comboBox.setSelectedIndex(
					(index + 1) / blackBodyData.size());
		}
		return comboBox;
	}

	private static void setBlackBodyName(JComboBox<String> comoBox) {
		for (String entry : blackBodyData.keySet()) {
			comoBox.addItem(entry);
		}
	}

	public static double getBlackBodyError(String blackBodyName, double[] pointValue) {
		BlackBodyGenerator generator = blackBodyData.get(blackBodyName);
		return generator.getUncertainty(pointValue);
	}
}
