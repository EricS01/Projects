package edu.ncsu.csc216.stp.model.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;

/**
 * Will read in a file that contains the system test plan. Will divide
 * up the contents of the file into test plans and cases and eventually
 * will added these to a sortedlist.
 * @author Eric Samuel
 */
public class TestPlanReader {
	
	/** Counter used for checking invalid actual results */
	private static int counter = 0;
	
	/**
	 * Constructs the testPlanReader
	 */
	public TestPlanReader() {
		//
	}
	
	/**
	 * This method reads in the system test plan from the given file
	 * @param f is the file the system test plan is being read from
	 * @return a sorted list containing the system test plan
	 * @throws FileNotFoundException if the file to read from can't be found.
	 */
	public static ISortedList<TestPlan> readTestPlansFile(File f) {
		ISortedList<TestPlan> testPlans = new SortedList<>();
		FileReader readFile = null;
		try {
			readFile = new FileReader(f);
			BufferedReader bufferedReader  = new BufferedReader(readFile);
			StringBuffer inputBuffer = new StringBuffer();
			String token = "";
			String line = "";
			while((line = bufferedReader.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append("\n");
			}
			bufferedReader.close();
			token = inputBuffer.toString().trim();
			if (token.charAt(0) != '!') {
				throw new IllegalArgumentException("Unable to load file.");
			}
			String[] plan = token.split("\\r?\\n?[!]");
			for(int i = 1; i < plan.length; i++) {
				
				String[] testCase = plan[i].split("\\r?\\n?[#]");
				TestPlan planAdd = processTestPlan(testCase[0].trim());
				for(int j = 1; j < testCase.length; j++) {
					try {
						TestCase test = processTest(testCase[j].trim());
						planAdd.addTestCase(test);
						if(counter > 0) {
							planAdd.removeTestCase(planAdd.getTestCases().size() - 1);
						}
					}
					catch (IllegalArgumentException e) {
						// Line is invalid
					}
				}
				testPlans.add(planAdd);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	

		
		return testPlans;
	}
	
	/**
	 * Processes a line and gets the testplan
	 * @param testPlan is the line to be processed
	 * @return returns a TestPlan object
	 */
	private static TestPlan processTestPlan(String testPlan) {
		TestPlan plan = new TestPlan(testPlan);
		return plan;
	}
	
	/**
	 * Processes a line and gets the testcase
	 * @param testCase is the line to be processed
	 * @return returns a TestCase object
	 */
	private static TestCase processTest(String testCase) {
		try {
			
			String[] actualResult = testCase.split("\n- ");
			String[] id = actualResult[0].split(",");
			String[] descriptExpect = id[1].split("\\r?\\n?[*] ");
			
			TestCase test = new TestCase(id[0], descriptExpect[0], descriptExpect[1], descriptExpect[2]);
			
			for (int i = 1; i < actualResult.length; i++) {
				String s = actualResult[i];
				
				if (s.substring(0, 4).equals("FAIL")) {
					String result = s.substring(6, s.length());
					test.addTestResult(false, result);
				}
				else if (s.substring(0, 4).equals("PASS")) {
					String result = s.substring(6, s.length());
					test.addTestResult(true, result);
				}
				else {
					counter++;
				}
	
				
				
			}
			
			return test;
			
			
		
		} catch(IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Index out of bounds.");
		}
	}
}
