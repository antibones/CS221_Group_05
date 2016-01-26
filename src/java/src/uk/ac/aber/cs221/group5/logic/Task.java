package uk.ac.aber.cs221.group5.logic;

import java.util.ArrayList;

public class Task {
	private String taskID;
	private String taskName;
	private String taskStart;
	private String taskEnd;
	private String taskMembers;
	
	TaskStatuses status;
	
	ArrayList<Element> taskElements;
	
	public Task(String id, String name, String start, String end, String members, TaskStatuses status){
		this.taskID    = id;
		this.taskName  = name;
		this.taskStart = start;
		this.taskEnd   = end;
		
		this.taskMembers = members;
		
		this.status = status;
		
		taskElements = new ArrayList<Element>();
	}
	
	public String getID(){
		return this.taskID;
	}
	
	public String getName(){
		return this.taskName;
	}
	
	public String getStart(){
		return this.taskStart;
	}
	
	public String getEnd(){
		return this.taskEnd;
	}
	
	public String getMembers(){
		return this.taskMembers;
	}
	
	public String getStatus(){
		return this.status.toString();
	}
	
	public void setStatus(TaskStatuses newStatus){
		this.status = newStatus;
	}
	
	public void addElement(String elementName, String elementComment){
		Element newElement = new Element(elementName, elementComment);
		taskElements.add(newElement);
	}
	
	public void clearAllElements(){
		taskElements.clear();
	}
	
	public Element getElement(int index){
		return taskElements.get(index);
	}
	
	public ArrayList<String[]> getAllElementPairs(){
		ArrayList<String[]> allPairs = new ArrayList<String[]>();
		
		for (Element index : taskElements){
			String name = index.getName();
			String comment = index.getComment();

			String[] elementPair = {name, comment};
			allPairs.add(elementPair);
		}
		
		return allPairs;
	}
	
	public int getNumElements(){
		return this.taskElements.size();
	}
	
	
	public class Element {
		String elementDesc;
		String comment;
		
		public Element(String description, String comment){
			this.elementDesc = description;
			this.comment = comment;	//If the element has no comment, this is an empty String
		}
		
		
		
		public String getName(){
			return this.elementDesc;
		}
		
		public String getComment(){
			return this.comment;
		}
		
		public void setComment(String newComment){
			this.comment = newComment;
		}
	}

	
}
