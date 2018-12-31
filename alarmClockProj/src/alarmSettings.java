/*
 * This class is mainly used to store the details of the alarm to a file.
 */


public class alarmSettings {
     private int hour;
     private int min;
     private boolean repeat;
     private String soundFile;
     
     alarmSettings(int h ,int m,boolean repeat,String soundFile){
    	 hour = h;
    	 min  = m;
    	 this.repeat = repeat;
    	 this.soundFile = soundFile;
     }
    
     //Empty constructor to invoke this class on special occasion if there is no need to add any details.
     alarmSettings(){
     }
     
     public int getMin() {
    	 return min;
     }
     
     public int getHour() {
    	 return hour;
     }
     
     public boolean toRepeat() {
    	 return repeat;
     }
     
     public String getSoundFile() {
    	 return soundFile;
     }
}
