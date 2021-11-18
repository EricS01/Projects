/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the Activity class for functionality that Course and Event tests do not cover (checkConflict method)
 * @author Eric Samuel
 */
class ActivityTest {

	/**
	 * Tests the checkConflict method for functionality
	 * test that overlapping events and/or courses throw the proper conflictexception and message.
	 */
	@Test
	void testCheckConflict() {
		Activity a1 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
		Activity a2 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "TH", 1330, 1445);
		
		 assertDoesNotThrow(() -> a1.checkConflict(a2));
		 assertDoesNotThrow(() -> a2.checkConflict(a1));
		 
		Activity a3 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
		Activity a4 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
		
		Activity a5 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "FW", 1330, 1445);
		Activity a6 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "F", 1300, 1400);
		
	
		
		Activity a7 = new Event("Gaming", "SU", 1330, 1445, "Mario Kart ");
		Activity a8 = new Event("Gaming", "SU", 1300, 1330, "Mario Kart ");
		
		Activity a9 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "FW", 1330, 1445);
		Activity a10 = new Course("CSC 216", "Software Development Fundamentals", "001", 3, "sesmith5", "F", 1400, 1430);
		
	
		 Exception e1 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
		 assertEquals("Schedule conflict.", e1.getMessage());
			
		 Exception e2 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
		 assertEquals("Schedule conflict.", e2.getMessage());
		 
		 Exception e3 = assertThrows(ConflictException.class, () -> a5.checkConflict(a6));
		 assertEquals("Schedule conflict.", e3.getMessage());
		 
		 Exception e4 = assertThrows(ConflictException.class, () -> a7.checkConflict(a8));
		 assertEquals("Schedule conflict.", e4.getMessage());
		 
		 Exception e5 = assertThrows(ConflictException.class, () -> a9.checkConflict(a10));
		 assertEquals("Schedule conflict.", e5.getMessage());
		 
		 
		}
	}


