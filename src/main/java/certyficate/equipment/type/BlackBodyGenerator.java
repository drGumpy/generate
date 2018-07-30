package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EquipmentCalculate;
import certyficate.property.DataCalculation;

public class BlackBodyGenerator extends TProbe {
	private static final int DATA_INDEX = 0;
	
	public BlackBodyGenerator(String path) throws IOException {
		super(path);
		calculate = new EquipmentCalculate();
	}
	
	public double getUncertainty(double[] point) {
		DataProbe data = getPointData(point);
		return data.getUncertainty(DATA_INDEX);
	}

	@Override
	protected void setDrifts(String[] elements) {
		return;
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.setValue(getInteger(elements[0]), DATA_INDEX);
        data.setUncertainty(DataCalculation.getDouble(elements[2]), DATA_INDEX);
		return data;
	}
}
