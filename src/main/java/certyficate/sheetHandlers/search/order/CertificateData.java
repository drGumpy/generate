package certyficate.sheetHandlers.search.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.*;
import certyficate.property.CalibrationData;

public class CertificateData {
    private static final String ORDERS_LABEL = "Zlecenia";
    private static final String EMPTY_CELL = "";
    private static final String WHITE_SPACE = "\\s+";
    private static final String PROBE_SEPARATOR = ",";
    
	private static Sheet sheet;
    
    private static List<Order> orders;

    private static Map<String, Client> clientsData;
    
    private static Map<String, Probe> probesData;
    
    private static Map<String, Device> devicesData;
    
    private static CodeVerification verification;
    
    public static void findOrdersData() {
        setSheetAndfindOrders();
        ClientsData.findClientData(clientsData);
        DeviceData.findDeviceData(devicesData);
        ProbeData.findProbeData(probesData);
        gatherData();
        CalibrationData.orders = orders;
    }
    
    //Wyszukiwanie nie wsytawionych świadectw - brak daty wzorcowania
    private static void setSheetAndfindOrders()  {
    	settings();
    	setSheet();
        findOrders();
    }

	private static void settings() {
		verification = new  CodeVerification();
		setContainers();
	}

	private static void setContainers() {
		orders = new ArrayList<Order>();
		clientsData = new HashMap<String, Client>();
		probesData = new HashMap<String, Probe>();
		devicesData = new HashMap<String, Device>();
	}
	
	private static void setSheet() {
		sheet = CalibrationData.spreadSheet.getSheet(ORDERS_LABEL);
	}

	private static void findOrders() {
		int line = findFirstOrder();   
        while(!EMPTY_CELL.equals(sheet.getValueAt(5,line))) {
        	checkLine(line);
            line++;
        }
	}

	private static int findFirstOrder() {
    	int line = 0;
    	String element;
    	do {
    		line++;
    		element = sheet.getValueAt(2, line).toString();
    	} while(!EMPTY_CELL.equals(element));
		return line;
	}

	private static void checkLine(int line) {
		if(EMPTY_CELL.equals(sheet.getValueAt(2, line))) {
        	checkAndAddOrderData(line);
        }
	}
	
	private static void checkAndAddOrderData(int line) {
		Order order = new Order();
		order.calibrationCode = sheet.getValueAt(9, line).toString();
		if(verification.checkCalibrationCode(order)) {
			addOrder(line, order);
		}
	}
	
	private static void addOrder(int line, Order order) {
		String probe = sheet.getValueAt(8, line).toString();
		setProbeData(order, probe);
        order.numberOfCalibration = sheet.getValueAt(1, line).toString();
        order.deviceSerialNumber = sheet.getValueAt(6, line).toString();
        order.calibrationCode = sheet.getValueAt(9, line).toString();
        order.calibrationDate = sheet.getValueAt(10, line).toString();
        addToContainers(line, order);
        orders.add(order);
	}

	private static void setProbeData(Order order, String probe) {
		String[] probeSerialArray = {EMPTY_CELL};
		order.probeSerialNumber = probe;
        if(!probe.equals(",")) {
            probe = probe.replaceAll(WHITE_SPACE, EMPTY_CELL);
            probeSerialArray = probe.split(PROBE_SEPARATOR);
        }
		order.probeSerial = probeSerialArray;
	}
	
	private static void addToContainers(int line, Order order) {
		setClient(3, line, order.declarant);
		setClient(4, line, order.user);
		setDevice(line, order.device);
		setProbe(line, order.probe);
	}

	private static void setClient(int column, int line, Client client) {
		client.name = sheet.getValueAt(column, line).toString();
		clientsData.put(client.name, client);
	}

	private static void setDevice(int line, Device device) {
		device.model = sheet.getValueAt(5, line).toString();
		devicesData.put(device.model, device);
	}

	private static void setProbe(int line, Probe probe) {
		probe.model = sheet.getValueAt(7, line).toString();
		probesData.put(probe.model, probe);
	}

    private static void gatherData(){
        for (int i=0; i < orders.size(); i++) {
        	completeCertyficationData(i);
        }
    }
       
    private static void completeCertyficationData(int index) {
    	Order certyficate  = orders.get(index);
        certyficate.declarant = clientsData.get(orders.get(index).declarant.name);
        certyficate.user = clientsData.get(orders.get(index).user.name);
        certyficate.device = devicesData.get(orders.get(index).device.model);
        certyficate.probe = findProbe(certyficate, index);
        orders.set(index, certyficate);
	}

	private static Probe findProbe(Order certyficate, int index) {
		Probe probe = null;
		if(probesData.containsKey(orders.get(index).probe.model)) {
			probe = probesData.get(orders.get(index).probe.model);
		}
		return probe;
	}
}

