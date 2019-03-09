import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import javax.xml.crypto.Data;

public class MonitoredData {

	private String activityLabel;
	private String startTime;
	private String endTime;
	
	private LocalDateTime  dataStart ;
	private LocalDateTime  dataEnd ;
	
	public MonitoredData( String startTime, String endTime, String activityLabel){
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;
		conversieData();
	}
	
	public void conversieData(){
		DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss");			
		try {
				this.setDataStart(LocalDateTime.parse( startTime,dataFormat ));
				this.setDataEnd(LocalDateTime.parse(endTime, dataFormat));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	public double totalTimpActivitate(){
		//int totalTimpActivitateS = dataEnd.getSecond() - dataStart.getSecond();
		//int totalTimpActivitateM = dataEnd.getSecond() - dataStart.getSecond();
		//int totalTimpActivitate = dataEnd.getSecond() - dataStart.getSecond();
		double seconds = ChronoUnit.SECONDS.between(dataStart, dataEnd);
		double hours = seconds / 3600;
		return hours;
	}
	public double totalTimpActivitateMinutes(){
		//int totalTimpActivitateS = dataEnd.getSecond() - dataStart.getSecond();
		//int totalTimpActivitateM = dataEnd.getSecond() - dataStart.getSecond();
		//int totalTimpActivitate = dataEnd.getSecond() - dataStart.getSecond();
		double seconds = ChronoUnit.SECONDS.between(dataStart, dataEnd);
		double minutes = seconds / 60;
		return minutes;
	}
	
	private void setDataEnd(LocalDateTime parse) {
		this.dataEnd =  parse;

	}

	private void setDataStart(LocalDateTime parse) {
		this.dataStart =  parse;
		
	}

	public int getDayStart(){
		int zi;
		zi = dataStart.getDayOfMonth();
		System.out.println("ziua e " + zi);
		return zi;
	}
	
	
	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String toString(){
		return "timpul de start este ~~" + startTime + "~~ timpul de finish este ~~" + endTime + "~~ activitatea este ~~" + activityLabel  
				+" dataS: "+ dataStart +" dataE: "+ dataEnd +"\n";
		
	}

	public LocalDateTime getDataStart() {
		return dataStart;
	}

	

	public LocalDateTime getDataEnd() {
		return dataEnd;
	}

	
}
