package certyficate.sheetHandlers.search.order;

import java.util.Map;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.Device;
import certyficate.property.CalibrationData;

public class DeviceData {
	private static final String DEVICES_LABEL = "Urządzenia";
	private static final String EMPTY_CELL = "";
	private static final String PROBE_SEPARATOR = ",";
	 
	private static Sheet sheet;
	private static Map<String, Device> data;
	
	public static void findDeviceData(Map<String, Device> devicesData) {
		sheet = CalibrationData.spreadSheet.getSheet(DEVICES_LABEL);
		data = devicesData;
		findDevices();
	}
	
	private static void findDevices() {
		int line = 1;
		while(!EMPTY_CELL.equals(sheet.getValueAt(0,line))) {
        	checkDevice(line);
            line++;
        }	
	}

	private static void checkDevice(int line) {
		String model = sheet.getValueAt(0,line).toString();
        if(data.containsKey(model)) {
        	addDevice(line);
        }
	}

	private static void addDevice(int line){
		Device device = new Device();
		addChannels(device, line);
        device.setType(sheet.getValueAt(1, line).toString());
        device.setProducent(sheet.getValueAt(2, line).toString());
        device.setResolution(setResolution(line));
        addModel(device, line);
	}

	private static void addChannels(Device device, int line) {
		String channel = sheet.getValueAt(11, line).toString();
		addChannels(device, channel);
	}

	private static void addChannels(Device device, String channel) {
		String[] channelArray = {EMPTY_CELL};
		if(!channel.equals(PROBE_SEPARATOR)) {
            channelArray = channel.split(PROBE_SEPARATOR);
		}
		device.setChannel(channelArray);
	}
	
	private static String[] setResolution(int line) {
		String[] resolution = new String[2];
		resolution[0] = sheet.getValueAt(4, line).toString();
        resolution[1] = sheet.getValueAt(7, line).toString();;
		return resolution;
	}
	
	private static void addModel(Device device, int line) {
		String model = sheet.getValueAt(0, line).toString();
		device.setModel(model);
		data.put(model, device);
	}
}
