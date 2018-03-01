/*
 * T-301-REIR: Algorithms
 * Assignment: S2 - Pattern Recognition
 * Sorting algorithm
 * Due: 15.09.2017
 * By eddasr15 and birgittab15
 */

package s2;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Fast {
	
	// Prints a list in the form of point -> point -> point
	public static void printList ( List<Point> lis) {
		
		Point[] tmp = lis.toArray(new Point[0]);
		for( int k = 0; k < tmp.length; k++ ) {
			StdOut.print(tmp[k]);
			if(k != tmp.length-1) {
				StdOut.print(" -> ");
			}
		} StdOut.println();
	}
	
	public static void main(String[] args) {
		
		// Read points into array from arguments
		// ( Code segment from Point.java by teacher)
		In in = new In();
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt(), y = in.readInt();
			points[i] = new Point(x, y);
		} Arrays.sort(points);
		
		// Checks slope in respect to each point
		for (int i = 0; i < n; i++) { 
			
			// Clone point array and choose compare point
			Point[] comp_points = points.clone( );
			Point comparePoint = points[i];
			
			// Sort cloned array from i to end by comparator
			// Comparator used in respect to compare point
			Arrays.sort( comp_points, i, n, comparePoint.SLOPE_ORDER );
			
			// Create a list, should hold line linear to origin point
			List<Point> tmpLis = new ArrayList<Point>( );
			tmpLis.add(comparePoint); tmpLis.add(comp_points[i]);
			int num_of_equals = 1; 
			
			// Iterate from origin point to end of array
			// Store collinear points if they are more than 3 (plus origin point)
			for(int j = i; j < n - 1; j++) {
				
				// If slopes match they are added to the list
				if( comparePoint.slopeTo(comp_points[j]) ==
					comparePoint.slopeTo(comp_points[j+1])) {
					num_of_equals++;
					tmpLis.add( comp_points[j+1] );
				}
				// When we reach a non-collinear point
				// Array is printed, then cleared and everything re-initiated
				else {
					if( num_of_equals >= 3) { printList ( tmpLis ); }
					tmpLis.clear( ); num_of_equals = 1; 
					tmpLis.add( comparePoint );
					tmpLis.add( comp_points[ j + 1 ] );
				} 
			}
			// Post-loop check whether some line is still in list
			// Print line if so
			if ( tmpLis.size( ) >= 4) { printList ( tmpLis ); }
		}
	 }
}
