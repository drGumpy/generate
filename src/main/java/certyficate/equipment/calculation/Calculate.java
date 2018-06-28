package certyficate.equipment.calculation;

public class Calculate {
	DataProbe[] pointsInRange;
	int[] point;
	
	public DataProbe findPoint(DataProbe[] pointsInRange, int[] point) {
		this.point = point;
		this.pointsInRange = pointsInRange;
		return getDataPoint(point);
	}
	
	protected DataProbe getDataPoint(int[] point) {
    	DataProbe data  = setCorrections(point);
    	setData(data);
		return data;
	}
	
    protected DataProbe setCorrections(int[] point) {
    	DataProbe data  = new DataProbe(point);
    	estimateCorection(data);
		return data;
	}

	private void estimateCorection(DataProbe data) {
		double[] correction = findCorrection();
		data.correction = correction[0];
		data.correctionRh = correction[1];
	}

	protected double[] findCorrection() {
		return calculateCorrection(pointsInRange);
	}
	
	protected double[] calculateCorrection(DataProbe[] points) {
		LineCreator creator = new LineCreator(points);
		return creator.findCorrection(point[0]);
	}

	private void findUncertainty(DataProbe data) {
		double[] uncertainty = maxUncertainty();
		data.uncertainty = uncertainty[0];
		data.uncertainty = uncertainty[1];
	}
	
    public double[] maxUncertainty() {
		double[] uncertainty = new double[2];
		uncertainty[0] = maxUncertaintyT();
		uncertainty[1] = maxUncertaintyRh();
		return uncertainty;
	}
    
	private double maxUncertaintyT() {
		double uncertainty = 0D;
		for(int i = 0; i < pointsInRange.length; i++) {
			double pointUncertainty = pointsInRange[i].uncertainty;
			uncertainty = Math.max(uncertainty, pointUncertainty);
		}
		return uncertainty;
	}
	
	private double maxUncertaintyRh() {
		double uncertainty = 0D;
		for(int i = 0; i < pointsInRange.length; i++) {
			double pointUncertainty = pointsInRange[i].uncertaintyRh;
			uncertainty = Math.max(uncertainty, pointUncertainty);
		}
		return uncertainty;
	}
	
    private void setData(DataProbe data) {
    	data.setDrift(pointsInRange[0]);
    	findUncertainty(data);	
	}
}
