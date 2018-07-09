package certyficate.generate;

import certyficate.property.CalibrationData;

public class CertificateText {
	private static final String[] environmentParametrs = 
			new String[] {"°C", "%Rh"};
	
	private static double[] environmentData;
	public static String[] getEnviromentData() {
		String[] environment = new String[2];
		environmentData = CalibrationData.eniromentData;
		for(int i = 0; i < 2; i++) {
			environment[i] = setRanges(i);
		}
		return environment;
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

}
