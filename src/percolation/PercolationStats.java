package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private Percolation p;
	private double[] stats;
	private int T;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		T = trials;
		stats = new double[trials];
		double totalSites = n * n;
		for (int i = 0; i < trials; i++) {
			p = new Percolation(n);
			int openSites;
			double ratio;
			while (!p.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!p.isOpen(row, col)) {
					p.open(row, col);
				}

			}
			openSites = p.numberOfOpenSites();
			ratio = openSites / totalSites;
			stats[i] = ratio;
		}
	}

	public double mean() {
		return StdStats.mean(stats);
	}

	public double stddev() {
		return StdStats.stddev(stats);

	}

	public double confidenceLo() {
		return ((mean() - (1.96 * stddev()) / Math.sqrt(T)));
	}

	public double confidenceHi() {
		return ((mean() + (1.96 * stddev()) / Math.sqrt(T)));
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		double start = System.currentTimeMillis();
		PercolationStats ps = new PercolationStats(n, T);
		System.out.println("Time taken " + (System.currentTimeMillis() - start));
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
	}
}
