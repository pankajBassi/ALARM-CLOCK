import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * This class will play the sound whenever a thread's delay is completed.
 */
public class playSound{
	   private Clip audioClip;    
	
	    // Open an audio input stream.
	   public playSound(String soundFile){
		   System.out.println(soundFile);
		   File file = new File(soundFile);
		try {
			boolean playCompleted = false;
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			  
	        AudioFormat format = audioStream.getFormat();
	 
	        DataLine.Info info = new DataLine.Info(Clip.class, format);
	 
	        audioClip = (Clip) AudioSystem.getLine(info);		 
	        audioClip.open(audioStream);
	             
	        audioClip.start();
	           
	        while (!playCompleted) {
	            try {
	            	Thread.sleep(1000);
	            } catch (InterruptedException ex) {
	                System.out.println(ex);
	            }
	        }
	             
	        audioClip.close();
		} catch (UnsupportedAudioFileException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
	    } catch (LineUnavailableException e) {
			System.out.println(e);
	   }
   }
	   
   public void stopSound() {
   }
}