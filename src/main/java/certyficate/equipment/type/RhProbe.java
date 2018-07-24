package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.CalculateRh;
import certyficate.equipment.calculation.DataProbe;
import certyficate.property.DataCalculation;

public class RhProbe extends ReferenceProbe {
    public RhProbe(String path) throws IOException{
    	super(path);
    	calculate = new CalculateRh();
    }
  
	@Override
	protected void setDrifts(String[] elements) {
		driftT = DataCalculation.getDouble(elements[1]);
		driftRh = DataCalculation.getDouble(elements[2]);
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
		data.drift = driftT;
		data.driftRh = driftRh;
		return data;
	}

	@Override
	protected void setRanges() {
		rangeSize = 4;
		ranges = new int[numberOfRanges][rangeSize];	
	}

	@Override
	protected boolean equalPoint(double[] point, int index) {
		DataProbe data = standardPoints[index];
		return (point[0] == data.value) && (point[1] == data.valueRh);
	}

	@Override
	protected boolean inRange(double[] point, int[] range) {
		return (point[0] >= range[0]) && (point[0] <= range[1]) 
				&& (point[1] >= range[2]) && (point[1] <= range[3]);
	}

	@Override
	protected DataProbe[] findPointsInRange(int[] range) {
		DataProbe[] pointsInRange = new DataProbe[rangeSize];
		for(int i = 0; i < 2; i++) {
			pointsInRange[i] = findInStandardPoints(
					new double[]{range[0], range[i+2]});
			pointsInRange[i+2] = findInStandardPoints(
					new double[]{range[1], range[i+2]});
		}
		return pointsInRange;
	}

	@Override
	protected DataProbe caluculate(DataProbe[] pointsInRange, double[] point) {
		return calculate.findPoint(pointsInRange, point);
	}
}