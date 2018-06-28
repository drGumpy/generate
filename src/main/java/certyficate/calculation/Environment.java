package certyficate.calculation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import certyficate.dataContainer.*;
import certyficate.equipment.calculation.DataProbe;
import certyficate.generate.*;

public class Environment {
	//dane o wzorcowaniu
    private DataProbe[] certificateData= new DataProbe[4];
    
    double[] new_data = new double[4];
    
    int[] range = new int[4];
    
    //plik z informacjami o urządzeniu do odczytu danych
    private File doc = new File(DisplayedText.dataPath+"w-srod.txt");
    
    //pobranie danych o wzorcowaniu
    private void _find(){
        try {
            Scanner sc = new Scanner(doc);
            for(int i=0; i<4; i++){
            	certificateData[i]= new DataProbe();
            	certificateData[i].value=sc.nextInt();
            	certificateData[i].valueRh=sc.nextInt();
            	certificateData[i].correction=sc.nextDouble();
            	certificateData[i].correctionRh=sc.nextDouble();
            	certificateData[i].uncertainty=sc.nextDouble();
            	certificateData[i].uncertaintyRh=sc.nextDouble();                
            }
            sc.close();
            range[0] = certificateData[0].value;
            range[1] = certificateData[3].value;
            range[2] = certificateData[0].valueRh;
            range[3] = certificateData[3].valueRh;
        }catch (FileNotFoundException e){}    
    }
    
    // uwzględnienie poprawek
    private void _findData(double a, double b, int i){
        double correctionT = (a-range[0])/(range[1]-range[0]);
        double correctionRh = (b-range[2])/(range[3]-range[2]);
        double     c_t= DataCalculation.calculate(correctionT, correctionRh,
        			certificateData[0].correction, certificateData[2].correction,
        			certificateData[1].correction, certificateData[3].correction),
                c_Rh=DataCalculation.calculate(correctionT, correctionRh,
                		certificateData[0].correctionRh, certificateData[2].correctionRh,
                		certificateData[1].correctionRh, certificateData[3].correctionRh);
        
        new_data[0+i]= a + c_t;
        new_data[2+i]= b + c_Rh;
    }
    
    //uzyskanie danych wyjściowych
    private void _corection() {
        double a= Math.max(Math.max(certificateData[0].uncertainty, certificateData[1].uncertainty),
                Math.max(certificateData[2].uncertainty, certificateData[3].uncertainty)),
               b= Math.max(Math.max(certificateData[0].uncertaintyRh, certificateData[1].uncertaintyRh),
                        Math.max(certificateData[2].uncertaintyRh, certificateData[3].uncertaintyRh));
        new_data[0]-=a;
        new_data[1]+=a;
        new_data[2]-=b;
        new_data[3]+=b;
        new_data[0]=DataCalculation.round_d(new_data[0], 0.1);
        new_data[1]=DataCalculation.round_d(new_data[1], 0.1);
        new_data[2]=DataCalculation.round_d(new_data[2], 0.1);
        new_data[3]=DataCalculation.round_d(new_data[3], 0.1);
    }
    
    void print(){
        for(int i=0; i<certificateData.length; i++)
            System.out.println(certificateData[i]+"\n");
    }
    
    //przekazanie danych do bezpośredniego umieszczenia na świadectwie
    public String[] calculateData(double[] data){
        _find();
        _findData(data[0], data[2], 0);
        _findData(data[1], data[3], 1);
        _corection();
        String[] solution = new String[2];
        solution[0]= "("+new_data[0]+ " ÷ "+new_data[1]+")°C";
        solution[0]= solution[0].replace(".", ",");
        solution[1]= "("+new_data[2]+ " ÷ "+new_data[3]+")%Rh";
        solution[1]= solution[1].replace(".", ",");
        return solution;
    }    
}