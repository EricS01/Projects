/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Test class that tests the BacklogManager and its various methods
 * @author Eric Samuel
 */
class BacklogManagerTest {

	/** Instance of the BacklogManager class to be used for testing */
	private BacklogManager manager;
	
	/** valid file location */
	public static final String VALIDFILE = "test-files/tasks1.txt";
	
	@BeforeEach
	public void setUp() throws Exception {
		manager = BacklogManager.getInstance();
		manager.resetManager();
	}
	
	/**
	 * Test execute command method
	 */
	@Test
	void testExecuteCommand() {
		manager.resetManager();
		Command c = new Command(CommandValue.CLAIM, "Eric", "notes");
		manager.addProduct("Testing");
		manager.addTaskToProduct("Express", Type.BUG, "Eric", "Samuel");
		manager.executeCommand(1, c);
		assertEquals("Owned", manager.getTaskById(1).getStateName());
		
	}

	/**
	 * Tests saveToFile method
	 */
	@Test
	void testSaveToFile() {
		
		manager.resetManager();
		manager.addProduct("Testing");
		assertEquals("Testing", manager.getProductName());
		manager.loadProduct("Testing");
		manager.addTaskToProduct("Express", Type.BUG, "Eric", "Samuel");
		manager.saveToFile("test-files/newtestfile.txt");
		
		
	}
	/**
	 * Test saving to file throwing error message
	 */
	@Test
	void testSaveToFileInvalid() {
		
		manager.resetManager();
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> manager.saveToFile("test-files/newtestfile.txt"));
		assertEquals("Unable to save file.", e2.getMessage());
	}
	
	/**
	 * Test loading products that are invalid
	 */
	@Test
	void testLoadProudctInvalid() {
		manager.resetManager();
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> manager.loadProduct("testing"));
		assertEquals("Product not available.", e2.getMessage());
	}

	/**
	 * Tests loadFromFile method
	 */
	@Test
	void testLoadFromFile() {
		manager.resetManager();
		manager.loadFromFile(VALIDFILE);
		assertEquals("Shopping Cart Simulation", manager.getProductName());
		
	}

	

	/**
	 * Test method for getTaskById
	 */
	@Test
	void testGetTaskById() {

		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		manager.resetManager();
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> manager.loadFromFile("Dfdjk"));
		assertEquals("Unable to load file.", e1.getMessage());
				
	
		
	}

	/**
	 * Test method for delete task by id
	 */
	@Test
	void testDeleteTaskById() {
		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		manager.deleteTaskById(1);
		assertNull(manager.getTaskById(1));
		
	}



	/**
	 * Test method for addTaskToProduct
	 */
	@Test
	void testAddTaskToProduct() {
		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		String[][] test = manager.getTasksAsArray();
		assertEquals("1", test[0][0]);
	}

	/**
	 * Test method for getProductName
	 */
	@Test
	void testGetProductNameInvalid() {

		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> manager.addProduct(null));
		assertEquals("Invalid product name.", e1.getMessage());
		
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> manager.addProduct("Product1"));
		assertEquals("Invalid product name.", e2.getMessage());
		
	}


	/**
	 * Test method for clearProducts
	 */
	@Test
	void testClearProducts() {

		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		manager.clearProducts();
		assertEquals(0, manager.getProductList().length);
	}

	/**
	 * Test method for editProduct
	 */
	@Test
	void testEditProduct() {
		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		manager.editProduct("Product2");
		assertEquals("Product2", manager.getProductName());
	}
	/**
	 * Tests invalid edit product scenario
	 */
	@Test
	void testEditProductInvalid() {
		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> manager.editProduct(null));
		assertEquals("Invalid product name.", e2.getMessage());
		
		
		
	}
	
	/**
	 * Tests editing product when no product selected
	 */
	@Test
	void testEditProductInvalidEmpty() {
		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		manager.deleteProduct();
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> manager.editProduct("Test"));
		assertEquals("No product selected.", e3.getMessage());
	}


	/**
	 * Test method for deleteProduct
	 */
	@Test
	void testGetProductList() {
		manager.resetManager();
		manager.addProduct("Product1");
		manager.loadProduct("Product1");
		manager.addTaskToProduct("Tasking", Type.BUG, "Eric", "Samuel");
		assertEquals("Tasking", manager.getTaskById(1).getTitle());
		manager.deleteProduct();
		assertEquals(0, manager.getProductList().length);
	}


	
	

}
