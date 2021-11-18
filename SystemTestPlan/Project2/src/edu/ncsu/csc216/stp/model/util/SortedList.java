package edu.ncsu.csc216.stp.model.util;


/**
 * Creates a list that is sorted by alphabetical order
 * @author John Cirincione
 *
 * @param <E> the object being passed
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {
	
	/**
	 * Creates a listNode to be added to the linked list
	 * @author John Cirincione
	 *
	 */
	public class ListNode {
		
		/** the object where data will be stored */
		public E data;
		/**  the next object */
		private ListNode next;
		
		/**
		 * Constructs a listNode with a given element and connects to another node
		 * @param data the element to be added
		 * @param next the node the element will point to
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
	
	/** holds the beginning of the list object */
	private ListNode front;
	
	/** represents the size of the list */
	private int size;
	
	/**
	 * Constructs the sortedList
	 */
	public SortedList() {
		front = null;
		size = 0;
	}
	
	/**
	 * Adds the element to the list in sorted order.
	 * @param element element to add
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element cannot be added 
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (front == null || element.compareTo(front.data) < 0 ) {
			front = new ListNode(element, front);
		}
		else {
			ListNode current = front;
			ListNode duplicateCheck = front;
			while (duplicateCheck.next != null) {
				if (duplicateCheck.data == element) {
					throw new IllegalArgumentException("Cannot add duplicate element.");
				}
				duplicateCheck = duplicateCheck.next;
			}
			while (current.next != null && current.next.data.compareTo(element) < 0) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		size++;
		
	}

	/**
	 * Returns the element from the given index.  The element is
	 * removed from the list.
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public E remove(int idx) {
		if(idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		E val = null;
		
		if(idx == 0) {
			val = front.data;
			front = front.next;	
		} 
		else {
			
			ListNode current = front;
			for(int i = 0; i < idx - 1; i++)
			{
				current = current.next;
			}
			
			val = current.next.data;
			current.next = current.next.next;
			
		}
		size--;
		return val;
	}

	/**
	 * Returns true if the element is in the list.
	 * @param element element to search for
	 * @return true if element is found
	 */
	
	@Override
	public boolean contains(E element) {
		ListNode current = front;
		for(int i = 0; i < size; i++) {
			if(current.data.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
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
		
		if(idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		ListNode current = front;
		ListNode count = front;
		
		for(int i = 0; i < size - 1; i++)
		{
			if(count.equals(count.next))
			{
				throw new IllegalArgumentException();
			}
			count = count.next;
		}
		
		for(int i = 0; i < idx; i++)
		{
			current = current.next;
		}
		
		return current.data;
	}
	/**
	 * Returns the number of elements in the list.
	 * @return number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}

	







}
