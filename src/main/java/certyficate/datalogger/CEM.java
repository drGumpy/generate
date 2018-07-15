package certyficate.datalogger;

public class CEM extends Logger {
	protected static final String LINE_DATA_SEPARATOR = "\t";
	protected static final String DATE_FORMAT = "dd-MM-yy/HH:mm:ss";
	protected static final String WHITESPACE = "\\s+";
	protected static final String NUMBER_SEPARATOR = "\\.";
	
	protected int nonDataLine = 11;
	
    public CEM(boolean Rh) {
        super(Rh);
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
		return string.replaceAll("\\s+","");
	}
}
