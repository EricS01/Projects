package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 *This class reads in and stores as a list all the Course Records
 *@author Eric Samuel
 */


public class WolfScheduler {

	/** catalog for Courses */
	private ArrayList<Course> catalog;
	
	/** schedule for Courses */
	private ArrayList<Activity> schedule;
	
	/** schedule title */
	private String title;
	
	
	/**
	 * Constructs catalog and schedule arraylist and sets title of schedule
	 * @param fileName is the fileName being read in
	 */

	public WolfScheduler(String fileName) {
		this.schedule = new ArrayList<Activity>();
		this.title = "My Schedule";
		try {
		this.catalog = CourseRecordIO.readCourseRecords(fileName);
		}
		catch(FileNotFoundException e){
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
	
	/**
	 * Sets the title of the schedule
	 * @param title of schedule

	 */

	public void setScheduleTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
		
	}
	
	/**
	 * returns the title of the schedule
	 * @return title of the schedule
	 */

	public String getScheduleTitle() {
		
		return title;
	}
	
	
	/**
	 * Adds an event to the schedule and checks if its a duplicate or not. Also checks to see that the 
	 * course trying to be added does overlap time-wise with another activity.
	 * @param eventTitle is the title of the event
	 * @param eventMeetingDays is the meeting days for the event
	 * @param eventStartTime is the start time of the event
	 * @param eventEndTime is the end time of the event
	 * @param eventDetails is the details of the event
	 * @throws IllegalArgumentException if the event being added overlaps (time-wise) with another Activity in the schedule
	 */
	
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
		
		Event e1 = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		
		
				for(int j = 0; j < schedule.size(); j++) {
					if(schedule.get(j).isDuplicate(e1)) {
						throw new IllegalArgumentException("You have already created an event called " + schedule.get(j).getTitle());
					}
					try {
						e1.checkConflict(schedule.get(j));
					}
					catch(ConflictException e) {
						throw new IllegalArgumentException("The event cannot be added due to a conflict.");
					}
				}
				
				
				schedule.add(e1);
		
			}
		
		
		
	
	
	/**
	 * Exports schedule to file location
	 * @param fileName is location that schedule will be exported to

	 */

	public void exportSchedule(String fileName) {
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		}
		catch(IOException e){
			throw new IllegalArgumentException("The file cannot be saved.");
		}
		
	}
	
	/**
	 * Attempts to add course if course exists and isn't already added. Also checks to see that the 
	 * course trying to be added does overlap time-wise with another activity.
	 * @param name is the name of the course to add
	 * @param section is the section of the course to add
	 * @throws IllegalArgumentException if the course being added overlaps (time-wise) with another Activity in the schedule
	 * @return true if course is in catalog and added to schedule otherwise false

	 */

	public boolean addCourseToSchedule(String name, String section) {
		
		
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				for(int j = 0; j < schedule.size(); j++) {
					if(schedule.get(j).isDuplicate(catalog.get(i))) {
						throw new IllegalArgumentException("You are already enrolled in " + catalog.get(i).getName());
					}
					try {
						catalog.get(i).checkConflict(schedule.get(j));
					}
					catch(ConflictException e) {
						throw new IllegalArgumentException("The course cannot be added due to a conflict.");
					}
				}
				schedule.add(catalog.get(i));
				return true;
			}
		}
		
		return false;
		
	}
	
	/**
	 * gets the name, section, and title attributes of the objects in arraylist of the schedule and puts it into a 2d array
	 * @return scheduleTwo which is the 2d array of the schedule

	 */

	public String[][] getScheduledActivities() {
		
		 String [][] scheduleArray = new String[schedule.size()][3];
	        for (int i = 0; i < schedule.size(); i++) {
	            Activity c = schedule.get(i);
	            scheduleArray[i] = c.getShortDisplayArray();
	        }
	        return scheduleArray;
	}
	
	/**
	 * gets the objects in arraylist of the schedule and puts it into a 2d array
	 * @return fullScheduleTwo which is the 2d array of the full schedule

	 */

	public String[][] getFullScheduledActivities() {

		 String [][] fullScheduleArray = new String[schedule.size()][6];
	        for (int i = 0; i < schedule.size(); i++) {
	            Activity c = schedule.get(i);
	            fullScheduleArray[i] = c.getLongDisplayArray();
	        }
	        return fullScheduleArray;
		
	}
	
	/**
	 * gets the objects in arraylist of the catalog and puts it into a 2d array
	 * @return catalogTwo which is the 2d array of the course

	 */

	public String[][] getCourseCatalog() {
		
        String [][] catalogArray = new String[catalog.size()][3];
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            catalogArray[i] = c.getShortDisplayArray();
        }
        return catalogArray;
		
		
	}
	
	/**
	 * Determines if coures being searched for is in catalog
	 * @param name is the name of course being looked for
	 * @param section is the section of the course being looked for
	 * @return course being looked for
	 */

	public Course getCourseFromCatalog(String name, String section) {
		
		
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				
				return catalog.get(i);
			
			}	
			
		}
		return null;
	}
	
	/**
	 * removes a course from schedule if it is in schedule
	 * @param idx TODO
	 * @return true if course is in schedule or false if course isn't in schedule
	 */


	public boolean removeActivityFromSchedule(int idx) {
//		for(int i = 0; i < schedule.size(); i++) {
//			if(schedule.get(i).getName().equals(name) && schedule.get(i).getSection().equals(section)) {
//				schedule.remove(i);
//				return true;
//			
//			}	
//			
//		}
//		return false;
		
		try {
			schedule.remove(idx);
			return true;
			
		}
		catch(IndexOutOfBoundsException e) {
			return false;
			
		}
		
	}
	
	/**
	 * resets the schedule 
	 */


	public void resetSchedule() {
		schedule = new ArrayList<Activity>();
		
	}

}
