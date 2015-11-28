package com.example.mythbusters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class LinkedListTest {
	private LinkedList<String> emptyList() {
		return new LinkedList<String>();
    }
	
	private LinkedList<String> listWithItem() {
		LinkedList<String> newList = emptyList();
		newList.add("I am an element");
		return newList;
	}
	
	@Test
	public void testNewListIsEmpty() {
		assertEquals(true, emptyList().isEmpty());
	}
	
	@Test
	public void testListWithItemIsNotEmpty() {
		assertEquals(false, listWithItem().isEmpty());
	}
	
	@Test
	public void testListWithItemHasSizeOne() {
		assertEquals(1, listWithItem().size());
	}
	
}
