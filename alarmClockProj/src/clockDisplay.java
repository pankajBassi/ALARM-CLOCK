/*
 * This is the main Frame of this application displaying all the main contents of the clock.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class clockDisplay extends JFrame implements ActionListener{

	private JLabel lbl;
	private JTextField timeLbl;
	private JButton start;
	private JLabel dayLbl;
	private JTextField dayDisp;
	private JButton stop;
	private JButton addDelAlarm;
	private ArrayList <alarmSettings> alList;
	private startAlarms activated;
	private JButton info;
	clockRunnable t1;
	Thread h1;
	
	//Constructor with the details of all bounds and a list of saved alarms.
	public clockDisplay(int x, int y, int w, int h,ArrayList <alarmSettings> alList) {
		this.setTitle("Clock By Pankaj and Sehaj");
		this.setVisible(true);
		this.setBounds(x,y,w,h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(null);
		this.alList = alList;
		
		//Method to create all GUI elements
		createGUI();
		
		//This class will start a thread for all the saved alarms so that they ring on the time set by USER.
		activated = new startAlarms(this.alList);
	}
	
	public void createGUI() {
		lbl = new JLabel("Time :");
		attach(lbl,57,50,40,25);
		
		timeLbl = new JTextField();
		attach(timeLbl,130,50,80,25);
		timeLbl.setEditable(false);
		
		dayLbl = new JLabel("Date :");
		attach(dayLbl,57,90,40,25);
		
		dayDisp = new JTextField();
		attach(dayDisp,130,90,120,25);
		dayDisp.setEditable(false);
		
		start = new JButton("START");
		attach(start,55,130,100,25);
		start.addActionListener(this);
		
		stop = new JButton("STOP");
		attach(stop,185,130,100,25);
		stop.addActionListener(this);
		
		addDelAlarm = new JButton("ADD/DEL ALARM");
		attach(addDelAlarm,320,60,140,40);
		addDelAlarm.addActionListener(this);
		
		info = new JButton("INFO");
		attach(info,400,175,60,25);
		info.addActionListener(this);
		
		//Thread for getting the current time and date.
		t1 = new clockRunnable(timeLbl,dayDisp);
		h1 = new Thread(t1);
	}
	
	   //Attaching all the GUI elements to the Frame
	public void attach(JComponent jany,int x,int y,int w,int h) {
		this.add(jany);
		jany.setBounds(x,y,w,h);
	}
	
	
    //All actions for the button pressed are performed here.
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton b = (JButton)arg0.getSource();
		
		
		if(start == arg0.getSource()) {
			start.setEnabled(false);
			stop.setEnabled(true);
			h1.start();
			
		}
		 if(stop == b) {
			 h1.interrupt();
			stop.setEnabled(false);
			start.setEnabled(true);
			h1=new Thread(t1);
		}
		 
		if(addDelAlarm== b) {
			//If add/del button is pressed a new frame will be open with all the details to save a new alarm or delete current alarm.
			alarmFrame aF = new alarmFrame(10,10,500,285,addDelAlarm,alList,activated);
		}
		
		if(info == b) {
			JOptionPane.showMessageDialog(this, "\tHOW To Use This Clock Application : \n 1.START button will start the current clock"
					+ "\n 2.STOP Button will stop the current clock."
					+ "\n 3.ADD/DEL button will allow you to add new alarms,edit saved alarms ,delete saved alarms");
		}
	}
       
}
