package certyficate.GUI.path;

import java.io.File;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class SheetSettings extends PathSettings {
	private static final String PANEL_NAME =
			"Arkusz z danymi";
	
	SheetSettings(){
		setCurrentPath(PathCreator.sheetPath());
		setPanelName(PANEL_NAME);
		file = new File(currentPath);
		updateFile(file);
	}
	
	@Override
	protected void updateFile(File file) {
		this.file = file;
		CalibrationData.sheet = file;
	}

}
