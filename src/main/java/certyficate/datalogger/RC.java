package certyficate.datalogger;

import certyficate.dataContainer.PointData;

public class RC extends Logger{
	protected static final String LINE_DATA_SEPARATOR = "\t";
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	protected static final String NUMBER_SEPARATOR = "\\.";
	
	protected int nonDataLine = 23;
	
    public RC(boolean RH) {
        super(RH);
    }
    
    @Override
    protected PointData divide(String line){
    	PointData point= new PointData();
        String[] data = line.split(LINE_DATA_SEPARATOR);
        point.setDate(data[2], DATE_FORMAT);
        point.setTemperature(data[4], NUMBER_SEPARATOR);
        setHumidity(point, data);
        return point;    
    }
    
    private void setHumidity(PointData point, String[] data) {
		if(Rh){
			point.setHumidity(data[6], NUMBER_SEPARATOR);
		} 
	}
}

