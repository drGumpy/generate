package certyficate.equipment;

import java.io.File;
import java.util.ArrayList;

import certyficate.dataContainer.CalibrationPoint;
import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.type.Equipment;
import certyficate.equipment.type.TProbe;
import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class EquipmentParameters {
	static DataProbe[] dataProbe;
	static ArrayList<CalibrationPoint> point;
	
	static String equpipmentName;
	
	public static void find(EquipmentType equipment) {
		point = CalibrationData.point;
		String filePath = PathCreator.filePath(CalibrationData.referenceSerial);
		dataProbe = new DataProbe[point.size()];
		Equipment probe = new TProbe(new File(filePath));
	    for(int i=0; i<point.size(); i++){
			int t=Integer.parseInt(CalibrationData.point.get(i).temp);
	        dataProbe[i]=probe.getPointData(t, 0);
	    }
	}
	
}
