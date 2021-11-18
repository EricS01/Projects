/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * This class will handle various conflicts that might arise when adding overlapping activities
 * These conflicts also include if atleast one of the start time or end time is equal to the original
 * events start time or end time.
 * @author Eric Samuel
 */
public interface Conflict {
	
	/** Checks if there is a conflict with activity being added and throws exception if so 
	 * @param possibleConflictingActivity is the object that it is checking for conflict
	 * @throws ConflictException if there is a schedule conflict 
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

	

}
