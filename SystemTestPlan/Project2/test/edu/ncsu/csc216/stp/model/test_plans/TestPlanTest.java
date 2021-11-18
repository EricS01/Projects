package edu.ncsu.csc216.stp.model.test_plans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Tests the TestPlan class
 * @author Eric Samuel
 */
class TestPlanTest {

	/**
	 * Tests the TestPlan Constructor
	 */
	@Test
	public void testTestPlan() {
		TestPlan test = new TestPlan("hi");
		TestCase iD1 = new TestCase("ID1", "type1", "description1", "expected1");
		TestCase iD2 = new TestCase("ID2", "type2", "description2", "expected2");
		TestCase iD3 = new TestCase("ID3", "type3", "description3", "expected3");
		TestCase iD4 = new TestCase("ID4", "type4", "description4", "expected4");
		test.addTestCase(iD1);
		test.addTestCase(iD2);
		test.addTestCase(iD3);
		test.addTestCase(iD4);
		assertEquals("FAIL", iD1.getStatus());
		assertEquals("hi", test.getTestPlanName());
		assertEquals(4, test.getTestCasesAsArray().length);
	}

	/**
	 * Tests the compareTo method
	 */
	@Test
	void testCompareTo() {
		TestPlan test = new TestPlan("hi");
		TestPlan test1 = new TestPlan("hey");
		assertEquals(4, test.compareTo(test1));
	}

}
