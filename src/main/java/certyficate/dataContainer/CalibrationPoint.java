package certyficate.dataContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import certyficate.property.CalibrationData;

//przechowywanie danych o wzorcowanych puntkach
public class CalibrationPoint{
	private static final String SHEET_DATE_FORMAT = "EEE MMM dd hh:mm:ss z yyyy";
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private static final String SHEET_TIME_FORMAT = "'PT'HH'H'mm'M's'S'";
	private static final String TIME_FORMAT = "HH:mm";
	
	public double[] point;
	
	public int number;
	
	public String date = "";
	public String time = "";
	
	private Date pointDate;
	
	public CalibrationPoint() {
		point = new double[CalibrationData.numberOfParameters];
	}
	
	public void set(int number){
		this.number= number;
	}

	public void setDate(String dateString) {
		pointDate = getDate(dateString);
		date = new SimpleDateFormat(DATE_FORMAT).format(pointDate);
	}
	

	private Date getDate(String dateString) {
		Date date;
    	try {
			date = new SimpleDateFormat(SHEET_DATE_FORMAT, Locale.US).parse(dateString);
		} catch (ParseException e) {
			date = defaultDateFormat(dateString);
		}
		return date;
	}

	private Date defaultDateFormat(String dateString) {
		Date date;
		try {
			date = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(dateString);
		} catch (ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}

	public void setTime(String timeString) {
		Date dataTime = getTime(timeString);
		setPointTime(dataTime);	
	}

	private Date getTime(String timeString) {
		Date date;
		try {
			date = new SimpleDateFormat(SHEET_TIME_FORMAT).parse(timeString);
		} catch (ParseException e) {
			date = defaultTimeFormat(timeString);
		}
		return date;
	}
	
	private Date defaultTimeFormat(String timeString) {
		Date date;
		try {
			date = new SimpleDateFormat(TIME_FORMAT, Locale.US).parse(timeString);
		} catch (ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}
	
	@SuppressWarnings("deprecation")
	private void setPointTime(Date dataTime) {
		time = new SimpleDateFormat(TIME_FORMAT).format(dataTime);
		pointDate.setHours(dataTime.getHours());
		pointDate.setMinutes(dataTime.getMinutes());
	}
    

	public static Comparator<CalibrationPoint> comparator = new Comparator<CalibrationPoint>() {
		public int compare(CalibrationPoint point1, CalibrationPoint point2) {
			return point1.pointDate.compareTo(point2.pointDate);
		}
	};
	
}