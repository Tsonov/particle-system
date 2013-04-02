package simulation.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPriorityQueue<Item extends Comparable<Item>> implements
		Iterable<Item> {
	private static final int DEFAULT_START_SIZE = 16;
	private int size;
	private Item[] binaryHeap;

	public MinPriorityQueue() {
		this(DEFAULT_START_SIZE);
	}

	@SuppressWarnings("unchecked")
	public MinPriorityQueue(int capacity) {
		this.binaryHeap = (Item[]) new Comparable[capacity];
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public int capacity() {
		return this.binaryHeap.length;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void insert(Item item) {
		if (item == null) {
			throw new IllegalArgumentException(
					"Given item for the PriorityQueue must be non-null");
		}
		if (this.size == this.capacity()) {
			this.resize(this.capacity() * 2);
		}
		this.binaryHeap[size] = item;
		this.sendUpwards(size);
		size++;
	}

	public Item removeMin() {
		if (this.isEmpty()) {
			throw new NoSuchElementException(
					"The priority queue was empty when extracting");
		}
		size--;
		swap(0, this.size);
		Item min = this.binaryHeap[size];
		this.binaryHeap[size] = null;
		sendDown(0);
		// Resize if needed
		// Best results if we delete half of the heap
		// When we reach 1/4 size
		if (this.size > 0 && (this.size == (this.capacity() / 4))) {
			this.resize(this.capacity() / 2);
		}
		return min;
	}

	private void sendUpwards(int position) {
		int parentIndex = parent(position);
		while (position > 0 && isGreater(parentIndex, position)) {
			this.swap(position, parentIndex);
			position = parentIndex;
			parentIndex = parent(position);
		}

	}

	private void sendDown(int position) {
		while (2 * position + 1 < this.size) {
			int child = 2 * position + 1;
			// Check whether we have another child and if it's smaller
			// If so, use it.
			if (child < this.size - 1 && isGreater(child, child + 1)) {
				child++;
			}
			if (isGreater(child, position)) {
				break;
			}
			swap(position, child);
			position = child;
		}
	}

	private int parent(int position) {
		return (int) Math.floor((position - 1) / 2);
	}

	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		if (newSize <= this.size) {
			throw new IllegalArgumentException(
					"New size must not be smaller than the current one!");
		}
		Item[] newHeap = (Item[]) new Comparable[newSize];
		for (int i = 0; i < this.size(); i++) {
			newHeap[i] = this.binaryHeap[i];
		}
		this.binaryHeap = newHeap;
	}

	private void swap(int firstIndex, int secondIndex) {
		Item saveValue = this.binaryHeap[firstIndex];
		this.binaryHeap[firstIndex] = this.binaryHeap[secondIndex];
		this.binaryHeap[secondIndex] = saveValue;
	}

	private boolean isGreater(int first, int second) {
		return this.binaryHeap[first].compareTo(this.binaryHeap[second]) > 0;
	}

	public void assertIsMinHeap() {
		assert this.isMinHeap(0);
	}

	private boolean isMinHeap(int parent) {
		if (parent > this.size) {
			return true;
		}
		int leftChild = 2 * parent + 1;
		int rightChild = 2 * parent + 2;
		if (leftChild < this.size && isGreater(parent, leftChild)) {
			return false;
		}
		if (rightChild < this.size && isGreater(parent, rightChild)) {
			return false;
		}
		boolean result = isMinHeap(leftChild) && isMinHeap(rightChild);
		return result;
	}

	@Override
	public Iterator<Item> iterator() {
		return new PriorityQueueIterator();
	}

	private class PriorityQueueIterator implements Iterator<Item> {
		private MinPriorityQueue<Item> copy;
		
		private PriorityQueueIterator() {
			this.copy = new MinPriorityQueue<>(size());
			int elementCount = size();
			for (int i = 0; i < elementCount; i++) {
				this.copy.insert(binaryHeap[i]);
			}
		}
		@Override
		public boolean hasNext() {
			return this.copy.isEmpty() == false;
		}

		@Override
		public Item next() {
			if(this.hasNext() == false) {
				throw new NoSuchElementException("The priority queue is empty!");
			}
			Item result = this.copy.removeMin();
			return result;
		}

		@Override
		public void remove() {
			// Not supported for now
			throw new UnsupportedOperationException(
					"Remove is not allowed at this point");
		}

	}

}
