package certyficate.GUI.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import certyficate.GUI.path.PathFinder;
import certyficate.GUI.path.PathType;

public class ButtonListener implements ActionListener {
	private PathFinder pathFinder;
	
	public ButtonListener(PathFinder pathFinder) {
		this.pathFinder = pathFinder;
	}

	public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = getChooser();
        chooser.showOpenDialog(chooser);
        setPath(chooser);   
    }

	//TODO native system chooser
	private JFileChooser getChooser() {
		JFileChooser chooser = new JFileChooser(pathFinder.getFile());
		if(pathFinder.getPathType() != PathType.SHEET)
			chooser.setFileSelectionMode(
					JFileChooser.DIRECTORIES_ONLY);
		return chooser;
	}

	private void setPath(JFileChooser chooser) {
		pathFinder.setFile(chooser.getSelectedFile());
	}
}
