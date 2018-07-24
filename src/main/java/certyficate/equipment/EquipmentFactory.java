package certyficate.equipment;

import java.io.IOException;

import certyficate.equipment.type.ChamberRh;
import certyficate.equipment.type.ChamberT;
import certyficate.equipment.type.Equipment;
import certyficate.equipment.type.RhProbe;
import certyficate.equipment.type.TProbe;
import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class EquipmentFactory {	
	private static Equipment equipment;
	
	public static Equipment getEquipment(EquipmentType equipmentType)
			throws IOException {
		findEquipmentType (equipmentType);
		return equipment;
	}
	
	public static Equipment getChamber() throws IOException {
		findChamberType();
		return equipment;
	}

	private static void findEquipmentType(EquipmentType equipmentType) 
			throws IOException {
		switch(equipmentType) {
		case TEMPERATURE_REFERENCE:
			setTemperatureReference();
			break;
		case HUMIDITY_REFERENCE:
			setHumidityReference();
			break;
		case INFRARED_REFERENCE:
			setInfraredReference();
			break;
		default:
			setEnvironment();
			break;
		}
	}

	private static void setTemperatureReference() throws IOException {
		String path = PathCreator.filePath(EquipmentFiles.getTemperatureFileName());
		equipment = new TProbe(path);
	}
	
	private static void setHumidityReference() throws IOException {
		String path = PathCreator.filePath(EquipmentFiles.getHumidityFileName());
		equipment = new RhProbe(path);
	}
	
	private static void setInfraredReference() throws IOException {
		String path = PathCreator.filePath(EquipmentFiles.getInfraredFileName());
		equipment = new TProbe(path);
	}
	
	private static void setEnvironment() throws IOException {
		String path = PathCreator.filePath(EquipmentFiles.getEnvironmentFileName());
		equipment = new RhProbe(path);
	}
	
	private static void findChamberType() throws IOException {
		switch(CalibrationData.calibrationType) {
		case HUMINIDITY:
			equipment = setChamberHumidity();
			break;
		default:
			equipment = setChamberTemperature();
			break;
		}
	}
	
	private static Equipment setChamberTemperature() throws IOException {
		String path = PathCreator.filePath(
				EquipmentFiles.getChamberTemperatureFileName());
		return new ChamberT(path);
	}
	
	private static Equipment setChamberHumidity() throws IOException {
		String path = PathCreator.filePath(
				EquipmentFiles.getChamberHumidityFileName());
		return new ChamberRh(path);
	}
}
