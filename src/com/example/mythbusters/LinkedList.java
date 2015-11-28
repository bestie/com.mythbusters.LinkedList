package com.example.mythbusters;

import java.lang.reflect.Array;
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

		public T getElement() {
			return mElement;
		}

		public Node<T> getNext() {
			return mNext;
		}

		public void setNext(Node<T> newNode) {
			mNext = newNode;
		}

		public void setElement(T element) {
			mElement = element;
		}
	}

	private interface NodeCallback<A, T> {
		public A call(A accumulator, LinkedList<T>.Node<T> currentNode);
	}

	private Node<T> mHead = null;

	public LinkedList() {

	}

	public LinkedList(LinkedList<T> other) {
		for (int i = 0; i < other.size(); i++) {
			T valueToCopy = other.get(i);
			add(valueToCopy);
		}
	}

	@Override
	public void add(int index, T element) {
		Node<T> prevNode = getNodeByIndex(index - 1);
		Node<T> nextNode = prevNode.getNext();

		Node<T> newNode = new Node<T>(element, nextNode);
		prevNode.setNext(newNode);
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
	public boolean addAll(Collection<? extends T> elements) {
		for (T element : elements) {
			add(element);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> elements) {
		Node<T> insertionPoint = getNodeByIndex(index - 1);
		Node<T> rest = getNodeByIndex(index);

		LinkedList<T> insertionElements = new LinkedList<T>();
		insertionElements.addAll(elements);

		insertionPoint.setNext(insertionElements.mHead);
		Node<T> last = insertionElements.getNodeByIndex(insertionElements.size() - 1);
		last.setNext(rest);

		return true;

	}

	@Override
	public void clear() {
		mHead = null;
	}

	@Override
	public boolean contains(Object element) {
		return indexOf(element) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> elements) {
		for (Object element : elements) {
			if (!contains(element)) {
				return false;
			}
		}

		return true;
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
	public int indexOf(Object element) {
		NodeCallback<Integer, T> indexSearcher = new NodeCallback<Integer, T>() {
			int mCurrentIndex = 0;

			@Override
			public Integer call(Integer resultIndex, LinkedList<T>.Node<T> currentNode) {

				Integer retVal = null;

				if (currentNode.getElement().equals(element)) {
					if (resultIndex == -1) {
						retVal = mCurrentIndex;
					}
				}

				if (retVal == null) {
					retVal = resultIndex;
				}

				mCurrentIndex++;

				return retVal;
			}

		};

		return reduceNodes(-1, indexSearcher);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private Node<T> mNode = LinkedList.this.mHead;

			@Override
			public boolean hasNext() {
				return mNode != null;
			}

			@Override
			public T next() {
				T retval = mNode.getElement();
				mNode.getNext();
				return retval;
				
			}
		};
		
	}

	@Override
	public int lastIndexOf(Object element) {
		NodeCallback<Integer, T> indexSearcher = new NodeCallback<Integer, T>() {
			int mCurrentIndex = 0;

			@Override
			public Integer call(Integer resultIndex, LinkedList<T>.Node<T> currentNode) {

				Integer retVal;

				if (currentNode.getElement().equals(element)) {
					retVal = mCurrentIndex;
				} else {
					retVal = resultIndex;
				}

				mCurrentIndex++;

				return retVal;
			}

		};

		return reduceNodes(-1, indexSearcher);
	}

	@Override
	public ListIterator<T> listIterator() {
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T remove(int index) {

		if (index == 0) {
			T retval = mHead.getElement();
			mHead = mHead.getNext();
			return retval;
		} else {

			Node<T> prevNode = getNodeByIndex(index - 1);
			Node<T> actual = prevNode.getNext();
			Node<T> nextNode = prevNode.getNext().getNext();

			prevNode.setNext(nextNode);
			return actual.getElement();
		}
	}

	@Override
	public boolean remove(Object element) {
		if (contains(element)) {
			remove(indexOf(element));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> elements) {

		int initialSize = size();

		for (Object element : elements) {
			remove(element);
		}

		int sizeAfter = size();

		return initialSize != sizeAfter;
	}

	@Override
	public boolean retainAll(Collection<?> elements) {
		int initialSize = size();
		LinkedList<T> copy = new LinkedList<T>(this);
		while (copy.mHead != null && !elements.contains(copy.mHead.getElement())) {
			copy.mHead = copy.mHead.getNext();
		}

		Node<T> prevNode = copy.mHead;
		Node<T> currentNode = copy.mHead.getNext();
		while (currentNode != null) {
			if (!elements.contains(currentNode)) {
				prevNode.setNext(currentNode.getNext());
			} else {
				prevNode = currentNode;
			}
			currentNode = currentNode.getNext();
		}

		mHead = copy.mHead;
		int sizeAfter = size();

		return initialSize != sizeAfter;
	}

	@Override
	public T set(int index, T element) {
		Node<T> node = getNodeByIndex(index);
		T retVal = node.getElement();
		node.setElement(element);
		return retVal;
	}

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
	public List<T> subList(int fromIndex, int toIndex) {
		LinkedList<T> copy = new LinkedList<T>(this);

		for (int i = 0; i < fromIndex; i++) {
			copy.remove(0);
		}

		int lastPointer = toIndex - fromIndex - 1;

		copy.removeAfterIndex(lastPointer);

		return copy;

	}

	private void removeAfterIndex(int i) {
		getNodeByIndex(i).setNext(null);
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size()];
		Node<T> currentNode = mHead;
		int i = 0;
		while (currentNode != null) {
			result[i] = get(i++);
		}

		return result;
	}

	@Override
	public <A> A[] toArray(A[] array) {
		A[] result = (A[]) new Object[size()];
		Node<T> currentNode = mHead;
		int i = 0;
		while (currentNode != null) {
			result[i] = (A) get(i++);
		}

		return result;
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
}
