/*
 * T-301-REIR: Algorithms
 * Assignment: D0 - StdRand & StdStats
 * Due: 20.08.2017
 * By eddasr15
 */

package D0;

import java.awt.Font;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class Histogram{
	
	// Part I - StdDraw
	public static void main(String[] args) {
		
		// Array that stores occurrences of numbers
		// Each slot corresponding to number's occurrence
        double occurrence_counter[] = new double[11];
        
        // Array that stores the 100 generated numbers
        double generated_numbers[] = new double[100];
        
        
        // Generates 100 random numbers in range 1-10
        // Each increases corresponding slot in counter array
        // Each is pushed into the generated numbers array storage
		for (int i = 0; i < 100; i++) {
			double random_number = StdRandom.uniform(1, 11);
	        occurrence_counter[(int) random_number]++;
	        generated_numbers[i] = random_number;
		}
		
		// Fixing up plot tics to show values
		// X-tics show each number, Y-tics show their occurrences
		StdDraw.setXscale(1, 10); StdDraw.setYscale(-10, 35);
		StdDraw.line(2, 0, 9.5, 0);
		StdDraw.line(2.2, -0.5, 2.2, 25.5);
		Font ticfont = new Font("Arial", Font.PLAIN, 9);
		int xtic = 1;
		for(double x = 2.5; x < 10; x = x + 0.75) {
			StdDraw.setFont(ticfont);
			String tic = Integer.toString(xtic);
			StdDraw.text(x, -1, tic);
			xtic++;
		}
		for(int y = 1; y <= 25; y++) {
			StdDraw.setFont(ticfont);
			String tic = Integer.toString(y);
			StdDraw.text(2, y, tic);
		}
		
		// Marking tics
		StdDraw.text(6, -2.5, "Number");
		StdDraw.text(1.5, 10, "Number of occurrences", 90);
		
		// Plotting results
		StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
		StdStats.plotBars(occurrence_counter);
		
		// Title set for histogram 
		StdDraw.setPenColor(StdDraw.BLACK);
		Font titlefont = new Font("Arial", Font.BOLD, 18);
		StdDraw.setFont(titlefont);
		StdDraw.text(5.5, 30, "Occurrences of Randomly Generated Numbers");
		
		// Calculating average and standard deviation
		// Then displaying results on image
		double average = StdStats.mean(generated_numbers);
		double stddev = StdStats.stddev(occurrence_counter);
		String STDDEV_precision = String.format("%." + 2 + "f", stddev);
		String AVG_precision = String.format("%." + 2 + "f", average);
		Font calculationsfont = new Font("Arial", Font.BOLD, 10);
		StdDraw.setFont(calculationsfont);
		StdDraw.text(5.5, -6, "Average ≈ " + AVG_precision);
		StdDraw.text(5.5, -7, "Standard deviation ≈ " + STDDEV_precision);

	}
	
}
