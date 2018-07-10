package certyficate.generate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import certyficate.entitys.*;
import certyficate.property.CalibrationData;
import certyficate.sheetHandlers.insert.PutDate;


public class IRGenerate {
    private static List<String> done = new ArrayList<String>();

    public static void generateDocuments() {
    	done = new ArrayList<String>();
    	findCertificateData();
    	CalibrationData.done = done;
    }
    
    private static void findCertificateData() {
		for(Certificate certificate: CalibrationData.orders) {
			generateCalibrationDocuments(certificate);
		}
	}

	private static void generateCalibrationDocuments(Certificate certificate) {
		try {
			generateNoteAndCertificate(certificate);
		} catch (IOException e) {
			System.out.println("Generate file error");
			e.printStackTrace();
		}
	}

	private static void generateNoteAndCertificate(Certificate certificate)
			throws IOException {
		PyrometerNote.setNoteAndCertificate(certificate);
		addDone(certificate);
	}
	
    private static void addDone(Certificate certificate) {
    	done.add(certificate.numberOfCalibration);
    	PutDate.calibrationDate();
	}
}
