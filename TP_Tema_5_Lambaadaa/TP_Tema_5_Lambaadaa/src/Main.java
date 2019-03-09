import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		String [] startData = new String [300] ;
		String [] endData = new String [300] ;
		String [] activityData = new String [300] ;

		//MonitoredData monitoredData1 = new MonitoredData("1", "2", "3");
		//System.out.println(monitoredData1.toString());
		
		ProcesareFisier procesareFisier = new ProcesareFisier();
		
		ArrayList<MonitoredData> monitoredData = new ArrayList <MonitoredData> ();
		
		monitoredData = procesareFisier.getMonitoredData();
		
		//procesareFisier.printareListaActivitati();
		
		startData = procesareFisier.getStartDateVector();
		endData = procesareFisier.getEndDateVector();
		activityData = procesareFisier.getActivitateVector();

	
	   // System.out.println(Arrays.toString(startData)+ "\n");
	    //System.out.println(Arrays.toString(endData) + "\n");
	    //System.out.println(Arrays.toString(activityData) + "\n");
	    
	    //monitoredData.get(1).getDayStart();
	    procesareFisier.numaraZile();
	    procesareFisier.numaraActivitati();
	    procesareFisier.numaraActivitatiPeZile();
	    procesareFisier.activitateCuDurataMaxima();
	    procesareFisier.scrieFrecventaZile();
		//System.out.println(procesareFisier.monitoredData.toString());


	}

}
