package certyficate.GUI.path;

import java.io.File;

import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class CertificateSettings extends PathSettings {
	private static final String PANEL_NAME =
			"Folder zapisu świadectw wzorcowania";
	
	CertificateSettings() {
		setCurrentPath(PathCreator.certificatesPath());
		setPanelName(PANEL_NAME);
		file = new File(currentPath);
		updateFile(file);
	}
	
	@Override
	protected void updateFile(File file) {
		CalibrationData.certificate = file;
	}

}
