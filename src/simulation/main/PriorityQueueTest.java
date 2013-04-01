package simulation.main;

import simulation.structures.MinPriorityQueue;

public class PriorityQueueTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MinPriorityQueue<Integer> intPriorityQueue = new MinPriorityQueue<>();
//		for(int i = 50; i > 0;) {
//			intPriorityQueue.insert(i);
//			intPriorityQueue.assertIsMinHeap();
//			if(i % 2 == 0) i /= 2;
//			else i = 3*i + 2;
//		}
		for (int i = 50; i >= 0; i--) {
			intPriorityQueue.insert(i);
			intPriorityQueue.assertIsMinHeap();
		}
		for (int i = 51; i < 100; i++) {
			intPriorityQueue.insert(i);
			intPriorityQueue.assertIsMinHeap();
		}
		for (Integer integer : intPriorityQueue) {
			System.out.println(integer);
		}
		
	}

}
