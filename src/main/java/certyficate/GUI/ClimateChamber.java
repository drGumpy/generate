package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.GUI.infrared.InfraredParametrs;
import certyficate.calculation.EnvironmentData;
import certyficate.dataContainer.Chamber;
import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.type.Equipment;
import certyficate.equipment.type.RhProbe;
import certyficate.equipment.type.TProbe;
import certyficate.generate.DisplayedText;
import certyficate.generate.Generate;
import certyficate.generate.Generate;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.insert.PutData;
import certyficate.sheetHandlers.insert.PutDate;
import certyficate.sheetHandlers.search.CertificateData;
import certyficate.sheetHandlers.search.MeasurementsData;

@SuppressWarnings("serial")
public class ClimateChamber extends JPanel {
	public static final String LOGGER_BUTTON = "dane z rejestratorów";
	public static final String GENERATION_BUTTON = "generuj świadetwa";
	
	public static final int WIDTH = 200;
	public static final int HIGHT = 23;
	
	static ChamberSettings settings;
	
	private Console console;
	
	GridBagConstraints constrain = new GridBagConstraints();
	
	public ClimateChamber(Console console) {
		this.console = console;
		setPanelSettings();
		setPanelElements();
	}


	private void setPanelSettings() {
		 setLayout(new GridBagLayout());
		 constrain = new GridBagConstraints();
		 constrain.anchor = GridBagConstraints.PAGE_START;
		 constrain.ipadx = 10;
	}
	
	private void setPanelElements() {
		setChamberSettings();
		setLoggerButton();
		setGenerationButton();
	}


	private void setChamberSettings() {
		ChamberSettings settings = new ChamberSettings();
		constrain.gridwidth = 2;
        add(settings, constrain);
	}


	private void setLoggerButton() {
		JButton dataLogger = new JButton(LOGGER_BUTTON);
		dataLogger.setMinimumSize(new Dimension(WIDTH, HIGHT));
		dataLogger.addActionListener(new DataLoggerListener());
        constrain.gridwidth = 1;
        constrain.gridy = 1;
        add(dataLogger, constrain);
	}
	
	private class DataLoggerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSheetData();
			PutData.insertReferenceAndLoggersData();
		}
	}
	
	private void setSheetData() {
		SheetData.setChamberData(CalibrationData.calibrationType);
	}
	
	private void setGenerationButton() {
		JButton generate= new JButton(GENERATION_BUTTON);
		generate.setMinimumSize(new Dimension(WIDTH, HIGHT));
		generate.addActionListener(new GenerateListener());
		constrain.gridx = 1;
        add(generate, constrain);
	}

	private class GenerateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setSheetData();
			getFilesData();
			generateCalibrationDocuments();
			PutDate.calibrationDate();
			console.close();
		}
		
		private void getFilesData()  {
			try {
				findCalibrationData();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		private void findCalibrationData() throws IOException {
			findMeasurmentsData();
			findReferenceData();
			findChamberData();
			EnvironmentData.setEnvirometsData();
		}

		private void findMeasurmentsData() throws IOException {
			getCalibrationData();
			findMeasurementsData();
		}
		
		private void getCalibrationData() {
			CertificateData.findOrdersData();
		}
		
		private void findMeasurementsData() throws IOException {
			MeasurementsData.findMeasurementsData();
			MeasurementsData.findProbeData();
		}
		
		private void findReferenceData() throws IOException {
			EquipmentType probe = EquipmentType.setReferenceType();
			CalibrationData.probe = EquipmentParameters.find(probe);
		}
		
		private void findChamberData() throws IOException {
			EquipmentType chamber = EquipmentType.setChamber();
			CalibrationData.chamber = EquipmentParameters.find(chamber);
		}

		private void generateCalibrationDocuments() {
			Generate.generateDocuments();
		}
	}

	private JPanel _climateChamber(){
	    	JPanel jp = new JPanel();
	    	jp.setLayout(new GridBagLayout());
	        JButton dataLogger= new JButton(LOGGER_BUTTON);
	        final JButton clientData= new JButton("wybierz zlecenia");
	        final JButton generate= new JButton(GENERATION_BUTTON);
	        
	        dataLogger.setMinimumSize(new Dimension(200, 23));
	        clientData.setMinimumSize(new Dimension(200, 23));
	        generate.setMinimumSize(new Dimension(200, 23));

	        //wprowadzenie danych o rejestratorach
	        dataLogger.addActionListener(new ActionListener(){    
	            public void actionPerformed(ActionEvent e) {
	                long startTime = System.currentTimeMillis();
	                
	                File file = sheetFinder.getFile();
	                PutData.set(Rh, file , points);
	                try {
	                    calPoint=PutData.getPoints();
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	                PutData.run();
	                PutData.clean();
	                pointsBox.setEnabled(false);
	                t.setEnabled(false);
	                rh.setEnabled(false);
	                clientData.setEnabled(true);
	                long endTime   = System.currentTimeMillis();
	                System.out.println(" w czasie: " +(endTime - startTime)/1000.0+" s");
	            }
	        });
	        
	        //pozyskanie danych do świadectwa
	        clientData.addActionListener(new ActionListener(){    
	            public void actionPerformed(ActionEvent e) {
	                long startTime = System.currentTimeMillis();
	                File file = sheetFinder.getFile();
	                if(Rh)
	                	CertificateData.calibration=3;
	                else
	                	CertificateData.calibration=1;
	                CertificateData.findOrdersData();
	                try {
	                    MeasurementsData.setData(Rh);
	                    MeasurementsData.setFile(file);
	                    devices=MeasurementsData.findData2(points);
	                    patern=MeasurementsData.getPatern();
	                    point=MeasurementsData.getPoint();
	                } catch (IOException e1) {System.out.println("błąd pobierania danych");}
	                
	                try {
	                    dataProbe = new DataProbe[point.size()];
	                    Equipment probe;
	                    if(Rh)
	                        probe= new RhProbe(new File(DisplayedText.dataPath+"61602551.txt"));
	                    //	probe= new RhProbe(new File(DisplayedText.dataPath+"20055774.txt"));
	                    else
	                        probe= new TProbe(new File(DisplayedText.dataPath+"13.026.txt"));
	                    //	probe= new TProbe(new File(DisplayedText.dataPath+"12.926.txt"));
	                    for(int i=0; i<point.size(); i++){
	                        int t=Integer.parseInt(point.get(i).temp);
	                        int rh=0;
	                        if(Rh)
	                            rh=Integer.parseInt(point.get(i).hum);
	                        dataProbe[i]=probe.getPointData(t, rh);
	                    }
	                } catch (IOException e1) {System.out.println("błąd wzorca");}
	                
	                Chamber cham= new Chamber();
	                cham.start(Rh);
	                cham.getPoints(point);
	                chamberData=cham.get();
	                generate.setEnabled(true);
	                long endTime   = System.currentTimeMillis();
	                System.out.println("czas: " +(endTime - startTime)/1000.0 + " s");
	            }
	        });
	        //wygenerowanie świadectw wzorcowania
	        generate.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e) {
	                Generate make = new Generate();
	                make.putChamber(chamberData);
	                make.putDataProbe(dataProbe);
	                make.putDevice(devices);
	                make.putPatern(patern);
					make.putPaths(notesFinder.getFile().toString(),
							 certificateFinder.getFile().toString());
	                Environment d= new Environment();
	                make.putEnvironment(d.calculateData(
	                		environment.getEnviromentCondition()));
	                make.run(data);
	                try {
	                    File file = sheetFinder.getFile();
	                    PutDate.putFile(file);
	                    PutDate.date(make.getDone());
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	 
	                System.out.println("koniec wprowadzania");
	                close();
	            }
	        });
	        generate.setEnabled(false);
	        
	        GridBagConstraints constrain = new GridBagConstraints();
	        
	        constrain.gridwidth = 3;
	        constrain.gridx = 0;
	        constrain.gridy = 0;
	        jp.add(settings, constrain);
	        
	        constrain.gridwidth = 1;
	        constrain.ipadx=10;
	        constrain.anchor = GridBagConstraints.PAGE_START;
	        constrain.gridx = 0;
	        constrain.gridy = 1;
	        jp.add(dataLogger, constrain);
	       
	        constrain.gridx = 1;
	        jp.add(clientData,constrain);       

	        constrain.gridx = 2;
	        jp.add(generate, constrain);
	        return jp;
	    }
}
