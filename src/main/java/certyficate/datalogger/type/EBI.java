package certyficate.datalogger.type;

import certyficate.datalogger.Logger;
import certyficate.datalogger.PointData;

public class EBI extends Logger {
	protected static final String LINE_DATA_SEPARATOR = ",";
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected static final String NUMBER_SEPARATOR = "\\.";
	
    public EBI(boolean Rh) {
        super(Rh);
        nonDataLine = 10;
    }
    
    @Override
    protected PointData divide(String line){
    	PointData point= new PointData();
        String[] data = line.split(LINE_DATA_SEPARATOR);
        point.setDate(data[0], DATE_FORMAT);
        setData(point, data); 
        return point;    
    }

	private void setData(PointData point, String[] data) {
		if(Rh) {
			setTemperatureAndHuminidity(point, data);
		} else {
			setTemperature(point, data);
		}
	}

	private void setTemperatureAndHuminidity(PointData point, String[] data) {
		point.setHumidity(data[1], NUMBER_SEPARATOR);
		point.setTemperature(data[2], NUMBER_SEPARATOR);
	}

	private void setTemperature(PointData point, String[] data) {
		point.setTemperature(data[1], NUMBER_SEPARATOR);
	}
}
