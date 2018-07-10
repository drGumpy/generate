package certyficate.GUI.path;

import java.io.File;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class NotesSettings extends PathSettings {
	private static final String PANEL_NAME =
			"Folder zapisu zapisek z wzorcowania";
	
	NotesSettings() {
		setCurrentPath(PathCreator.notePath());
		setPanelName(PANEL_NAME);
		file = new File(currentPath);
		updateFile(file);
	}
	
	@Override
	protected void updateFile(File file) {
		CalibrationData.notes = file;
	}

}
