package certyficate.sheetHandlers.search.order;

import java.util.HashSet;
import java.util.Set;

import certyficate.entitys.Order;
import certyficate.property.CalibrationData;

public class CodeVerification {
	private static final String UNSTANDARD_CHARACTER = "-";
	private static final String CHANNEL_CHARACTER = "x";
	
	private Set<String> VerificationCriteria;
	
	public CodeVerification() {
		VerificationCriteria = new HashSet<String>();
		switch (CalibrationData.calibrationType) {
		case HUMINIDITY:
			VerificationCriteria.add("3");
			break;
		case INFRARED:
			VerificationCriteria.add("5");
			break;		
		default:
			VerificationCriteria.add("1");
			VerificationCriteria.add("2");
			break;
		}
	}
	
	public boolean checkCalibrationCode(Order order) {
		String code = findCodeAndPoints(order);
		checkNumberOfChannel(order, code);
		return VerificationCriteria.contains(code);
	}

	private String findCodeAndPoints(Order order) {
		String code = order.calibrationCode;
		int indexOfSeparator = code.indexOf(UNSTANDARD_CHARACTER);
		if(indexOfSeparator != -1) {
			setPoints(order, code, indexOfSeparator);
		} else {
			setStandardPoint(order, code);
		}
		return code;
	}

	private void setPoints(Order order, String code, int index) {
		code = code.substring(2, index);
		CalibrationPoints.setPoint(order);
	}

	private void setStandardPoint(Order order, String code) {
		code = code.substring(2, code.length());
		order.point = CalibrationPoints.point();
	}
	
	private void checkNumberOfChannel(Order order, String code) {
		String calibrationCode = order.calibrationCode;
		int indexOfSeparator = calibrationCode.indexOf(CHANNEL_CHARACTER);
		if(indexOfSeparator != -1) {
			order.channelNumber = setChannelNumber(calibrationCode, indexOfSeparator);
		} else {
			order.channelNumber = setStandardChannelNumber(code);
		}
	}

	private int setChannelNumber(String code, int index) {
		code = code.substring(index, code.length());
		return Integer.parseInt(code);
	}

	private int setStandardChannelNumber(String code) {
		int channelNumber = 1;
		if("2".equals(code)) {
			channelNumber = 2;
		}
		return channelNumber;
	}
}
