package certyficate.equipment.type;

import java.io.BufferedReader;
import java.io.IOException;

import certyficate.equipment.calculation.Calculate;
import certyficate.equipment.calculation.DataProbe;
import certyficate.files.ReaderCreator;

public abstract class Equipment {
	protected static final String SEPARATOR = "\t";
	
	protected Calculate calculate;
	
    protected int numberOfStandardPoint;
    protected int numberOfRanges;
    
    protected int[][] ranges;
    
    public Equipment(String file) throws IOException {
    	BufferedReader reader = ReaderCreator.getReader(file);
    	getConstantData(reader.readLine());
    	getCalibrationData(reader);
    	getNuberOfRanges(reader.readLine());
    	getRangesData(reader);
    	reader.close();
    }
    
    public abstract DataProbe getPointData(double[] point);

	protected abstract void getConstantData(String line);

	protected abstract void getCalibrationPoint(String line, int index);
	
	protected int getInteger(String element) {
		return Integer.parseInt(element);
	}
	
	protected abstract void setRanges();

	protected abstract void getRange(String readLine, int index);
	
	private void getCalibrationData(BufferedReader reader) throws IOException {
		for(int i = 0; i < numberOfStandardPoint; i++) {
			getCalibrationPoint(reader.readLine(), i);
		}
	}
	
	private void getNuberOfRanges(String line) {
		numberOfRanges = getInteger(line);	
	}
	
	private void getRangesData(BufferedReader reader) throws IOException {
		setRanges();
		for(int i = 0; i < numberOfRanges; i++) {
			getRange(reader.readLine(), i);
		}
	}
}
