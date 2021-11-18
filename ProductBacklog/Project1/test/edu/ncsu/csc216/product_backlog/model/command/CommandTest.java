/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;



/**
 * Tests the Command class and its methods
 * @author Eric Samuel
 */

class CommandTest {


	/**
	 * Test method for getCommand
	 */
	@Test
	void testGetCommand() {
		Command c = new Command(CommandValue.CLAIM, "Eric", "Hello");
		assertEquals(CommandValue.CLAIM, c.getCommand());
	}

	/**
	 * Test method for getNoteText
	 */
	@Test
	void testGetNoteText() {
		Command c = new Command(CommandValue.CLAIM, "Eric", "Hello");
		assertEquals("Hello", c.getNoteText());
	}

	/**
	 * Test method for getOwner
	 */
	@Test
	void testGetOwner() {
		Command c = new Command(CommandValue.CLAIM, "Eric", "Hello");
		assertEquals("Eric", c.getOwner());
	}
	
	/**
	 * Test empty or null owner when command is claim
	 */
	@Test
	void testGetEmptyorNullOwnerWhenClaim() {

		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.CLAIM, "", "Hello"));
		assertEquals("Invalid command.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.CLAIM, null, "Hello"));
		assertEquals("Invalid command.", e2.getMessage());
	
	
	}
	
	/**
	 * Test when there is an owner and command isn't claim
	 */
	@Test
	void testInvalidOnwerAssigned() {

		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.VERIFY, "Eric", "Hello"));
		assertEquals("Invalid command.", e2.getMessage());
	
	}
	
	/**
	 * Tests null or empty note when making command
	 */
	@Test
	void testNullorEmptyNote() {
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.VERIFY, "Eric", ""));
		assertEquals("Invalid command.", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.VERIFY, "Eric", null));
		assertEquals("Invalid command.", e3.getMessage());
		
	}
	
	/**
	 * Test null commandValue
	 */
	@Test
	void testNullCommandValue() {
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(null, "Eric", "Hello"));
		assertEquals("Invalid command.", e2.getMessage());
	}

}
