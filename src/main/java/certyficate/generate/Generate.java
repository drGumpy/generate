package certyficate.generate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import certyficate.entitys.*;
import certyficate.generate.note.Note;
import certyficate.generate.note.NoteFactor;
import certyficate.property.CalibrationData;
import certyficate.sheetHandlers.insert.PutDate;

public class Generate {
    private static List<String> done = new ArrayList<String>();

    public static void generateDocuments() {
    	done = new ArrayList<String>();
    	findCertificateData();
    	CalibrationData.done = done;
    	PutDate.calibrationDate();
    }
    
    private static void findCertificateData() {
		for(Order certificate: CalibrationData.orders) {
			generateCalibrationDocuments(certificate);
		}
	}

	private static void generateCalibrationDocuments(Order certificate) {
		try {
			generateNoteAndCertificate(certificate);
		} catch (IOException e) {
			System.out.println("Generate file error");
			e.printStackTrace();
		}
	}

	private static void generateNoteAndCertificate(Order certificate)
			throws IOException {
		Note note = NoteFactor.setNote();
		note.setNoteAndCertificate(certificate);
		markAsDone(certificate);
	}
	
    private static void markAsDone(Order certificate) {
    	done.add(certificate.numberOfCalibration);
    	CalibrationData.orders.remove(certificate);
	}
}
