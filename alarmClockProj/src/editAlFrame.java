import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * This class is the main frame for editing the saved alarms it includes changing the hour, min and the ringtone.
 */
public class editAlFrame extends JFrame implements ActionListener{
	private ArrayList <alarmSettings> aList =null;
	private JButton editbut;
	private JLabel hours;
	private JLabel min;
	private JComboBox <String>hrsList;
	private JComboBox <String>minList;
	private JComboBox <String>alarmList;
	private String hrs[] = new String[24];
	private String mins[] = new String[60];
	private alarmSettings selectedItem;
	private JButton save;
	private int listInd;
	private startAlarms activeAlarms;
	private String soundFiles[] = {"Missile","Chirping Birds","Police Siren"};
	private JComboBox <String> soundList;
	private JLabel soundLbl;
	
	/*
	 * This cunstroctor includes:
	 *         x,y starting coordinates
	 *         w,h width and height
	 *         alist the list of all saved alarms
	 *         alarmList list of alarms in combobox displayed
	 *         editBut edit button on the main clock display
	 *         activeAl includes all the alarms that were activated
	 */
    public editAlFrame(int x,int y,int w,int h,ArrayList <alarmSettings> alist,JComboBox <String> alarmList,JButton editBut,startAlarms activeAl) {
    	this.setTitle("Edit Selected Alarm");
    	this.setVisible(true);
		this.setBounds(x,y,w,h);
		this.setLayout(null);
    	this.aList = alist;
    	this.editbut = editBut;
    	this.editbut.setEnabled(false);
    	this.alarmList = alarmList;
    	this.listInd = alarmList.getSelectedIndex();
    	this.activeAlarms = activeAl;
    	
    	try {
    	selectedItem = aList.get(listInd);
    	}
    	catch(Exception e) {
    		JOptionPane.showMessageDialog(this,"No more Saved Alarms to Edit");
    		editbut.setEnabled(true);
    		closeFrame();
    	}
    	
    	this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
			    editbut.setEnabled(true);
			  }
			});
    	
    	createGUI();
    }
    
    //Creating GUI
    // NO INPUT and NO OUTPUT
    public void createGUI() {
    	hours = new JLabel("Hours");
		attach(hours,30,15,80,25);
		
		for(int i=0;i<24;i++)hrs[i]=Integer.toString(i);
		for(int i=0;i<60;i++)mins[i]=Integer.toString(i);
		
		hrsList = new JComboBox<String>(hrs);
		attach(hrsList,30,45,80,25);
		
		min = new JLabel("Minutes");
		attach(min,130,15,80,25);
		
		minList = new JComboBox<String>(mins);
		attach(minList,130,45,100,25);
		
		soundLbl = new JLabel("Select RingTone");
		attach(soundLbl,20,90,150,25);
		
		soundList = new JComboBox<String>(soundFiles);
		attach(soundList,130,90,150,25);
		
		save = new JButton("Save");
		attach(save,80,130,85,25);
		save.addActionListener(this);
    }
    
    public void attach(JComponent jany,int x,int y,int w,int h) {
		this.add(jany);
		jany.setBounds(x,y,w,h);
	}

    /*
     * (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * Action Listner for all the buttons on the frame
     */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(save == arg0.getSource()) {
			alarmSettings newObj = new alarmSettings(hrsList.getSelectedIndex(),minList.getSelectedIndex(),selectedItem.toRepeat(),soundFiles[(soundList.getSelectedIndex())]+".wav");
			aList.set(listInd,newObj);
			
			String yN="";
			if(newObj.toRepeat()) {
				yN="Yes";
			}
			else yN="No";
			String dispHr = Integer.toString(newObj.getHour());
			String dispMin = Integer.toString(newObj.getMin());
			
			if(newObj.getHour()<10)dispHr = "0"+dispHr;
			if(newObj.getMin()<10)dispMin = "0"+dispMin;
			
			String sFile = newObj.getSoundFile();
			
			String result = String.format("%-2s:%2s %7s %22s",dispHr,dispMin,yN,sFile.substring(0, sFile.length()-4));
			readWriteAppend abj = new readWriteAppend(aList);
	    	abj.writeToFile();
	    	alarmList.insertItemAt(result,listInd);
	    	alarmList.removeItemAt(listInd+1);
	    	activeAlarms.stopAlarms();
	        startAlarms tco = new startAlarms(aList);
	        activeAlarms = tco;
			
			editbut.setEnabled(true);
			closeFrame();
		}
	}
	
	//Method to return the changed alarmList.
	public ArrayList <alarmSettings> getAList(){
		return aList;
	}
	
	//Closing the frame as user presses save button.
	public void closeFrame() {
		this.dispose();
	}
}
