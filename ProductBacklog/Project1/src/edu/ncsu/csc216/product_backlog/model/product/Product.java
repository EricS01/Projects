/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;



import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;


/**
 * This class handles the main functions and information
 * relating to the product object. 
 * @author Eric Samuel
 */
public class Product {
	
	/** the name of the product */
	private String productName;
	
	/** the counter of the product */
	private int counter;
	
	/** ArrayList of the task type */
	private ArrayList<Task> tasks;
	
	/**
	 * Constructor for the product object
	 * @param productName is the name of the product
	 */
	public Product(String productName){
		setProductName(productName);
		tasks = new ArrayList<Task>();
		this.counter = 1;
	}
	
	/**
	 * sets the product name
	 * @param productName is the name of the product
	 * @throws IllegalArgumentException if productName is null or empty
	 */
	
	public void setProductName(String productName) {
		if(productName == null || productName.length() == 0) {
			throw new IllegalArgumentException("Invalid product name.");
		}
		this.productName = productName;
	}
	
	/**
	 * returns the product name
	 * @return productName which is the name of the product
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * sets the task counter
	 */
	
	private void setTaskCounter() {
		if(tasks.size() == 0) {
			this.counter = 1;
		}
		else {
			int max = tasks.get(0).getTaskId();
			for(int i = 0; i < tasks.size(); i++) {
				if(tasks.get(i).getTaskId() > max) {
					max = tasks.get(i).getTaskId();
				}
				
			}
			this.counter = max + 1;
		
		}
	}
	

//	
//	private void emptyList() {
//		tasks = new ArrayList<Task>();
//	}
	
	/**
	 * Adds a task to a product
	 * @param task is the task to be added
	 * @throws IllegalArgumentException if task already exists with given id
	 */
	public void addTask(Task task) {
		
		

	
			int locationToBeAdded = tasks.size();
		
		
		for(int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() == task.getTaskId()) {
				throw new IllegalArgumentException("Task cannot be added.");
			}
			else if(tasks.get(i).getTaskId() > task.getTaskId()) {
				locationToBeAdded = i;
				
			}
			
		}
		tasks.add(locationToBeAdded, task);
		setTaskCounter();

	}
	
	/**
	 * adds a task to the product with the given parameters
	 * @param title the title of the task
	 * @param type the type of task
	 * @param creator the creator the task
	 * @param owner the owner of the task
	 */
	public void addTask(String title, Type type, String creator, String owner) {
		Task newTask = new Task(counter, title, type, creator, owner);
		
		addTask(newTask);
		setTaskCounter();
		
		
	}
	
	/**
	 * gets the task in the list
	 * @return the list of tasks
	 */
	public ArrayList<Task> getTasks(){
		return tasks;
	}
	
	/**
	 * gets given task from id
	 * @param id of the task
	 * @return the task based on a given id
	 */
	public Task getTaskById(int id) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() == id) {
				return tasks.get(i);
			}
		}
		return null;
	}
	
	/**
	 * executes a given command from the user
	 * @param id is the task id that is about to get a command
	 * @param command is the command to be executed
	 */
	public void executeCommand(int id, Command command) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() == id) {
				tasks.get(i).update(command);
			}
		}
	}
	
	/**
	 * deletes a given task by the id number
	 * @param id of the task to be deleted
	 */
	public void deleteTaskById(int id) {
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getTaskId() == id) {
				tasks.remove(i);
			}
		}
	}
}
