package certyficate.calculation;

import java.io.IOException;
import certyficate.GUI.EnvironmentPanel;
import certyficate.equipment.EquipmentFactory;
import certyficate.equipment.EquipmentType;
import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.type.Equipment;
import certyficate.property.CalibrationData;

public class EnvironmentData {
    private static final int NUMBER_OF_PARAMETRS = 4;
    private static final int ROUNDING_ACCURACY = 1;
    
    private static double[] environmentData;
    
    private static DataProbe[] correctionData;
    
    private static Equipment equipment;
    
	public static void setEnvirometsData() throws IOException {
		equipment = EquipmentFactory.getEquipment(EquipmentType.ENVIRONMENT);
		findEnvironmetData();
	}

	private static void findEnvironmetData() {
		environmentData = EnvironmentPanel.getEnviromentCondition();
		correctionData = correctionData();
		setCorrections();
		CalibrationData.eniromentData = environmentData;
	}

	private static DataProbe[] correctionData() {
		DataProbe[] dataprobe = new DataProbe[NUMBER_OF_PARAMETRS];
		for(int i = 0; i < NUMBER_OF_PARAMETRS; i++) {
			dataprobe[i] = setDataProbe(i);
		}
		return dataprobe;
	}

	private static DataProbe setDataProbe(int index) {
		double[] subArray = setSubArray(index);
		return equipment.getPointData(subArray);
	}

	private static double[] setSubArray(int index) {
		return new double[] {environmentData[index], 
				environmentData[index + 2]};
	}
	
	private static void setCorrections() {
		addCorrections();
		addUnceinity();
		roundNumbers();
	}

	private static void addCorrections() {
		for(int i = 0; i < NUMBER_OF_PARAMETRS; i++) {
			environmentData[i] += correctionData[i].correction;
			environmentData[i + 2] += correctionData[i].correctionRh;
		}
	}

	private static void addUnceinity() {
		environmentData[0] -= correctionData[0].uncertainty;
		environmentData[1] += correctionData[1].uncertainty;
		environmentData[2] -= correctionData[0].uncertaintyRh;
		environmentData[3] += correctionData[1].uncertaintyRh;
	}
	
	private static void roundNumbers() {
		for(int i = 0; i < NUMBER_OF_PARAMETRS; i++) {
			environmentData[i] = DataCalculation.round_(
					environmentData[i], ROUNDING_ACCURACY);
		}	
	}
}