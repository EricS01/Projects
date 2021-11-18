package edu.ncsu.csc216.stp.model.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class SwapListTest {

	/**
	 * Tests adding to the list through the add method
	 */
	@Test
	void testAdd() {
		
		SwapList<String> log = new SwapList<>();
		log.add("cherry");
        log.add("apple");
        log.add("banana");
        assertEquals("cherry", log.get(0));
        assertEquals("apple", log.get(1));
        assertEquals("banana", log.get(2));
	}

	/**
	 * Tests removing from the list through the remove method
	 */
	@Test
	void testRemove() {
        SwapList<String> list = new SwapList<>();
        assertEquals(0, list.size());
        list.add("apple");
        assertEquals(1, list.size());
        list.add("banana");
        assertEquals(2, list.size());
        list.add("cherry");
        assertEquals(3, list.size());
        list.remove(0);
        assertEquals(2, list.size());
        list.remove(0);
        assertEquals(1, list.size());
        list.remove(0);
        assertEquals(0, list.size());
	}

	/**
	 * Tests moving an element up through the moveUp method
	 */
	@Test
	void testMoveDown() {
		SwapList<String> log = new SwapList<>();
		log.add("cherry");
        log.add("apple");
        log.add("banana");
        log.moveDown(0);
        assertEquals("cherry", log.get(1));
        log.moveDown(2);
        assertEquals("banana", log.get(2));
	}

	/**
	 * Tests moving an element down through the moveDown method
	 */
	@Test
	void testMoveUp() {
		SwapList<String> log = new SwapList<>();
		log.add("cherry");
        log.add("apple");
        log.add("banana");
        log.moveUp(1);
        assertEquals("apple", log.get(0));
	}

	/**
	 * Tests moving an element to the front of the list through the moveToFront method
	 */
	@Test
	void testMoveToFront() {
		SwapList<String> log = new SwapList<>();
		log.add("cherry");
        log.add("apple");
        log.add("banana");
        log.moveToFront(2);
        assertEquals("banana", log.get(0));
        assertEquals("cherry", log.get(1));
        assertEquals("apple", log.get(2));
	}

	/**
	 * Tests moving an element to the back of the list through the moveToBack method
	 */
	@Test
	void testMoveToBack() {
		SwapList<String> log = new SwapList<>();
		log.add("cherry");
        log.add("apple");
        log.add("banana");
        log.moveToBack(0);
        log.moveToBack(2);
        assertEquals("apple", log.get(0));
        assertEquals("banana", log.get(1));
        assertEquals("cherry", log.get(2));
	}

	/**
	 * Tests getting an element at a specific location through the get method
	 */
	@Test
	void testGet() {
		SwapList<String> list = new SwapList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("cherry", list.get(2));
	}



}
