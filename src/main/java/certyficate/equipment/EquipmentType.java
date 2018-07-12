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
	
	public static EquipmentType setEquipmentType() {
		EquipmentType equipment;
		if(CalibrationData.calibrationType == CalibrationType.HUMINIDITY) {
			equipment = HUMIDITY_REFERENCE;
		} else {
			equipment = TEMPERATURE_REFERENCE;
		}
		return equipment;
	}
}
