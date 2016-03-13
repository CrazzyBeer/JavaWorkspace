public class PercolationStats {
	private double x[];
	private int T;
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();
		x = new double[T];
		this.T = T;
		for (int i = 0; i < T; i++) {
			int openSites = 0;
			Percolation model = new Percolation(N);
			while (!model.percolates()) {
				int xx = StdRandom.uniform(1,N+1);
				int yy = StdRandom.uniform(1,N+1);
				if (!model.isOpen(xx, yy)) {
					model.open(xx, yy);
					openSites++;
				}
			}
			x[i] = (double) openSites/(double) (N * N);
		}
	}
	public double mean() {
		return StdStats.mean(x);
	}
	public double stddev() {
		return StdStats.stddev(x);
	}
	public double confidenceLo() {
		double mean = mean();
		double stddev = stddev();
		return mean - 1.96 * stddev / Math.sqrt( (double) T);
	}
	public double confidenceHi() {
		double mean = mean();
		double stddev = stddev();
		return mean + 1.96 * stddev / Math.sqrt( (double) T);
	}
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats result = new PercolationStats(N, T);
		StdOut.println("mean                    = " + result.mean());
        StdOut.println("stddev                  = " + result.stddev());
        StdOut.println("95% confidence interval = " + result.confidenceLo() + ", " + result.confidenceHi());
	}

}
