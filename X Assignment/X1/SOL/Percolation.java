/*
 * T-301-REIR: Algorithms
 * Assignment: X1 - Percolation
 * Percolation class
 * Due: 1.09.2017
 * By eddasr15
 */

package X1;

// import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
	
	/******************************************
	 *				VARIABLES
	 ******************************************/
	
	// A double array, containing information about grid index
	// True if index is open, otherwise false
	private boolean grid[][];
	
	// Size of grid
	private int grid_size;
	
	// Size of UF array (including dummy nodes)
	private int uf_size;
	
	// A quick union
	//private QuickFindUF uf;
	
	// Weighted quick union
	private WeightedQuickUnionUF uf;
	
	// Number of open sites in grid
	private int open_sites;
	
	
	
	
	
	/****************************************
	 *			 PUBLIC FUNCTIONS
	 ****************************************/
	
	// Create N by N grid with all sites initially blocked
	// Throws java.lang.IllegalArgumentException if  <= 0
	// Constructor should take time porportional to N^2
	public Percolation(int N) {
		
		// Check validity of N
		if(N <= 0) {
			
			throw new IllegalArgumentException("Illegal argument: " + Integer.toString(N));
		}
		
		grid_size = N;
		uf_size = grid_size*grid_size+2;
		open_sites = 0;
		grid = new boolean[grid_size][grid_size];
		//uf = new QuickFindUF(uf_size);
		uf = new WeightedQuickUnionUF(uf_size);
		
		// Modeling top node and bottom node connections (dummy nodes)
		// They connect to each node in adjacent row
		for(int i = 1; i <= grid_size; i++) {
			
			uf.union(0, i); uf.union(uf_size-1, parse_idx(grid_size-1, i-1));
		}
	}
	
	// Open the side (row, col) if not open already
	// Throws java.lang.IndexOutOfBoundsException if row/col is outside of range
	public void open(int row, int col) {
		check_idx(row, col);
		
		// Actions only made if index is previously closed
		if(!grid[row][col]) {
			
			// Increase open sites
			// Set grid idx to true
			open_sites++;
			grid[row][col] = true;
			
			// Update the UF model if appropriate
			if(row != 0) {
				// Check if upper square is open
				// Connect nodes if so
				if (isOpen(row-1, col)) {
					uf.union(parse_idx(row, col),
							 parse_idx(row-1, col));
				}
			}
			if(row != grid_size-1) {
				// Check if lower square is open
				// Connect nodes if so
				if(isOpen(row+1, col)) {
					uf.union(parse_idx(row, col),
							 parse_idx(row+1, col));
				}
			}
			if(col != 0) {
				// Check if left square is open
				// Connect nodes if so
				if(isOpen(row, col-1)) {
					uf.union(parse_idx(row, col),
							 parse_idx(row, col-1));
				}
			}
			if(col != grid_size-1) {
				// Check if right square is open
				// Connect nodes if so
				if(isOpen(row, col+1)) {
					uf.union(parse_idx(row, col),
							 parse_idx(row, col+1));
				}
			}
		}
	}
	

	// Throws java.lang.IndexOutOfBoundsException if row/col is outside of range
	public boolean isOpen(int row, int col) {
		
		check_idx(row, col); return grid[row][col];
	}
	

	// Throws java.lang.IndexOutOfBoundsException if row/col is outside of range
	public boolean isFull(int row, int col) {
		
		check_idx(row, col);
		return uf.connected(0, parse_idx(row, col))&& isOpen(row, col);
	}
	
	// Number of open sites
	public int numberOfOpenSites() {
		
		return open_sites;
	}
	
	// Returns true if the system percolates
	// System percolates if top and bottom dummy nodes "connect"
	public boolean percolates() {
		
		// Edge case: N = 1
		if(grid_size == 1) return isOpen(0,0);
		
		// Check if top and bottom dummy nodes connect
		return uf.connected(0, uf_size-1);
	}
	
	
	
	
	/********************************************
	 *		  PRIVATE HELPER FUNCTIONS
	 ********************************************/
	
	// Private function to check index validity
	// Throws an exception
	private void check_idx(int row, int col) {
		if(row >= grid_size || col >= grid_size || row < 0 || col < 0) {
			throw new IndexOutOfBoundsException("Index [" + row + ", " + col + "] out of bounds");
		}
	}
	
	// Private function to parse index given row and col number to match 1D array (the UF)
	// Note that first node is the top dummy node
	private int parse_idx(int row, int col) {
         return row * grid_size + col + 1;
	}
	
	
	
	
	
	/********************************************
	 *			    UNIT TESTING
	 ********************************************/
	
	public static void main(String[] args) {
		
		// Percolation stats uses all functions in percolation
		// Creates a set of percolation experiments
		PercolationStats pstats = new PercolationStats(30, 180);
		
		// Displaying result of perculation stats
		StdOut.println("Avg (estimate of p*): " + pstats.mean());
		StdOut.println("Stddev (estimate of sharpness of p*): " + pstats.stddev());
		
		StdOut.println("Low endpoint of 95% confidence interval: " + pstats.confidenceLow());
		StdOut.println("High endpoint of 95% confidence interval: " + pstats.confidenceHigh());
	}
}
