package certyficate.generate;

import certyficate.entitys.Order;
import certyficate.generate.certificate.Certificate;
import certyficate.property.CalibrationData;

public class TemplateData {
	private static final String HUMINIDITY_TEMPLATE = "sw_Rh.ods"; 
	private static final String INFRARED_TEMPLATE = "sw_IR.ods"; 
	
	private static Certificate certificate;
	
	private static Order order;

	public static void setData(Certificate certificateData, Order orderData) {
		certificate = certificateData;
		order = orderData;
		findData();
	}

	private static void findData() {
		switch(CalibrationData.calibrationType) {
		case TEMPERATURE:
			setTemperatureData();
			break;
		case HUMINIDITY:
			setHuminidityData();
			break;
		default:
			setInfraredData();
			break;
		}
	}

	private static void setTemperatureData() {
		Special special = new Special(order);
		certificate.setNumberOfChanel(special.getChannelNumber());
		certificate.setTemplate(special.getTemplate());
		certificate.setComments(special.getComments());
	}

	private static void setHuminidityData() {
		certificate.setTemplate(HUMINIDITY_TEMPLATE);
	}

	private static void setInfraredData() {
		certificate.setTemplate(INFRARED_TEMPLATE);
		setInfraredComments();
	}

	private static void setInfraredComments() {
		String[] comments = new String[2];
		comments[0] = CertificateText.getDistance(order.getPyrometrData());
		comments[1] = CertificateText.getEmissivity(order.getPyrometrData());
		certificate.setComments(comments);
	}

}
