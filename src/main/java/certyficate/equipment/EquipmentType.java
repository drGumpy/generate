package certyficate.equipment;

import certyficate.property.CalibrationData;
import certyficate.property.CalibrationType;

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
		if(CalibrationData.calibrationType == CalibrationType.HUMINIDITY) {
			equipment = HUMIDITY_REFERENCE;
		} else {
			equipment = TEMPERATURE_REFERENCE;
		}
		return equipment;
	}

	public static EquipmentType setChamber() {
		EquipmentType equipment;
		if(CalibrationData.calibrationType == CalibrationType.HUMINIDITY) {
			equipment = CHAMBER_HUMIDITY;
		} else {
			equipment = CHAMBER_TEMPERATURE;
		}
		return equipment;
	}
}
