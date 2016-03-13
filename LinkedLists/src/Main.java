import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		RandomizedQueue <Integer> q = new RandomizedQueue <Integer> ();
		for (int i = 0 ; i< 10 ;i++) q.enqueue(i);
		Iterator<Integer> it = q.iterator();
		while (it.hasNext()) System.out.println(it.next());
		
	}

}
