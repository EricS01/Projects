package edu.ncsu.csc216.stp.model.util;


/**
 * Creates a list where elements can be added, removed, and swapped locations
 * @param <E> is the generic type
 * @author John Cirincione, Eric Samuel
 *
 */
public class SwapList<E> implements ISwapList<E> {

	/**
	 * Constructor for SwapList
	 */
	@SuppressWarnings("unchecked") // Only objects of type E are put into the array
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}
	
	/** Initial capacity of the list */
	private static final int INITIAL_CAPACITY  = 10;
	/** A swap list of elements */
	private E[] list;
	/** size of the list */
	private int size;
	
	/**
	 * Adds an element to the list
	 */
	@Override
	public void add(E element) {
		
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (size() + 1 > list.length) {
			growArray();
		}
		list[size] = element;
		size++;
	
		
	}
	/**
	 * Removes an element from the script
	 */
	@Override
	public E remove(int idx) {
		
		checkIndex(idx);
		E rtn = list[idx];
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}

		list[size - 1] = null; 
		size--;
		return rtn;
	}
	/**
	 * moves an element of the list up
	 */
	@Override
	public void moveDown(int idx) {
		
		checkIndex(idx);
		if (size() == 0) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		if(idx == size - 1) {
			list[idx] = list[idx];
		}
		else {
			
			E movingDown = list[idx + 1];
			E movingUp = list[idx];
			list[idx + 1] = movingUp;
			list[idx] = movingDown;
				
			
		}
	}
	/**
	 * Moves an element of the list down
	 */
	@Override
	public void moveUp(int idx) {
		checkIndex(idx);
		if(idx == 0) {
			list[0] = list[0];
		}
		else {
			E movingUp = list[idx - 1];
			E movingDown = list[idx];
			list[idx] = movingUp;
			list[idx - 1] = movingDown;
				
			}
		}
	
		
	
	/**
	 * Moves an element of the list to the front of the list
	 */
	@Override
	public void moveToFront(int idx) {
		checkIndex(idx);
		if(idx == 0) {
			list[0] = list[0];
		}
		else {
			E moved = list[idx];
			for(int i = idx; i > 0; i--) {
				list[i] = list[i - 1];
			}
			list[0] = moved;	
		}
		
	}

	/**
	 * Moves an element to the back of the list
	 */
	@Override
	public void moveToBack(int idx) {
		checkIndex(idx);
		if(idx == size - 1) {
			list[idx] = list[idx];
		}
		else {
			E moved = list[idx];
			for(int i = idx; i < size - 1; i++) {
				list[i]  = list[i + 1];	
			}
			list[size - 1] = moved;
		}
		
		
	}

	/**
	 * Gets an element of the list
	 * @return the element of the list
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		return list[idx];
	}
	/**
	 * Gets the size of the list
	 * @return the size of the list
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
		E[] list2 = (E[]) new Object[size() * 2];
		for (int i = 0; i < list.length; i++) {
			list2[i] = list[i];
		}
		list = list2; 
		
	}
	
	/**
	 * Helper method to simplify out of bounds checking.
	 * @param idx an index
	 * @throws IndexOutOfBoundsException if the index is out of bounds for this list
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}
	
	
	

}
