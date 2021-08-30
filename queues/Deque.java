package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

//25 Aug 2021
/**
 *
 * @author cen7
 *
 */
public class Deque<Item> implements Iterable<Item> {

	private Node first, last;
	private int n;

	// using linked-list enqueue implementation
	private class Node {
		Item item;
		Node next;
	}

	// Construct an empty deque
	public Deque() {

		first = null;
		last = null;
		n = 0;
	}

	// return the number of items on the deque
	public int size() {
		return n;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return first == null;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		if (n == 0)
			last = first;

		n++;

	}

	// add the item to the back
	public void addLast(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;

		if (isEmpty())
			first = last;
		else oldLast.next = last;

		n++;

	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();

		Item item = first.item;
		first = first.next;
		n--;

		return item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = last.item;

		if (first == last) {
			first = null;
			last = null;
		} else {
			Node current = first;
			while (current.next != last) {
				current = current.next;
			}
			current.next = null;
			last = current;
		}

		return item;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new LinkedIterator();
	}

	private class LinkedIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<Integer> dq = new Deque<Integer>();

		dq.addLast(2);
		dq.addLast(5);
		dq.addFirst(45);
		dq.addLast(9);
		dq.addLast(15);
		dq.addLast(5555);
//		dq.removeFirst();
		dq.removeLast();
	

		for (Integer i : dq)
			System.out.print(i + " ");

	}
}
