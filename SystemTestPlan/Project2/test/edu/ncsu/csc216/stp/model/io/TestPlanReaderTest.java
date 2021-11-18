package edu.ncsu.csc216.stp.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;


import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.util.ISortedList;

/**
 * Tests that the TestPlanReader correctly reads in a system test plan
 * and can identify test plans and test cases
 * @author Eric Samuel
 */
class TestPlanReaderTest {
	/** Valid file to be read */
	public static final File F = new File("test-files/test-plans0.txt");
	/** fake file to be read */
	public static final File N = new File("fake-file.txt");
	/**
	 * Tests test plan reader constructor
	 */
	@Test
	void testTestPlanReader() {
		ISortedList<TestPlan> plan = TestPlanReader.readTestPlansFile(F);
		
		assertTrue(plan.get(0).getTestCase(1).isTestCasePassing());
		assertFalse(plan.get(0).getTestCase(0).isTestCasePassing());
		assertThrows(IllegalArgumentException.class, () -> TestPlanReader.readTestPlansFile(N));
		
	}

}
