/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.backlog;


import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;


/**
 * This is the class that handles alot of user input and how this input will affect 
 * products and tasks
 * @author Eric Samuel
 */
public class BacklogManager {
	
	/** an arraylist of various products */
	private ArrayList<Product> products = new ArrayList<Product>();
	
	/** represents the current product selected */
	private Product currentProduct;
	
	/** Singleton is a static instance of the BacklogManager class */
	private static BacklogManager singleton;
	
	private BacklogManager() {
		
	}
	
	/**
	 * Determines if instance of class is single and makes sure there is only
	 * one instance of the class
	 * @return Singleton value
	 */
	public static BacklogManager getInstance() {
		
		if(singleton == null) {
			singleton = new BacklogManager(); 
		}
		return singleton;
		
	}
	
	/**
	 * saves products and tasks to file
	 * @param file is the filename being saved to
	 * @throws IllegalArgumentException if current product is null or there is no task in current product
	 */
	public void saveToFile(String file) {
		if(currentProduct == null || currentProduct.getTasks().size() == 0) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		ProductsWriter.writeProductsToFile(file, products);
		
			
	}
	
	/**
	 * loads products and tasks from a file
	 * @param file is the file being loaded from
	 */
	public void loadFromFile(String file) {
		
			
			ArrayList<Product> importedProducts = ProductsReader.readProductsFile(file);
			currentProduct = importedProducts.get(0);
			for(Product p : importedProducts) {
				products.add(p);
			}
			}
	
	
	
	/**
	 * loads product
	 * @param productName is the name of the product that is being set to current product
	 * @throws IllegalArgumentException if requested product is not in the list
	 */
	public void loadProduct(String productName) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().equals(productName)) {
				currentProduct = products.get(i);
			}
//			else {
//				throw new IllegalArgumentException("Product not available.");
//			}
		}
		if(currentProduct == null) {
			throw new IllegalArgumentException("Product not available.");
		}
	}
	

	
//	private void isDuplicateProduct(String productName) {
//		for(int i = 0; i < products.size(); i++) {
//			if(products.get(i).getProductName().equals(productName)) {
//				throw new IllegalArgumentException("Duplicate exists");
//			}
//		}
//	}
	
	/**
	 * returns a task as a 2d array
	 * @return the 2d array of the tasks
	 */
	public String[][] getTasksAsArray(){
		
		if(currentProduct == null) {
			return null;
		}
		String[][] arrayOfTasks = new String[currentProduct.getTasks().size()][4];
		for(int i = 0; i < currentProduct.getTasks().size(); i++) {
			
				String idString = Integer.toString(currentProduct.getTasks().get(i).getTaskId());
				arrayOfTasks[i][0] = idString;
				arrayOfTasks[i][1] = currentProduct.getTasks().get(i).getStateName();
				arrayOfTasks[i][2] = currentProduct.getTasks().get(i).getTypeLongName();
				arrayOfTasks[i][3] = currentProduct.getTasks().get(i).getTitle();
				
				
			
		}
		return arrayOfTasks;
	}
	
	/**
	 * returns a task based on id
	 * @param id is the id of the task to be returned
	 * @return the task based on the id parameter
	 */
	public Task getTaskById(int id){
	
		return currentProduct.getTaskById(id);
	}
	
	/**
	 * executes the given command
	 * @param id is the task id that is about to get a command
	 * @param command the command to be executed
	 */
	public void executeCommand(int id, Command command) {
		currentProduct.executeCommand(id, command);
	}

	/**
	 * deletes a task by id
	 * @param id of task to be deleted
	 */
	public void deleteTaskById(int id) {
		currentProduct.deleteTaskById(id);
	}
	
	/**
	 * adds a task to a product
	 * @param title is the title of the task
	 * @param type is the type of the task
	 * @param creator is the creator of the task
	 * @param owner is the owner of the task
	 */
	public void addTaskToProduct(String title, Type type, String creator, String owner) {
		currentProduct.addTask(title, type, creator, owner);
	}
	
	/**
	 * returns the product name
	 * @return the name of the current product
	 */
	public String getProductName() {
		if(currentProduct == null) {
			return null;
		}
		else {
		return currentProduct.getProductName();
		}
	}
	
	/**
	 * returns a string of array of product names in the order they are listed
	 * @return productArray is the string array of product names;
	 */
	public String[] getProductList() {
		String[] productArray = new String[products.size()];
		for(int i =  0; i < products.size(); i++) {
			productArray[i] = products.get(i).getProductName();
		}
		return productArray;
	}
	
	/**
	 * clears the products list
	 */
	public void clearProducts() {
		products.clear();
		currentProduct = null;
	}
	
	/**
	 * edits the product
	 * @param productName is the product being edited
	 * @throws IllegalArgumentException if current product is null when attempting to edit
	 */
	public void editProduct(String productName) {
		if(currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		if(productName == null || productName.length() == 0) {
			throw new IllegalArgumentException("Invalid product name.");
		}
		currentProduct.setProductName(productName);
		
	}
	
	/**
	 * adds a product and sets it to the current product
	 * @param productName is the product being added
	 * @throws IllegalArgumentException if there is already a product with the same name 
	 * or if the name is null or empty
	 */
	public void addProduct(String productName) {
		Product product = new Product(productName);
		
		if(productName == null || productName.length() == 0) {
			throw new IllegalArgumentException("Invalid product name.");
		}
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().equalsIgnoreCase(productName)) {
				throw new IllegalArgumentException("Invalid product name.");
			}
		}
		products.add(product);
		
		loadProduct(productName);
		
		
	}
	
	/**
	 * deletes the current product
	 * @throws IllegalArgumentException if current product is null when attempting to delete
	 */
	public void deleteProduct() {
		if(currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		products.remove(currentProduct);
		if(products.size() == 0) {
			currentProduct = null;
		}
		else {
			currentProduct = products.get(0);
		}
			
	}
	
	/**
	 * resets the backlogmanager
	 */
	protected void resetManager() {
		singleton = null;
	}
	
	
	
	
	
}
