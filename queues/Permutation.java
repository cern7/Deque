package queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

//26 Aug 2021
/**
 *
 * @author cen7
 *
 */
public class Permutation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while (n > 0) {
			String item = StdIn.readString();
			rq.enqueue(item);
			n--;
		}
		for(String str : rq) {
			StdOut.println(str);
		}
			
	}

}
