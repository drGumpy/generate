package certyficate.sheetHandlers.search;

import java.util.HashSet;

import certyficate.entitys.Certificate;
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
	
	public boolean checkCalibrationCode(Certificate order) {
		String code = findCodeAndPoints(order);
		return VerificationCriteria.contains(code);
	}
	
	private String findCodeAndPoints(Certificate order) {
		String code = order.calibrationCode;
		int indexOfSeparator = code.indexOf("-");
		if(indexOfSeparator != -1) {
			code = code.substring(2, indexOfSeparator);
		} else {
			code = code.substring(2, code.length());
			order.point = StandardPoint.point();
		}
		return code;
	}
	
	 private static boolean _findCode(Certificate cal){
			String calibrationCode= cal.calibrationCode.toUpperCase();
			String points="";
			try{
				points = calibrationCode.substring
						(calibrationCode.indexOf("(")+1,calibrationCode.indexOf(")"));
				calibrationCode = calibrationCode.replace("-N("+points+")", "");
			}catch(StringIndexOutOfBoundsException e){}
			calibrationCode=calibrationCode.replaceAll("SW", "");
			String[] data = calibrationCode.split("X");
			int s;
			try{
				s=Integer.parseInt(data[0]);
				if(s!=calibration && s!=2) return true;
				if(s==2) {
					if(calibration!=1) return true;
					cal.channelNumber=2;
				} else if(data.length==2) {
					int channel = _check(data[1]);
					cal.channelNumber=channel;
					
				}else{
					cal.channelNumber=1;
				}
				cal.point=StandardPoint.point(points,calibration);
			}catch(NumberFormatException e){
				System.out.println("błąd kodu wzorcowania");
				return true;
			}
	    	return false;
	    }
}
