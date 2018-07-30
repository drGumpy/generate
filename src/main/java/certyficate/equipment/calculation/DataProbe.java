package certyficate.equipment.calculation;

import certyficate.property.CalibrationData;

public class DataProbe {
	private boolean question = true;
	
	private double[] value;
	private double[] correction;
	private double[] uncertainty;
	private double[] drift;
    
	public DataProbe() {
		setSize();
	}
	
	public DataProbe(boolean set) {
		question = set;
	}

	public DataProbe(double[] point) {
		setSize();
		value = point;
	}

	public boolean haveData() {
		return question;
	}
	
	public void setValue(double value, int index) {
		this.value[index] = value;
	}
	
	public double getValue(int index) {
		return value[index];
	}
	
	public void setCorrection(double correction, int index) {
		this.correction[index] = correction;
	}
	
	public double getCorrection(int index) {
		return correction[index];
	}
	
	public void setUncertainty(double uncertainty, int index) {
		this.uncertainty[index] = uncertainty;
	}
	
	public double getUncertainty(int index) {
		return uncertainty[index];
	}
	
	public void setDrift(DataProbe data) {
		drift = data.drift;
	}
	
	public void setDrift(double[] drift) {
		this.drift = drift;
	}

	public double getDrift(int index) {
		return drift[index];
	} 
	
	public StraightLine uncertaintyLine(DataProbe data, int index) {
		double b = (double) this.correction[index];
		double a = b - data.correction[index];
		a /= (this.value[index] - data.value[index]);
		b -= a * this.value[index];
		return new StraightLine(a, b);
	}
	
	private void setSize() {
		int size = CalibrationData.numberOfParameters;
		value = new double[size];
		correction = new double[size];
		uncertainty = new double[size];
		drift = new double[size];
	}
}