import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item> {
	private int size;
	private class Node {
		Item item;
		Node right;
		Node left;
		public Node() {
			item = null;
			right = null;
			left = null;
		}
	}
	private Node back;
	private Node front;
	private Node center;
	public Deque()  {
		center = null;
		front = center;
		back = center;
		
	}
	public boolean isEmpty()  {
		return size == 0;
	}
	public int size()  {
		return size;
	}
	public void addFirst(Item item)  {
		if (item == null) throw new java.lang.NullPointerException();
		if (size == 0) {
			center = new Node();
			center.item = item;
			front = center;
			back = center;
		} else {
			Node newFirst = new Node();
			newFirst.item = item;
			newFirst.right = front;
			front.left = newFirst;
			front = newFirst;		
		}
		size++;
	}
	public void addLast(Item item)  {
		if (item == null) throw new java.lang.NullPointerException();
		if (size == 0) {
			center = new Node();
			center.item = item;
			front = center;
			back = center;
		} else {
			Node newLast = new Node();
			newLast.item = item;
			newLast.left = back;
			back.right = newLast;
			newLast.left = back;
			back = newLast;
		}
		size++;

	}
	public Item removeFirst() {
		if (size == 0) throw new java.util.NoSuchElementException();
		size--;
		Item item = front.item;
		front.item = null;
		if (front.right != null) {
			front = front.right;
			front.left = null;
		}
		else front = null;
		if (size == 0) 
			front = null;
		return item;
	}
	public Item removeLast() {
		if (size == 0) throw new java.util.NoSuchElementException();
		size--;
		Item item = back.item;
		back.item = null;
		if (back.left != null) {
			back = back.left;
			back.right = null;
		}
		else back = null;
		if (size == 0) 
			front = null;
		return item;
	}
	public Iterator<Item> iterator()  { 
		   return new ListIterator();  
	   }
	   private class ListIterator implements Iterator<Item> {
	       private Node current = front;
	       public boolean hasNext()  { return current != null;                     }
	       public void remove()      { throw new UnsupportedOperationException();  }
	       public Item next() {
	           if (!hasNext()) throw new NoSuchElementException();
	           Item item = current.item;
	           current = current.right; 
	           return item;
	       }
	   }
	public static void main(String[] args) {

	}


}
