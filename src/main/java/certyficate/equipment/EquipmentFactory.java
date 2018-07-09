package certyficate.equipment;

import java.io.IOException;

import certyficate.equipment.type.Equipment;
import certyficate.equipment.type.RhProbe;
import certyficate.equipment.type.TProbe;
import certyficate.files.PathCreator;

public class EquipmentFactory {
	private static Equipment equipment;
	
	public static Equipment getEquipment(EquipmentType equipmentType)
			throws IOException {
		findAndGetEquipment (equipmentType);
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
		case CHAMBER_TEMPERATURE:
			setChamberTemperature();
			break;
		case CHAMBER_HUMIDITY:
			setChamberHumidity();
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
	
	private static void setChamberTemperature() throws IOException {
		String path = PathCreator.filePath("");
		//TODO chamber method
	}
	
	private static void setChamberHumidity() throws IOException {
		String path = PathCreator.filePath("");
		//TODO chamber method
	}

	private static void setEnvironment() throws IOException {
		String path = PathCreator.filePath("w-srod.txt");
		equipment = new RhProbe(path);
	}

}
