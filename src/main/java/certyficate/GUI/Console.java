package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import certyficate.entitys.*;
import certyficate.equipment.*;
import certyficate.equipment.calculation.DataProbe;
import certyficate.equipment.type.Equipment;
import certyficate.equipment.type.RhProbe;
import certyficate.equipment.type.TProbe;
import certyficate.generate.*;
import certyficate.sheetHandlers.insert.*;
import certyficate.sheetHandlers.search.*;
import certyficate.GUI.infrared.InfraredPanel;
import certyficate.GUI.path.PathFinder;
import certyficate.GUI.path.PathType;
import certyficate.calculation.*;
import certyficate.dataContainer.*;

@SuppressWarnings("serial")
public class Console extends JFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private static final String TITLE = "wydawanie świadectw";
	
	private static EnvironmentPanel environment;
	private static PathFinder sheetFinder;
    private static PathFinder notesFinder;
    private static PathFinder certificateFinder;
    
    static ChamberSettings settings = new ChamberSettings();
    
	//dane na temat wzorcowania
    static ArrayList<Order> data = new ArrayList<Order>();
    static ArrayList<CalibrationPoint> calPoint = new ArrayList<CalibrationPoint>();
    
    private static DataProbe[] dataProbe;
    
    private static ArrayList<Measurements> devices = new ArrayList<Measurements>();
    private static ArrayList<CalibrationPoint> point = new ArrayList<CalibrationPoint>();    
    private static Measurements patern = new Measurements(0);
    
    private static ChamberData[] chamberData;
    
    //parametry wzorcowania
    private static boolean Rh = false;
    private static int points=3;
    
    private static AbstractButton t, rh;
    
    private static JComboBox<Integer> pointsBox = new JComboBox<Integer>();   
    
    private static GridBagConstraints constrain;
   
    public static void main(String[] args) {
        run();    
    }
    
    private static void run(){
        SwingUtilities.invokeLater(new Runnable(){
            Console console = new Console();
            public void run(){
                console.setTitle(TITLE);
                console.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                console.setSize(WIDTH, HEIGHT);
            }
        });
    }
    
    public Console(){
    	setPanelSettings();
    	addEnvironmentPanel();
    	addSheetPanel();
    	addNotesPanel();
    	addCertificatePanel();
    	addTabbetPane();
    }
    
	private void setPanelSettings() {
    	setLayout(new GridBagLayout());
    	constrain = new GridBagConstraints();
    	constrain.fill= GridBagConstraints.VERTICAL;
    	constrain.anchor =GridBagConstraints.PAGE_START;
	}

	private void addEnvironmentPanel() {
		EnvironmentPanel environment = new EnvironmentPanel();
		constrain.weighty = 0.2;
    	constrain.gridy = 0;
    	add(environment, constrain);
	}

	private void addSheetPanel() {
		PathFinder sheetFinder = new PathFinder(PathType.SHEET);
		constrain.weighty = 0.1;
    	constrain.gridy = 1;
    	add(sheetFinder, constrain);
	}

	private void addNotesPanel() {
		PathFinder notesFinder = new PathFinder(PathType.NOTES);
		constrain.gridy = 2;
    	add(notesFinder, constrain);
	}
	
	private void addCertificatePanel() {
		PathFinder certificateFinder = new PathFinder(PathType.CERTIFICATES);
		constrain.gridy = 3;
    	add(certificateFinder, constrain);		
	}

	private void addTabbetPane() {
    	JTabbedPane tabbedPane = setTabbedPane();
    	tabbedPane.addTab("komora klimatyczna", _climateChamber());
    	tabbedPane.addTab("pirometry", new InfraredPanel(this));
    	tabbedPane.setMaximumSize(new Dimension(700, 10));
    	constrain.weighty=0.2;
    	constrain.gridy=4;
    	add(tabbedPane, constrain);
		
	}

    private JTabbedPane setTabbedPane() {
    	JTabbedPane tabbedPane = new JTabbedPane();
    	int width = 700;
    	int height = 10;
    	tabbedPane.addTab("komora klimatyczna", _climateChamber());
    	tabbedPane.addTab("pirometry", new InfraredPanel(this));
    	tabbedPane.setMaximumSize(new Dimension(width, height));
		return tabbedPane;
	}

	public void close(){
		super.dispose();
	}
    
    //wzorcowanie w komorze klimatycznej
    private JPanel _climateChamber(){
    	JPanel jp = new JPanel();
    	jp.setLayout(new GridBagLayout());
        JButton dattaLogger= new JButton("dane z rejestratorów");
        final JButton clientData= new JButton("wybierz zlecenia");
        final JButton generation= new JButton("generuj świadetwa");
        
        dattaLogger.setMinimumSize(new Dimension(200, 23));
        clientData.setMinimumSize(new Dimension(200, 23));
        generation.setMinimumSize(new Dimension(200, 23));

        //wprowadzenie danych o rejestratorach
        dattaLogger.addActionListener(new ActionListener(){    
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
                generation.setEnabled(true);
                long endTime   = System.currentTimeMillis();
                System.out.println("czas: " +(endTime - startTime)/1000.0 + " s");
            }
        });
        //wygenerowanie świadectw wzorcowania
        generation.addActionListener(new ActionListener(){
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
        generation.setEnabled(false);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        jp.add(settings, c);
        
        c.gridwidth = 1;
        c.ipadx=10;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 1;
        jp.add(dattaLogger, c);
       
        c.gridx = 1;
        jp.add(clientData,c);       

        c.gridx = 2;
        jp.add(generation, c);
        return jp;
    }
}