/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;


/**
 * Tests the product class and its various methods
 * @author Eric Samuel
 */
class ProductTest {
	
	
	/**
	 * Test method for delete task by id
	 */
	@Test
	void testDeleteTask() {
		Product p = new Product("Product");
		assertEquals(0, p.getTasks().size());
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks1 = new Task(3, "Verifying", "Express", "F", "Eric", "Tim", "true",  notes);
		p.addTask(tasks1);
		p.deleteTaskById(3);
		assertNull(p.getTaskById(3));
		
	}
	/**
	 * Tests adding a task to a product
	 */
	@Test
	void testAddTaskStrings() {
		Product p = new Product("TestingAdd");
		p.addTask("Express", Type.FEATURE, "Eric", "Eric");
		assertEquals("Feature", p.getTaskById(1).getTypeLongName());
	}
	

	/**
	 * Tests an invalid product name
	 */
	@Test
	void testProductNameInvalid() {
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Product(null));
		assertEquals("Invalid product name.", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Product(""));
		assertEquals("Invalid product name.", e2.getMessage());
	}
	
	/**
	 * tests the product name when constructing
	 */
	@Test
	void testProudctName() {
		Product products = new Product("Testing");
		assertEquals("Testing", products.getProductName());
		
	}

	/**
	 * Test method for adding and getting task by id
	 */
	@Test
	void testAddTaskAndGetById() {
		Product p = new Product("Product");
		assertEquals(0, p.getTasks().size());
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks1 = new Task(3, "Verifying", "Express", "F", "Eric", "Tim", "true",  notes);
		Task tasks2 = new Task(2, "Backlog", "Express", "F", "Eric", "Tim", "true",  notes);
		Task tasks3 = new Task(3, "Verifying", "Express", "F", "Eric", "Tim", "true",  notes);
		p.addTask(tasks1);
		assertEquals("Verifying", p.getTaskById(3).getStateName());
		p.addTask(tasks2);
		assertEquals("Backlog", p.getTaskById(2).getStateName());
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> p.addTask(tasks3));
		assertEquals("Task cannot be added.", e1.getMessage());
	}
	
	/**
	 * Test method for the execute command mehtod
	 */
	@Test
	void testExecuteCommand() {
		Product p = new Product("Product");
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Timber");
		Task tasks1 = new Task(3, "Backlog", "Express", "F", "Eric", "Tim", "true",  notes);
		p.addTask(tasks1);
		Command c = new Command(CommandValue.CLAIM, "Eric", "text");
		p.executeCommand(3, c);
		assertEquals("Owned", p.getTaskById(3).getStateName());
		
	}
	

	
}
