import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;

/*
 * This class will get the delay and ring the alarm when delay is over.
 * It also makes sure if the alarm is not repeatable it gets deleted once it rang.
 */
public class ringAlarm implements Runnable{
	private long delay;
	private playSound obj;
	private boolean ifRinged =false;
	private ArrayList <alarmSettings> list;
	int currentObj ;

	ringAlarm(long dl,ArrayList <alarmSettings> list,int currObj){
		this.list = list;
		this.currentObj = currObj;
		delay = dl;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
	      {
	         while(true)
	         {
	             Thread.sleep(delay);
	             ringBell();
	         }
	      }
	      catch(Exception exception)
	      {
	      }
	}
	
	//Method for activating the play Sound file
	//It also deletes the non repeateble alarm from file.
	public void ringBell() {
		String soundFile = (list.get(currentObj)).getSoundFile();
		
		if(!(list.get(currentObj)).toRepeat()) {
		list.remove(currentObj);
    	readWriteAppend abj = new readWriteAppend(list);
    	abj.writeToFile();
		}
		obj = new playSound(soundFile);
		Thread.currentThread().interrupt();
	}
	
	public boolean AllRinged() {
		return ifRinged;
	}
}
