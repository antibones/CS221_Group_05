/**
 * @author David (daf5)
 * This class implements methods required by the login GUI
 */

package uk.ac.aber.cs221.group5.gui;

import uk.ac.aber.cs221.group5.logic.MemberList;

/**
 * @author David
 * Provides a wrapper for common window functions
 * such as creating and destroying the login window
 * 
 */
public class LoginWindow extends WindowCommon{

	private LoginWindowGUI childWindow;
	private MemberList memberList = new MemberList();
	
	public LoginWindow() {
		//Initiate common window functions
		super();
	}
	
	public void passMemberList(MemberList recievingList){
		for(int memberCount = 0; memberCount < recievingList.getLength(); memberCount++){
			memberList.addMember(recievingList.getMember(memberCount));
		}
	}
		
	/**
	 * This method creates a new login window
	 * and sets it to visible
	 */
	@Override
	public void createWindow() {
		//Create new childWindow for super to work on
		childWindow = new LoginWindowGUI();
		//Ask parent to setup window for us and pass
		//this class's methods for it to work on
		childWindow.passMemberList(memberList);
		
		setupWindowLaunch(this);


	}	
	@Override
	public void callWindowLaunch() throws Exception {
		childWindow.launchWindow();
	}


	@Override
	public void destroyWindow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitleText(String newTitleText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayError(String errorText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayWarning(String warnText) {
		// TODO Auto-generated method stub
		
	}

	public void logIn(String userName) {
		// TODO Auto-generated method stub
		//This should initiate DB login method
	}

	public void openConnSettings() {
		// TODO Auto-generated method stub
		//This should open ConnSettings Window 
	}
	
	


	



}
