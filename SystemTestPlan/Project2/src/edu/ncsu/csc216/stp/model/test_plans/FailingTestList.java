package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;
/**
 * This class represents the failing test cases from all the
 * different test plans in the system test plan
 * @author John Cirincione
 * @author Eric Samuel
 *
 */
public class FailingTestList extends AbstractTestPlan {
	/** The name of the failing test list */
	public static final String FAILING_TEST_LIST_NAME = "Failing Tests";
	
	/**
	 * Constructs the failing test
	 */
	public FailingTestList() {
		super(FAILING_TEST_LIST_NAME);
	}

	
	/**
	 * Adds a test case to the failing tests plan
	 * @param t the failing test case
	 */
	@Override
	public void addTestCase(TestCase t) {
		
		if(t.getStatus().equals("FAIL")){
			super.addTestCase(t);
		}
		else {
			throw new IllegalArgumentException("Cannot add passing test case.");
		}
	}
	
	/**
	 * Sets the name of the test plan
	 * @param testPlanName the name of the test plan
	 */
	@Override
	public void setTestPlanName(String testPlanName) {
		if(testPlanName.equals(FAILING_TEST_LIST_NAME)) {
			super.setTestPlanName(FAILING_TEST_LIST_NAME);
		}
		else {
			throw new IllegalArgumentException("The Failing Tests list cannot be edited.");
		}
	}

	@Override
	public String[][] getTestCasesAsArray() {
		
		String[][] failingCases = new String[getTestCases().size()][3];
		for(int i = 0; i < getTestCases().size(); i++) {
			//if(getTestCases().get(i).getStatus().equals("FAIL")) {;
			TestCase temp = getTestCase(i);
			if(!temp.isTestCasePassing()) {
				failingCases[i][0] = temp.getTestCaseId();
				failingCases[i][1] = temp.getTestType();
			if(temp.getTestPlan() != null) {
				failingCases[i][2] = temp.getTestPlan().getTestPlanName();
			}
			else {
				failingCases[i][2] = "";
			}
			}
		}
		return failingCases;
		
	}
	
	/**
	 * Clears the failing test cases
	 */
	public void clearTests() {
		
		for(int i = 0; i < super.getTestCases().size(); i++) {
			TestCase temp = super.getTestCases().get(i);
			if(!temp.isTestCasePassing()) {
				super.removeTestCase(i);
				i--;
			}
		}
		
	}



}
