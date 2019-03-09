//import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ProcesareFisier {
	
	ArrayList<MonitoredData> monitoredData;
	private String fileName = "Activities.txt";
	private String[] startDate;
	private String[] startDateVector = new String [300];
	private String[] endDate ;
	private String[] endDateVector = new String [300];
	private String[] activitate ;
	private String[] activitateVector = new String [300];
	MonitoredData monDataObj;
	private static int count = -1;
	// private PrintWriter fisierScriere;
	private Map <String, Integer> activitatiNrAparitiiMap;
	private Map<Integer, Map<String, Long>> numaraActivitatiPeZile; 
	private List <String> numaraActivitati;
	// privateMap<String, Map<String, Long>> activitatiCuDurataMaxima;
	private Map<String,Double> activitatiCuDurataMaxima;
	private List<String> activitatiScurte;
	public ProcesareFisier(){
		
		monitoredData = new ArrayList <MonitoredData> ();
		try (Stream <String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach ( iterator-> { count++;
										 // System.out.println(count);
										  
										  startDate  = iterator.substring(0, 20).split("	");
										  startDateVector[count] =  startDate[0];
										  
								 		  endDate    = iterator.substring(21, 41).split("	");
								 		  endDateVector[count] = endDate[0];
								 		  
								 		  activitate = iterator.substring(42).split("[\\r\\n]+");
								 		  activitateVector[count] = activitate[0];
								 		  
								 		  monDataObj = new MonitoredData( startDate[0], endDate[0], activitate[0]);
								 		  monitoredData.add(monDataObj);
								      }
						 );

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void numaraZile(){
		List <Integer> daysCounter = monitoredData
				.stream()
				.map(monitoredDataAux -> monitoredDataAux.getDayStart())
				.distinct()
				.collect(Collectors.toList());
		long counter = daysCounter.stream().count();
		
		try{
		    PrintWriter fisierScriere = new PrintWriter("Raspuns1.txt");
		    fisierScriere.println("Numarul de zile distincte este: "+ counter);
		    fisierScriere.close();
		} 
		catch (IOException e) {
		   System.out.println("Eroare fisier 1");
		}
		finally {
			System.out.println("Count the distinct days that appear in the monitoring data.");
			System.out.println("-----------------------TASK 1---------------------------------------------------");
		}
	}
	
	
	public void numaraActivitati(){
		numaraActivitati = monitoredData
				.stream()
				.map(monitoredDataAux -> monitoredDataAux.getActivityLabel())
				.distinct()
				.collect(Collectors.toList());
		
		activitatiNrAparitiiMap = new TreeMap<>();
		
		numaraActivitati.stream()
		.forEach(aux -> activitatiNrAparitiiMap.put( aux, (int) monitoredData.stream()
																		  .filter(monitoredDataAux -> monitoredDataAux.getActivityLabel().equals(aux))
																		  .count()
											    )
				);
		
		try{
		    PrintWriter fisierScriere = new PrintWriter("Raspuns2.txt");
		    for(String iterator: activitatiNrAparitiiMap.keySet())
		    {
		    	fisierScriere.println(iterator + ":"  + activitatiNrAparitiiMap.get(iterator));
	    	}
		    fisierScriere.close();
		} 
		catch (IOException e) {
		   System.out.println("Eroare fisier 2");
		}
		finally {
			System.out.println("Determine a map of type <String, Integer> that maps to each distinct action type the number of occurrences in the log. Write the resulting map into a text file");

			System.out.println("-----------------------TASK 2---------------------------------------------------");
		}
	}
	
	
	public void numaraActivitatiPeZile(){
	
		numaraActivitatiPeZile = monitoredData
			   .stream()
			   .collect(Collectors.groupingBy(MonitoredData :: getDayStart, Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()))
					   );
		String cerinta ="Generates a data structure of type Map<Integer, Map<String, Integer>> that contains the activity count for each day of the log (task number 2 applied for each day of the log) and writes the result in a text file." ;
		scrieInFisier("Raspuns3.txt", "Eroare fisier 3", "TASK 3", cerinta,  numaraActivitatiPeZile);
	
	}
	
	
	public void activitateCuDurataMaxima() {
		 
		activitatiCuDurataMaxima = monitoredData
						.stream()
		                .collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.reducing((double)0, MonitoredData :: totalTimpActivitate, Double :: sum)))
		                .entrySet()
		                .stream()
		                .filter(monitoredDataAux -> monitoredDataAux.getValue() >= 10)
		                .collect(Collectors.toMap(Map.Entry :: getKey, Map.Entry :: getValue));
		String cerinta = "Determine a data structure of the form Map<String, DateTime> that maps for each activity the total duration computed over the monitoring period. Filter the activities with total duration larger than 10 hours. Write the result in a text file";
		scrieInFisier("Raspuns4.txt", "Eroare fisier 4", "TASK 4",cerinta, activitatiCuDurataMaxima);
		
	}
	
	
	public static double procentPerActivitate(String activity, ProcesareFisier procFisier){
		double maiMicDe5, maiMareDe5;
			maiMicDe5 = procFisier
										.monitoredData
										.stream()
										.filter(monitoredDataAux-> monitoredDataAux.getActivityLabel().equals(activity) && monitoredDataAux.totalTimpActivitateMinutes()<5)
										.count();
			maiMareDe5 = procFisier
										.monitoredData
										.stream()
										.filter(monitoredDataAux-> monitoredDataAux.getActivityLabel().equals(activity) && monitoredDataAux.totalTimpActivitateMinutes()>5)
										.count();
			
		double rezultat = maiMicDe5 * 100 / (maiMicDe5 + maiMareDe5);
		
		return rezultat;
	}
	  
	  
	  public void scrieFrecventaZile(){
		
				activitatiScurte = monitoredData.stream()
																.filter(monitoredDataAux-> ProcesareFisier.procentPerActivitate(monitoredDataAux.getActivityLabel(),this) > 90)
																.map(monitoredDataAux-> monitoredDataAux.getActivityLabel())
																.distinct()
																.collect(Collectors.toList());
	        try{
	    		PrintWriter fisierScriere = new PrintWriter("Raspuns5.txt");
	    		
	    		 for(String x : activitatiScurte)
	    		    {
	    			// System.out.println("Ziua" + iterator + ": " + durationActivities.get(iterator));
	    			 fisierScriere.println(x );
	    	    	}
	    		 fisierScriere.close();

	    	}
	    	catch (IOException e) 
	    	{
	    		System.out.println("Eroare fisier 5");
	    	}
	    	finally 
	    	{
	    		System.out.println("Filter the activities that have 90% of the monitoring samples with duration less than 5 minutes, collect the results in a List<String> containing only the distinct activity names and write the result in a text file.");
	    		System.out.println("-----------------------TASK 5---------------------------------------------------");
	    	}
	    }
	
	  public void scrieInFisier(String numeFisier, String eroare, String task, String cerinta, Object lista){
		     try{
		    		PrintWriter fisierScriere = new PrintWriter(numeFisier);
		    		 for(Object iterator: ((Map<?, ?>) lista).keySet())
		    		    {
		    			// System.out.println("Ziua" + iterator + ": " + durationActivities.get(iterator));
		    			 fisierScriere.println(iterator + ": " + ((Map<?, ?>) lista).get(iterator));
		    	    	}
		    		 fisierScriere.close();

		    	}
		    	catch (IOException e) 
		    	{
		    		System.out.println(eroare);
		    	}
		    	finally 
		    	{
		    		System.out.println(cerinta);
		    		System.out.println("-----------------------"+task+"---------------------------------------------------");
		    	}
	    }
	  
	public void printareListaActivitati(){
		for (Iterator<MonitoredData> iterator = monitoredData.iterator(); iterator.hasNext();) {
			MonitoredData x = iterator.next();
			System.out.println(x.toString());
		}
	}

	
	public ArrayList<MonitoredData> getMonitoredData() {
		return monitoredData;
	}

	public void setMonitoredData(ArrayList<MonitoredData> monitoredData) {
		this.monitoredData = monitoredData;
	}

	public MonitoredData getMonDataObj() {
		return monDataObj;
	}

	public void setMonDataObj(MonitoredData monDataObj) {
		this.monDataObj = monDataObj;
	}

	public String[] getStartDateVector() {
		return startDateVector;
	}

	public void setStartDateVector(String[] startDateVector) {
		this.startDateVector = startDateVector;
	}

	public String[] getEndDateVector() {
		return endDateVector;
	}

	public void setEndDateVector(String[] endDateVector) {
		this.endDateVector = endDateVector;
	}

	public String[] getActivitateVector() {
		return activitateVector;
	}

	public void setActivitateVector(String[] activitateVector) {
		this.activitateVector = activitateVector;
	}

}
