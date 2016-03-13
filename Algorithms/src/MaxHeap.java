import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;



public class MaxHeap <Key> implements Iterable<Key> {
	Key[] Heap;
	private int N;
	private Comparator<Key> comparator;
	private int size;
	
	public MaxHeap() {
		Heap = (Key[]) new  Object[2];
		N = 0;
		size = 0;
	}
	public boolean isEmpty() {
		return (size == 0);
	}
	
	
	public void insert(Key key) {
		if (N >= Heap.length - 1) resize(Heap.length*2);
		Heap[++N] = key;
		swim(N);
		size++;
	}
	
	public Key delMax() {
		if (isEmpty()) throw new NoSuchElementException();
		Key k = Heap[1];
		Heap[1] = Heap[N];
		Heap[N--] = null;
		sink(1);
		size--;
		return k;
	}
	
	public Key max() {
        if (isEmpty()) throw new NoSuchElementException();
        return Heap[1];
    }
	
	private void swim(int k) {
		while (k>1 && less(k/2,k)) {
			exch(k/2,k);
			k /= 2;
		}
	}
	
	private void sink(int k) {
		while(2*k <= N) {
			int j = 2*k;
			if (j<N && less(j,j+1)) j++;
			if (!less(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	
	private void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= N; i++) temp[i] = Heap[i];
        Heap = temp;
    }
	
	private boolean less(int i, int j) {
        if (comparator == null)
            return ((Comparable<Key>) Heap[i]).compareTo(Heap[j]) < 0;
        else
            return comparator.compare(Heap[i], Heap[j]) < 0;
    }
	
	private void exch(int i, int j) {
		Key temp = Heap[i];
		Heap[i] = Heap[j];
		Heap[j] = temp;
	}
	
	public Iterator<Key> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Key> {

        private Key[] HeapArr;
        private int index;
        private int N;
        
        public HeapIterator() {
        	HeapArr = Heap;
        	index = 1;
            this.N = size;
        }

        public boolean hasNext()  { return index<=N;                     }
        public void remove()      { throw new UnsupportedOperationException();  }
        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return HeapArr[index++];
        }
    }
	
	
	
}
