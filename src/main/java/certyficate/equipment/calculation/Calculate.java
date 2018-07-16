package certyficate.equipment.calculation;

public class Calculate {
	DataProbe[] pointsInRange;
	double[] point;
	
	public DataProbe findPoint(DataProbe[] pointsInRange, double[] point) {
		this.point = point;
		this.pointsInRange = pointsInRange;
		return getDataPoint(point);
	}
	
	protected DataProbe getDataPoint(double[] point) {
    	DataProbe data  = setCorrections(point);
    	setData(data);
		return data;
	}
	
    protected DataProbe setCorrections(double[] point) {
    	DataProbe data  = new DataProbe(point);
    	estimateCorection(data);
		return data;
	}

	protected void estimateCorection(DataProbe data) {
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
	

	protected void setData(DataProbe data) {
    	data.setDrift(pointsInRange[0]);
    	findUncertainty(data);	
	}

	protected void findUncertainty(DataProbe data) {
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
}
