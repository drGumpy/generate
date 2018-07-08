package certyficate.equipment.type;

import java.io.IOException;

import certyficate.equipment.calculation.EqipmentCalculate;

public class BlackBodyGenerator extends TProbe {
	public BlackBodyGenerator(String path) throws IOException {
		super(path);
		calculate = new EqipmentCalculate();
	}
}
