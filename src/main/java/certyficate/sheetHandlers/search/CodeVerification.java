package certyficate.sheetHandlers.search;

import java.util.HashSet;

import certyficate.entitys.Order;
import certyficate.property.CalibrationData;

public class CodeVerification {
	private HashSet<String> VerificationCriteria;
	
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
		return VerificationCriteria.contains(code);
	}
	
	private String findCodeAndPoints(Order order) {
		String code = order.calibrationCode;
		int indexOfSeparator = code.indexOf("-");
		if(indexOfSeparator != -1) {
			code = code.substring(2, indexOfSeparator);
			CalibrationPoints.setPoint(order);
		} else {
			code = code.substring(2, code.length());
			order.point = CalibrationPoints.point();
		}
		return code;
	}
}
