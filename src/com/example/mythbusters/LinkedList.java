package com.example.mythbusters;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import javax.lang.model.element.Element;

public class LinkedList<T> implements List<T> {

	private class Node<T> {
		private T mElement;
		private Node<T> mNext;

		public Node(T element, Node<T> next) {
			mElement = element;
			mNext = next;
		}

		public Node<T> getNext() {
			return mNext;
		}

		public T getElement() {
			return mElement;
		}

		public void setNext(Node<T> newNode) {
			mNext = newNode;
		}
	}

	private Node<T> mHead = null;

	@Override
	public int size() {
		NodeCallback<Integer, T> func = new NodeCallback<Integer, T>() {

			@Override
			public Integer call(Integer accumulator, LinkedList<T>.Node<T> currentNode) {
				return accumulator + 1;
			}

		};

		return reduceNodes(0, func);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object element) {
		NodeCallback<Boolean, T> containsElement = new NodeCallback<Boolean, T>() {

			@Override
			public Boolean call(Boolean accumulator, Node<T> currentNode) {
				return accumulator || currentNode.getElement().equals(element);
			}
			
		};
		
		return reduceNodes(false, containsElement);
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> A[] toArray(A[] array) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T element) {
		Node<T> newNode = new Node<T>(element, null);

		if (mHead == null) {
			mHead = newNode;
		} else {
			NodeCallback<Node<T>, T> cb = new NodeCallback<LinkedList<T>.Node<T>, T>() {

				@Override
				public LinkedList<T>.Node<T> call(LinkedList<T>.Node<T> accumulator,
						LinkedList<T>.Node<T> currentNode) {
					return currentNode;
				}
			};

			Node<T> lastNode = reduceNodes(null, cb);
			lastNode.setNext(newNode);

		}

		return true;
	}
	

	@Override
	public void add(int index, T element) {
		Node<T> prevNode = getNodeByIndex(index - 1);
		Node<T> nextNode = prevNode.getNext();
		
		Node<T> newNode = new Node<T>(element, nextNode);
		prevNode.setNext(newNode);
	}

	@Override
	public boolean remove(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> elements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> elements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> elements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> elements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> elements) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public int indexOf(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(int index) {
		Node<T> node = getNodeByIndex(index);
		if (node != null) {
			return node.getElement();
		} else {
			return null;
		}
	}

	@Override
	public T set(int index, T element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	private Node<T> getNodeByIndex(int index) {
		NodeCallback<Node<T>, T> nodeRetriever = new NodeCallback<Node<T>, T>() {
			int mCurrentPointer = 0;

			@Override
			public Node<T> call(Node<T> accumulator, Node<T> currentNode) {
				if (mCurrentPointer++ == index) {
					return currentNode;
				} else {
					return accumulator;
				}
			}
		}; 
		
		return reduceNodes(null, nodeRetriever);
	}
	
	private <A> A reduceNodes(A accumulator, NodeCallback<A, T> callback) {
		if (mHead == null) {
			return accumulator;
		}

		Node<T> currentNode = mHead;

		while (currentNode != null) {
			accumulator = callback.call(accumulator, currentNode);
			currentNode = currentNode.getNext();
		}

		return accumulator;

	}

	private interface NodeCallback<A, T> {
		public A call(A accumulator, LinkedList<T>.Node<T> currentNode);
	}
}
