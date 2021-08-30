# Deque and Randomized queue 

This is my version for second assignment from *Robert Sedgewick*  & *Kevin Wayne* **Algorithms** Course. 86%

For the Deque class I used the Linked-List queue implementation and FIFO as  inventory management method for Iterator  implementation.
```
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
```
For Randomized Queue I used the resized array implementation for queue to be able to access an arbitrary item from that queue and modify the queue capacity. The item to be removed is chosen uniformly at random  by static mehtod ```uniform(int n)``` from StdRandom class.  After removing the random item, the items to right of the removed one will be shifted to the left with ```moveItems(int startPost)``` private method. The implementation supports all the operation in *constant amortized time.*
```
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
```
The iterator for this class should return the items in uniformly random order.  To do so, in the Iterator implementation, I used an additional array ```int[] randomPos``` of the same size as the queue array filled with integer numbers from 0 to *randomPas.length - 1*.  Finally the array was shuffled applying an improved version (Durstenfeld) of the Fisher-Yates algorithm.
```
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

```
