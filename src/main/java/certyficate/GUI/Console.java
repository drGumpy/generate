package certyficate.GUI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import certyficate.GUI.infrared.InfraredPanel;
import certyficate.GUI.path.PathFinder;
import certyficate.GUI.path.PathType;

@SuppressWarnings("serial")
public class Console extends JFrame {
	private static final String CLIMATE_CHAMBER = "komora klimatyczna";
	private static final String INFRARED = "pirometry";
	
	private static final int WIDTH = 750;
	private static final int HEIGHT = 400;
	
	private static final String TITLE = "wydawanie świadectw";

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
                console.setVisible(true);
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
    	constrain.fill = GridBagConstraints.VERTICAL;
    	constrain.anchor = GridBagConstraints.PAGE_START;
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
    	constrain.weighty=0.2;
    	constrain.gridy=4;
    	add(tabbedPane, constrain);
		
	}

    private JTabbedPane setTabbedPane() {
    	JTabbedPane tabbedPane = new JTabbedPane();
    	int width = 700;
    	int height = 10;
    	tabbedPane.addTab(CLIMATE_CHAMBER, new ClimateChamber(this));
    	tabbedPane.addTab(INFRARED, new InfraredPanel(this));
    	tabbedPane.setMaximumSize(new Dimension(width, height));
		return tabbedPane;
	}

	public void close(){
		super.dispose();
	}
}