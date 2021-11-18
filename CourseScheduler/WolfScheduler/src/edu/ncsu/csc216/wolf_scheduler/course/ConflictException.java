/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Custom exception that requires client to handles the exception in code. This exception will be used
 * when we implement the checkConflict in the WolfScheduler when adding courses/events.
 * @author Eric Samuel
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * parameterized constructor with a String specifying a message for the Exception object. The message is passed to the parent’s constructor
	 * @param message for exception object
	 */
	public ConflictException (String message) {
		super(message);
	}
	
	/**
	 * parameterless constructor that calls the parameterized constructor with an author specified default message
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

}
