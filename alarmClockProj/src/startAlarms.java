import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

/*
 * This is the class that includes main function of computing the time difference and making a delay in
 * staring the thread which will in future rings the bell once completed.
 */
public class startAlarms{
       private ArrayList <alarmSettings> alarmList;
       private Thread ths[] ;
       private int activatedThreadCount = 0;
       
       startAlarms(ArrayList <alarmSettings> allist){
    	   alarmList = allist;
    	   ths = new Thread[alarmList.size()];
    	   startThreads();
    	   System.out.println("Number of Threads Activated :" +activatedThreadCount);
    	   readWriteAppend rwa = new readWriteAppend(alarmList);
	       rwa.writeToFile();
       }
       
       /*
        * This method is starting the thread for an each alarm saved onto the file.
        */
       public void startThreads() {
    	   int k=0;
    	   if(alarmList.size()>=0) {
    		   for(alarmSettings obj:alarmList) {
    			   long delay = getAlarmDelay(obj);
    			   ringAlarm object = new ringAlarm(delay,alarmList,k);
    			   ths[activatedThreadCount] = new Thread(object);
    			   
    			   if(delay>=0){
    				    if(!obj.toRepeat()) {
   	    	                    ths[activatedThreadCount].start();
   	    	                    activatedThreadCount++;
    				       }
    				    else{
    					    ths[activatedThreadCount].start();
      	    	            activatedThreadCount++;
    				    }
    			   }
    			   k++;
    		   }
    	   }
       }
       
       
       public long getAlarmDelay(alarmSettings ob) {
    	   readWriteAppend abj = new readWriteAppend();
    	   alarmList = abj.readAlarmFile();
    	  
    	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    	   LocalDateTime now = LocalDateTime.now();
    	   String currDate = dtf.format(now);
    	   System.out.println(currDate);
           
    	   int currHr = Integer.parseInt(currDate.substring(0,2));
    	   int currMin = Integer.parseInt(currDate.substring(3,5));
    	   int currSec = Integer.parseInt(currDate.substring(6));
    	   
    	   long delay= computeTimeDiffInSec(ob.getHour(),ob.getMin(),currHr,currMin,currSec)*1000;
    	   
    	   if(delay<0) {
    		   return computeTimeDiffInSec(ob.getHour()+24,ob.getMin(),currHr,currMin,currSec)*1000;
    	   }
    	   else return delay;
    
       }
       
      //This method is mainly to compute the difference in millisecs of current Time and alarm set.
      //Input : alHr alarm Hour
      //        alMin alarm Min
      //        currHr current Hour
      //        currMin current Min
      //        currSec current Sec
      //This method also makes sure if the alarm is set for next day.
      public long computeTimeDiffInSec(int alHr,int alMin,int currHr,int currMin,int currSec) {
    	  long dl=0;
    	  int diffMin=0;
    	  int hrDiff=0;
    	  int secDiff=0;
    	  if(currSec>0) {
    		  alMin-=1;
    		  secDiff=60-currSec;
    		  System.out.println(secDiff);
    	  }
    	  if(currMin>alMin) {
    		  alHr-=1;
    		  alMin+=60;
    		  diffMin = alMin-currMin;
    		  System.out.println(diffMin);
    	  }
    	  else {
    		  diffMin = alMin-currMin;
    		  System.out.println(diffMin);
    	  }
    	  
    	  if(currHr>alHr) {
    		  hrDiff = alHr-currHr;
    		  System.out.println(hrDiff);

    	  }
    	  else {
    		  hrDiff = alHr-currHr;
    		  System.out.println(hrDiff);
    	  }
    	  
    	  dl = hrDiff*60*60 + diffMin*60 + secDiff;
    	  return dl;
      }

    //This method is used for stoping all the threads that are currently activated.
	public void stopAlarms() {
		for(int i=0;i<ths.length;i++) {
			ths[i].interrupt();
		}
	}
}
