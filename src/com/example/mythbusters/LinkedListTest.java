package com.example.mythbusters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
	public void testListContainsSpecialAddedItems() {
		int itemCount = 29;
		LinkedList<String> newList = listWithNItems(itemCount);
		String element = "bees";

		int insertPosition = itemCount - 2;
		newList.add(insertPosition, element);

		assertEquals(element, newList.get(insertPosition));
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

	@Test
	public void testGetReturnsNullForAbsentElement() {
		int itemCount = 3;
		int nonContainingIndex = itemCount + 57;

		LinkedList<String> newList = listWithNItems(itemCount);

		assertNull(newList.get(nonContainingIndex));
	}

	@Test
	public void testGetReturnsElementForPresentElement() {
		int itemCount = 3;
		int containingIndex = 2;

		LinkedList<String> newList = listWithNItems(itemCount);

		String element = fabricateElementForIndex(containingIndex);
		assertEquals(element, newList.get(containingIndex));
	}

	@Test
	public void testAddAll() {
		List<String> elements = new ArrayList<String>();

		elements.add("bees");
		elements.add("faces");

		LinkedList<String> list = emptyList();

		list.addAll(elements);

		for (String element : elements) {
			assertTrue(list.contains(element));
		}
	}

	@Test
	public void testAddAllAtIndex() {
		List<String> elements = new ArrayList<String>();

		elements.add("bees");
		elements.add("faces");

		int count = 2;
		int insertionPoint = 1;

		LinkedList<String> list = listWithNItems(count);

		list.addAll(insertionPoint, elements);

		for (int i = 0; i < elements.size(); i++) {
			assertEquals(elements.get(i), list.get(insertionPoint + i));
		}

		assertEquals(fabricateElementForIndex(1), list.get(list.size() - 1));
	}

	@Test
	public void testClear() {
		LinkedList<String> list = listWithNItems(10);
		list.clear();
		assertTrue(list.isEmpty());
	}

	@Test
	public void testContainsAll() {
		List<String> elements = new ArrayList<String>();

		elements.add("bees");
		elements.add("faces");

		LinkedList<String> list = emptyList();

		list.addAll(elements);

		assertTrue(list.containsAll(elements));
	}

	@Test
	public void testIndexOf() {
		LinkedList<String> list = listWithNItems(10);
		int expectedIndex = 3;
		String elementToFind = fabricateElementForIndex(expectedIndex);

		assertEquals(expectedIndex, list.indexOf(elementToFind));

	}

	@Test
	public void testLastIndexOf() {
		LinkedList<String> list = listWithNItems(10);
		int expectedIndex = 4;
		String elementToFind = fabricateElementForIndex(expectedIndex);

		list.add(elementToFind);

		assertEquals(list.size() - 1, list.lastIndexOf(elementToFind));

	}

	@Test
	public void testRemove() {
		LinkedList<String> list = listWithNItems(10);
		int removeIndex = 4;
		String elementToBeRemoved = fabricateElementForIndex(removeIndex);

		String returnVal = list.remove(removeIndex);

		assertEquals(9, list.size());
		assertEquals(elementToBeRemoved, returnVal);
		assertFalse(list.contains(elementToBeRemoved));
	}

	@Test
	public void testRemoveAtZero() {
		LinkedList<String> list = listWithNItems(2);
		int removeIndex = 0;
		String elementToBeRemoved = fabricateElementForIndex(removeIndex);

		String returnVal = list.remove(removeIndex);

		assertEquals(1, list.size());
		assertEquals(elementToBeRemoved, returnVal);
		assertFalse(list.contains(elementToBeRemoved));
	}

	@Test
	public void testRemoveWithElement() {
		LinkedList<String> list = listWithNItems(3);
		int removeIndex = 1;
		String elementToBeRemoved = fabricateElementForIndex(removeIndex);

		boolean returnVal = list.remove(elementToBeRemoved);

		assertEquals(2, list.size());
		assertTrue(returnVal);
		assertFalse(list.contains(elementToBeRemoved));
	}

	@Test
	public void testRemoveAll() {
		List<String> elements = new ArrayList<String>();

		elements.add("bees");
		elements.add("faces");
		elements.add("wasps");
		elements.add("potatos");

		LinkedList<String> list = emptyList();

		list.add("wasps");
		list.add("potatos");

		list.addAll(elements);
		boolean retVal = list.removeAll(elements);

		assertEquals(2, list.size());
		assertTrue(retVal);
	}

	@Test
	public void testRemoveAllUnchages() {
		List<String> elements = new ArrayList<String>();

		elements.add("bees");

		LinkedList<String> list = listWithNItems(10);
		boolean retVal = list.removeAll(elements);

		assertEquals(10, list.size());
		assertFalse(retVal);
	}

	@Test
	public void testRetainAll() {
		List<String> elements = new ArrayList<String>();

		elements.add("bees");

		LinkedList<String> list = listWithNItems(10);
		list.add("bees");
		
		boolean retVal = list.retainAll(elements);

		assertEquals(1, list.size());
		assertTrue(list.contains("bees"));
		assertTrue(retVal);
	}
	
	@Test
	public void testSet() {
		List<String> list = listWithNItems(10);
		list.set(3,"bees");
		
		assertEquals("bees", list.get(3));
	}
	
	@Test
	public void testSubList() {
		List<String> list = listWithNItems(10);
		int fromIndex = 3;
		int toIndex = 7;
		
		List<String> subList = list.subList(fromIndex, toIndex);

		for (int i = fromIndex; i < toIndex; i++) {
			String expected = fabricateElementForIndex(i);
			assertTrue(subList.contains(expected));
		}
	}
}
