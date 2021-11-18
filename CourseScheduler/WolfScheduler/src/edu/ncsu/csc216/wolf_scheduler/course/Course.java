package edu.ncsu.csc216.wolf_scheduler.course;

import java.util.Objects;

/**
 *This class represents a course and is a subclass of Activity. It holds important fields of 
 *information relating to the course.
 *Since wolfscheduler now allows for events to be added, this class distinguishes itself from
 *events through various requirements. The name, section, credits, and instructor id are exclusive
 *to course.
 *@author Eric Samuel
 */


public class Course extends Activity {

	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Minimum name length */
	private static final int MIN_NAME_LENGTH = 5;
	
	/** Maximum name length */
	private static final int MAX_NAME_LENGTH = 8;
	
	/** Minimum amount of letters */
	private static final int MIN_LETTER_COUNT = 1;
	
	/** Maximum amount of letters */
	private static final int MAX_LETTER_COUNT = 4;
	
	/** Amount of digits */
	private static final int DIGIT_COUNT = 3;
	
	/** Length of the section */
	private static final int SECTION_LENGTH = 3;
	
	/** Maximum amount of credits */
	private static final int MAX_CREDITS = 5;
	
	/** Minimum amount of credits */
	private static final int MIN_CREDITS = 1;
	
	/**
	 * Construct a Course object with values for all fields
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meetings days for Course as series of chars
	 * @param startTime start time for Course
	 * @param endTime end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);

	    setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);

	}
	
	

	
	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for
	 * courses that are arranged.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param meetingDays meetings days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}
	
	/**
	 * Determines if the course trying to be added is already in the schedule
	 * @param activity that is being checked for repeat
	 * @return true if duplicate and false if not
	 */

	
	public boolean isDuplicate(Activity activity) {
		
		   if (activity instanceof Course) {
			      Course c = (Course)activity;
			     if(this.getName().equals(c.getName())) {
			      return true;
			     }
			
		   }
		 
			     return false;
		   
	}
	
	
	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the Course's name and ensures it follows the valid course name format.
	 * @param name the name to set
	 * @throws IllegalArgumentException if course name doesn't meet valid requirements
	 */
	private void setName(String name) {
		if(name == null)
			throw new IllegalArgumentException("Invalid course name.");
		
		if(name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
			throw new IllegalArgumentException("Invalid course name.");
		
		int numberLetters = 0;
		int numberDigits = 0;
		boolean spaceFound = false;
		for (int i = 0; i < name.length(); i++) {
			if(!spaceFound) {
				if(Character.isLetter(name.charAt(i))) {
					numberLetters++;
				}
				else if (name.charAt(i) == ' ') {
					spaceFound = true;
				}
				else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
			else if(spaceFound) {
				if(Character.isDigit(name.charAt(i))) {
					numberDigits++;
				}
				else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		if(numberLetters < MIN_LETTER_COUNT || numberLetters > MAX_LETTER_COUNT)
			throw new IllegalArgumentException("Invalid course name.");
		
		if(numberDigits != DIGIT_COUNT)
			throw new IllegalArgumentException("Invalid course name.");
		
		this.name = name;
	}
	
	/**
	 * Returns the Course's section.
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the Course's section.
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is null or there is a letter.
	 */
	public void setSection(String section) {
		
		if(section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		
		for (int i = 0; i < section.length(); i++) {
			if(Character.isLetter(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		this.section = section;
	}
	/**
	 * Returns the Course's credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * Sets the Course's credits.
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits are not in valid range
	 */
	public void setCredits(int credits) {
		if(credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}
	/**
	 * Returns the Course's InstructorId
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}
	/**
	 * Sets the Course's InstructorId
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorid is null
	 */
	public void setInstructorId(String instructorId) {
		if(instructorId == null || "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		
		if(instructorId == null || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		
		this.instructorId = instructorId;
	}
	
	
	
	/**
	 * Returns a comma separated value String of all Course fields. 
	 * Overiden due to difference with fields from Event
	 * @return String representation of Course
	 */
	
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(credits, instructorId, name, section);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(name, other.name) && Objects.equals(section, other.section);
	}
	
	/**
	 * Overridden to account for the valid days that a course can take place on
	 * @param meetingDays the days that the course meets on
	 * @param startTime the start time of the course
	 * @param endTime the end time of the course
	 * @throws IllegalArgumentException if meetingdays and time requirements aren't met
	 */
	
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
	
		if("A".equals(meetingDays)) {
			if(startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
	
		}
		else {
			int mondayCounter = 0;
			int tuesdayCounter = 0;
			int wednesdayCounter = 0;
			int thursdayCounter = 0;
			int fridayCounter = 0;
				for(int i = 0; i < meetingDays.length(); i++) {
					if(meetingDays.charAt(i) == 'M') {
						mondayCounter++;
					}
					else if (meetingDays.charAt(i) == 'T') {
						tuesdayCounter++;
					}
					else if (meetingDays.charAt(i) == 'W') {
						wednesdayCounter++;
					}
					else if (meetingDays.charAt(i) == 'H') {
						thursdayCounter++;
					}
					else if (meetingDays.charAt(i) == 'F') {
						fridayCounter++;
					}
					else {
						throw new IllegalArgumentException("Invalid meeting days and times.");
					}
				}
				if(mondayCounter > 1 || tuesdayCounter > 1 || wednesdayCounter > 1 || thursdayCounter > 1 || fridayCounter > 1) {
					
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
		
	}
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		
	}
	
	/**
	 * Receives array that contains the course and its partial information, different to that of events
	 * @return shortDisplay the array containing this course info.
	 */
	
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[4];
		for (int i = 0; i < shortDisplay.length; i++) {
			if(i == 0) {
				shortDisplay[i] = getName();
				
			}
			if(i == 1) {
				shortDisplay[i] = getSection();
				
			}
			if(i == 2) {
				shortDisplay[i] = getTitle();
				
			}
			
			if(i == 3) {
				shortDisplay[i] = getMeetingString();
				
			}
			
		}
		return shortDisplay;
		
		
	}
	
	/**
	 * Receives array that contains the full course and its information, different to that of events
	 * @return longDisplay the array containing this course info.
	 */
	@Override
	public String[] getLongDisplayArray() {
	
		String[] longDisplay = new String[7];
		for (int i = 0; i < longDisplay.length; i++) {
			if(i == 0) {
				longDisplay[i] = getName();
				
			}
			if(i == 1) {
				longDisplay[i] = getSection();
				
			}
			if(i == 2) {
				longDisplay[i] = getTitle();
				
			}
			
			if(i == 3) {
				int creds = getCredits();
				String s = Integer.toString(creds);
				longDisplay[i] = s;
				
			}
			
			if(i == 4) {
			
				longDisplay[i] = getInstructorId();
				
			}
			if(i == 5) {
				
				longDisplay[i] = getMeetingString();
				
			}
			
			if(i == 6) {
				
				longDisplay[i] = "";
				
			}
			
			
		}
		return longDisplay;
		
		
	}

	

	

}
