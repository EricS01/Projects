/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

import edu.ncsu.csc216.product_backlog.model.task.Task.Type;


/**
 * Tests the Task class and its methods
 * @author Eric Samuel
 */
class TaskTest {




	/**
	 * Test method for addNoteToList
	 */
	@Test
	void testAddNoteToList() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks = new Task(3, "Backlog", "Express", "F", "Eric", "Tim", "true",  notes);
		tasks.addNoteToList("texting add");
		assertEquals(2, notes.size());
		assertEquals("[Backlog] texting add", notes.get(1));
		
	}
	/**
	 * Tests the get state name method
	 */
	
	@Test
	void testGetStateName() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks = new Task(3, "Owned", "Express", "F", "Eric", "Tim", "true",  notes);
		assertEquals("Owned", tasks.getStateName());
		Task tasks1 = new Task(3, "Done", "Express", "F", "Eric", "Tim", "true",  notes);
		assertEquals("Done", tasks1.getStateName());
		
	}
	


	/**
	 * Test method for getTypeShortName
	 */
	@Test
	void testGetTypeShortName() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks = new Task(3, "Verifying", "Express", "B", "Eric", "Tim", "true",  notes);
		assertEquals("B", tasks.getTypeShortName());
		Task tasks1 = new Task(3, "Verifying", "Express", "F", "Eric", "Tim", "true",  notes);
		assertEquals("F", tasks1.getTypeShortName());
		Task tasks2 = new Task(3, "Verifying", "Express", "KA", "Eric", "Tim", "true",  notes);
		assertEquals("KA", tasks2.getTypeShortName());
		Task tasks3 = new Task(3, "Verifying", "Express", "TW", "Eric", "Tim", "true",  notes);
		assertEquals("TW", tasks3.getTypeShortName());
		
//		Task test = new Task(3, "Express", Type.BUG,"Eric", "note");
//		assertEquals("B", test.getTypeShortName());
	}

	/**
	 * Test method for getTypeLongName
	 */
	@Test
	void testGetTypeLongName() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks = new Task(3, "Done", "Express", "B", "Eric", "Tim", "true",  notes);
		assertEquals("Bug", tasks.getTypeLongName());
		Task tasks1 = new Task(3, "Done", "Express", "TW", "Eric", "Tim", "true",  notes);
		assertEquals("Technical Work", tasks1.getTypeLongName());
		Task tasks2 = new Task(3, "Done", "Express", "F", "Eric", "Tim", "true",  notes);
		assertEquals("Feature", tasks2.getTypeLongName());
		Task tasks3 = new Task(3, "Done", "Express", "KA", "Eric", "Tim", "true",  notes);
		assertEquals("Knowledge Acquisition", tasks3.getTypeLongName());
	}
	
	/**
	 * Tests invalid taskId
	 */
	@Test
	void testTaskIdInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(-1, "Done", "Express", "B", "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
	}
	
	/**
	 * Tests invalid owner
	 */
	@Test
	void testOwnerInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "Eric", "", "true",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "Eric", null, "true",  notes));
		assertEquals("Invalid task information.", e2.getMessage());
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "Eric", "unowned", "true",  notes));
		assertEquals("Invalid task information.", e3.getMessage());
		
		
	}
	
	/**
	 * tests the is verified method when its invalid
	 */
	
	@Test
	void testIsVerifiedInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "Eric", "Eric", "",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "Eric", "Eric", "yes",  notes));
		assertEquals("Invalid task information.", e2.getMessage());
	}
	
	/**
	 * tests the invalid notes
	 */
	@Test
	void testNotesInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "Eric", "Eric", "false",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * tests the note string invalid
	 */
	
	@Test
	void testNotesStringInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Express", Type.BUG, "Eric", ""));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * tests the invalid type
	 */
	@Test
	void testTypeInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Express", null, "Eric", "note"));
		assertEquals("Invalid task information.", e1.getMessage());
	}
	
	/**
	 * Tests invalid creator
	 */
	@Test
	void testCreatorInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "Express", "B", "", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
	}
	
	/**
	 * Tests invalid title
	 */
	@Test
	void testTitleInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Done", "", "B", "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
	}
	
	/**
	 * Tests invalid state
	 */
	@Test
	void testStateInvalid() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "", "Express", "B", "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Nothing", "Express", "B", "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e2.getMessage());
		
		
		
	}
	
	/**
	 * Tests the mini task constructor
	 */
	@Test
	void testMiniConstructor() {
		Task test = new Task(3, "Express", Type.BUG, "Eric", "note");
		assertEquals("unowned", test.getOwner());
		assertEquals(3, test.getTaskId());
		
	}
	
	/**
	 * Test method for isVerified
	 */
	@Test
	void testIsVerified() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		assertTrue(tasks.isVerified());
		
		Task tasks1 = new Task(3, "Rejected", "Express", "KA", "Eric", "Tim", "false", notes);
		assertFalse(tasks1.isVerified());
	}

	/**
	 * tests the type string when its invalid
	 */

	@Test
	void testTypeStringInvalid(){
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Processing", "Express", null, "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Processing", "Express", "ZZ", "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Task(3, "Processing", "Express", "", "Eric", "Tim", "true",  notes));
		assertEquals("Invalid task information.", e3.getMessage());
		
		
	}
	/**
	 * Tests that the toString formats correctly
	 */
	@Test
	void testToString() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		assertEquals("* 3,Processing,Express,TW,Eric,Tim,true\n"
				+ "- Timber\n", tasks.toString());
	}

	/**
	 * Test method for update
	 */
	@Test
	void testUpdate() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Command claim = new Command(CommandValue.CLAIM, "owner", "note");
		Command backlog = new Command(CommandValue.BACKLOG, null, "note");
		Command processing = new Command(CommandValue.PROCESS, null, "note");
		Command verifying = new Command(CommandValue.VERIFY, null, "note");
		Command done = new Command(CommandValue.COMPLETE, null, "note");
		Command rejected = new Command(CommandValue.REJECT, null, "note");
		
		Task tasks = new Task(3, "Backlog", "Express", "TW", "Eric", "Tim", "true",  notes);
		tasks.update(claim);
		assertEquals("Owned", tasks.getStateName());
		tasks.update(backlog);
		assertEquals("Backlog", tasks.getStateName());
		tasks.update(claim);
		assertEquals("Owned", tasks.getStateName());
		tasks.update(rejected);
		assertEquals("Rejected", tasks.getStateName());
		tasks.update(backlog);
		assertEquals("Backlog", tasks.getStateName());
		tasks.update(claim);
		tasks.update(processing);
		assertEquals("Processing", tasks.getStateName());
		tasks.update(verifying);
		assertEquals("Verifying", tasks.getStateName());
		tasks.update(done);
		assertEquals("Done", tasks.getStateName());
		tasks.update(processing);
		assertEquals("Processing", tasks.getStateName());
		tasks.update(verifying);
		tasks.update(done);
		tasks.update(backlog);
		assertEquals("Backlog", tasks.getStateName());
		tasks.update(rejected);
		assertEquals("Rejected", tasks.getStateName());
		
		Task tasks2 = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		tasks2.update(backlog);
		assertEquals("Backlog", tasks2.getStateName());
		
		Task tasks3 = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		tasks3.update(processing);
		assertEquals("Processing", tasks3.getStateName());
		
		Task tasks4 = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		tasks4.update(verifying);
		assertEquals("Verifying", tasks4.getStateName());
	
		Task tasks5 = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		tasks5.update(done);
		assertEquals("Done", tasks5.getStateName());
		
		
		
	}

	/**
	 * Tests the getName method for the concrete classes
	 */
	@Test
	void testGetNameConcreteClasses(){
		
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks5 = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		assertEquals("Processing", tasks5.getStateName());
		//CommandValue.BACKLOG.get
	}
	/**
	 * Test method for getNotesArray
	 */
	@Test
	void testGetNotesArray() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		notes.add("Fall");
		Task tasks2 = new Task(3, "Processing", "Express", "TW", "Eric", "Tim", "true",  notes);
		String[] arrayOfNotes = new String[notes.size()];
		for(int i = 0; i < notes.size(); i++) {
			arrayOfNotes[i] = notes.get(i);
		}
		assertEquals("Timber", arrayOfNotes[0]);
		String[] notesArray = tasks2.getNotesArray();
		assertEquals("Timber", notesArray[0]);
	}
	
	/**
	 * tests that the note string is properly constructed
	 */
	@Test
	void testNotesString() {
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("testing notes");
		Task t = new Task(3, "Processing", "Express", "F", "Eric", "Samuel", "false", notes);
		assertEquals("- testing notes\n", t.getNotesList());
	}

}
