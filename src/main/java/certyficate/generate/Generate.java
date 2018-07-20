package certyficate.generate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import certyficate.entitys.*;
import certyficate.generate.note.Note;
import certyficate.property.CalibrationData;
import certyficate.sheetHandlers.insert.PutDate;

public class Generate {
	private static final String GENERATR_ERROR = "Generate file error";
	
    private static List<String> done = new ArrayList<String>();
    
    private static Note note;

    public static void generateDocuments() throws IOException {
    	done = new ArrayList<String>();
    	setNote();
    	findCertificateData();
    	PutDate.calibrationDate(done);
    }
    
    private static void setNote() throws IOException {
    	note = NoteFactor.setNote();
	}

	private static void findCertificateData() {
		for(Order certificate: CalibrationData.orders) {
			generateCalibrationDocuments(certificate);
		}
		CalibrationData.orders = null;
	}

	private static void generateCalibrationDocuments(Order certificate) {
		try {
			generateNoteAndCertificate(certificate);
		} catch (IOException e) {
			System.out.println(GENERATR_ERROR);
			e.printStackTrace();
		}
	}

	private static void generateNoteAndCertificate(Order certificate)
			throws IOException {
		note.setNoteAndCertificate(certificate);
		markAsDone(certificate);
	}
	
    private static void markAsDone(Order certificate) {
    	done.add(certificate.getNumberOfCalibration());
	}
}
