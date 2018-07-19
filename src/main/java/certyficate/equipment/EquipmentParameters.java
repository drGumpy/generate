package certyficate.equipment;

import java.io.IOException;
import java.util.List;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.type.Equipment;
import certyficate.property.CalibrationData;
import certyficate.sheetHandlers.CalibrationPoint;

public class EquipmentParameters {
	private static Equipment equipment;
	
	private static List<CalibrationPoint> points;
	private static DataProbe[] equipmentData;
	
	public static DataProbe[] findProbe(EquipmentType probe) throws IOException {
		equipment = EquipmentFactory.getEquipment(probe);
		setData();
		return equipmentData;
	}

	public static DataProbe[] findChamber() throws IOException {
		equipment = EquipmentFactory.getChamber();
		setData();
		return equipmentData;
	}
	
	private static void setData() {
		points = CalibrationData.point;
		findData();
	}

	private static void findData() {
		int size = points.size();
		equipmentData = new DataProbe[size];
		for(int i = 0; i < size; i++) {
			setPoint(i);
		}
	}

	private static void setPoint(int i) {
		double[] point = points.get(i).getPointData();
		equipmentData[i] = equipment.getPointData(point);
	}
}
