package edu.ncsu.csc216.stp.model.tests;

/**
 * This class represents the test results of a given test case
 * @author Eric Samuel
 *
 */
public class TestResult {
	/** a passing test */
	public static final String PASS = "PASS";
	/** A failing test */
	public static final String FAIL = "FAIL";
	/** Boolean of whether or not a test is passing */
	private boolean passing;
	/** The actual results of a test case */
	private String actualResults;
	
	/**
	 * Constructs a test result objects of a given test case
	 * @param passing whether or not the test is passing
	 * @param actualResults the actual results of the test
	 */
	public TestResult(boolean passing, String actualResults) {
		setActualResults(actualResults);
		setPassing(passing);
		
	}
	
	/**
	 * Returns the actual results of the test case
	 * @return Returns the actual results of the test case
	 */
	public String getActualResults() {
		return actualResults;
	}
	
	/**
	 * Sets the actual results of the test case
	 * @param actualResults is the string of the actual results
	 * @throws IllegalArgumentException if actualResults is empty
	 */
	private void setActualResults(String actualResults) {
		if (actualResults == null || "".equals(actualResults)) {
			throw new IllegalArgumentException("Invalid test results.");
		}
		this.actualResults = actualResults;
	}
	
	/**
	 * Determines if the test case is passing or failing
	 * @return true is the case is passing and false if failing
	 */
	public boolean isPassing() {
			
		return passing;
	}
	
	/**
	 * Sets if the case is passing or failing
	 * @param passing is the boolean variable of if its passing or failing (true or false)
	 */
	private void setPassing(boolean passing) {
		this.passing = passing;
	}
	
	/**
	 * Converts the results to a proper string form that can be used throughout the program
	 * @return the string form of the test results
	 */
	public String toString() {
		String s = "";
		if (isPassing()) {
			s += PASS + ": " + getActualResults();
		}
		else {
			s += FAIL + ": " + getActualResults();
		}
		return s;
	}
}
