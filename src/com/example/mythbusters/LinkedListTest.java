package com.example.mythbusters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class LinkedListTest {
	private LinkedList<String> emptyList() {
		return listWithNItems(0);
    }
	
	private LinkedList<String> listWithNItems(int n) {
		LinkedList<String> newList = new LinkedList<String>();
		for (int i = 0; i < n; i++) {
			newList.add("I am element " + n);
		}
		return newList;
	}
	
	@Test
	public void testNewListIsEmpty() {
		assertEquals(true, emptyList().isEmpty());
	}
	
	@Test
	public void testListWithItemIsNotEmpty() {
		assertEquals(false, listWithNItems(1).isEmpty());
	}
	
	@Test
	public void testListWithItemHasSizeOne() {
		assertEquals(1, listWithNItems(1).size());
	}
}
