package certyficate.equipment.calculation;

public class DataProbe {
	public boolean question = true;
	
	public double value;
	public double valueRh;
	public double correction;
    public double correctionRh;
    public double uncertainty;
    public double uncertaintyRh;   
    public double drift;
    public double driftRh;
    
	public DataProbe() {
		super();
	}
	
	public DataProbe(boolean set) {
		question = set;
	}
	
	public DataProbe(int[] point) {
		value = point[0];
		if(point.length == 2)
			valueRh = point[1];
	}
	
	public DataProbe(double[] point) {
		value = point[0];
		if(point.length == 2)
			valueRh = point[1];
	}

	public void setDrift(DataProbe data) {
		drift = data.drift;
		driftRh = data.driftRh;
	}
	
	public StraightLine uncertaintyLineT(DataProbe data) {
		double b = (double) this.correction;
		double a = b - data.correction;
		a /= (this.value - data.value);
		b -= a * this.value;
		return new StraightLine(a, b);
	}
	
	public StraightLine uncertaintyLineRh(DataProbe data) {
		double b = (double) this.correctionRh;
		double a = b - data.correctionRh;
		a /= (this.valueRh - data.valueRh);
		b -= a * this.valueRh;
		return new StraightLine(a, b);
	}

	public double getUncertainty(int parametrIndex) {
		if(parametrIndex == 0) {
			return uncertainty;
		} else {
			return uncertaintyRh;
		}
	}

	public double getDrift(int parametrIndex) {
		if(parametrIndex == 0) {
			return drift;
		} else {
			return driftRh;
		}
	}  
}