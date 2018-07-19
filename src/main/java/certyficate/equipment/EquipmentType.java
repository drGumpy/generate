package certyficate.equipment;

import certyficate.property.CalibrationData;

public enum EquipmentType {
	TEMPERATURE_REFERENCE,
	HUMIDITY_REFERENCE,
	INFRARED_REFERENCE,
	CHAMBER_TEMPERATURE,
	CHAMBER_HUMIDITY,
	BLACK_BODY_GENERATOR,
	ENVIRONMENT;
	
	public static EquipmentType setReferenceType() {
		EquipmentType equipment;
		switch(CalibrationData.calibrationType) {
		case HUMINIDITY:
			equipment = HUMIDITY_REFERENCE;
			break;
		default:
			equipment = TEMPERATURE_REFERENCE;
			break;
		}
		return equipment;
	}

	public static EquipmentType setChamber() {
		EquipmentType equipment;
		switch(CalibrationData.calibrationType) {
		case HUMINIDITY:
			equipment = CHAMBER_HUMIDITY;
			break;
		default:
			equipment = CHAMBER_TEMPERATURE;
			break;
		}
		return equipment;
	}
}
