import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This class is for reading writing and appending to a file , Whenever there is some change in the saved alarm or a new alarm is added.
 */
public class readWriteAppend {
      private alarmSettings obj;
      private static ArrayList <alarmSettings> list;
      
      public readWriteAppend(){
      }
      
      public readWriteAppend(alarmSettings obj) {
    	  this.obj = obj;
      }
      
      public readWriteAppend(ArrayList <alarmSettings> list) {
    	  this.list = list;
      }
      
      //Appending to the file.
      public void appendToFile(alarmSettings abj) {
    	  try {
    	BufferedWriter writer = new BufferedWriter(new FileWriter("alarmfile.txt", true));
  		String output=""+abj.getHour()+","+abj.getMin()+","+abj.toRepeat()+","+abj.getSoundFile();
  		writer.append(output+"\n");
  		writer.close();
    	  }
  		catch(Exception e){
  			System.out.print("Error :"+e);
  		}
      }
      
      //Reading the file for all saved alarms
      public static ArrayList<alarmSettings> readAlarmFile() {
  		list = new ArrayList();
     	 try {
     		 File file = new File("alarmfile.txt");
     		 Scanner s = new Scanner((file));
     		 while(s.hasNextLine()) {
     			 String element[] = (s.nextLine()).split(",");
     			 alarmSettings obj = new alarmSettings(Integer.parseInt(element[0]),Integer.parseInt(element[1]),Boolean.parseBoolean(element[2]),element[3]);
               list.add(obj);
     		 }
     		 
     		 s.close();
     	 }catch(Exception e){
     		e.printStackTrace();
     	 }
     	 
     	 return list;
      }
      
      //Writing to the file all new added alarms.
      public void writeToFile() {
    	  try {
    	BufferedWriter writer = new BufferedWriter(new FileWriter("alarmfile.txt",false));
    	for(alarmSettings abj: list) {
  		 String output=""+abj.getHour()+","+abj.getMin()+","+abj.toRepeat()+","+abj.getSoundFile();
  		 writer.write(output+"\n");
    	 }
    	  writer.close();
    	}
  		catch(Exception e){
  			System.out.print("Error :"+e);
  		}
      }
}
