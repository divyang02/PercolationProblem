package percolation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int[][] percarray;
	private int upperBound = 0;
	private int lowerBound;
	private WeightedQuickUnionUF uf;
	private int rows;
	private int openSites = 0;

	public Percolation(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		uf = new WeightedQuickUnionUF(n * n + 2);
		lowerBound = n * n + 1;
		percarray = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				percarray[i][j] = 1;
			}
		}
		rows = n;
	}

	public void open(int row, int col) {

		if (isOpen(row, col))
			return;

		if (row == 1) {

			percarray[row - 1][col - 1] = 0;
			openSites++;
			uf.union(upperBound, findIndex(row, col));

			if (col == 1) {

				if (isOpen(row, col + 1)) {
					uf.union(findIndex(row, col), findIndex(row, col + 1));
				}

				if (isOpen(row + 1, col)) {
					uf.union(findIndex(row, col), findIndex(row + 1, col));
				}
			}

			if (col > 1 && col < rows) {

				if (isOpen(row, col - 1)) {
					uf.union(findIndex(row, col), findIndex(row, col - 1));
				}

				if (isOpen(row, col + 1)) {
					uf.union(findIndex(row, col), findIndex(row, col + 1));
				}

				if (isOpen(row + 1, col)) {
					uf.union(findIndex(row, col), findIndex(row + 1, col));
				}
			}

			if (col == rows) {

				if (isOpen(row, col - 1)) {
					uf.union(findIndex(row, col), findIndex(row, col - 1));
				}

				if (isOpen(row + 1, col)) {
					uf.union(findIndex(row, col), findIndex(row + 1, col));
				}
			}
		}

		if (row > 1 && row < rows) {
			openSites++;
			percarray[row - 1][col - 1] = 0;

			if (col > 1 && col < rows) {

				if (isOpen(row, col - 1)) {
					uf.union(findIndex(row, col), findIndex(row, col - 1));
				}

				if (isOpen(row, col + 1)) {
					uf.union(findIndex(row, col), findIndex(row, col + 1));
				}

				if (isOpen(row - 1, col)) {
					uf.union(findIndex(row, col), findIndex(row - 1, col));
				}

				if (isOpen(row + 1, col)) {
					uf.union(findIndex(row, col), findIndex(row + 1, col));
				}

			}

			if (col == 1) {

				if (isOpen(row, col + 1)) {
					uf.union(findIndex(row, col), findIndex(row, col + 1));
				}

				if (isOpen(row - 1, col)) {
					uf.union(findIndex(row, col), findIndex(row - 1, col));
				}

				if (isOpen(row + 1, col)) {
					uf.union(findIndex(row, col), findIndex(row + 1, col));
				}

			}

			if (col == rows) {

				if (isOpen(row - 1, col)) {
					uf.union(findIndex(row, col), findIndex(row - 1, col));
				}

				if (isOpen(row + 1, col)) {
					uf.union(findIndex(row, col), findIndex(row + 1, col));
				}

				if (isOpen(row, col - 1)) {
					uf.union(findIndex(row, col), findIndex(row, col - 1));
				}

			}
		}

		if (row == rows) {
			openSites++;
			percarray[row - 1][col - 1] = 0;
			uf.union(lowerBound, findIndex(row, col));

			if (col == 1) {

				if (isOpen(row, col + 1)) {
					uf.union(findIndex(row, col), findIndex(row, col + 1));
				}

				if (isOpen(row - 1, col)) {
					uf.union(findIndex(row, col), findIndex(row - 1, col));
				}

			}
			if (col > 1 && col < rows) {

				if (isOpen(row, col - 1)) {
					uf.union(findIndex(row, col), findIndex(row, col - 1));
				}

				if (isOpen(row, col + 1)) {
					uf.union(findIndex(row, col), findIndex(row, col + 1));
				}

				if (isOpen(row - 1, col)) {
					uf.union(findIndex(row, col), findIndex(row - 1, col));
				}

			}
			if (col == rows) {

				if (isOpen(row, col - 1)) {
					uf.union(findIndex(row, col), findIndex(row, col - 1));
				}

				if (isOpen(row - 1, col)) {
					uf.union(findIndex(row, col), findIndex(row - 1, col));
				}

			}
		}
	}

	public boolean isOpen(int row, int col) {

		// System.out.println(row +" " + col);
		if (row > rows || col > rows || row < 1 || col < 1) {
			throw new IndexOutOfBoundsException();
		}
		return percarray[row - 1][col - 1] == 0;
	}

	public boolean isFull(int row, int col) {
		return uf.connected(upperBound, findIndex(row, col));
	}

	public int numberOfOpenSites() {
		return openSites;
	}

	public boolean percolates() {
		return uf.connected(upperBound, lowerBound);
	}

	private int findIndex(int row, int col) {
		return ((row - 1) * rows + (col - 1) + 1);
	}

	public static void main(String[] args) {

		Percolation p = null;
		try {
			BufferedReader rd = new BufferedReader(new FileReader("input1.txt"));
			int count = 0;
			while (true) {

				String line = rd.readLine();
				if (line == null || line.length() == 0)
					break;
				line = line.trim();
				if (count == 0) {
					p = new Percolation(Integer.parseInt(line));
					count++;
				} else {

					String[] temp = line.split("\\ +");
					p.open(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
				}

			}

			rd.close();

		} catch (IOException ex) {
			System.out.println("file not found");
		}

		System.out.println(p.percolates());
	}
}
