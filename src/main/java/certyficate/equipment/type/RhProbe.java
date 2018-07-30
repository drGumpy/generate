package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.CalculateRh;
import certyficate.equipment.calculation.DataProbe;
import certyficate.property.CalibrationData;
import certyficate.property.DataCalculation;

public class RhProbe extends ReferenceProbe {
    public RhProbe(String path) throws IOException{
    	super(path);
    	calculate = new CalculateRh();
    }
  
	@Override
	protected void setDrifts(String[] elements) {
		for(int i = 0; i < CalibrationData.numberOfParameters; i++) {
			drift[i] = DataCalculation.getDouble(elements[i + 1]);
		}
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
		data.setDrift(drift);
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
		return (point[0] == data.getValue(0)) && (point[1] == data.getValue(1));
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