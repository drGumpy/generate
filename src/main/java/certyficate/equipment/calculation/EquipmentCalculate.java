package certyficate.equipment.calculation;

public class EquipmentCalculate extends Calculate {
	@Override
	protected DataProbe getDataPoint(double[] point) {
    	DataProbe data  = setCorrections(point);
    	findUncertainty(data);
		return data;
	}
	
	protected double[] findCorrection() {
		return findMaxCorrection(pointsInRange);
	}

	private double[] findMaxCorrection(DataProbe[] pointsInRange) {
		double[] correction = new double[2];
		correction[0] = maxCorrectionT();
		correction[1] = maxCorrectionRh();
		return correction;
	}
	private double maxCorrectionT() {
		double correction = 0D;
		for(int i = 0; i < pointsInRange.length; i++) {
			double pointCorrection = pointsInRange[i].correction;
			correction = Math.max(correction, pointCorrection);
		}
		return correction;
	}

	private double maxCorrectionRh() {
		double correction = 0D;
		for(int i = 0; i < pointsInRange.length; i++) {
			double pointCorrection = pointsInRange[i].correctionRh;
			correction = Math.max(correction, pointCorrection);
		}
		return correction;
	}
}
