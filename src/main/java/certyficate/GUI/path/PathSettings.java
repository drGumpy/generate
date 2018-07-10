package certyficate.GUI.path;

import java.io.File;

public abstract class PathSettings {
	protected String panelName;
	protected String currentPath;
	
	protected File file;
	
	public File getFile() {
		return file;
	}
	
	public static PathSettings getSettings(PathType pathType) {
		PathSettings settings;
		switch(pathType) {
		case CERTIFICATES:
			settings = new CertificateSettings();
			break;
		case NOTES:
			settings = new NotesSettings();
			break;
		default:
			settings = new SheetSettings();
			break;	
		}
		return settings;
	}
	
	public String getPanelName() {
		return panelName;
	}
	
	public String getCurrentPath() {
		return currentPath;
	}
	
	protected abstract void updateFile(File file) ;	
	
	protected void setPanelName(String name) {
		panelName = name;
	}
	
	protected void setCurrentPath(String name) {
		panelName = name;
	}
}
