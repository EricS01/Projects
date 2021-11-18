package edu.ncsu.csc216.stp.model.util;

/**
 * Creates a simple list where elements can be added and used.
 * @author John Cirincione
 * @param <E> the object in the list
 * 
 */
public class Log<E> implements ILog<E> {
	/** A log of elements */
	private E[] log;
	/** The size of the list */
	private int size;
	/** The initial capacity of the list */
	public static final int INIT_CAPACITY = 10;
	/**
	 * Constructor for the log list
	 */
	@SuppressWarnings("unchecked")
	public Log() {
		log = (E[]) new Object[INIT_CAPACITY];
		size = 0;
	}
	/**
	 * Adds the element to the end of the list.
	 * @param element element to add
	 * @throws NullPointerException if element is null 
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (size() + 1 > log.length) {
			growArray();
		}
		log[size] = element;
		size++;
	}
	/**
	 * Returns the element at the given index.
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		return log[idx];
	}
	/**
	 * Returns the number of elements in the list.
	 * @return number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Helper method that extends the length of the array
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] log2 = (E[]) new Object[size() * 2];
		for (int i = 0; i < log.length; i++) {
			log2[i] = log[i];
		}
		log = log2; 
		
	}

}
