package certyficate.GUI.path;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import certyficate.GUI.listeners.ButtonListener;

@SuppressWarnings("serial")
public class PathFinder  extends JPanel {
	private static final int WIDTH = 650;
	private static final int HEIGHT = 50;
	private static final int FIELD_SIZE = 50;

	private static final String BUTTON_TEXT = "Zmień";
	
	private File file;
	
	private PathType pathType;
	
	private JTextField pathTextField;
	
	private PathSettings setting;
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
		setting.updateFile(file);
		pathTextField.setText(file.toString());
	}
	
	public PathType getPathType() {
		return pathType;
	}
	
	public PathFinder(PathType pathType) {
		this.pathType = pathType;
		setSettingsAndFile();
		setPanelSettings();
		setPanelElements();
	}

	private void setSettingsAndFile() {
		setting = PathSettings.getSettings(pathType);
		file = setting.getFile();
	}

	private void setPanelSettings() {
		String panelName = setting.panelName;
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setLayout(new FlowLayout());
		setBorder(new TitledBorder(panelName));
	}

	private void setPanelElements() {
		addButton();
		addTextField();
	}
	
	private void addButton() {
		JButton button = createButton();
		add(button);
	}
	
	private void addTextField() {
		setTextField();
		add(pathTextField);
	}

	private JButton createButton() {
		JButton button = new JButton(BUTTON_TEXT);
		button.addActionListener(new ButtonListener(this));
		return button;
	}

	private void setTextField() {
		String currentPath = file.toString();
		pathTextField = new JTextField(FIELD_SIZE);
		pathTextField.setText(currentPath);
		pathTextField.setEditable(false);
	}
}
