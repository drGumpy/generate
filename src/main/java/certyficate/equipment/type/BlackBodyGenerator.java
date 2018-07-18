package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EquipmentCalculate;
import certyficate.property.DataCalculation;

public class BlackBodyGenerator extends TProbe {
	public BlackBodyGenerator(String path) throws IOException {
		super(path);
		calculate = new EquipmentCalculate();
	}
	
	public double getUncertainty(double[] point) {
		DataProbe data = getPointData(point);
		return data.uncertainty;
	}

	@Override
	protected void setDrifts(String[] elements) {
		return;
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.value = getInteger(elements[0]);
        data.uncertainty = DataCalculation.getDouble(elements[2]);
		return data;
	}
}
