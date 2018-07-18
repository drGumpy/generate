package certyficate.datalogger.type;

import certyficate.datalogger.Logger;
import certyficate.datalogger.PointData;

public class Onset extends Logger{
	protected static final String LINE_DATA_SEPARATOR = ";";
	protected static final String DATE_FORMAT = "MM.dd.yyyy HH:mm:ss";
	protected static final String NUMBER_SEPARATOR = ",";
	
    public Onset(boolean Rh) {
        super(Rh);
        nonDataLine = 3;
    }
    
    @Override
    protected PointData divide(String line){
    	PointData point= new PointData();
        String[] data = line.split(LINE_DATA_SEPARATOR);
        point.setDate(data[1], DATE_FORMAT);
        point.setTemperature(data[2], NUMBER_SEPARATOR);
        setHumidity(point, data);
        return point;    
    }
    
    private void setHumidity(PointData point, String[] data) {
		if(Rh){
			point.setHumidity(data[3], NUMBER_SEPARATOR);
		} 
	}
}
