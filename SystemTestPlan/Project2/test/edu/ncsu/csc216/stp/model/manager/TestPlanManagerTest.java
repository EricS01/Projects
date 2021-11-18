package edu.ncsu.csc216.stp.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Tests the TestPlanManager class
 * @author Eric Samuel
 */
class TestPlanManagerTest {

	/** Valid file to be read */
	public static final File F = new File("test-files/test-plans0.txt");
	
	/**
	 * Tests the addTestPlan method
	 */
	@Test
	void testAddTestPlan() {
	
		TestPlanManager manager = new TestPlanManager();
		manager.addTestPlan("WolfScheduler");
		manager.addTestPlan("PackScheduler");
		TestCase testCase = new TestCase("ID", "Type", "Description", "Expected");
		testCase.addTestResult(false, "Description");
		TestCase testCase1 = new TestCase("Add", "Test", "Add test", "10");
		testCase1.addTestResult(true, "hi");
		manager.addTestCase(testCase);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase);
		assertEquals("WolfScheduler", manager.getTestPlanNames()[2]);
	}


	/**
	 * Tests the editTestPlan method
	 */
	@Test
	void testEditTestPlan() {
		TestPlanManager manager = new TestPlanManager();
		manager.addTestPlan("WolfScheduler");
		manager.addTestPlan("PackScheduler");
		TestCase testCase = new TestCase("ID", "Type", "Description", "Expected");
		testCase.addTestResult(false, "Description");
		TestCase testCase1 = new TestCase("Add", "Test", "Add test", "10");
		testCase1.addTestResult(true, "hi");
		manager.addTestCase(testCase);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase);
		manager.editTestPlan("Zahir");
		assertEquals("WolfScheduler", manager.getTestPlanNames()[1]);
		assertEquals("Zahir", manager.getTestPlanNames()[2]);
	}

	/**
	 * Tests the clearTestPlans method
	 */
	@Test
	void testClearTestPlans() {
		TestPlanManager manager = new TestPlanManager();
		manager.addTestPlan("WolfScheduler");
		manager.addTestPlan("PackScheduler");
		TestCase testCase = new TestCase("ID", "Type", "Description", "Expected");
		testCase.addTestResult(false, "Description");
		TestCase testCase1 = new TestCase("Add", "Test", "Add test", "10");
		testCase1.addTestResult(true, "hi");
		manager.addTestCase(testCase);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase);
		manager.editTestPlan("Zahir");
		manager.clearTestPlans();
		assertEquals(1, manager.getTestPlanNames().length);
	}
	
	@Test
	public void testLoadTestPlans() {
		TestPlanManager manager = new TestPlanManager();
		manager.loadTestPlans(F);
		assertEquals(3, manager.getCurrentTestPlan().getTestCasesAsArray().length);
	}
	
	/**
	 * Tests removing test plans from manager
	 */
	@Test
	public void testRemoveTestPlans() {
		TestPlanManager manager = new TestPlanManager();
		manager.addTestPlan("WolfScheduler");
		manager.addTestPlan("PackScheduler");
		TestCase testCase = new TestCase("ID", "Type", "Description", "Expected");
		testCase.addTestResult(false, "Description");
		TestCase testCase1 = new TestCase("Add", "Test", "Add test", "10");
		testCase1.addTestResult(true, "hi");
		manager.addTestCase(testCase);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase);
	
		manager.removeTestPlan();
		
		assertEquals("Failing Tests", manager.getCurrentTestPlan().getTestPlanName());
		
	}
	
	@Test
	public void testAddTestResults() {
		TestPlanManager manager = new TestPlanManager();
		manager.addTestPlan("WolfScheduler");
		TestCase testCase = new TestCase("ID", "Type", "Description", "Expected");
		testCase.addTestResult(false, "Description");
		TestCase testCase1 = new TestCase("Add", "Test", "Add test", "10");
		testCase1.addTestResult(true, "hi");
		manager.addTestCase(testCase);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase1);
		manager.addTestCase(testCase);
		manager.addTestResult(0, false, "hi");
		assertEquals("- FAIL: Description\n- FAIL: hi\n", manager.getCurrentTestPlan().getTestCase(0).getActualResultsLog());
	}

}
