/*
 *  Frame for adding deleting and editing currently stored alarms.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class alarmFrame extends JFrame implements ActionListener{
	private JLabel hours; //Label showing hours in front of hour comboBox
	private JLabel min;  
	private JComboBox <String> hrsList;//This is the list of 0 to 23 hours displayed in the comboBox
	private JComboBox <String> minList;//This is the list of 0 to 59 mins displayed in the combBox
	private JComboBox <String> soundList;
	private JButton addBtn;
	private JCheckBox repeat;
	private JLabel alarmW;
	private JComboBox <String> alarmList;
	private JButton editAl;
	private JButton delAl;
	private JLabel timeLbl;
	private JLabel repeatLbl;
	private JButton info;
	private JLabel selAl;
	private JLabel sound;
	
	private startAlarms activatedAl;
	private String hrs[] = new String[24];
	private String mins[] = new String[60];
	private String soundFiles[] = {"Missile","Chirping Birds","Police Siren"};
	private boolean toRepeat = false;
	private String realList[];
	int realLsCount=0;
	
	ArrayList <alarmSettings> list = new ArrayList<alarmSettings>();
	
	/*
	 * Input Includes : x,y starting coordinates
	 *                  w,h width and height of the frame
	 *                  mainbtn this is the ADD/DEL button reference from the clockDisplay frame.
	 *                  list it stores all the alarms previously stored.
	 *                  activated includes the list of all activated threads for the alarms.
	 */
	public alarmFrame(int x, int y, int w, int h,JButton mainbtn,ArrayList <alarmSettings> list,startAlarms activated) {
		this.setTitle("Set Alarms");
		this.setVisible(true);
		this.setBounds(x,y,w,h);
		this.setLayout(null);
		//This disables the ADD/DEL button so that user might not be able to use the button again and open multiple frames for same thing.
		mainbtn.setEnabled(false);
		
		//This make the ADD/DEL button to be back on once user exits this window.
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
			    mainbtn.setEnabled(true);
			  }
			});
		
		this.list=list;
		
		//Getting the object of the class which have currently activated alarms.
		this.activatedAl=activated;
		
		//Here we stop all the activated alarms and activate new ones with new added or edited alarms.
		activatedAl.stopAlarms();
        startAlarms tco = new startAlarms(this.list);
        activatedAl = tco;
		
		createGUI();
	}
	
	//This method creates the GUI
	//No Input
	public void createGUI() {	
		realList = new String[list.size()];
		
		 int k=0;
		 String result;
		 String repeatS;
		 
		 //Storing all the list of alarms into a combobox.
		    for(alarmSettings abj: list) {
		    	if(abj.toRepeat())repeatS="Yes";
		    	else repeatS="No";
		    	String dispHr = Integer.toString(abj.getHour());
				String dispMin = Integer.toString(abj.getMin());
				
				if(abj.getHour()<10)dispHr = "0"+dispHr;
				if(abj.getMin()<10)dispMin = "0"+dispMin;
				String sFile = abj.getSoundFile();
				
		    	result = String.format("%-2s:%2s %7s %22s",dispHr,dispMin,repeatS,sFile.substring(0, sFile.length()-4));
		    	realList[k]=result;
		    	k++;
		    }
		
		hours = new JLabel("Hours");
		attach(hours,30,25,80,25);
		
		for(int i=0;i<24;i++)hrs[i]=Integer.toString(i);
		for(int i=0;i<60;i++)mins[i]=Integer.toString(i);
		
		hrsList = new JComboBox<String>(hrs);
		attach(hrsList,30,55,80,25);
		
		min = new JLabel("Minutes");
		attach(min,130,25,80,25);
				
		minList = new JComboBox<String>(mins);
		attach(minList,130,55,100,25);
		
		soundList = new JComboBox<String>(soundFiles);
		attach(soundList,130,90,150,25);

		
		addBtn = new JButton("ADD ALARM");
		attach(addBtn,350,55,110,25);
		addBtn.addActionListener(this);
		
	    repeat = new JCheckBox("Repeat");
	    attach(repeat,250,55,100,25);
	    repeat.addActionListener(this);
	    
	    alarmW = new JLabel("Select Alarms:");
		attach(alarmW,30,130,90,25);
		
		editAl = new JButton("EDIT");
		attach(editAl,260,170,80,25);
		editAl.addActionListener(this);
		
		delAl = new JButton("DELETE");
		attach(delAl,350,170,80,25);
		delAl.addActionListener(this);
		
		info = new JButton("INFO");
		attach(info,400,210,80,25);
		info.addActionListener(this);
		
		selAl = new JLabel("Select RingTone");
		attach(selAl,30,90,150,25);
		
		timeLbl = new JLabel("Time");
		attach(timeLbl,30,150,55,25);
		
		repeatLbl = new JLabel("Repeat");
		attach(repeatLbl,75,150,90,25);
		
		sound = new JLabel("Sound");
		attach(sound,150,150,90,25);
		
		alarmList = new JComboBox<String>(realList);
		attach(alarmList,30,170,200,25);
		
	}
	
	//This method is common to all GUI components as it adds all the elements onto the Frame.
	public void attach(JComponent jany,int x,int y,int w,int h) {
		this.add(jany);
		jany.setBounds(x,y,w,h);
	}

	//Here the action for all the events performed by user are Listned by the cmoputer.
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	    JComponent src = (JComponent) arg0.getSource();
	    if(src == addBtn) {
	    	int i = hrsList.getSelectedIndex();
	    	int j = minList.getSelectedIndex();
	    	int k = soundList.getSelectedIndex();
	        alarmSettings obj = new alarmSettings(Integer.parseInt(hrs[i]),Integer.parseInt(mins[j]),toRepeat,soundFiles[k]+".wav");
	        list.add(obj);
	        
	        //Write to file will write new added alarm to file and to JComboBox of alarmList.
	        writeToFile(obj);
	        
	        activatedAl.stopAlarms();
	        startAlarms tco = new startAlarms(list);
	        activatedAl = tco;
	    }
	    
	    if(src == editAl) {
	    	
	    	//This will activate the frame for editing the selected alarm. 
	    	editAlFrame obj = new editAlFrame(30,30,300,200,list,alarmList,editAl,activatedAl);
	    	obj.setEnabled(true);
	    	
	    	//this will get a new list from the edit frame class.
	    	list = obj.getAList();
	    }
	    
	    if(src == delAl) {
	    	int j = alarmList.getSelectedIndex();
	    	deleteAlarm(j);
	    }
	    
	    if(src == repeat) {
	    	if(repeat.isSelected())toRepeat=true;
	    	else toRepeat=false;
	    }
	    
	    if(src == info) {
	    	//This is the info session if user does not understand how to use this Application
	    	JOptionPane.showMessageDialog(this, "\tHOW To Use ADD,DELETE AND EDIT function : \n 1.For Adding new Alarms"
					+ "\n \t A.Select hour from the hrs list and min from the min List"
	    			+ "\n \t B.Select the to repeat option for repeating alarm everyday"
	    			+ "\n \t B.Press ADD button"
					+ "\n 2.DELETE a alarm"
					+ "\n \t A.Select a alarm to be deleted from alarm List"
					+ "\n \t B.Press DELETE button"
					+ "\n 3.Editing a saved alarm"
					+ "\n \t A.Select a alarm from alarm List"
					+ "\n \t B.Press EDIT button"
					+ "\n \t C.Follow the same procedure in edit frame as in adding alarm."
					+ "\n \t D.Press Save button"
					+ "\n"
					+ "\n"
					+ "!!! CAUTION:If you dont select toRepeat option alarms will be"
					+ "\n automatically deleted once they ranged in this application.");
	    }
	    
	}
	
	//Input : obj includes the settings of new alarm added.
	//        Ouput : No output as it is a void function.
	public void writeToFile(alarmSettings obj){
		readWriteAppend newObj = new readWriteAppend(obj);
		
		//Adding a new entry to the file.
		newObj.appendToFile(obj);
		String yN="";
		if(obj.toRepeat()) {
			yN="Yes";
		}
		else yN="No";
		String dispHr = Integer.toString(obj.getHour());
		String dispMin = Integer.toString(obj.getMin());
		
		if(obj.getHour()<10)dispHr = "0"+dispHr;
		if(obj.getMin()<10)dispMin = "0"+dispMin;
		String sFile = obj.getSoundFile();
		
		String result = String.format("%-2s:%2s %7s %22s",dispHr,dispMin,yN,sFile.substring(0, sFile.length()-4));
		alarmList.addItem(result);
	}
	
	//Deleting the selected alarm from arrayList,file and ComboList.
	public void deleteAlarm(int j) {
		try {
    	list.remove(j);
    	alarmList.removeItemAt(j);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(this,"No more saved Alarms");
		}
    	readWriteAppend abj = new readWriteAppend(list);
    	abj.writeToFile();
    	activatedAl.stopAlarms();
    	startAlarms tco = new startAlarms(list);
    	activatedAl = tco;
	}
}
