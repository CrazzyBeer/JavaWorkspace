import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item arr[];
   private int n;
   public RandomizedQueue(){
	   arr = (Item[]) new Object[2];
	   n = 0;
   }
   private void resize(int newSize) {
	   Item[] temp = (Item[]) new Object[newSize];
	   for (int i = 0; i<n; i++) temp[i] = arr[i];
	   arr = temp;
   }
   public boolean isEmpty(){
	   return n == 0;
   }
   public int size(){
	   return n;
   }
   private void check() {
	   if (n == 0) throw new java.util.NoSuchElementException();
   }
   public void enqueue(Item item)  {
	   if (item == null) throw new java.lang.NullPointerException();
	   if (n == arr.length) resize(arr.length*2); 
	   arr[n++] = item;
   }
   public Item dequeue()  {
	   check();
	   int index = StdRandom.uniform(n);
	   Item item = arr[index];
	   arr[index] = arr[n-1];
	   arr[n-1] = null;
	   n--;
	   if (n > 0 && n == arr.length/4) resize(arr.length/2);
	   return item;
   }
   public Item sample()  {
	   check();
	   int index = StdRandom.uniform(n);
	   return arr[index];
	   
   }
   public Iterator<Item> iterator()  {   
	   return new ListIterator();  
   }
   private class ListIterator implements Iterator<Item> {
	   
       private int current = 0;
       private int sz;
       private Item[] a;
       public ListIterator() {
    	   sz = n;
    	   a = (Item[]) new Object[sz];
    	   for (int i = 0; i<sz; i++) 
    		   a[i] = arr[i];
    	   StdRandom.shuffle(a);
       }
       public boolean hasNext()  { 
    	   return !(current == sz);                     
       }
       public void remove()      { throw new UnsupportedOperationException();  }
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = a[current];
           current++; 
           return item;
       }
   }
   public static void main(String[] args) {
	   
   }
}
