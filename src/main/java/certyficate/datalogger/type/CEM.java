package certyficate.datalogger.type;

import certyficate.datalogger.Logger;
import certyficate.datalogger.PointData;

public class CEM extends Logger {
	private static final String LINE_DATA_SEPARATOR = "\t";
	private static final String DATE_FORMAT = "dd-MM-yy/HH:mm:ss";
	private final static String NON_NUMBER = "[^\\d\\.]";
	private static final String NUMBER_SEPARATOR = "\\.";
	private static final String EMPTY_SPACE = "";

    public CEM(boolean Rh) {
        super(Rh);
        nonDataLine = 11;
    }
 
    protected PointData divide(String line){
    	PointData point= new PointData();
        String[] data = line.split(LINE_DATA_SEPARATOR);
        String temperature = extractNumericData(data[1]);
        point.setDate(data[5], DATE_FORMAT);
        point.setTemperature(temperature, NUMBER_SEPARATOR);
        setHumidity(point, data); 
        return point;    
    }

	private void setHumidity(PointData point, String[] data) {
		if(Rh){
			String humidity = extractNumericData(data[3]);
			point.setHumidity(humidity, NUMBER_SEPARATOR);
		} 
	}

	private String extractNumericData(String string) {
		return string.replaceAll(NON_NUMBER, EMPTY_SPACE);
	}
}
