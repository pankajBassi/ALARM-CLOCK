/* Author : Pankaj Bassi And Sehaj Brar.
 * This application allows you to see current time and set and save alarms to be used again and again.
 */
import java.util.ArrayList;

public class mainClass {
	
	public static void main(String[] args) {
		// Creating an arrayList for reading all the saved alarms and their settings.
		
		ArrayList <alarmSettings> alist = new ArrayList<alarmSettings>();
		
		//Reading the file filled of saved alarms.
		readWriteAppend object = new readWriteAppend();
		alist = object.readAlarmFile();
		
		//Creating a main frame which includes display for current date,time and option to save alarm. 
		clockDisplay used = new clockDisplay(10,10,500,250,alist);	
	}	
}
