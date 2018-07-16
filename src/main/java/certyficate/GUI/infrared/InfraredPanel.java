package certyficate.GUI.infrared;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.GUI.Console;
import certyficate.GUI.listeners.IRGenerateListener;

@SuppressWarnings("serial")
public class InfraredPanel extends JPanel {
	public static final String BUTTON_LABEL = "generuj świadetwa";
	
	public static final int WIDTH = 200;
	public static final int HIGHT = 23;
	
	private JButton generate;
	
	private Console console;
	
	public InfraredPanel(Console console) {
		this.console = console;
		setButtons();
	}
	
	public Console getConsole() {
		return console;
	}

	public void close() {
		console.close();
	}
	
	private void setButtons() {
		setCalibrationDataButton();
		add(generate);
	}

	private void setCalibrationDataButton() {
		Dimension size = new Dimension(WIDTH, HIGHT);
		generate = new JButton(BUTTON_LABEL);
		generate.setMinimumSize(size);
		generate.addActionListener(new IRGenerateListener(this));
	}
}
