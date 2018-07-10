package certyficate.generate;

import java.util.List;

import certyficate.dataContainer.CertificateValue;
import certyficate.entitys.Certificate;

public class PyrometerCertificate {
	static Certificate certificate;
	static List<CertificateValue> pointData;
	
	public static void setCertificate(Certificate certificateData,
			List<CertificateValue> calibration) {
		certificate = certificateData;
		pointData = calibration;
		createCertificate();
	}

	private static void createCertificate() {
		
	}

}
