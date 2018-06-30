package certyficate.equipment;

import java.io.IOException;
import java.util.ArrayList;

import certyficate.dataContainer.CalibrationPoint;
import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.type.Equipment;
import certyficate.property.CalibrationData;

public class EquipmentParameters {
	private static Equipment equipment;
	
	private static ArrayList<CalibrationPoint> points;
	private static DataProbe[] equipmentData;
	
	public static DataProbe[] find(EquipmentType equipmentType) throws IOException {
		equipment = EquipmentFactory.getEquipment(equipmentType);
		points = CalibrationData.point;
		findData();
		return equipmentData;
	}

	private static void findData() {
		int size = points.size();
		equipmentData = new DataProbe[size];
		for(int i = 0; i < size; i++)
			setPoint(i);
	}

	private static void setPoint(int i) {
		int[] point = points.get(i).point;
		equipmentData[i] = equipment.getPointData(point);
	}
}
