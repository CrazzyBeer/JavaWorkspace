import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	//Percolation objects
	private boolean[] open;
	private int N;
	private WeightedQuickUnionUF unionVirtualModel;
	private WeightedQuickUnionUF unionModel;

	public Percolation(int N) {
		if (N <= 0) throw new java.lang.IllegalArgumentException();
		this.N = N;
		open = new boolean[N*N+3];
		for (int i = 0; i <= N*N+2; i++) open[i] = false;
		unionModel = new WeightedQuickUnionUF(N*N+4);
		unionVirtualModel = new WeightedQuickUnionUF(N*N+4);
		for (int i = 1; i <= N; i++) {
			unionVirtualModel.union(N*N+1, i);
			unionModel.union(N*N+1, i);
		}
		for (int i = N*N; i >= N*N-N+1; i--) 
			unionVirtualModel.union(N*N+2, i);
	}
	public void open(int i, int j) {
		checker(i, j);
		if (!isOpen(i, j)) {
			int site = (i - 1) * N + j;
			open[site] = true;
			int siteTop = (i - 2) * N + j;
			int siteBottom = i * N + j;
			int siteLeft = (i - 1)* N + (j - 1);
			int siteRight = (i - 1) * N + (j + 1);
			if (i > 1 && open[siteTop]) {
				unionModel.union(site, siteTop);
				unionVirtualModel.union(site, siteTop);
			}
			if (i < N && open[siteBottom]) {
				unionModel.union(site, siteBottom);
				unionVirtualModel.union(site, siteBottom);
			}
			if (j > 1 && open[siteLeft]) {
				unionModel.union(site, siteLeft);
				unionVirtualModel.union(site, siteLeft);
			}
			if (j < N && open[siteRight]) {
				unionModel.union(site, siteRight);  
				unionVirtualModel.union(site, siteRight);  
			}
		} 
	}
	private void checker(int i, int j) {
		if (i < 1 ||  i > N || j < 1 || j > N) throw new java.lang.IndexOutOfBoundsException();
	}
	public boolean isOpen(int i, int j) {
		checker(i, j);
		return open[ (i-1)*N+j ];
	}
	public boolean isFull(int i, int j) {
		checker(i, j);
		int site = (i - 1) * N + j;
		return (unionModel.connected(site, N*N+1) && open[site]);
	}
	public boolean percolates() {
		if (N == 1) {
			return open[1];
		}
		return (unionVirtualModel.connected(N*N+1, N*N+2));
	}
}
