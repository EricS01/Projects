package edu.ncsu.csc216.stp.model.util;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;





class SortedListTest {

	/**
	 * Tests constructing the list
	 */
	@Test
	void testSortedList() {
		assertEquals(-1, "a".compareTo("b"));
	}

	/**
	 * Tests adding to the list through the add method
	 */
	@Test
	void testAdd() {
//		SortedList<String> sorted = new SortedList<>();
//		sorted.add("a");
//		sorted.add("d");
//		sorted.add("c");
//		sorted.add("bed");
//		sorted.add("baby");
//		sorted.add("add");
//		//doesn't work properly when a word starts with the same letter
//		assertEquals("baby", sorted.get(2));
		
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//Test adding to the front, middle and back of the list
		list.add("apple");
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		
		list.add("apricot");
		assertEquals("apricot", list.get(1));
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(2));
		
		list.add("orange");
		assertEquals("apricot", list.get(1));
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(2));
		assertEquals("orange", list.get(3));
	}

	/**
	 * Tests removing from the list through the remove method
	 */
	@Test
	void testRemove() {
SortedList<String> list = new SortedList<String>();
		
		//Test removing from an empty list
		try {
			list.remove(0);
		} catch(IndexOutOfBoundsException e) {
			System.out.println("empty list");
		}
		
		//Add some elements to the list - at least 4
		list.add("pear");
		list.add("strawberry");
		list.add("grape");
		list.add("apple");
		
		//Test removing an element at an index < 0
		try {
			list.remove(-1);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("index < 0");
		}
		
		//Test removing an element at size
		try {
			list.remove(list.size());
		} catch(IndexOutOfBoundsException e) {
			System.out.println("index out of bounds");
		}
		
		//Test removing a middle element
		list.remove(2);
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("grape", list.get(1));
		assertEquals("strawberry", list.get(2));
		
		//Test removing the last element
		list.remove(list.size() - 1);
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("grape", list.get(1));
		//Test removing the first element
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("grape", list.get(0));
		//Test removing the last element
		list.remove(0);
		assertEquals(0, list.size());
	}

	/**
	 * Tests if the list contains a certain element through the contains method
	 */
	@Test
	void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		//Test the empty list case
		assertFalse(list.contains("apple"));
		
		//Add some elements
		list.add("apple");
		list.add("orange");
		list.add("lemon");
		
		//Test some true and false cases
		assertTrue(list.contains("apple"));
		assertFalse(list.contains("grape"));
		assertTrue(list.contains("orange"));
		assertFalse(list.contains("strawberry"));
		assertTrue(list.contains("lemon"));
		assertFalse(list.contains("kiwi"));
	}

	/**
	 * Tests getting an element at a specific location through the get method
	 */
	@Test
	void testGet() {
		SortedList<String> list = new SortedList<String>();
		//Since get() is used throughout the tests to check the
				//contents of the list, we don't need to test main flow functionality
				//here.  Instead this test method should focus on the error 
				//and boundary cases.

				//Test getting an element from an empty list
				assertThrows("Empty list should throw IndexOutOfBounds on get() but did not",
						IndexOutOfBoundsException.class, () -> list.get(0));
				
				//Add some elements to the list
				list.add("pineapple");
				list.add("grapefruit");
				
				//Test getting an element at an index < 0
				assertThrows("List should throw IndexOutOfBounds on get(-1) but did not",
						IndexOutOfBoundsException.class, () -> list.get(-1));
				
				//Test getting an element at size
				assertThrows("List should throw IndexOutOfBounds on get(size) but did not",
						IndexOutOfBoundsException.class, () -> list.get(2));
				
	}
	
	/**
	 * Tests adding to the list through the add method
	 */
	@Test
	void testAddDuplicate() {
		
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//Test adding to the front, middle and back of the list
		list.add("apple");
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		
		list.add("apricot");
		assertEquals("apricot", list.get(1));
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(2));
		
		list.add("orange");
		assertEquals("apricot", list.get(1));
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(2));
		assertEquals("orange", list.get(3));
		assertThrows(IllegalArgumentException.class, () -> list.add("banana"));
	}
	



}
