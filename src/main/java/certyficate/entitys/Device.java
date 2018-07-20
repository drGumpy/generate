package certyficate.entitys;

public class Device{
	private String model;
	private String type;
	private String producent;
	private String[] resolution;
	private String[] channel;
	
	public void setModel(String model) {
		this.model = model;
	}
	  
	public String getModel() {
		return model;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	  
	public String getType() {
		return type;
	}
	
	public void setProducent(String producent) {
		this.producent = producent;
	}
	  
	public String getProducent() {
		return producent;
	}
	
	public void setResolution(String[] resolution) {
		this.resolution = resolution;
	}
	  
	public String getResolution(int index) {
		return resolution[index];
	}
	
	public int getResolutionLength() {
		return resolution.length;
	}
	
	public void setChannel(String[] channel) {
		this.channel = channel;
	}
	  
	public String getChannel(int index) {
		return channel[index];
	}
	
	public int getChannelLength() {
		return resolution.length;
	}
}
