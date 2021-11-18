/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 *This class represents an event and is a subclass of Activity. It holds important fields of 
 *information relating to the event.
 *Since wolfscheduler now allows for events to be added, this class distinguishes itself from
 *courses through various requirements. The event details are exclusive to events.
 *@author Eric Samuel
 */

public class Event extends Activity {

	/** the details of the event */
	private String eventDetails;
	
	/**
	 * Returns the short display of the Event in course catalog and schedule
	 * @return shortDisplay array
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[4];
		shortDisplay[0] = "";
		shortDisplay[1] = "";
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		return shortDisplay;
	
		
	}
	
	/**
	 * Determines if the event trying to be added is already in the schedule
	 * @param activity that is being checked for repeat
	 * @return true if duplicate and false if not
	 */

	
	
	public boolean isDuplicate(Activity activity) {
		
		   if (activity instanceof Event) {
			      Event e = (Event)activity;
			     if(this.getTitle().equals(e.getTitle())) {
			      return true;
			     }
			     
			     
			     
			   }
			      return false;
			   
	}

	
	/**
	 * Overridden to account for the Saturday and Sunday letters that are valid for events
	 * @param meetingDays the days the event takes place
	 * @param startTime the start time of the event
	 * @param endTime the end time of the event
	 * @throws IllegalArgumentException is meeting days or time don't meet valid requirements.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		if(meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if(meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

			int mondayCounter = 0;
			int tuesdayCounter = 0;
			int wednesdayCounter = 0;
			int thursdayCounter = 0;
			int fridayCounter = 0;
			int saturdayCounter = 0;
			int sundayCounter = 0;
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
					else if (meetingDays.charAt(i) == 'S') {
						saturdayCounter++;
					}
					else if (meetingDays.charAt(i) == 'U') {
						sundayCounter++;
					}
					else {
						throw new IllegalArgumentException("Invalid meeting days and times.");
					}
				
				}
				if(mondayCounter > 1 || tuesdayCounter > 1 || wednesdayCounter > 1 || thursdayCounter > 1 || fridayCounter > 1 || saturdayCounter > 1 || sundayCounter > 1) {
					
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			
				
				super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
				
				

		
				
}
				
				
		
				
				
				
				
	

	/**
	 * Creates an event with the following parameters. Retreieves title, meetingdays,
	 * starttime, and endtime from the super class activity.
	 * @param title is the title of the event
	 * @param meetingDays is the meeting days of the event
	 * @param startTime is the start time of the event
	 * @param endTime is the end time of the event
	 * @param eventDetails is the details of the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
		super(title, meetingDays, startTime, endTime);
		setEventDetails(eventDetails);
	}

	/**
	 * returns the event details
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Assigns the event details and checks for null
	 * @throws IllegalArgumentException if eventDetails is null
	 * @param eventDetails the eventDetails to set
	 */
	public void setEventDetails(String eventDetails) {
		if(eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		this.eventDetails = eventDetails;
	}
	
	/**
	 * Returns the long display of the Event in final schedule
	 * @return longDisplay array
	 */

	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		longDisplay[0] = "";
		longDisplay[1] = "";
		longDisplay[2] = getTitle();
		longDisplay[3] = "";
		longDisplay[4] = "";
		longDisplay[5] = getMeetingString();
		longDisplay[6] = eventDetails;
		return longDisplay;
	}

	/**
	 * Overridden to output the comma separated string for events instead of course.
	 * @return the comma separated string for events.
	 */

	public String toString() {
		return getTitle() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime() + "," + getEventDetails();
		
	}

}
