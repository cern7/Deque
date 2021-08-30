package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

//25 Aug 2021
/**
 *
 * @author cen7
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private int n;

	// construct an empty randomized queue
	public RandomizedQueue() {
		q = (Item[]) new Object[1];
		n = 0;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return n == 0;
	}

	// return the n umner of items on the randomized queue
	public int size() {
		return n;
	}

	// resize the array
	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < n; i++)
			temp[i] = q[i];
		q = temp;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		if (n == q.length)
			resize(q.length * 2);
		q[n++] = item;

	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		int randomIndex = StdRandom.uniform(n);

		Item item = q[randomIndex];

		q[randomIndex] = null;
		if (randomIndex < n - 1) {
			moveItems(randomIndex);
		}
		n--;
		if (n > 0 && n == q.length / 4)
			resize(q.length / 2);
		return item;
	}

	// moving items to the left
	private void moveItems(int startPos) {
		for (int i = startPos; i < n - 1; i++) 
			q[i] = q[i + 1];
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int randomIndex = StdRandom.uniform(n);

		Item item = q[randomIndex];
		return item;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		private int i = 0;
		private int[] randomPos = new int[n];

		public RandomizedQueueIterator() {
			shuffleArrayPos();
		}

		private void shuffleArrayPos() {
			for (int j = 0; j < n; j++) {
				randomPos[j] = j;
			}
			/*
			 * An improved version (Durstenfeld) of the Fisher-Yates algorithm with O(n)
			 * time complexity
			 * 
			 */
			for (int i = randomPos.length - 1; i > 0; i--) {
				int indx = StdRandom.uniform(i);
				// swap
				randomPos[indx] += randomPos[i];
				randomPos[i] = randomPos[indx] - randomPos[i];
				randomPos[indx] -= randomPos[i];
			}
		}

		public boolean hasNext() {
			return i < n;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();

			Item item = q[randomPos[i++]];
			return item;
		}
	}

	// unit testing (required)
	public static void main(String[] args) {

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				rq.enqueue(item);
			else if (!rq.isEmpty())
				StdOut.print(rq.dequeue() + " ");
		}
		rq.enqueue("5555");
		rq.enqueue("54sdf");
		rq.enqueue("asda54sd");
		System.out.println(rq.sample());
		StdOut.println("(" + rq.size() + " left on queue)");

	}

}
