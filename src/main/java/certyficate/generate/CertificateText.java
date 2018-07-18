package certyficate.generate;

import certyficate.entitys.Order;
import certyficate.GUI.infrared.IRData;
import certyficate.entitys.Client;
import certyficate.property.CalibrationData;

public class CertificateText {
	private static final String[] environmentParametrs = {"°C", "%Rh"};
	private static final String[] environmentText = {"Temperatura: ",  "Wilgotność: "};
	
	private static final char OPEN_BRACKET = '(';
	private static final char CLOSE_BRACKET = ')';
	private static final char DOT = '.';
	private static final char FILE_NAME_SEPARTOR = '_';
	
	private static final String EMPTY_STRING = "";
	private static final String COMPARTMENT = "  ÷  ";
	private static final String MODEL = ", model: ";
	private static final String MANOFACTURER = ", producent: ";
	private static final String SERIAL_NUMBER = ", nr seryjny: ";
	private static final String COMMA = ", ";
	private static final String INFRARED_EMISSIVITY_TEXT = "Emisyjność źródła ε=";
	private static final String INFRARED_DISTANCE_TEXT = 
			"Odległość pirometru wzorcowanego od źródła w czasie wzorcowania wynosiła: ";
	private static final String MEASURE = " mm";
	private static final String FILE_EXTENCTION = ".ods";
	private static final String TEMPERATURE_PROBE = "Czujnik temperatury: ";
	private static final String CHANNEL_NAME = " (nazwa kanału: ";
	private static final String WITH  = " ,z";
	
	private static double[] environmentData;
	
	public static String[] getEnviromentData() {
		String[] environment = new String[2];
		environmentData = CalibrationData.eniromentData;
		for(int i = 0; i < 2; i++) {
			environment[i] = setRanges(i);
		}
		return environment;
	}
	
	public static String[] getEnvironmentText() {
		String[] environment = new String[2];
		environmentData = CalibrationData.eniromentData;
		for(int i = 0; i < 2; i++) {
			environment[i] = setText(i);
		}
		return environment;
	}

	public static String setDescription(Order certificate) {
		StringBuilder builder = new StringBuilder(certificate.device.type);
		builder.append(MODEL); 
		builder.append(certificate.device.model);
		builder.append(MANOFACTURER);
		builder.append(certificate.device.producent);
		builder.append(SERIAL_NUMBER);
		builder.append(certificate.deviceSerialNumber);
		if(!EMPTY_STRING.equals(certificate.probeSerialNumber)) {
			builder.append(setProbe(certificate));
		}
		builder.append(DOT);
		return builder.toString();
	}
	
	public static String getAdres(Client client) {
		StringBuilder builder = new StringBuilder(client.address);
		builder.append(COMMA);
		builder.append(client.postalCode);
		builder.append(COMMA);
		builder.append(client.town);
		return builder.toString();
	}

	public static String getEmissivity(IRData pyrometr) {
		StringBuilder builder = new StringBuilder(INFRARED_EMISSIVITY_TEXT);
		builder.append(pyrometr.emissivity);
		builder.append(DOT);
		return builder.toString();
	}

	public static String getDistance(IRData pyrometr) {
		StringBuilder builder = new StringBuilder(INFRARED_DISTANCE_TEXT);
		builder.append(pyrometr.distance);
		builder.append(MEASURE);
		return builder.toString();
	}

	public static String setCertificateFileName(Order certificate) {
		StringBuilder bulid = new StringBuilder(certificate.numberOfCalibration);
		bulid.append(FILE_NAME_SEPARTOR);
		bulid.append(certificate.declarant.name);
		bulid.append(FILE_EXTENCTION);
		return bulid.toString();
	}
	
	public static String getChannelText(Order certificate, int channelNumber) {
		String channel = EMPTY_STRING;
		String probeSerial = getProbeSerial(certificate, channelNumber);
		String channelName = certificate.device.channel[channelNumber];
		if(checkChannel(certificate.device.channel[channelNumber])) {
			channel = getChannelText(probeSerial, channelName);
		}
		return channel;
	}
	
	private static String setText(int index) {
		StringBuilder builder = new StringBuilder(environmentText[index]);
		builder.append(setRanges(index));
		return builder.toString();
	}

	private static String setRanges(int index) {
		StringBuilder builder = new StringBuilder();
		builder.append(OPEN_BRACKET);
		builder.append(environmentData[2 * index]);
		builder.append(COMPARTMENT);
		builder.append(environmentData[2 * index +1]);
		builder.append(CLOSE_BRACKET);
		builder.append(environmentParametrs[index]);
		return builder.toString();
	}
	
	private static StringBuilder setProbe(Order certificate) {
		StringBuilder builder;
		if(certificate.probe.type.equals(EMPTY_STRING)) {
			builder = probeWithoutModel(certificate);
		} else {
			builder = probeWithModel(certificate);
		}
		return builder;
	}

	private static StringBuilder probeWithoutModel(Order certificate) {
		StringBuilder builder = new StringBuilder(WITH);
		builder.append(certificate.probe.model);
		builder.append(MANOFACTURER);
		builder.append(certificate.probeSerialNumber);
		return builder;
	}

	private static StringBuilder probeWithModel(Order certificate) {
		StringBuilder builder = new StringBuilder(WITH);
		builder.append(certificate.probe.type);
		builder.append(MODEL);
		builder.append(certificate.probe.model);
		builder.append(MANOFACTURER);
		builder.append(certificate.probe.producent);
		builder.append(SERIAL_NUMBER);
		builder.append(certificate.probeSerialNumber);
		return builder;
	}
	
	private static String getProbeSerial(Order certificate, int channelNumber) {
		String probeSerial = EMPTY_STRING;
		if(certificate.probeSerial.length > channelNumber) {
			probeSerial = certificate.probeSerial[channelNumber];
		}
		return probeSerial;
	}

	private static boolean checkChannel(String channel) {
		return !EMPTY_STRING.equals(channel);
	}
	
	private static String getChannelText(String probeSerial, String channel) {
		StringBuilder build = new StringBuilder(TEMPERATURE_PROBE);
		build.append(probeSerial);
		build.append(CHANNEL_NAME);
		build.append(channel);
		build.append(CLOSE_BRACKET);
		return build.toString();
	}
}
