package certyficate.sheetHandlers.search;

import certyficate.property.CalibrationData;

public class StandardPoint {
	private final static int[][] TEMPERATURE_STANDARD_POINT = new int[][] {{-25},{0},{25}}; 
	private final static int[][] HUMINIDITY_STANDARD_POINT 
		 = new int[][] {{15, 50}, {25, 30}, {25, 50}, {25, 70}, {35, 50}};
	private final static int[][] INFRARED_STANDARD_POINT = new int[][] {{25},{90},{180}}; 
	
	static int toInt(String data){
		data = data.replaceAll("[^\\d.]", "");
		return Integer.parseInt(data);
	}
	
	static int[] humPoint(String data){
		int[] point = new int[2];
		data = data.replaceAll("\\[|\\]", "");
		String[] d = data.split(";");
		if(d.length!=2) return null;
		point[0]= toInt(d[0]);
		point[1]= toInt(d[1]);
		return point;
	}
	
	static int[][] sort(int[][] array){
		if(array.length==2)
			for(int i=0; i<array[0].length; i++){
				for(int j=array[0].length-1; j>i; j--){
					if(array[0][j]<array[0][j-1] && 
							(array[0][j]==array[0][j-1] || array[1][j]<array[1][j-1])){
						int t = array[0][j],
							rh = array[1][j];
						array[0][j] = array[0][j-1];
						array[1][j] = array[1][j-1];
						array[0][j-1] = t;
						array[1][j-1] = rh;
				}
			}
		}else{
			for(int i=0; i<array[0].length; i++){
				for(int j=array[0].length-1; j>i; j--){
					if(array[0][j]<array[0][j-1]){
						int t = array[0][j];
						array[0][j] = array[0][j-1];
						array[0][j-1] = t;
					}
				}
			}
		}
		return array;
	}
	
	static int[][] point(String data, int code){
		if(data.equals(""))
			return point();
		data= data.replaceAll(" ", "");
		String[] points = data.split(",");
		int[][] point;
		switch(code){
		case 1:
		case 2:
		case 5:
			point = new int[1][points.length];
			for(int i=0; i<points.length; i++){
				point[0][i]= toInt(points[i]);
			}
			break;
		case 3:
			point = new int[2][points.length];
			int j=0;
			for(int i=0; i<points.length; i++){
				int[] number = humPoint(points[i]);
				if(number==null) continue;
				point[0][j]= number[0];
				point[1][j]= number[1];
				j++;
			}
			break;
		default: return null;
		}
		point = sort(point);
		return point;	
	}

	static int[][] point(){
		int[][] point;
		switch (CalibrationData.calibrationType) {
		case HUMINIDITY:
			point = HUMINIDITY_STANDARD_POINT;
			break;
		case INFRARED:
			point = INFRARED_STANDARD_POINT;
			break;		
		default:
			point = TEMPERATURE_STANDARD_POINT;
			break;
		}
		return point;
	}
	
}
