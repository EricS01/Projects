package edu.ncsu.csc216.wolf_scheduler.course;

import java.util.Objects;

/**
 * This class represents an activity. It holds important fields of information for the activity.
 * This is a super class that parents the Course and Event classes. Used to represent either
 * a course or an event. Both are considered an activity.
 * @author Eric Samuel
 */

public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/** Highest hour */
	private static final int UPPER_HOUR = 24;
	/** Highest minute */
	private static final int UPPER_MINUTE = 60;
	
	/**
	 * Creates an Activity  with the given title, meetingDays, startTime, and endTime
	 * @param title title of Activity
	 * @param meetingDays meetings days for Course as series of chars\
	 * @param startTime is the start time of activity
	 * @param endTime is the end time of activity
	 */

	 public Activity(String title, String meetingDays, int startTime, int endTime) {
	        super();
	        setTitle(title);
	        setMeetingDaysAndTime(meetingDays, startTime, endTime);
	 }
	 
	 /** method that will check in events and course if activity being added is a duplicate or not 
	  * @param activity that is being checked for repeat
	  * @return true if its duplicate and false if not 
	  */
	 
	 public abstract boolean isDuplicate(Activity activity);
	 
	 
	 /** Used to populate the rows of the course catalog and student schedule 
	  * @return an array of strings that populates course catalog and student schedule
	  */
	 
	 public abstract String[] getShortDisplayArray();
	 
	 /** Used to display the final schedule 
	  * @return an array of strings that is used to display the final schedule
	  */
	 public abstract String[] getLongDisplayArray();

	/**
	 * Returns the Course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's title.
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or empty string
	 */
	public void setTitle(String title) {
		if(title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		if(title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the Course's Meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * returns the start time of course
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * returns the end time of course
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the Course's Meeting days
	 * @param meetingDays the meeting Days of course
	 * @param startTime the start time of course
	 * @param endTime the end time of course
	 * @throws IllegalArgumentException if parameters don't meet valid requirements
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if(meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if(meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		

				if(endTime < startTime) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				int startHour = startTime / 100;
				int startMin = startTime % 100;
				int endHour = endTime / 100;
				int endMin = endTime % 100;
				
				if(startHour >= UPPER_HOUR || startHour < 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				if(startMin >= UPPER_MINUTE || startMin < 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				if(endHour >= UPPER_HOUR || endHour < 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				if(endMin >= UPPER_MINUTE || endMin < 0) {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				
				this.meetingDays = meetingDays;
				this.startTime = startTime;
				this.endTime = endTime;
				
		
		
	}

	/**
	 * Converts the military time to standard time and constructs string about meeting
	 * @return meetingString which is the completed meeting string with standardtime
	 */
	public String getMeetingString() {
		
		int startHour = startTime / 100;
		int startMin = startTime % 100;
		int endHour = endTime / 100;
		int endMin = endTime % 100;
		String sHour;
		String sMin;
		String sTime = "";
		String eHour;
		String eMin;
		String eTime = "";
		String meetingString;
		
		if("A".equals(meetingDays)) {
			return "Arranged";
		}
		if(startHour > 12) {
			startHour -= 12;
			sHour = Integer.toString(startHour);
			if (startMin < 10) {
				sMin = Integer.toString(startMin);
				sMin = '0' + sMin;
				sTime = meetingDays + " " + sHour + ":" + sMin + "PM-"; 
				
			}
			else {
				sMin = Integer.toString(startMin);
				sTime = meetingDays + " " + sHour + ":" + sMin + "PM-";
			}
			
		}
		else if (startHour == 0) {
			startHour = 12;
			sHour = Integer.toString(startHour);
			if (startMin < 10) {
				sMin = Integer.toString(startMin);
				sMin = '0' + sMin;
				sTime = meetingDays + " " + sHour + ":" + sMin + "AM-"; 
				
			}
			else {
				sMin = Integer.toString(startMin);
				sTime = meetingDays + " " + sHour + ":" + sMin + "AM-";
			}
		}
	
		else if (startHour == 12) {
			startHour = 12;
			sHour = Integer.toString(startHour);
			if (startMin < 10) {
				sMin = Integer.toString(startMin);
				sMin = '0' + sMin;
				sTime = meetingDays + " " + sHour + ":" + sMin + "PM-"; 
				
			}
			else {
				sMin = Integer.toString(startMin);
				sTime = meetingDays + " " + sHour + ":" + sMin + "PM-";
			}
		}
		
		else {
			sHour = Integer.toString(startHour);
			if (startMin < 10) {
				sMin = Integer.toString(startMin);
				sMin = '0' + sMin;
				sTime = meetingDays + " " + sHour + ":" + sMin + "AM-"; 
				
			}
			else {
				sMin = Integer.toString(startMin);
				sTime = meetingDays + " " + sHour + ":" + sMin + "AM-";
			}
			
		}
		
		if(endHour > 12) {
			endHour -= 12;
			eHour = Integer.toString(endHour);
			if (endMin < 10) {
				eMin = Integer.toString(endMin);
				eMin = '0' + eMin;
				eTime = eHour + ":" + eMin + "PM"; 
				
			}
			else {
				eMin = Integer.toString(endMin);
				eTime = eHour + ":" + eMin + "PM"; 
				
			}
			
		}
		else if (endHour == 0) {
			endHour = 12;
			eHour = Integer.toString(endHour);
			if (endMin < 10) {
				eMin = Integer.toString(endMin);
				eMin = '0' + eMin;
				eTime = eHour + ":" + eMin + "AM"; 
			}
			else {
				eMin = Integer.toString(endMin);
				eTime = eHour + ":" + eMin + "AM"; 
				
			}
			
		}
		
		else if (endHour == 12) {
			endHour = 12;
			eHour = Integer.toString(endHour);
			if (endMin < 10) {
				eMin = Integer.toString(endMin);
				eMin = '0' + eMin;
				eTime = eHour + ":" + eMin + "PM"; 
			}
			else {
				eMin = Integer.toString(endMin);
				eTime = eHour + ":" + eMin + "PM"; 
				
			}
			
		}
		else {
			eHour = Integer.toString(endHour);
			if (endMin < 10) {
				eMin = Integer.toString(endMin);
				eMin = '0' + eMin;
				eTime = eHour + ":" + eMin + "AM"; 
				
			}
			else {
				eMin = Integer.toString(endMin);
				eTime = eHour + ":" + eMin + "AM"; 
				
			}
		}
		
		meetingString = sTime + eTime;
		return meetingString;
		
	}
	/**
	 * Reduces passed fields to integers from string
	 * @return hashed parameters
	 */
	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}
	/**
	 * Checks for equality to the passed object
	 * @return true or false based on if they are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}

	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		int startCurrent = this.getStartTime();
		int endCurrent = this.getEndTime();
		String daysCurrent = this.getMeetingDays();
		boolean mondayCurrent = false;
		boolean tuesdayCurrent = false;
		boolean wednesdayCurrent = false;
		boolean thursdayCurrent = false;
		boolean fridayCurrent = false;
		boolean saturdayCurrent = false;
		boolean sundayCurrent = false;
		
		int startAdd = possibleConflictingActivity.getStartTime();
		int endAdd = possibleConflictingActivity.getEndTime();
		String daysAdd = possibleConflictingActivity.getMeetingDays();
		boolean mondayAdd = false;
		boolean tuesdayAdd = false;
		boolean wednesdayAdd = false;
		boolean thursdayAdd = false;
		boolean fridayAdd = false;
		boolean saturdayAdd = false;
		boolean sundayAdd = false;
		
		for(int i = 0; i < daysCurrent.length(); i++) {
			if (daysCurrent.charAt(i) == 'M') {
				mondayCurrent = true;
			}
			if (daysCurrent.charAt(i) == 'T') {
				tuesdayCurrent = true;
			}
			if (daysCurrent.charAt(i) == 'W') {
				wednesdayCurrent = true;
			}
			if (daysCurrent.charAt(i) == 'H') {
				thursdayCurrent = true;
			}
			if (daysCurrent.charAt(i) == 'F') {
				fridayCurrent = true;
			}
			if (daysCurrent.charAt(i) == 'S') {
				saturdayCurrent = true;
			}
			if (daysCurrent.charAt(i) == 'U') {
				sundayCurrent = true;
			}
			
		}
		
		for(int i = 0; i < daysAdd.length(); i++) {
			if (daysAdd.charAt(i) == 'M') {
				mondayAdd = true;
			}
			if (daysAdd.charAt(i) == 'T') {
				tuesdayAdd = true;
			}
			if (daysAdd.charAt(i) == 'W') {
				wednesdayAdd = true;
			}
			if (daysAdd.charAt(i) == 'H') {
				thursdayAdd = true;
			}
			if (daysAdd.charAt(i) == 'F') {
				fridayAdd = true;
			}
			if (daysAdd.charAt(i) == 'S') {
				saturdayAdd = true;
			}
			if (daysAdd.charAt(i) == 'U') {
				sundayAdd = true;
			}
			
		}
		
		
		if(mondayCurrent && mondayAdd || tuesdayCurrent && tuesdayAdd || wednesdayCurrent && wednesdayAdd || thursdayCurrent && thursdayAdd || 
				fridayCurrent && fridayAdd || saturdayCurrent && saturdayAdd || sundayCurrent && sundayAdd) {
			if(startAdd <= endCurrent && endAdd >= endCurrent) {
				throw new ConflictException("Schedule conflict.");
			}
			else if(endAdd >= startCurrent && startAdd <= startCurrent) {
				throw new ConflictException("Schedule conflict.");
			}
			else if (startCurrent <= startAdd && endCurrent >= endAdd) {
				throw new ConflictException("Schedule conflict.");
			}
			else if (startAdd <= startCurrent && endAdd >= endCurrent) {
				throw new ConflictException("Schedule conflict.");
			}
			
			else if (startCurrent == startAdd || startCurrent == endAdd || endCurrent == startAdd || endCurrent == endAdd) {
				throw new ConflictException("Schedule conflict.");
			}
		}
		
		
	}

}