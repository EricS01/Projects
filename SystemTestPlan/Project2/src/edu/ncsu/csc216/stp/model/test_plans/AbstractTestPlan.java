/**
 * 
 */
package edu.ncsu.csc216.stp.model.test_plans;

import java.util.Objects;

import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISwapList;
import edu.ncsu.csc216.stp.model.util.SwapList;

/**
 * Abstract class for test plan
 * @author John Cirincione
 *
 */
public abstract class AbstractTestPlan {
	
	/** Name of the test plan */
	private String testPlanName;
	
	/** swaplist of testcases */
	private SwapList<TestCase> testCases;
	/**
	 * Constructs a Test Plan object
	 * @param testPlanName the name of the plan
	 * @throws IllegalArgumentException if there are any errors when trying to save file
	 */
	public AbstractTestPlan(String testPlanName) {
		setTestPlanName(testPlanName);
		testCases = new SwapList<>();
	}
	
	/**
	 * Sets the name of the test plan
	 * @param testPlanName is the name of the test plan
	 * @throw IllegalArgument if name is empty
	 */
	public void setTestPlanName(String testPlanName) {
		if(testPlanName == null || "".equals(testPlanName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.testPlanName = testPlanName;
	}
	
	/**
	 * Returns the test plan name
	 * @return the test plans name
	 */
	public String getTestPlanName() {
		return testPlanName;
	}
	
	/**
	 * Gets the test cases in the form of a swaplist
	 * @return a swaplist of test cases
	 */
	public ISwapList<TestCase> getTestCases() {
		return testCases;
	}
	
	/**
	 * Adds a test case to the given test plan
	 * Throws an exception in the TestCase setter method if needed.
	 * @param t is the testcase object to be added
	 */
	public void addTestCase(TestCase t) {
		testCases.add(t);
	}
	
	/**
	 * Removes a test case from the test plan
	 * @param idx is the index of the case to be remvoed
	 * @return returns the test case object that was removed
	 */
	public TestCase removeTestCase(int idx) {
		TestCase temp = testCases.get(idx);
		testCases.remove(idx);
		return temp;
	}
	
	/**
	 * Returns a test case at a given index
	 * @param idx is the index of the test case to be returned
	 * @return returns the test case at the index
	 */
	public TestCase getTestCase(int idx) {
		
		return testCases.get(idx);
	}
	
	/**
	 * Returns the number of failing tests
	 * @return returns the number of failing test cases
	 */
	public int getNumberOfFailingTests() {
		int counter = 0;
		for(int i = 0; i < testCases.size(); i++) {

			if(testCases.get(i).getStatus().equals("FAIL")) {
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Adds a test results to a test case object
	 * @param idx is the id name of the test case to add results to
	 * @param passing is whether the test has been changed or not
	 * @param actualResults is the string of the results to be added
	 */
	public void addTestResult(int idx, boolean passing, String actualResults) {
		for(int i = 0; i < testCases.size(); i++) {
			if(i == idx) {
				testCases.get(i).addTestResult(passing, actualResults);
			}
		}
	}
	
	/**
	 * Gets the tests cases as a 2d array
	 * @return returns the test cases as a 2d array
	 */
	public abstract String[][] getTestCasesAsArray();

	@Override
	public int hashCode() {
		return Objects.hash(testPlanName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTestPlan other = (AbstractTestPlan) obj;
		return Objects.equals(testPlanName.toLowerCase(), other.testPlanName.toLowerCase());
	}

	
	
	
	
}
