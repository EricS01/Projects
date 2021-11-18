package edu.ncsu.csc216.stp.model.test_plans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Tests the AbstractTestPlan class for functionality
 * @author Eric Samuel
 */
class AbstractTestPlanTest {

	/**
	 * Tests the addTestCase method
	 */
	@Test
	void testAddTestCase() {
		
		AbstractTestPlan plan = new TestPlan("First Plan");
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test.addTestResult(false, "failure");
		test.addTestResult(true, "passing");
		plan.addTestCase(test);
		assertEquals("New test", plan.getTestCasesAsArray()[0][0]);
	
	}

	/**
	 * Tests the removeTestcase method
	 */
	@Test
	void testRemoveTestCase() {
		AbstractTestPlan plan = new TestPlan("First Plan");
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test.addTestResult(false, "failure");
		test.addTestResult(true, "passing");
		plan.addTestCase(test);

		TestCase test2 = new TestCase("Second test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test2.addTestResult(false, "failure");
		test2.addTestResult(true, "passing");
		plan.addTestCase(test2);
		
		assertEquals("New test", plan.removeTestCase(0).getTestCaseId());
	}

	/**
	 * Tests the getNumberOfFailingTests method
	 */
	@Test
	void testGetNumberOfFailingTests() {
		
		AbstractTestPlan plan = new TestPlan("First Plan");
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test.addTestResult(false, "failure");
		test.addTestResult(true, "passing");
		plan.addTestCase(test);

		TestCase test2 = new TestCase("Second test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test2.addTestResult(false, "failure");
		test2.addTestResult(true, "passing");
		plan.addTestCase(test2);
		
		assertEquals(0, plan.getNumberOfFailingTests());
	}

	/**
	 * Tests the addTestResult method
	 */
	@Test
	void testAddTestResult() {
		AbstractTestPlan plan = new TestPlan("First Plan");
		TestCase test = new TestCase("New test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		test.addTestResult(false, "failure");
		test.addTestResult(true, "passing");
		plan.addTestCase(test);

		TestCase test2 = new TestCase("Second test", "Invalid", "1. load gui.\n2. click select", "GUI loads");
		plan.addTestCase(test2);
		
		plan.addTestResult(1, true, "passing");
		assertEquals("PASS", plan.getTestCasesAsArray()[1][2]);
		
	}
	
	/**
	 * Tests setting invalid test plan name
	 */
	@Test
	void testSetTestPlanNameInvalid() {
		AbstractTestPlan p = new TestPlan("Test");
		assertThrows(IllegalArgumentException.class, () -> p.setTestPlanName(""));
	}
	
	


}
