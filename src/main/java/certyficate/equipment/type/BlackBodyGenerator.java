package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EquipmentCalculate;

public class BlackBodyGenerator extends TProbe {
	public BlackBodyGenerator(String path) throws IOException {
		super(path);
		calculate = new EquipmentCalculate();
	}
	
	public double getUncertainty(int[] point) {
		DataProbe data = getPointData(point);
		return data.uncertainty;
	}

	@Override
	protected void setDrifts(String[] elements) {
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.value = getInteger(elements[0]);
        data.uncertainty = getDouble(elements[1]);
		return data;
	}
}
