package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.calculation.EnvironmentCalculate;
import certyficate.property.DataCalculation;

public class Environment extends RhProbe {

	public Environment(String path) throws IOException {
		super(path);
		calculate = new EnvironmentCalculate();
	}
	
	@Override
	public DataProbe getPointData(double[] point) {
		DataProbe pointData = findInRange(point, ranges[0]);
		return pointData;
	}
	
	@Override
	protected void setDrifts(String[] elements) {
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.setValue(getInteger(elements[0]), 0);
        data.setValue(getInteger(elements[1]), 1);
        data.setCorrection(DataCalculation.getDouble(elements[2]), 0);
        data.setUncertainty(DataCalculation.getDouble(elements[4]), 0);
        data.setCorrection(DataCalculation.getDouble(elements[3]), 1);
        data.setUncertainty(DataCalculation.getDouble(elements[5]), 1);
		return data;
	}

	@Override
	protected DataProbe findInRange(double[] point, int[] range) {
		DataProbe[] pointsInRange = findPointsInRange(range);
		DataProbe data = caluculate(pointsInRange, point);
		return data;
	}

	@Override
	protected DataProbe caluculate(DataProbe[] pointsInRange, double[] point) {
		return calculate.findPoint(pointsInRange, point);
	}
}
