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
		findAndGetEquipment (equipmentType);
		return equipment;
	}
	
	public static Equipment getChamberData() throws IOException {
		Equipment equipment = GetChamberData ();
		return equipment;
	}

	private static void findAndGetEquipment(EquipmentType equipmentType) 
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
		String path = PathCreator.filePath("13.026.txt");
		equipment = new TProbe(path);
	}
	
	private static void setHumidityReference() throws IOException {
		String path = PathCreator.filePath("61602551.txt");
		equipment = new RhProbe(path);
	}
	
	private static void setInfraredReference() throws IOException {
		String path = PathCreator.filePath("12030011.txt");
		equipment = new TProbe(path);
	}
	
	private static void setEnvironment() throws IOException {
		String path = PathCreator.filePath("w-srod.txt");
		equipment = new RhProbe(path);
	}
	
	private static Equipment GetChamberData() throws IOException {
		Equipment equipment;
		switch(CalibrationData.calibrationType) {
		case HUMINIDITY:
			equipment = setChamberHumidity();
			break;
		default:
			equipment = setChamberTemperature();
			break;
		}
		return equipment;
	}
	
	private static Equipment setChamberTemperature() throws IOException {
		String path = PathCreator.filePath("12-03914 t.txt");
		return new ChamberT(path);
	}
	
	private static Equipment setChamberHumidity() throws IOException {
		String path = PathCreator.filePath("12-03914 Rh.txt");
		return new ChamberRh(path);
	}
}
