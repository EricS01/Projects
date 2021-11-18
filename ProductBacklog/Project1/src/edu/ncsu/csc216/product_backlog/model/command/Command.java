/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

/**
 * Class that handles user commands
 * @author Eric Samuel
 *
 */
public class Command {
	
	/**
	 * The corresponding value of the given command
	 * @author Eric Samuel
	 *
	 */
	public enum CommandValue { BACKLOG, CLAIM, PROCESS, VERIFY, COMPLETE, REJECT }


	/** note for command */
	private String noteText;
	
	/** owner for command */
	private String owner;
	
	/** command value field */
	CommandValue c;
	
	/** error message for invalid command */
	private static final String COMMAND_ERROR_MESSAGE = "Invalid command.";
	
	/**
	 * Is the command to be acted
	 * @param c is the specific command
	 * @param owner is the owner of the task
	 * @param noteText is the note for the task
	 */
	public Command(CommandValue c, String owner, String noteText ) {
		
		if(c == null) {
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		else {
			this.c = c;
		}
		
		if((owner == null || owner.length() == 0) && c == CommandValue.CLAIM)  {
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}

		else if (owner != null && c != CommandValue.CLAIM) {
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		else {
			this.owner = owner;
		}
		
		if(noteText == null || noteText.length() == 0) {
			throw new IllegalArgumentException(COMMAND_ERROR_MESSAGE);
		}
		else {
			this.noteText = noteText;
		}
	}
	
	/**
	 * gets the command that is acted on the task
	 * @return c the command givent to task
	 */
	public CommandValue getCommand() {
		
		return c;
		
	
	}
	
	/**
	 * Returns the notes text
	 * @return notetext is the notes for a task
	 */
	public String getNoteText() {
		
		return noteText;
	}
	
	/**
	 * gets the owner of a task
	 * @return the owner of a task
	 */
	public String getOwner() {
		
		return owner;
	}
	
}
