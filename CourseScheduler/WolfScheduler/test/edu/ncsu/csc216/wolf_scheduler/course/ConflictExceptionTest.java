/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the ConflictException interface
 * @author Eric Samuel
 */
class ConflictExceptionTest {

	/**
	 * Test method for parameterized ConflictException constructor
	 */
	@Test
	public void testConflictExceptionString() {
	    ConflictException ce = new ConflictException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}

	/**
	 * Test method for parameterless ConflictException constructor
	 */
	@Test
	void testConflictException() {
		 ConflictException ce = new ConflictException();
		    assertEquals("Schedule conflict.", ce.getMessage());
	}

}
