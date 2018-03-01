/*
 * T-301-REIR: Algorithms
 * Assignment: X1 - Percolation
 * PercolationStats class
 * Due: 1.09.2017
 * By eddasr15
 */

package X1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	
	// Holds results of experiments
	// Namely, each experiment's threshold
	private double[] thresholds;
	
	
	
	
	
	
	
	/********************************************
	 *	 		 CONDUCT EXPERIMENTS
	 ********************************************/
	
	// Perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T) {
		
		// T is at least 1. (N is checked in Percolation construction).
		if(T <= 0){
			
			throw new java.lang.IllegalArgumentException("Number of tests need to be at least");
		}
		
		// Holds thresholds (p*) for each experiment T
		thresholds = new double[T];
		
		for(int i = 0; i < T; i++) {
			
			// Creates a new percolation test
			Percolation percolation_test = new Percolation(N);
			int num_tries = 0;
			int grid_size = N*N;
			
			// Tests how many random openings are needed for system to percolate
			while(!percolation_test.percolates())
			{
				// Generate random row and col indices using uniform
				int r_row = StdRandom.uniform(0, N);
				int r_col = StdRandom.uniform(0, N);
				
				// Tries increased when openings are successful
				if(!percolation_test.isOpen(r_row, r_col)){
					
					percolation_test.open(r_row, r_col); num_tries++;
				}
			}
			
			// Calculate threshold of a single experiment
			// Thresholds determined as number of openings divided by grid size
			// Pass it onto a array of thresholds
			double threshold = (double)num_tries/grid_size;
			thresholds[i] = threshold;
		}
		
	}
	
	
	
	
	
	/********************************************
	 *	  EXPERIMENTS STATISTICS CALCULATIONS
	 ********************************************/
	
	// Calculate sample mean and standard deviation of percolation threshold
	public double mean() { return StdStats.mean(thresholds); }
	public double stddev() { return StdStats.stddev(thresholds); }
	
	// Calculate low and high endpoints of a 95% confidence interval
	// Use private helper function to calculate right side of a 95% confidence interval
	public double confidenceLow() { return mean() - get_rside();}
	public double confidenceHigh() { return mean() + get_rside(); }
	private double get_rside() { return (1.96*stddev())/(Math.sqrt(thresholds.length)); }
	
	public static void main(String[] args) {
		
		int T = 480;
		Stopwatch test_runtime = new Stopwatch();
		@SuppressWarnings("unused")
		PercolationStats pstats = new PercolationStats(30,T);
		StdOut.println("Operation took: " + test_runtime.elapsedTime() + " s for T = " + T);
		
	}
}
