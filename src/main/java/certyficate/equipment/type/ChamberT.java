package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EquipmentCalculate;
import certyficate.property.DataCalculation;

public class ChamberT extends TProbe{
	public ChamberT(String path) throws IOException {
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
        data.setValue(getInteger(elements[0]), 0);
        data.setCorrection(DataCalculation.getDouble(elements[1]), 0);
        data.setUncertainty(DataCalculation.getDouble(elements[2]), 0);
		return data;
	}
}
