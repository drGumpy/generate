package certyficate.generate.note;

import certyficate.property.CalibrationData;

public class NoteFactor {
	public static Note setNote() {
		Note note;
		switch(CalibrationData.calibrationType) {
		case TEMPERATURE :
			note = null;
			break;
		case HUMINIDITY :
			note = null;
			break;
		default :
			note = new PyrometerNote();
			break;
		}
		return note;
	}
}
