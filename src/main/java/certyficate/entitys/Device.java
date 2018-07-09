package certyficate.entitys;

public class Device{
	public String model;
	public String type;
	public String producent;
	public String[] resolution = new String[2];
	public String[] channel;
	
	public String toString(){
		return type+"\t"+model+"\t"+producent;
	}
}
