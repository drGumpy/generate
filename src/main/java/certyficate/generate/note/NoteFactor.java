package certyficate.generate.note;

import java.io.IOException;

import certyficate.property.CalibrationData;

public class NoteFactor {
	public static Note setNote() throws IOException {
		Note note;
		switch(CalibrationData.calibrationType) {
		case TEMPERATURE :
			note = new TemperatureNote();
			break;
		case HUMINIDITY :
			note = new HuminidityNote();
			break;
		default :
			note = new PyrometerNote();
			break;
		}
		return note;
	}
}
