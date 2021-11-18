package edu.ncsu.csc216.stp.model.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.util.ISortedList;

/**
 * Writes the list of test plans and test cases to a file with
 * the given format needed to read them.
 * @author Eric Samuel
 */
public class TestPlanWriter {
	
	/**
	 * Writes the System test plan to the file with the given format
	 * @param f is the file the system test plan is writen to
	 * @param test is the list of testplans to write
	 */
	public static void writeTestPlanFile(File f, ISortedList<TestPlan> test) {
		try (PrintStream fileWriter = new PrintStream(f)) {
			for(int i = 0; i < test.size(); i++) {
				TestPlan plan = test.get(i);
				fileWriter.println("! " + plan.getTestPlanName());
				for (int j = 0; j < plan.getTestCases().size(); j++) {
					fileWriter.print(plan.getTestCases().get(j).toString());
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
	}
	
}
