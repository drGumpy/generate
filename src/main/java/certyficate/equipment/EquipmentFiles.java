package certyficate.equipment;

public class EquipmentFiles {
	private static final String TEMPERATURE_FILE = "13.026.txt";
	private static final String HUMIDITY_FILE = "61602551.txt";
	private static final String INFRARED_FILE = "12030011.txt";
	private static final String ENVIRONMENT_FILE = "w-srod.txt";
	private static final String TEMPERATURE_CHAMBER_FILE = "12-03914 t.txt";
	private static final String HUMIDITY_CHAMBER_FILE = "12-03914 Rh.txt";

	static String getTemperatureFileName() {
		return TEMPERATURE_FILE;
	}
	
	static String getHumidityFileName() {
		return HUMIDITY_FILE;
	}
	
	static String getInfraredFileName() {
		return INFRARED_FILE;
	}
	
	static String getEnvironmentFileName() {
		return ENVIRONMENT_FILE;
	}
	
	static String getChamberTemperatureFileName() {
		return TEMPERATURE_CHAMBER_FILE;
	}
	
	static String getChamberHumidityFileName() {
		return HUMIDITY_CHAMBER_FILE;
	}
}
