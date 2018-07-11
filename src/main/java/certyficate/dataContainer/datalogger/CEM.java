package certyficate.dataContainer.datalogger;

import certyficate.dataContainer.PointData;

public class CEM extends Logger{
    public CEM(boolean RH) {
        super(RH);
    }
    
    public int getN(){
        return 11;
    }
 
    public PointData divide(String line){
        String[] Data = line.split("\t");
        String[] when =Data[5].split("/");
        PointData d= new PointData();
      //  d.num =Integer.parseInt(Data[0]);
        
         String[] linedate = when[0].split("-");
         d.date = linedate[0]+"."+linedate[1]+".20"+linedate[2];
         
         String[] linetime = when[1].split(":");
         d.time = linetime[0]+":"+linetime[1];
         
         String[] tempData = Data[1].split(" ");
         String[] tempDatafin= tempData[3].split("\\.");
         if(tempDatafin.length>1)
             d.temp =tempDatafin[0]+","+tempDatafin[1];
         else
             d.temp =tempDatafin[0]+",0";
         
         if(Rh){
             String[] humData = Data[3].split(" ");
             String[] humDatafin = humData[3].split("\\.");
             if(humDatafin.length>1)
                 d.hum = humDatafin[0]+","+humDatafin[1];
             else
                 d.hum = humDatafin[0]+",0";
         }
         
         return d;    
    }
}
