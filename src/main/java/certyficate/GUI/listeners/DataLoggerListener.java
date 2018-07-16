package certyficate.GUI.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import certyficate.GUI.ClimateChamber;
import certyficate.sheetHandlers.insert.PutData;

public class DataLoggerListener implements ActionListener {
	ClimateChamber climateChamber;
	
	public DataLoggerListener(ClimateChamber climateChamber) {
		this.climateChamber = climateChamber;
	}

	public void actionPerformed(ActionEvent e) {
		climateChamber.setSheetData();
		PutData.insertReferenceAndLoggersData();
	}
}