package certyficate.dataContainer.datalogger;

public class Onset extends Logger{
    public Onset(boolean RH) {
        super(RH);
    }
    
    public int getN(){
        return 3;
    }
    
    public Logger divide(String line){
        String[] Data = line.split(";");
        String[] when =Data[1].split(" ");
        
        Logger d= new Logger(RH);
        d.deviceNumber =Integer.parseInt(Data[0]);
        
        String[] linedate = when[0].split("\\.");
        
        d.date = linedate[1]+"."+linedate[0]+"."+linedate[2];
        
        d.temp = Data[2];
        
        if(RH) d.hum = Data[3];
        
        String[] linetime = when[1].split(":");
        d.time = linetime[0]+":"+linetime[1];
        return d;
    }
}
