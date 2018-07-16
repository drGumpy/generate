package certyficate.sheetHandlers.search.order;

import java.util.Map;

import org.jopendocument.dom.spreadsheet.Sheet;

import certyficate.entitys.Client;
import certyficate.property.CalibrationData;

public class ClientsData {
	final private static String CLIENTS_LABEL = "Klienci";
	 private static final String EMPTY_CELL = "";
	
	
	private static Sheet sheet;
	private static Map<String, Client> data;
	
	public static void findClientData(Map<String, Client> clientsData) {
		sheet = CalibrationData.spreadSheet.getSheet(CLIENTS_LABEL);
		data = clientsData;
		findClients();
	}
	
	private static void findClients() {
		int line = 1;
		while(!EMPTY_CELL.equals(sheet.getValueAt(0, line))) {
			checkClient(line);
			line++;
		}    
	}

	private static void checkClient(int line) {
		String name = sheet.getValueAt(0, line).toString();
		if(data.containsKey(name))
			addClient(line);
	}

	private static void addClient(int line) {
		Client client = new Client();
		client.address = sheet.getValueAt(1, line).toString();
		client.postalCode = sheet.getValueAt(2, line).toString();
		client.town = sheet.getValueAt(3, line).toString();
		addClient(client, line);
	}

	private static void addClient(Client client, int line) {
		String name = sheet.getValueAt(0, line).toString();
		client.name = name;
		data.put(name, client);
	}
}
