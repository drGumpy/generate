package certyficate.equipment.calculation;

public class EnvironmentCalculate extends CalculateRh {
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
	
	protected void setData(DataProbe data) {
    	findUncertainty(data);	
	}
	
    protected DataProbe setCorrections(double[] point) {
    	DataProbe data  = new DataProbe(point);
    	estimateCorection(data);
		return data;
	}
}
