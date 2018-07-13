package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EquipmentCalculate;

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
        data.value = getInteger(elements[0]);
        data.correction = getDouble(elements[1]);
        data.uncertainty = getDouble(elements[2]);
		return data;
	}
}
