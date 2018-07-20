package certyficate.datalogger.type;

import certyficate.datalogger.Logger;
import certyficate.datalogger.PointData;

public class CEM extends Logger {
	private static final String LINE_DATA_SEPARATOR = "\t";
	private static final String DATE_FORMAT = "dd-MM-yy/HH:mm:ss";
	private static final String WHITESPACE = "\\s+";
	private static final String NUMBER_SEPARATOR = "\\.";
	private static final String EMPTY_SPACE = "\\.";

    public CEM(boolean Rh) {
        super(Rh);
        nonDataLine = 11;
    }
 
    protected PointData divide(String line){
    	PointData point= new PointData();
        String[] data = line.split(LINE_DATA_SEPARATOR);
        String temperature = removewhiteSpace(data[1]);
        point.setDate(data[5], DATE_FORMAT);
        point.setTemperature(temperature, NUMBER_SEPARATOR);
        setHumidity(point, data); 
        return point;    
    }

	private void setHumidity(PointData point, String[] data) {
		if(Rh){
			String humidity = removewhiteSpace(data[3]);
			point.setHumidity(humidity, NUMBER_SEPARATOR);
		} 
	}

	private String removewhiteSpace(String string) {
		return string.replaceAll(WHITESPACE, EMPTY_SPACE);
	}
}
