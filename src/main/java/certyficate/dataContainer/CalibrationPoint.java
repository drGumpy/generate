package certyficate.dataContainer;

import java.util.Date;

import certyficate.property.CalibrationData;

//przechowywanie danych o wzorcowanych puntkach
public class CalibrationPoint{
	public double[] point;
	
	public int number;
	
	public String date = "";
	public String time = "";
	
	private Date pointDate;
	
	public CalibrationPoint() {
		point = new double[CalibrationData.numberOfParameters];
	}
	
	//ustalenie wartości nr. przyrządu na liście i plik z danymi
	public void set(int number){
		this.number= number;
	}

	public void setDate() {
		pointDate = new Date();
		setTime();
	}

	private void setTime() {
		String times = time.split(":");
		
	}
	
}