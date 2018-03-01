/*
 * T-301-REIR: Algorithms
 * Assignment: D1
 * Part B - Experiments
 * Due: 25.08.2017
 * By eddasr15
 */

package D1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class CouponCollectorStats {
	
	// Stores multiple experiment results
	private int[] experiments;
	
	// Constructs T times coupon collector test with number range 0-N
	public CouponCollectorStats(int N, int T) {
		
		// Stores an array of counts from experiment
		experiments = new int[T];
		for(int i = 0; i < T; i++) {
			experiments[i] = CouponCollectorTest(N);
		}
	}
	
	// Experiments once how many randomly generated numbers are needed to generate each 0-N number
	public int CouponCollectorTest(int N) {
			
		// Checks if all numbers are generated
		// Function terminates when value is set to true
		boolean all_numbers_generated = false;
			
		// Each corresponding slot (n-1) represents each number
		// Initially all slots are false (Java standard)
		boolean[] is_found = new boolean[N+1];
			
		// Counter of tries and of "unique" numbers
		int counter = 0;
		int unique_numbers = 0;
			
		// Loops while not all numbers have been generated
		while(!all_numbers_generated) {
				
			// Counter increases with every loop
			counter++;
				
			// Number is generated
			// If number has not previously been found, unique numbers is increased
			// In any case, value of corresponding slot should be true
			int random_number = StdRandom.uniform(0, N+1);
			if(is_found[random_number] == false) unique_numbers++;
			is_found[random_number] = true;
				
			// Checks if all numbers are found
			// Unique numbers should be equal to N+1 (including zero)
			if(unique_numbers == N+1) all_numbers_generated = true;
		}
			
		// After loop terminates, value is returned:
		// How many tries it took to get 0-N numbers
		return counter;
	}
	
	// Calculates the mean of the experiment's counts
	public double mean() {
		
		return StdStats.mean(experiments);
	}
	
	// Calculates the standard deviation of the experiment's counts
	public double stddev() {
		
		return StdStats.stddev(experiments);
	}
	
	// Conducts tests and displays results given number range 0-N and experiments T
	public static void conduct_test(int N, int T) {
		
		// Creating instance and calculate stddev and avg with precision
		CouponCollectorStats cstat = new CouponCollectorStats(N,T);
		String STDDEV_precision = String.format("%." + 2 + "f", cstat.stddev());
		String AVG_precision = String.format("%." + 2 + "f", cstat.mean());
		
		// Printing results of experiments
		StdOut.println(T + " experiments (T) conducted for number range 0-" + N + " (N):"); 
		StdOut.println("Average: " + AVG_precision);
		StdOut.println("Standard deviation: " + STDDEV_precision + '\n');
		
	}
	
	// TESTS
	public static void main(String[] args) {
			
		StdOut.println("*** TESTING COUPON COLLECTOR STATS ***" + '\n');
			
		// Initial tests conducted (part a)
		// N = 1000 and N = 10000, T = 3
		StdOut.println("Experiments part a tests:" + '\n');
			
		conduct_test(1000, 3); conduct_test(10000, 3);
			
		// Conclusion from a: time taken to conduct experiments
		// IS proportional to the number range!
			
			
		// Final tests conducted (part b)
		// N = 1000 and T ranging 10, 100, 1000, 10000
		StdOut.println("Experiments part b tests:" + '\n');
			
		conduct_test(1000, 10); conduct_test(1000, 100);
		conduct_test(1000, 1000); conduct_test(1000, 10000);
			
		// Conclusion for b: time taken to conduct experiments
		// IS proportional to the number of experiments!
			
	}
	
}




