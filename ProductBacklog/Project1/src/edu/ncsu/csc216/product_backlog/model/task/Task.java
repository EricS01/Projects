/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.task;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * The class handles information relating to the tasks
 * that are part of a product.
 * @author Eric Samuel
 */
public class Task {

	/**
	 * Interface for states in the Task State Pattern.  All 
	 * concrete task states must implement the TaskState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface TaskState {

		


		/**
		 * Update the Task based on the given Command
		 * An UnsupportedOperationException is thrown if the Command is not a
		 * is not a valid action for the given state.  
		 * @param c Command describing the action that will update the Task
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command c);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();
	
	}
	
	
	/**
	 * Represents the backlog task state
	 * @author Eric Samuel
	 */
	public class BacklogState implements TaskState {
		
		private BacklogState(){
			
		}
		/**
		 * Updates the state of the task
		 * @throws UnsupportedOperationException if command is not appropriate for current state
		 */
		@Override
		public void updateState(Command c) {
	
			
			
			if(c.getCommand() == CommandValue.CLAIM) {
				
				
				currentState = ownedState;
				addNoteToList(c.getNoteText());
				
				
			}
			else if (c.getCommand() == CommandValue.REJECT) {
				
				currentState = rejectedState;
				addNoteToList(c.getNoteText());
				
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
		}
		
		/**
		 * Returns the stateName
		 */
		@Override
		public String getStateName() {
			
			return currentState.getStateName();
			//return BACKLOG_NAME;
		}

	}
	
	/**
	 * Represents the done task state
	 * @author Eric Samuel
	 */
	public class DoneState implements TaskState {
		
		private DoneState() {
			
		}

		/**
		 * Updates the state of the task
		 * @throws UnsupportedOperationException if command is not appropriate for current state
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.PROCESS) {
				isVerified = false;
				currentState = processingState;
				addNoteToList(c.getNoteText());
				
			}
			else if(c.getCommand() == CommandValue.BACKLOG) {
				isVerified = false;
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				owner = UNOWNED;
				
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}
		
		/**
		 * Returns the stateName
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return currentState.getStateName();
			//return DONE_NAME;
		}
	}
	
	/**
	 * Represents the owned task state
	 * @author Eric Samuel
	 */
	public class OwnedState implements TaskState {
		
		private OwnedState() {
			
		}

		/**
		 * Updates the state of the task
		 * @throws UnsupportedOperationException if command is not appropriate for current state
		 */
		@Override
		public void updateState(Command c) {
	
			
			if(c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				owner = UNOWNED;
				
			}
			else if (c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				addNoteToList(c.getNoteText());
				
			}
			else if (c.getCommand() == CommandValue.REJECT) {
				currentState = rejectedState;
				addNoteToList(c.getNoteText());
				owner = UNOWNED;
				
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}
		
		/**
		 * Returns the stateName
		 */
		@Override
		public String getStateName() {
			
			return currentState.getStateName();
			//return OWNED_NAME;
		}

	}
	
	/**
	 * Represents the processing task state
	 * @author Eric Samuel
	 */
	public class ProcessingState implements TaskState {

		private ProcessingState() {
			
		}
		
		/**
		 * Updates the state of the task
		 * @throws UnsupportedOperationException if command is not appropriate for current state
		 */
		@Override
		public void updateState(Command c) {
			
			if(c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				addNoteToList(c.getNoteText());
				owner = UNOWNED;
				
			}
			else if (c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				addNoteToList(c.getNoteText());
				
			}
			else if ((type == Type.BUG || type == Type.FEATURE || type == Type.TECHNICAL_WORK) && c.getCommand() == CommandValue.VERIFY) {
				currentState = verifyingState;
				addNoteToList(c.getNoteText());
				
			}
			else if (type == Type.KNOWLEDGE_ACQUISITION  && c.getCommand() == CommandValue.COMPLETE) {
				currentState = doneState;
				addNoteToList(c.getNoteText());
				
			}
			else if (type != Type.KNOWLEDGE_ACQUISITION && c.getCommand() == CommandValue.COMPLETE && isVerified) {
				currentState = doneState;
				addNoteToList(c.getNoteText());
				
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}
		
		/**
		 * Returns the stateName
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return currentState.getStateName();
			//return PROCESSING_NAME;
		}

	}
	
	/**
	 * Represents the rejected task state
	 * @author Eric Samuel
	 */
	public class RejectedState implements TaskState {
		
		private RejectedState() {
			
		}

		/**
		 * Updates the state of the task
		 * @throws UnsupportedOperationException if command is not appropriate for current state
		 */
		@Override
		public void updateState(Command c) {
			if (c.getCommand() == CommandValue.BACKLOG) {
				currentState = backlogState;
				addNoteToList(c.getNoteText());
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}
		
		/**
		 * Returns the stateName
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return currentState.getStateName();
			//return REJECTED_NAME;
		}

	}
	
	/**
	 * Represents the verifying task state
	 * @author Eric Samuel
	 */
	public class VerifyingState implements TaskState {

		private VerifyingState() {
			
		}
		
		/**
		 * Updates the state of the task
		 * @throws UnsupportedOperationException if command is not appropriate for current state
		 */
		@Override
		public void updateState(Command c) {
			
			if (c.getCommand() == CommandValue.PROCESS) {
				currentState = processingState;
				addNoteToList(c.getNoteText());
				
			}
			else if (c.getCommand() == CommandValue.COMPLETE) {
				isVerified = true;
				currentState = doneState;
				addNoteToList(c.getNoteText());
				
			}
			else {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			
		}
		
		/**
		 * Returns the stateName
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return currentState.getStateName();
			//return REJECTED_NAME;
		}

	}
	
	/**
	 * this enum represents the type of task
	 * @author user
	 *
	 */
	public enum Type {
		
		/** the feature, bug, technical work, and knowledge acquisition task type */
		FEATURE, BUG, TECHNICAL_WORK, KNOWLEDGE_ACQUISITION;
		
		
	}





	
	/** task's id */
	private int taskId;
	
	/** title of the task*/
	private String title;
	
	/** creator of the task*/
	private String creator;
	
	/** owner of the task*/
	private String owner;
	
	/** if the task is verified*/
	private boolean isVerified;
	
	/** if the task is verified*/
	private ArrayList<String> notes;
	
	/** the name for Backlog */
	public static final String BACKLOG_NAME = "Backlog";
	
	/** the name for Owned */
	public static final String OWNED_NAME = "Owned";
	
	/** the name for Processing */
	public static final String PROCESSING_NAME = "Processing";
	
	/** the name for Verifying */
	public static final String VERIFYING_NAME = "Verifying";
	
	/** the name for Done */
	public static final String DONE_NAME = "Done";
	
	/** the name for Rejected */
	public static final String REJECTED_NAME = "Rejected";
	
	/** the name for Feature */
	public static final String FEATURE_NAME = "Feature";
	
	/** the name for Bug */
	public static final String BUG_NAME = "Bug";
	
	/** the name for Technical work */
	public static final String TECHNICAL_WORK_NAME = "Technical Work";
	
	/** the name for Knowledge acquisition */
	public static final String KNOWLEDGE_ACQUISITION_NAME = "Knowledge Acquisition";
	
	/** abbreivation for feature*/
	public static final String T_FEATURE = "F";
	
	/** abbreivation for bug*/
	public static final String T_BUG = "B";
	
	/** abbreivation for technical work*/
	public static final String T_TECHNICAL_WORK = "TW";
	
	/** abbreivation for knowledge acquisition*/
	public static final String T_KNOWLEDGE_ACQUISITION = "KA";
	
	/** variable for unowned string*/
	public static final String UNOWNED = "unowned";
	
	/** the current taskState */
	private TaskState currentState;
	
	/** the backlog taskState */
	private TaskState backlogState = new BacklogState();
	
	/** the processing taskState */
	private TaskState processingState = new ProcessingState();
	
	/** the rejected taskState */
	private TaskState rejectedState = new RejectedState();
	
	/** the done taskState */
	private TaskState doneState = new DoneState();
	
	/** the owned taskState */
	private TaskState ownedState = new OwnedState();
	
	/** the verifying taskState */
	private TaskState verifyingState = new VerifyingState();
	
	/** type instance variable */
	private Type type;
	
	

	
	
	
	/**
	 * Construct a task object with values for the given fields
	 * @param taskId is the taskId for the task object
	 * @param title is the title for the task object
	 * @param type is the type of task
	 * @param creator is the creator for the task object
	 * @param note is the note for the task
	 */
	
	public Task(int taskId, String title, Type type, String creator, String note) {
		
		
		
		owner = UNOWNED;
		notes = new ArrayList<String>();
		addNoteToList(note);
		setTaskId(taskId);
		setTitle(title);
		
		setCreator(creator);
		
		setType(type);
		
		setState(BACKLOG_NAME);
		isVerified = false;
	
	}
	
	/**
	 * constructs a task object with the following values
	 * @param taskId is the id of the task
	 * @param state is the state the task is in
	 * @param type is the type of the task
	 * @param title is the title of the task
	 * @param creator is the creator of the task
	 * @param owner is the owner of the task
	 * @param isVerified is if the task is verified
	 * @param notes is the notes for the task
	 */
	
	public Task(int taskId, String state, String title, String type, String creator, String owner, String isVerified, ArrayList<String> notes) {
		
		setTaskId(taskId);
		setTitle(title);
		setCreator(creator);
		setOwner(owner);
		setVerified(isVerified);
		setTypeFromString(type);
		
		setNotes(notes);
		setState(state);
	}
	
	/**
	 * Sets the task id number.
	 * @param taskId is the task's id number
	 * @throws IllegalArgumentException if less than or equal to 0
	 */
	private void setTaskId(int taskId) {
		
		if(taskId <= 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.taskId = taskId;
	}
	
	/**
	 * Sets the task title.
	 * @param title is the task's title.
	 * @throws if title is null or empty
	 */
	private void setTitle(String title) {
		if(title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.title = title;
	}
	
	/**
	 * Sets the task type.
	 * @param taskType is the type of task
	 * @throws IllegalArgumentException if type is null or empty
	 */
	private void setType(Type taskType) {
		if(taskType == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.type = taskType;
	
		
	}
	
	/**
	 * Sets the task's creator.
	 * @param creator is the creator of the task
	 * @throws IllegalArgumentException if creator is null or empty
	 */
	private void setCreator(String creator) {
		
		if(creator == null || creator.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.creator = creator;
	}
	
	/**
	 * Sets the task owner.
	 * @param owner is the owner of the task.
	 * @throws IllegalArgumentException if owner is null or empty
	 */
	private void setOwner(String owner) {
		if(owner == null || owner.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		if(owner == UNOWNED && (currentState != backlogState || currentState != rejectedState)) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		this.owner = owner;
		
	}
	
	/**
	 * Sets the isVerified based on the passed string
	 * @param verified is the string value of true or false
	 */
	private void setVerified(String verified) {
		if(verified == null || verified.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		if("false".equals(verified)) {
			this.isVerified = false;
		}
		else if("true".equals(verified)) {
			this.isVerified = true;
		}
		else {
			throw new IllegalArgumentException("Invalid task information.");
		}
	}
	
	/**
	 * Sets the task notes.
	 * @param notes is the task's notes arraylist.
	 * @throws IllegalArgumentException if notes is null or empty
	 */
	private void setNotes(ArrayList<String> notes) {
		if(notes == null || notes.size() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		this.notes = notes;
	}
	
	/**
	 * adds the task notes to a list.
	 * @param note is the task's notes.
	 * @return the index of the last note added
	 */
	public int addNoteToList(String note) {
		if(note == null || note.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		notes.add("[" + getStateName() + "] " + note );
		return notes.size();
		
	}
	
	
	/**
	 * returns the task id
	 * @return the task Id
	 */
	public int getTaskId() {
		return taskId;
	}
	
	/**
	 * returns the states name.
	 * @return the current states name
	 */
	public String getStateName() {
		if(currentState == backlogState) {
			return BACKLOG_NAME;
		}
		
		else if(currentState == processingState) {
			return PROCESSING_NAME;
		}
		else if(currentState == verifyingState) {
			return VERIFYING_NAME;
		}
		else if(currentState == doneState) {
			return DONE_NAME;
		}
		else if(currentState == rejectedState) {
			return REJECTED_NAME;
		}
		else if(currentState == ownedState) {
			return OWNED_NAME;
		}
		else {
			return BACKLOG_NAME;
		}

		//return currentState.getStateName();
	}
	
	/**
	 * sets the state name
	 * @param state is the name of the state
	 */
	private void setState(String state) {
		
		if(state == null || state.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
	
		else if(state.equals(BACKLOG_NAME)) {
			this.currentState = backlogState;
		}
		
		else if(state.equals(OWNED_NAME)) {
			this.currentState = ownedState;
		}
		
		else if(state.equals(PROCESSING_NAME)) {
			this.currentState = processingState;
		}
		
		else if(state.equals(VERIFYING_NAME)) {
			this.currentState = verifyingState;
		}
		
		else if(state.equals(DONE_NAME)) {
			this.currentState = doneState;
		}
		
		else if(state.equals(REJECTED_NAME)) {
			this.currentState = rejectedState;
		}
		else {
			
				throw new IllegalArgumentException("Invalid task information.");
			}
			
		}
		
	
	
	
	
	/**
	 * sets the type based on the given string
	 * @param type is the string that represents the type
	 */
	private void setTypeFromString(String type) {
		if(type == null || type.length() == 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}
		
		else if(type.equals(T_FEATURE)) {
			this.type = Type.FEATURE;
		}
		else if(type.equals(T_BUG)) {
			this.type = Type.BUG;
		}
		else if(type.equals(T_TECHNICAL_WORK)) {
			this.type = Type.TECHNICAL_WORK;
		}
		else if(type.equals(T_KNOWLEDGE_ACQUISITION)) {
			this.type = Type.KNOWLEDGE_ACQUISITION;
		}
		else {
				throw new IllegalArgumentException("Invalid task information.");
		}

	}
	
	/**
	 * returns the type
	 * @return the type of the task
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * returns the type name in short form
	 * @return the string abbreviation of the task type name
	 */
	
	public String getTypeShortName() {
		if(type == Type.FEATURE) {
			return T_FEATURE;
		}
		else if(type == Type.BUG) {
			return T_BUG;
		}
		else if(type == Type.TECHNICAL_WORK) {
			return T_TECHNICAL_WORK;
		}
		else if(type == Type.KNOWLEDGE_ACQUISITION) {
			return T_KNOWLEDGE_ACQUISITION;
		}
		
			return null;
		
		
	}
	
	/**
	 * returns the type name in full form
	 * @return the full string name of the task type name
	 */
	public String getTypeLongName() {
		if(type == Type.FEATURE) {
			return FEATURE_NAME;
		}
		else if(type == Type.BUG) {
			return BUG_NAME;
		}
		else if(type == Type.TECHNICAL_WORK) {
			return TECHNICAL_WORK_NAME;
		}
		else if(type == Type.KNOWLEDGE_ACQUISITION) {
			return KNOWLEDGE_ACQUISITION_NAME;
		}
		
			return null;
		
	}
	
	/**
	 * returns the owner of the task
	 * @return owner of the task
	 */
	
	public String getOwner() {
		return owner;
	}
	
	/**
	 * returns the title of the task
	 * @return title of the task
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * returns the creator of the task
	 * @return creator of the task
	 */
	public String getCreator() {
		return creator;
	}
	
	
	
	/**
	 * checks if task is verified
	 * @return true or false based on if task is verified or not
	 */
	public boolean isVerified() {
	
	
			return isVerified;
		
	}
	
	/**
	 * returns the notes in an arraylist
	 * @return the notes arraylist
	 */
	public ArrayList<String> getNotes(){
		return notes;
	}
	
	/**
	 * returns the proper formating of the notes string
	 * @return the formated notes
	 */
	public String getNotesList() {
		String listOfNotes = "";
		for(int i = 0; i < notes.size(); i++) {
			
			listOfNotes += "- " + notes.get(i) + "\n";
			
		}
		
		return listOfNotes;
		
		
	}

	/**
	 * Formats the task to align with how the files need to be saved
	 * @return task string of the formated task line
	 */
	public String toString() {
		String task;
		task = "* " + getTaskId() + "," + getStateName() + "," + getTitle() + "," + getTypeShortName() + 
				"," + getCreator() + "," + getOwner() + "," + isVerified() + "\n" + getNotesList();
		
		return task;
	}
	
	/**
	 * updates the task
	 * @param command is the update that is taking place
	 * @throws UnsupportedOperationException if command is not appropriate for current state
	 */
	public void update(Command command) {
		
		this.currentState.updateState(command);
		if(command.getCommand() == CommandValue.CLAIM) {
		setOwner(command.getOwner());
		}
	}
	
	
	/**
	 * converts notes array list to 1d array
	 * @return arrayOfNotes is the 1d array from the notes array list
	 */
	public String[] getNotesArray() {
		String[] arrayOfNotes = new String[notes.size()];
		for(int i = 0; i < notes.size(); i++) {
			arrayOfNotes[i] = notes.get(i);
		}
		return arrayOfNotes;
	}
	
}
 