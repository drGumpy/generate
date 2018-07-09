package certyficate.generate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import certyficate.calculation.DataCalculation;
import certyficate.dataContainer.CertificateValue;
import certyficate.dataContainer.Measurements;
import certyficate.entitys.Certificate;
import certyficate.equipment.calculation.DataProbe;
import certyficate.files.PathCreator;
import certyficate.property.CalibrationData;

public class PyrometerNote {
	private static final String NOTE_FILE = "z_T.ods";
	private static final String NOTE_SHEET = "Wyniki wzorcowania";
	
	private static final int POINT_GAP = 32;
	
	private static Sheet sheet;
	
	private static List<CertificateValue> calibrationData;
	
	private static int calibrationPointCount;
	
	private static Certificate certificate;
	
	private static DataProbe[] reference;
	
	private static String[] environment;
	
	public static void setNote(Certificate certificateData) {
		setCalibrationData(certificateData);
		setDataSheet();
	}
	
	private static void setCalibrationData(Certificate certificateData) {
		certificate = certificateData;
		reference = CalibrationData.probe;
		environment = CertificateText.getEnviromentData();
	}

	private static void setDataSheet() {
		try {
			setSheet();
			setSheetData();
		} catch (IOException e) {
			System.out.println("Note file fail");
			e.printStackTrace();
		}
	}

	private static void setSheet() throws IOException {
		String pathToNote = PathCreator.filePath(NOTE_FILE);
		File noteFile = new File(pathToNote);
		sheet = SpreadSheet.createFromFile(noteFile).getSheet(NOTE_SHEET);
	}
	
	private static void setSheetData() {
		calibrationData = new ArrayList<CertificateValue>();
		calibrationPointCount = 0;
		for(int i = 0; i< CalibrationData.calibrationPoints; i++) { 
			if(checkData(i)) {
				setPointData(i);
				calibrationPointCount++;
			}
		}
	}

	private static boolean checkData(int index) {
		boolean answer = certificate.measurmets[index].haveMeasurments;
		answer &= reference[index].question;
		answer &= reference[index].value 
				!= certificate.point[calibrationPointCount][0];
        return answer;
	}
	
	private static void setPointData(int index) {
		int line = calibrationPointCount * POINT_GAP + 3;
		setConstantValue(line);
		setMeasurmentValue(line, index);
		setCalibrationBudget(line, index);
	}

	private static void setConstantValue(int line) {
		sheet.setValueAt(certificate.numberOfCalibration, 3 , line);
        sheet.setValueAt(certificate.calibrationCode, 8 , line);
        sheet.setValueAt(certificate.calibrationDate, 13 , line);
        sheet.setValueAt(environment[0], 3 , line+1);
        sheet.setValueAt(environment[1], 7 , line+1);
        sheet.setValueAt(certificate.device.type, 3 , line+3);
        sheet.setValueAt(certificate.device.model, 3 , line+6);
        sheet.setValueAt(certificate.deviceSerialNumber, 3 , line+9);
        sheet.setValueAt(certificate.device.producent, 3 , line+11);
        sheet.setValueAt(certificate.device.resolution[0], 3 , line+13);
	}

	private static void setMeasurmentValue(int line, int index) {
		double referenceValue =
				certificate.pyrometr.reference[calibrationPointCount];
		for(int i=0; i<10; i++){
			sheet.setValueAt(referenceValue,
					1 , line+17+i);
			sheet.setValueAt(certificate.measurmets[index].data[i][0],
					3 , line+17+i);
		}
	}
	
	private static void setCalibrationBudget(int line, int index) {
		double[] uncerinity = findUncerinity(index, 0);
		sheet.setValueAt(certificate.measurmets[index].average[0], 7 , line+5);
        sheet.setValueAt(certificate.pyrometr.reference[calibrationPointCount],
        		7 , line+7);
        sheet.setValueAt(reference[index].correction, 7 , line+9);
        sheet.setValueAt(certificate.device.resolution[0], 9 , line+6);
        sheet.setValueAt(reference[index].uncertainty, 9, line+9);
        sheet.setValueAt(reference[index].drift, 9, line+10);
        sheet.setValueAt(certificate.pyrometr.blackBodyError[calibrationPointCount],
        		9, line+11);
        for(int j=0; j<uncerinity.length; j++){
            sheet.setValueAt(uncerinity[j], 13, line+5+j);
        }
	}
	
	private static double[] findUncerinity(int index, int parametrIndex) {
		double[] uncerinity = new double[7];
        uncerinity[0] = certificate.measurmets[index].standardDeviation[parametrIndex];
        uncerinity[1] = Double.parseDouble(certificate.device.resolution[parametrIndex]) / Math.sqrt(3);
        uncerinity[2] = 0;
        uncerinity[3] = 0.1 / Math.sqrt(3);
        uncerinity[4] = reference[index].getUncertainty(parametrIndex) / 2;
        uncerinity[5] = reference[index].getDrift(parametrIndex) / Math.sqrt(3);
        uncerinity[6] = certificate.pyrometr.blackBodyError[calibrationPointCount] / 2;
		return uncerinity;
	}

	private  void _generateDoc(Measurements device, Certificate type){
	        
	        point = device.averageT.length;
	        ArrayList<CertificateValue> cdata = new ArrayList<CertificateValue>();
	        try {
	            final Sheet sheet = SpreadSheet.createFromFile(note).getSheet(DisplayedText.noteSheet);
	            int count=0;
	            for(int i=0; i<point; i++){
	                if(device.q[i] || !dataProbe[i].question)
	                    continue;
	                if(dataProbe[i].value!=type.point[0][count])
	                	continue;
	                CertificateValue val= new CertificateValue();
	                int line = i*32+3;
	                sheet.setValueAt(type.numberOfCalibration, 3 , line);
	                sheet.setValueAt(type.calibrationCode, 8 , line);
	                sheet.setValueAt(type.calibrationDate, 13 , line);
	                sheet.setValueAt(environment[0], 3 , line+1);
	                sheet.setValueAt(environment[1], 7 , line+1);
	                sheet.setValueAt(type.device.type, 3 , line+3);
	                sheet.setValueAt(type.device.model, 3 , line+6);
	                sheet.setValueAt(type.deviceSerialNumber, 3 , line+9);
	                sheet.setValueAt(type.device.producent, 3 , line+11);
	                sheet.setValueAt(type.device.resolutionT, 3 , line+13);
	                for(int j=0; j<10; j++){
	                    sheet.setValueAt(type.pyrometr.reference[count],
	                    		1 , line+17+j);
	                    sheet.setValueAt(device.dataT[i][j], 3 , line+17+j);
	                }
	                
	                double[] uncT= new double[7];
	                uncT[0]=device.standardT[i];
	                uncT[1]=Double.parseDouble(type.device.resolutionT)/Math.sqrt(3);
	                uncT[2]=0;
	                uncT[3]=0.1/Math.sqrt(3);
	                uncT[4]=dataProbe[i].uncertainty/2;
	                uncT[5]=dataProbe[i].drift/Math.sqrt(3);
	                uncT[6]=type.pyrometr.blackBodyError[count]/2;
	 
	                sheet.setValueAt(device.averageT[i], 7 , line+5);
	                sheet.setValueAt(type.pyrometr.reference[count], 7 , line+7);
	                sheet.setValueAt(dataProbe[i].correction, 7 , line+9);
	                sheet.setValueAt(type.device.resolutionT, 9 , line+6);
	                sheet.setValueAt(dataProbe[i].uncertainty, 9, line+9);
	                sheet.setValueAt(dataProbe[i].drift, 9, line+10);
	                sheet.setValueAt(type.pyrometr.blackBodyError[count], 9, line+11);
	                for(int j=0; j<uncT.length; j++){
	                    sheet.setValueAt(uncT[j], 13, line+5+j);
	                }
	                double unc =DataCalculation.uncertainty(uncT);
	                double round = DataCalculation.findRound(2*unc, Double.parseDouble(type.device.resolutionT));
	                double pt=DataCalculation.round_d(type.pyrometr.reference[count]+
	                		dataProbe[i].correction,round);
	                double div =DataCalculation.round_d(device.averageT[i],round);
	                val.probeT= DataCalculation.round(pt,round).replace(".", ",");
	                val.deviceT = DataCalculation.round(div,round).replace(".", ",");
	                val.errorT = DataCalculation.round(div-pt,round).replace(".", ",");
	                val.uncertaintyT = DataCalculation.round(2*unc,round).replace(".", ",");
	                
	                sheet.setValueAt(val.probeT, 5, line+17);
	                sheet.setValueAt(val.deviceT, 7, line+17);
	                sheet.setValueAt(val.errorT, 9, line+17);
	                sheet.setValueAt(val.uncertaintyT, 13, line+17);
	                count++;
	                cdata.add(val);
	            }
	            String name = notePath+type.numberOfCalibration+"_"+type.device.model + ".ods";
	            sheet.getSpreadSheet().saveAs(new File(name));
	            _generateCal(cdata,type);
	            done.add(type.numberOfCalibration);
	        }catch (IOException e){}
	    }
}
