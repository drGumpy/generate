package certyficate.generate;

import certyficate.dataContainer.IRData;
import certyficate.entitys.Certificate;
import certyficate.entitys.Client;
import certyficate.property.CalibrationData;

public class CertificateText {
	private static final String[] environmentParametrs = {"°C", "%Rh"};
	private static final String[] environmentText = {"Temperatura: ",  "Wilgotność: "};
	
	private static double[] environmentData;
	
	public static String[] getEnviromentData() {
		String[] environment = new String[2];
		environmentData = CalibrationData.eniromentData;
		for(int i = 0; i < 2; i++) {
			environment[i] = setRanges(i);
		}
		return environment;
	}
	
	public static String[] getEnvironmentText() {
		String[] environment = new String[2];
		environmentData = CalibrationData.eniromentData;
		for(int i = 0; i < 2; i++) {
			environment[i] = setText(i);
		}
		return environment;
	}

	private static String setText(int index) {
		StringBuilder builder = new StringBuilder(environmentText[index]);
		builder.append(setRanges(index));
		return builder.toString();
	}

	private static String setRanges(int index) {
		StringBuilder builder = new StringBuilder("(");
		builder.append(environmentData[2 * index]);
		builder.append("  ÷  ");
		builder.append(environmentData[2 * index +1]);
		builder.append(")");
		builder.append(environmentParametrs[index]);
		return builder.toString();
	}

	public static String setDescription(Certificate certificate) {
		StringBuilder builder = new StringBuilder(certificate.device.type);
		builder.append(", model:"); 
		builder.append(certificate.device.model);
		builder.append(", producent: ");
		builder.append(certificate.device.producent);
		builder.append(", nr seryjny: ");
		builder.append(certificate.deviceSerialNumber);
		if(!"".equals(certificate.probeSerialNumber)) {
			builder.append(setProbe(certificate));
		}
		builder.append(".");
		return builder.toString();
	}

	private static StringBuilder setProbe(Certificate certificate) {
		StringBuilder builder;
		if(certificate.probe.type.equals("")) {
			builder = probeWithoutModel(certificate);
		} else {
			builder = probeWithModel(certificate);
		}
		return builder;
	}

	private static StringBuilder probeWithoutModel(Certificate certificate) {
		StringBuilder builder = new StringBuilder(", z");
		builder.append(certificate.probe.model);
		builder.append(", nr seryjny: ");
		builder.append(certificate.probeSerialNumber);
		return builder;
	}

	private static StringBuilder probeWithModel(Certificate certificate) {
		StringBuilder builder = new StringBuilder(", z");
		builder.append(certificate.probe.type);
		builder.append(" model ");
		builder.append(certificate.probe.model);
		builder.append(", producent: ");
		builder.append(certificate.probe.producent);
		builder.append(", nr seryjny: ");
		builder.append(certificate.probeSerialNumber);
		return builder;
	}

	public static String getAdres(Client client) {
		StringBuilder builder = new StringBuilder(client.address);
		builder.append(", ");
		builder.append(client.postalCode);
		builder.append(", ");
		builder.append(client.town);
		return builder.toString();
	}

	public static String getEmissivity(IRData pyrometr) {
		StringBuilder builder = new StringBuilder("Emisyjność źródła ε=");
		builder.append(pyrometr.emissivity);
		builder.append(".");
		return builder.toString();
	}

	public static String getDistance(IRData pyrometr) {
		StringBuilder builder = new StringBuilder(
				"Odległość pirometru wzorcowanego od źródła w czasie wzorcowania wynosiła: ");
		builder.append(pyrometr.distance);
		builder.append(" mm");
		return builder.toString();
	}
}
