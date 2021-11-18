package edu.ncsu.csc216.stp.model.test_plans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Tests the failingTestList class
 * @author Eric Samuel
 */
class FailingTestListTest {

	/**
	 * Tests the getTestCasesAsArray method
	 */
	@Test
	void testGetTestCasesAsArray() {
		
		AbstractTestPlan fail = new FailingTestList();
		//AbstractTestPlan plan = new TestPlan("First Plan");
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test.addTestResult(false, "failure");
		//test.addTestResult(true, "passing");
		fail.addTestCase(test);

		TestCase test2 = new TestCase("Second test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test2.addTestResult(false, "failure");
		//test2.addTestResult(true, "passing");
		fail.addTestCase(test2);
		
//		AbstractTestPlan plan2 = new TestPlan("First Plan");
//		TestCase test3 = new TestCase("Third test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
//		test.addTestResult(false, "failure");
//		test.addTestResult(true, "passing");
//		plan2.addTestCase(test3);
//
//		TestCase test4 = new TestCase("fourth test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
//		test2.addTestResult(false, "failure");
//		test2.addTestResult(true, "passing");
//		plan2.addTestCase(test4);
		
		assertEquals("Second test", fail.getTestCasesAsArray()[1][0]);
	}

	/**
	 * Tests the clearTests method
	 */
	@Test
	void testClearTests() {
		FailingTestList fail = new FailingTestList();
		//AbstractTestPlan plan = new TestPlan("First Plan");
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		assertEquals("FAIL", test.getStatus());
		//test.addTestResult(true, "passing");
		fail.addTestCase(test);

		TestCase test2 = new TestCase("Second test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test2.addTestResult(false, "failure");
		//test2.addTestResult(true, "passing");
		fail.addTestCase(test2);
		
		fail.clearTests();
		assertEquals(0, fail.getNumberOfFailingTests());
	}

}
