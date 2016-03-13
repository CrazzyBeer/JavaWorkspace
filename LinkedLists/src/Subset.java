
public class Subset {
	public static void main(String args[]) {
		RandomizedQueue <String> q = new RandomizedQueue <>();
		int k = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty()) {
			q.enqueue(StdIn.readString());
		}
		while (k > 0) {
			StdOut.println(q.dequeue());
			k--;
		}
	}
}
