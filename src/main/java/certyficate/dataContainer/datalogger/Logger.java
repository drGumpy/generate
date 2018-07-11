package certyficate.dataContainer.datalogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import certyficate.dataContainer.PointData;
import certyficate.files.ReaderCreator;

public class Logger{
	public File file;
	
	protected boolean Rh;
	
	public int deviceNumber;
	
	public String date = "";
	public String time = "";
	public String temp = "";
	public String hum = "";
	
	public Logger(boolean RH){
		this.Rh = RH;
	}
	
	public void FindPoints() throws IOException {
    	BufferedReader reader = ReaderCreator.getReader(file);
    	removeNonDataLine(reader);
    	reader.close();
    }

	private void removeNonDataLine(BufferedReader reader) {
		// TODO Auto-generated method stub
		
	}

	public PointData divide(String nextLine) {
		return new PointData();
	}

	public int getN() {
		return 0;
	}
	
	public void setFile(File file) {
		this.file= file;		
	}

	public void setDeviceNumber(int index) {
		deviceNumber = index;
	}
}
