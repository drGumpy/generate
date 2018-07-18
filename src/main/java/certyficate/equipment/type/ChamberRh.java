package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EquipmentCalculate;
import certyficate.property.DataCalculation;

public class ChamberRh extends RhProbe{

	public ChamberRh(String path) throws IOException {
		super(path);
		calculate = new EquipmentCalculate();
	}
	
	@Override
	protected void setDrifts(String[] elements) {
		return;
	}
	
	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.value = getInteger(elements[0]);
        data.valueRh = getInteger(elements[1]);
        data.correction = DataCalculation.getDouble(elements[2]);
        data.correctionRh = DataCalculation.getDouble(elements[3]);
        data.uncertainty = DataCalculation.getDouble(elements[4]);
        data.uncertaintyRh = DataCalculation.getDouble(elements[5]);
		return data;
	}

}
