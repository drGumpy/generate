package certyficate.generate;

import java.io.IOException;

import certyficate.generate.note.HuminidityNote;
import certyficate.generate.note.Note;
import certyficate.generate.note.PyrometerNote;
import certyficate.generate.note.TemperatureNote;
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
