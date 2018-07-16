package certyficate.equipment.type;

import java.io.BufferedReader;
import java.io.IOException;

import certyficate.equipment.calculation.Calculate;
import certyficate.equipment.calculation.DataProbe;
import certyficate.files.ReaderCreator;

public abstract class Equipment {
	protected static final String SEPARATOR = "\t";
	private static final String COMMA = ",";
	private static final String DOT = ".";
	
	public Calculate calculate;
	
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

	protected abstract void getConstantData(String line);
	
	private void getCalibrationData(BufferedReader reader) throws IOException {
		for(int i = 0; i < numberOfStandardPoint; i++) {
			getCalibrationPoint(reader.readLine(), i);
		}
	}

	protected abstract void getCalibrationPoint(String line, int index);
	
	private void getNuberOfRanges(String line) {
		numberOfRanges = getInteger(line);	
	}
	
	protected int getInteger(String element) {
		return Integer.parseInt(element);
	}
	
	protected double getDouble(String element) {
		element = element.replaceAll(COMMA, DOT);
		return Double.parseDouble(element);
	}
	
	private void getRangesData(BufferedReader reader) throws IOException {
		for(int i = 0; i < numberOfRanges; i++) {
			getRange(reader.readLine(), i);
		}
	}

	protected abstract void getRange(String readLine, int index);
	
	public abstract DataProbe getPointData(double[] point);
}
