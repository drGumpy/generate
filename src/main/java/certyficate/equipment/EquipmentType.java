package certyficate.equipment;

import certyficate.dataContainer.CalibrationType;
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
