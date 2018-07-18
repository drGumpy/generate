package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.Calculate;
import certyficate.equipment.calculation.DataProbe;
import certyficate.property.DataCalculation;


public class TProbe extends ReferenceProbe {
    public TProbe(String path) throws IOException {
    	super(path);
    	calculate = new Calculate();
	}
    
	@Override
	protected void setDrifts(String[] elements) {
		driftT = DataCalculation.getDouble(elements[1]);
	}

	@Override
	protected DataProbe findProbeData(String[] elements) {
		DataProbe data = new DataProbe();
        data.value = getInteger(elements[0]);
        data.correction = DataCalculation.getDouble(elements[1]);
        data.uncertainty = DataCalculation.getDouble(elements[2]);
        data.drift = driftT;
		return data;
	}

	@Override
	protected void setRanges() {
		rangeSize = 2;
		ranges = new int[numberOfRanges][rangeSize];
	}

	@Override
	protected boolean equalPoint(double[] point, int index) {
		DataProbe data = standardPoints[index];
		return point[0] == data.value;
	}

	@Override
	protected boolean inRange(double[] point, int[] range) {
		return (point[0] >= range[0]) && (point[0] <= range[1]);
	}
	
	@Override
	protected DataProbe[] findPointsInRange(int[] range) {
		DataProbe[] pointsInRange = new DataProbe[rangeSize];
		for(int i = 0; i < 2; i++) {
			pointsInRange[i] = findInStandardPoints(
					new double[]{range[i]});
			
		}
		return pointsInRange;
	}

	@Override
	protected DataProbe caluculate(DataProbe[] pointsInRange, double[] point) {
		return calculate.findPoint(pointsInRange, point);
	}
}