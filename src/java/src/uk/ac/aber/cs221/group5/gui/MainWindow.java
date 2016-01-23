/**
 * 
 */
package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.aber.cs221.group5.logic.MemberList;
import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.TaskList;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;

import uk.ac.aber.cs221.group5.logic.DbStatus;
import uk.ac.aber.cs221.group5.logic.MemberList;
import uk.ac.aber.cs221.group5.logic.TaskList;


/**
 * @author David (daf5)
 * Provides a wrapper for common window functions
 * such as creating and destroying the main window
 * 
 */
public class MainWindow extends WindowCommon {
	
	private MainWindowGUI childWindow;
	

	private TaskList taskList = new TaskList();
	private MemberList memberList = new MemberList();
	
	public static void main(String args[]) throws InterruptedException, NumberFormatException, IOException{
		TaskList taskList = new TaskList();
		MemberList memberList = new MemberList();
		
		memberList.loadMembers("memberSaveFile.txt");
		
		MainWindow mainWindow = new MainWindow();
		if(!mainWindow.doesGUIExist()){
			mainWindow.createWindow();
		}
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.passMemberList(memberList);
		loginWindow.createWindow();	
	}
	
	private boolean doesGUIExist(){
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Main Window")){
				return true;
			}
		}
		return false;
	}
		
	public void updateUsers(MemberList newUserList){
		//TODO implement updateUsers
		
		//Here for initial development 
		System.out.println("Update users called");
	}
	
	public void updateTasks(TaskList newTaskList){
		//TODO implement updateTasks
		
		//Here for initial development
		System.out.println("Update tasks called");
	}
	
	public void setConnStatus(DbStatus connStatus){
		//TODO implement setConnStatus
	}
	
	//GUI Methods Below
	

	public MainWindow(){
		//Setup common window features
		super();
	}
	
	public void createWindow(){
		
		//Get a new child window for super class
		childWindow = new MainWindowGUI();
		
		//Load the tasks into the Task List
		try {
			loadTasks("taskSaveFile.txt");
		} catch (IOException e1) {
			System.out.println("Failed to load Task File");
			//At this point, need to re-configure task file
			e1.printStackTrace();
		}
		
		//Load the members into the Member List
		try {
			memberList.loadMembers("memberSaveFile.txt");
		} catch (NumberFormatException | IOException e) {
			System.out.println("Failed to load member save file");
			e.printStackTrace();
		}
			
		//Ask parent to setup window for us and pass
		//this class's methods for it to work on
		setupWindowLaunch(this);
	}
	

	public void callWindowLaunch(){
		childWindow.launchWindow();		
	}

	@Override
	public void destroyWindow() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#setTitleText(java.lang.String)
	 */
	@Override
	public void setTitleText(String newTitleText) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String errorText) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#displayWarning(java.lang.String)
	 */
	@Override
	public void displayWarning(String warnText) {
		// TODO Auto-generated method stub

	}
	
	public void loadTasks(String filename) throws IOException{
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		int numOfTasks = 0;
		String taskID           = null;
		ArrayList<String[]> elements = new ArrayList<String[]>();
		String taskName         = null;
		TaskStatuses taskStatus = null;
		String assigned         = null;
		String startDate        = null;
		String endDate          = null;
		
		//First read in the number of tasks
		numOfTasks = Integer.parseInt(read.readLine());
		//Load data and create Task objects
		for(int loopCount = 0; loopCount < numOfTasks; loopCount++){
			taskID     = read.readLine();
			//Skip over elements in this method - they are loaded when the user wants to view them
			read.readLine();
			taskName   = read.readLine();
			taskStatus = TaskStatuses.valueOf(TaskStatuses.class, read.readLine());
			assigned   = read.readLine();
			startDate  = read.readLine();
			endDate    = read.readLine();
			Task task = new Task(taskID, taskName, startDate, endDate, assigned, taskStatus);
			taskList.addTask(task);
		}
		childWindow.populateTable(taskList);
	}
	
	public ArrayList<String[]> getElements(String filename, int tableIndex) throws IOException{
		ArrayList<String[]> elements = new ArrayList<String[]>();
		int elementLine = (7 * tableIndex) + 1;	//Finds the line in the file where the element(s) for the selected task
												// are located
		FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);
		String taskElements = new String();
		String[] elementPair = {"", ""};	//A single element name and comment pair
		
		//Skip over the lines in the file that we don't need
		for(int lineCount = 0; lineCount <= elementLine; lineCount++){
			reader.readLine();
		}
		taskElements = reader.readLine();
		reader.close();
		fileReader.close();
		
		elementPair = seperateElement(taskElements);
		if(elementPair != null){
			while(elementPair != null){
				elements.add(elementPair);
				taskElements = removePair(taskElements);
				elementPair = seperateElement(taskElements);
			}
			
		}
		else{
			String[] noElements = {"No Elements", "No Comments"};
			elements.add(noElements);
			return elements;
		}
				
		return elements;
	}
	
	private String[] seperateElement(String fileLine){
		String elementName    = new String();
		String elementComment = new String();
		String[] elementPair = {"", ""};
		int split;	//The index of the character that seperates the element name and the element comment
		int elementEnd;	//The index of the character that indicates the end of the element in the file
		
		//True if there are no elements for the Task
		if(fileLine.indexOf(',') == 0){
			return null;
		}
		
		split = fileLine.indexOf(',');
		elementEnd = fileLine.indexOf('|');
		elementName = fileLine.substring(0, split);
		elementPair[0] = elementName;
		elementPair[1] = fileLine.substring(split+1, elementEnd);	//split+1 so the seperator is not included in the comment
		
		return elementPair;
	
	}
	
	//Once a pair is loaded from the file, it is removed from the line so the next to be loaded always starts 
	// at position 0
	private String removePair(String fileLine){
		char[] fileLineChar;
		fileLineChar = fileLine.toCharArray();
		
		//This evaluates True if there is only one element left in the line
		if(fileLine.indexOf('|') == fileLine.length()-1){
			//Signifies there are no more elements. Stops the seperateElement method from trying to seperate an element
			// that does not exist
			fileLine = ",|";
		}
		else{
			for(int charCount = 0; charCount <= fileLine.indexOf('|'); charCount++){
				fileLineChar[charCount] = ' ';
			}
			fileLine = fileLineChar.toString();
			fileLine.trim();
		}
		
		return fileLine;
	}
}
