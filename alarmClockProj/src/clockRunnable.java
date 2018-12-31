import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JTextField;

/**
   A runnable that will repeatably prints the current time and date
*/
public class clockRunnable implements Runnable
{
   private static final int DELAY = 1000; // in milliseconds
   
   private String Time;
   private String myDate;
   
   private JTextField display;
   private JTextField displayDate;

   /**
      Constructs the runnable object.
      @param aGreeting the greeting to display
   */
    public clockRunnable(JTextField textfld,JTextField text2) {
    	display = textfld;
    	displayDate = text2;
    }

   public void run()
   {
      try
      {
         while(true)
         {
        	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
      	     LocalDateTime now = LocalDateTime.now();  
      	     Time = dtf.format(now);
      	     
      	     Date now2 = new Date();
      	     SimpleDateFormat simpleDateformat = new SimpleDateFormat("E d MMM yyyy");
      	     myDate = simpleDateformat.format(now2);
      	     display.setText(Time);
      	     displayDate.setText(myDate);
             Thread.sleep(DELAY);         
         }
      }
      catch (InterruptedException exception)
      {
      }
   }
}

