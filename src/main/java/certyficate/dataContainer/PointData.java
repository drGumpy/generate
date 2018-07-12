package certyficate.dataContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class PointData {
	private static final int dataLength = 2;
	
	private String[] temperature;
	private String[] humidity;
	
	private Date date;
	
	public String[] getTemperature() {
		return temperature;
	}
	
	public String[] getHumidity() {
		return humidity;
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
		array[1] = "0";
		return array;
	}

	public boolean equalsData(CalibrationPoint point) {
		boolean answer = point.getPointDate().compareTo(date) == 0;
		return answer;
	}
}
