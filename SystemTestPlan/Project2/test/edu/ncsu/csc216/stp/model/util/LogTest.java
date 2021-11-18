package edu.ncsu.csc216.stp.model.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;




class LogTest {



	/**
	 * Tests adding to the log
	 */
	@Test
	public void testAdd() {
		
		Log<String> log = new Log<>();
		log.add("cherry");
        log.add("apple");
        log.add("banana");
        assertEquals("cherry", log.get(0));
        assertEquals("apple", log.get(1));
        assertEquals("banana", log.get(2));
	}

	/**
	 * Tests getting an element of the log at a given location
	 */
	@Test
	public void testGet() {
		  Log<String> log = new Log<>();
	        log.add("apple");
	        log.add("banana");
	        log.add("cherry");
	        assertEquals("apple", log.get(0));
	        assertEquals("banana", log.get(1));
	        assertEquals("cherry", log.get(2));
	}

	/**
	 * Tests getting the size of the log
	 */
	@Test
	public void testSize() {
		Log<String> log = new Log<>();
        assertEquals(0, log.size());
        log.add("apple");
        assertEquals(1, log.size());
        log.add("banana");
        assertEquals(2, log.size());
        log.add("cherry");
        assertEquals(3, log.size());

	}
	
	/**
	 * Tests grow array
	 */
	@Test
	public void testGrowArray() {
		Log<String> log = new Log<>();
        log.add("apple");
        log.add("banana");
        log.add("cherry");
        log.add("dragonfruit");
        log.add("grape");
        log.add("orange");
        log.add("watermelon");
        log.add("blueberry");
        log.add("pineapple");
        log.add("mango");
        assertEquals(10, log.size());

        log.add("coconut");
        assertEquals(11, log.size());
	}

}
