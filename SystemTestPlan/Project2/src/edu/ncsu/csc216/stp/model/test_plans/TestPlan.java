package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * Testplan class that is a subclass of abstract test plan.
 * Represents a test plan in the system test plan program.
 * @author Eric Samuel
 * @author John Cirincione
 */
public class TestPlan extends AbstractTestPlan implements Comparable<TestPlan> {
	/**
	 * constructs a test plan
	 * @param testPlanName the name of the test plan
	 */
	public TestPlan(String testPlanName) {
		super(testPlanName);
		if(testPlanName.equalsIgnoreCase(FailingTestList.FAILING_TEST_LIST_NAME)){
			throw new IllegalArgumentException("Invalid name.");
		}
		
	}
	
	/**
	 * Gets the test plan's test cases as an array
	 * @return a 2d array of the test cases
	 */
	public String[][] getTestCasesAsArray() {
		
		String[][] cases = new String[getTestCases().size()][3];
		for(int i = 0; i < getTestCases().size(); i++) {
			cases[i][0] = getTestCases().get(i).getTestCaseId();
			cases[i][1] = getTestCases().get(i).getTestType();
			cases[i][2] = getTestCases().get(i).getStatus();
		}
		return cases;
	}
	
	/**
	 * Adds a test case to the test plan
	 * @param t is the test case objec to be added
	 */
	@Override
	public void addTestCase(TestCase t) {
		super.addTestCase(t);
		t.setTestPlan(this);
	}
	
	
	/**
	 * Compares the names of the test plans
	 */
	@Override
	public int compareTo(TestPlan p) {
		int compare = this.getTestPlanName().compareToIgnoreCase(p.getTestPlanName());
		return compare;
		
	}
}