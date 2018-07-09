package certyficate.GUI.infrared;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import certyficate.GUI.Console;
import certyficate.calculation.EnvironmentData;
import certyficate.dataContainer.CalibrationType;
import certyficate.equipment.EquipmentParameters;
import certyficate.equipment.EquipmentType;
import certyficate.property.CalibrationData;
import certyficate.property.SheetData;
import certyficate.sheetHandlers.SheetBulider;
import certyficate.sheetHandlers.search.CertificateData;
import certyficate.sheetHandlers.search.MeasurementsData;

@SuppressWarnings("serial")
public class InfraredPanel extends JPanel {
	public static final String BUTTON_LABEL = "generuj";
	
	public static final int WIDTH = 200;
	public static final int HIGHT = 23;
	
	private JButton generate;
	
	private Console console;
	
	public InfraredPanel(Console console) {
		this.console = console;
		setButtons();
	}

	private void setButtons() {
		setCalibrationDataButton();
		add(generate);
	}

	private void setCalibrationDataButton() {
		Dimension size = new Dimension(WIDTH, HIGHT);
		setSize(size);
		generate = new JButton(BUTTON_LABEL);
		generate.setMinimumSize(size);
		generate.addActionListener(new IRGenerateListener());
	}
	
	private class IRGenerateListener implements ActionListener {
		final private static int MAXIMUM_CALIBRATION_POINTS = 6;
		
		public void actionPerformed(ActionEvent e) {
			CalibrationData.calibrationType = CalibrationType.INFRARED;
			SheetData.setInfrared();
			getFilesData();
			generateCalibrationCeryficate();
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
			EnvironmentData.setEnvirometsData();
		}

		private void findMeasurmentsData() throws IOException {
			bulidSheet();
			getCalibrationData();
			findMeasurementsData();
			new InfraredParametrs(console);
		}

		private void bulidSheet() throws IOException  {
			SheetBulider.setSpreadSheet();
		}
		
		private void getCalibrationData() {
			CertificateData.findOrdersData();
		}
		
		private void findMeasurementsData() {
			CalibrationData.calibrationPoints 
				= MAXIMUM_CALIBRATION_POINTS;
			MeasurementsData.findMeasurementsData();
		}
		
		private void findReferenceData() throws IOException {
			CalibrationData.probe 
				= EquipmentParameters.find(EquipmentType.INFRARED_REFERENCE);
		}	

		private void generateCalibrationCeryficate() {
			
		}

		public void doing() {
		/*	File file = sheetFinder.getFile();
            CertificateData.setFile(file);
            CertificateData.calibration=5;
            try {
                CertificateData.run();
                data=CertificateData.getData();
            } catch (IOException e1) {}
            new IRChoose(Console.this, true, data);
            try {
                GetData.setData(false);
                GetData.IR();
                GetData.setFile(file);
                devices = GetData.findData(6);
                point = GetData.getPoint();
            } catch (IOException e1) {}
            try {
                dataProbe = new DataProbe[point.size()];
                PaternProbe probe;
                probe= new TProbe(new File(DisplayedText.dataPath+"12030011.txt"));
                for(int i=0; i<point.size(); i++){
                    int t=Integer.parseInt(point.get(i).temp);
                    dataProbe[i]=probe.get(t, 0);
                }
            } catch (IOException e1) {}
            generation.setEnabled(true);
        }    
		}*/
	}
	}
	
	
	private class GenerationListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void doing() {
		/*	 IRGenerate make = new IRGenerate();
             make.putDataProbe(dataProbe);
             make.putDevice(devices);
			 make.putPaths(notesFinder.getFile().toString(),
					 certificateFinder.getFile().toString());
             Environment d= new Environment();
             make.putEnvironment(d.calculateData(
            		 environment.getEnviromentCondition()));
             make.run(data);
             try {
                 File file = sheetFinder.getFile();
                 PutDate.putFile(file);
                 PutDate.date(make.get_done());
             } catch (IOException e1) {
                 e1.printStackTrace();
             }
             System.out.println("koniec wprowadzania");
		}*/
		}
	}

}
