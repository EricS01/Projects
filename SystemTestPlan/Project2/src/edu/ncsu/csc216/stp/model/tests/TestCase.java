package edu.ncsu.csc216.stp.model.tests;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.util.Log;
/**
 * This class creates a test case and these cases are contained in a test plan
 * @author John Cirincione
 * @author Eric Samuel
 *
 */
public class TestCase {
	/** The id of the test case */
	private String testCaseId;
	/** The type of the test case */
	private String testType;
	/** A description of the test */
	private String testDescription;
	/** The expected results of the test */
	private String expectedResults;
	/** A test plan object being assigned */
	private TestPlan testPlan;
	/** log of test results */
	private Log<TestResult> testResults;
	/**
	 * Constructs a test case
	 * @param testCaseId the id of the test
	 * @param testType the type of the test
	 * @param testDescription the description of the test
	 * @param expectedResults the expected results of the test
	 */
	public TestCase(String testCaseId, String testType, String testDescription, String expectedResults) {
		testResults = new Log<>();
		testPlan = null;
		setTestCaseId(testCaseId);
		setTestType(testType);
		setTestDescription(testDescription);
		setExpectedResults(expectedResults);
	}
	/**
	 * Gets the id of the test
	 * @return testCaseId the id of the test
	 */
	public String getTestCaseId() {
		return testCaseId;
	}
	/**
	 * Sets the test id
	 * @param testCaseId the testCaseId to set
	 * @throws IllegalArgumentException if id is empty
	 */
	private void setTestCaseId(String testCaseId) {
		if (testCaseId == null || "".equals(testCaseId)) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.testCaseId = testCaseId;
	}
	/**
	 * Gets the test type
	 * @return the testType
	 */
	public String getTestType() {
		return testType;
	}
	/**
	 * Sest the test type
	 * @param testType the testType to set
	 * @throws IllegalArgumentException if testype is empty
	 */
	private void setTestType(String testType) {
		if (testType == null || "".equals(testType)) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.testType = testType;
	}
	/**
	 * gets the test description
	 * @return the testDescription
	 */
	public String getTestDescription() {
		return testDescription;
	}
	/**
	 * Sets the tests description
	 * @param testDescription the testDescription to set
	 * @throws IllegalArgumentException if testDescription is empty
	 */
	private void setTestDescription(String testDescription) {
		if (testDescription == null || "".equals(testDescription)) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.testDescription = testDescription;
	}
	
	/**
	 * Adds the results of test case
	 * @param b determines if case is true or false
	 * @param s the contents of the results
	 */
	public void addTestResult(boolean b, String s) {
		try {
			TestResult result = new TestResult(b, s);
			testResults.add(result);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid test results.");
		}
	}
	
	/**
	 * Determines if the test case is passing or not
	 * @return true if its passing and false if its failing
	 */
	public boolean isTestCasePassing() {
		if (testResults.size() == 0) {
			return false;
		}
		return testResults.get(testResults.size() - 1).isPassing();
	}
	
	/**
	 * Returns the status of the test case
	 * @return the status of the test case
	 */
	public String getStatus() {
		if (isTestCasePassing()) {
			return TestResult.PASS;
		}
		return TestResult.FAIL;
	}
	
	/**
	 * Returns the contents of the actual result
	 * @return the contents of the actual results section
	 */
	public String getActualResultsLog() {
		String s = "";
		for(int i = 0; i < testResults.size(); i++) {
			s += "- " + testResults.get(i).toString() + "\n";
		}
		return s;
	}
	
	/**
	 * Returns the string version of the test case
	 * @return returns the string version of the test case
	 */
	public String toString() {
		String s = "";
		s += "# " + getTestCaseId() + "," + getTestType() + "\n" + "* " + getTestDescription() + "\n" + "* " + getExpectedResults() + "\n" + getActualResultsLog(); 
		return s;
	}
	
	/**
	 * Gets the expected results of the test
	 * @return the expectedResults
	 */
	public String getExpectedResults() {
		return expectedResults;
	}
	/**
	 * Sets the expected results of the test
	 * @param expectedResults the expectedResults to set
	 * @throws IllegalArgumentException if expectedResults is empty
	 */
	public void setExpectedResults(String expectedResults) {
		if (expectedResults == null || "".equals(expectedResults)) {
			throw new IllegalArgumentException("Invalid test information.");
		}
		this.expectedResults = expectedResults;
	}
	/**
	 * Gets the test plan the test case is in
	 * @return the testPlan
	 */
	public TestPlan getTestPlan() {
		return testPlan;
	}
	/**
	 * Sets the test plan
	 * @param testPlan the testPlan to set
	 * @throws IllegalArgumentException if testPlan is null
	 */
	public void setTestPlan(TestPlan testPlan) {
		if (testPlan == null) {
			throw new IllegalArgumentException("Invalid test plan.");
		}
		this.testPlan = testPlan;
	}
	
}
