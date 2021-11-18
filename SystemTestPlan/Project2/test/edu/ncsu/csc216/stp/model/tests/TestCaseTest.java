package edu.ncsu.csc216.stp.model.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the TestCase class
 * @author Eric Samuel
 */
class TestCaseTest {

	/**
	 * Tests the toString method
	 */
	@Test
	void testToString() {
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test.addTestResult(false, "failure");
		test.addTestResult(true, "passing");
		assertEquals("# New test,Invalid\n* 1. load gui.\n2. click select\n* GUI loads\n- FAIL: failure\n- PASS: passing\n", test.toString());
	}




}
