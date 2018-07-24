package certyficate.GUI.path;

import java.io.File;
import java.io.IOException;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class SheetSettings extends PathSettings {
	private static final String PANEL_NAME = "Arkusz z danymi";
	private static final String ERROR = "Błąd arkusza z danymi";
	
	SheetSettings(){
		setCurrentPath(PathCreator.sheetPath());
		setPanelName(PANEL_NAME);
		file = new File(currentPath);
		updateFile(file);
	}
	
	@Override
	public void updateFile(File file) {
		this.file = file;
		CalibrationData.sheet = file;
		setSheet();
	}

	private void setSheet() {
		try {
			CalibrationData.spreadSheet = SpreadSheet.createFromFile(file);
		} catch (IOException e) {
			System.out.println(ERROR);
			e.printStackTrace();
		}
	}

}
