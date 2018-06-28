package certyficate.equipment.calculation;

// clasic y = ax + b straight line
class StraightLine {
	private double a;
	private double b;
	
	StraightLine(double a, double b) {
		this.a = a;
		this.b = b;
	}
	
	double findPointValue(int x) {
		return a * x + b;
	}
}
