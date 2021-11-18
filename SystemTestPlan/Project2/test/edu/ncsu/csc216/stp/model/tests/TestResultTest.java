package edu.ncsu.csc216.stp.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestResultTest {

	/**
	 * Tests the toString method
	 */
	@Test
	public void testToString() {
		TestResult test = new TestResult(true, "everything is empty");
		assertEquals("PASS: everything is empty", test.toString());
	}

}
