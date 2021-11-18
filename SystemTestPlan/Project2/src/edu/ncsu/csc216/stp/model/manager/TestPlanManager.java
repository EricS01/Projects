/**
 * 
 */
package edu.ncsu.csc216.stp.model.manager;

import java.io.File;

import edu.ncsu.csc216.stp.model.io.TestPlanReader;
import edu.ncsu.csc216.stp.model.io.TestPlanWriter;
import edu.ncsu.csc216.stp.model.test_plans.AbstractTestPlan;
import edu.ncsu.csc216.stp.model.test_plans.FailingTestList;
import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;

/**
 * This class handles all the operations relating to the various test plans
 * of a given system test plan file.
 * @author Eric Samuel
 */
public class TestPlanManager {

	/** Determines whether a test plan changed or not */
	private boolean isChanged;
	
	/** Sorted list of test plans */
	private ISortedList<TestPlan> testPlans;
	
	/** The current test plan */
	private AbstractTestPlan currentTestPlan;
	
	/** Failing Test List object */
	private FailingTestList failingTestList;
	
	/**
	 * Constructs the test plan manager
	 */
	public TestPlanManager() {
		testPlans = new SortedList<TestPlan>();
		failingTestList = new FailingTestList();
		currentTestPlan = failingTestList;
		isChanged = false;
		
	}
	
	/**
	 * This method calls the TestPlanReader methods to read in the system test plan
	 * files. This is connected to the actual GUI load button
	 * @param filename is the file name that is being loaded
	 * 
	 */
	public void loadTestPlans(File filename) {
		
		
		testPlans = TestPlanReader.readTestPlansFile(filename);
		//setCurrentTestPlan(testPlans.get(0).getTestPlanName());
		getFailingTests();
		setCurrentTestPlan("Failing Tests");
		isChanged = true;
		
	}
	
	/**
	 * This method calls the TestPlanWriter methods to write
	 * the new system test plan to a file.
	 * @param filename is the the file name to be saved to
	 */
	public void saveTestPlans(File filename) {
		
		TestPlanWriter.writeTestPlanFile(filename, testPlans);
		isChanged = false;
	}
	
	/**
	 * Determines whether the test plan is changed or not
	 * @return true if tesplan is changed and false if not
	 */
	public boolean isChanged() {
		return isChanged;
		
	}
	
	/**
	 * This method adds a new, empty test plan to the system test plan program
	 * @param testPlan is the name of the new test plan
	 * @throws IllegalArgumentException if name is empty, matches existing test plan, or matches "Failing Tests"
	 */
	public void addTestPlan(String testPlan) {
		if(testPlan.equalsIgnoreCase(FailingTestList.FAILING_TEST_LIST_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for(int i = 0; i < testPlans.size(); i++) {
			if(testPlans.get(i).getTestPlanName().equalsIgnoreCase(testPlan)) {
				throw new IllegalArgumentException("Invalid name.");
			}
			
		}
		
		TestPlan p = new TestPlan(testPlan);
		testPlans.add(p);
		setCurrentTestPlan(testPlan);
		isChanged = true;
	}
	
	/**
	 * This program gets the names of all the test plans in the system test plan
	 * in the form of an Array.
	 * @return an array of strings of all the test plan names
	 */
	public String[] getTestPlanNames() {
		String[] testPlanNames = new String[testPlans.size() + 1];
		testPlanNames[0] = FailingTestList.FAILING_TEST_LIST_NAME;
		for(int i = 1; i < testPlans.size() + 1; i++) {
			
			testPlanNames[i] = testPlans.get(i - 1).getTestPlanName();
		}
		return testPlanNames;
		
	}
	
	/**
	 * This method retrieves all the failing test cases from the different test plans
	 */
	private void getFailingTests() {
		
		failingTestList = new FailingTestList();
		for(int i = 0; i < testPlans.size(); i++) {
			
			for(int j = 0; j < testPlans.get(i).getTestCases().size(); j++) {
			
				if(!testPlans.get(i).getTestCases().get(j).isTestCasePassing()) {
					
					failingTestList.addTestCase(testPlans.get(i).getTestCases().get(j));
					
				}
			}
			
			
		}
	}
	
	/**
	 * This method sets the active/current test plan in the System test plan
	 * that is will need to be active to edit/add
	 * @param testPlanName is the name of the test plan to be set to current
	 */
	public void setCurrentTestPlan(String testPlanName) {
		getFailingTests();
		boolean found = false;
		for(int i = 0; i < testPlans.size(); i++) {
			if(testPlans.get(i).getTestPlanName().equals(testPlanName) && !testPlans.get(i).getTestPlanName().equals("Failing Tests")) {
				currentTestPlan = testPlans.get(i);
				found = true;
				
			}
		}
		if(!found) {
			currentTestPlan = failingTestList;
			
		}
		
		
	}
	
	/**
	 * This method retrieves what the current test plan is
	 * @return returns the  current test plan object
	 */
	public AbstractTestPlan getCurrentTestPlan() {
		return currentTestPlan;
	}
	
	/**
	 * This method will allow for the test plan name to be changed.
	 * @param name is the new name that the test plan will be changed to.
	 * @throws IllegalArgumentException if user trys to edit "Failing Tests" or
	 *  If the test plan name is empty, matches an existing test plan, or matches “Failing Tests” (all case-insensitive)
	 */
	public void editTestPlan(String name) {
		if(currentTestPlan.equals(failingTestList)) {
			throw new IllegalArgumentException("The Failing Tests list may not be edited.");
		}
		if("Failing Tests".equals(name)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for(int i = 0; i < testPlans.size(); i++) {
			if(testPlans.get(i).getTestPlanName().equals(name)) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		TestPlan edited = null;
		for(int i = 0; i < testPlans.size(); i++) {
			if(currentTestPlan.getTestPlanName().equals(testPlans.get(i).getTestPlanName())){
				edited = testPlans.remove(i);
			}
		}
		edited.setTestPlanName(name);
		
		testPlans.add(edited);
		currentTestPlan = edited;
		isChanged = true;
		
	}
	
	/**
	 * This method allows for a test plan to be removed from the system test plan program.
	 * @throws IllegalArgumentException if user attempts to delete Failing Tests list.
	 */
	public void removeTestPlan() {
		if(currentTestPlan.equals(failingTestList)) {
			throw new IllegalArgumentException("The Failing Tests list may not be deleted.");
		}
		else {
			for(int i = 0; i < testPlans.size(); i++) {
				if(currentTestPlan.getTestPlanName().equals(testPlans.get(i).getTestPlanName())){
					testPlans.remove(i);
					currentTestPlan = failingTestList;
					isChanged = true;
				}
			}
		}
	}
	
	/**
	 * This method adds a test case to the current test plan
	 * @param c is the test case object to be added
	 * @throws IllegalArgumentException if user attempts to add case to Failing Tests.
	 */
	public void addTestCase(TestCase c) {
		for(int i = 0; i < testPlans.size(); i++) {
			if(currentTestPlan.getTestPlanName().equals(testPlans.get(i).getTestPlanName())){
				testPlans.get(i).addTestCase(c);
				isChanged = true;
				if(!c.isTestCasePassing()) {
					getFailingTests();
				}
			}
		}
	
	}
	
	/**
	 * Adds a test results to a test case object
	 * @param idx is the index of the test case with results being added.
	 * @param b is whether the test has been changed or not
	 * @param s is the string of the results to be added
	 */
	public void addTestResult(int idx, boolean b, String s) {
		
		for(int i = 0; i < testPlans.size(); i++) {
			if(currentTestPlan.getTestPlanName().equals(testPlans.get(i).getTestPlanName())){
				testPlans.get(i).getTestCase(idx).addTestResult(b, s);
				if(!b) {
					getFailingTests();
				}
			}
		}
	}
	
	/**
	 * This method deletes all the test cases in the given test plan
	 */
	public void clearTestPlans() {
		testPlans = new SortedList<TestPlan>();
		failingTestList = new FailingTestList();
		currentTestPlan = failingTestList;
		isChanged = false;
	}
	
	
}
