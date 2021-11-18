package edu.ncsu.csc216.stp.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;



/**
 * Tests that the class properly saves the system test plan
 * to a file in the correct data format.
 * @author user
 *
 */
class TestPlanWriterTest {

	/**
	 * Tests the testplan writer constructor
	 */
	@Test
	void testTestPlanWriter() {
		ISortedList<TestPlan> p = new SortedList<TestPlan>();

		TestPlan plan = new TestPlan("e");
		plan.addTestCase(new TestCase("hi", "hey", "hello", "five"));
		p.add(plan);
		File g = new File("test-files/test-plans320984.txt");
		TestPlanWriter.writeTestPlanFile(g, p);
		
		File f = new File("");
		
		//Tests invalid file
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> TestPlanWriter.writeTestPlanFile(f, p));
		assertEquals("Unable to save file.", e1.getMessage());
	}


}
