package com.example.mythbusters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;


public class LinkedListTest {
	private LinkedList<String> emptyList() {
		return listWithNItems(0);
    }
	
	private String fabricateElementForIndex(int i) {
		return "I am element " + i;
	}
	
	private LinkedList<String> listWithNItems(int n) {
		LinkedList<String> newList = new LinkedList<String>();
		for (int i = 0; i < n; i++) {
			newList.add(fabricateElementForIndex(i));
		}
		return newList;
	}
	
	@Test
	public void testNewListIsEmpty() {
		assertTrue(emptyList().isEmpty());
	}
	
	@Test
	public void testListWithItemIsNotEmpty() {
		assertFalse(listWithNItems(1).isEmpty());
	}
	
	@Test
	public void testListWithItemHasSizeOne() {
		assertEquals(1, listWithNItems(1).size());
	}
	
	@Test
	public void testAddRandomItemsGivesCorrectLength() {
		int itemCount = 2;
		assertEquals(itemCount, listWithNItems(itemCount).size());

		itemCount = 35;
		assertEquals(itemCount, listWithNItems(itemCount).size());
		
		itemCount = 1337;
		assertEquals(itemCount, listWithNItems(itemCount).size());
	}
	
	@Test
	public void testListContainsAddedItems() {
		int itemCount = 29;
		int containingIndex = 16;
		
		LinkedList<String> newList = listWithNItems(itemCount);
		
		String element = fabricateElementForIndex(containingIndex);		
		assertTrue(newList.contains(element));
	}
	
	@Test
	public void testListDoesNotContainMissingItems() {
		int itemCount = 29;
		int nonContainingIndex = itemCount + 27;
		
		LinkedList<String> newList = listWithNItems(itemCount);
		
		String element = fabricateElementForIndex(nonContainingIndex);
		assertFalse(newList.contains(element));
	}
	
	@Test
	public void testEmptyListDoesNotContainNotItems() {
		assertFalse(emptyList().contains("nothing"));
	}
}
