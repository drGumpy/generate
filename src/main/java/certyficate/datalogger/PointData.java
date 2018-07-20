package certyficate.datalogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import certyficate.sheetHandlers.CalibrationPoint;

public class PointData {
	private static final int dataLength = 2;
	
	private static final String ZERO = "0";
	private static final String TIME_FORMAT = "HH:mm";
	
	private String[] temperature;
	private String[] humidity;
	
	private Date date;
	
	public String[] getTemperature() {
		return temperature;
	}
	
	public String[] getHumidity() {
		return humidity;
	}
	
	public String getTime() {
		return new SimpleDateFormat(TIME_FORMAT).format(date);
	}
	
	@SuppressWarnings("deprecation")
	public void setDate(String dateData, String dateFormat) {
		
		try {
			date = new SimpleDateFormat(dateFormat, Locale.US).parse(dateData);
			date.setSeconds(0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setTemperature(String temperatureData,
			String temperatureSplit) {
		temperature = setArray(temperatureData, temperatureSplit);
	}
	
	public void setHumidity(String humidityData,
			String humiditySplit) {
		humidity = setArray(humidityData, humiditySplit);
	}
	
	private String[] setArray(String Data, String split) {
		String[] array = Data.split(split);
		if(array.length != dataLength) {
			array = addZero(array);
		}
		return array;
	}

	private String[] addZero(String[] array) {
		array = Arrays.copyOf(array, dataLength);
		array[1] = ZERO;
		return array;
	}

	public boolean equalsData(CalibrationPoint point) {
		return point.getPointDate().compareTo(date) == 0;
	}
}
