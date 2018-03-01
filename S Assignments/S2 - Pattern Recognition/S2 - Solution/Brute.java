/*
 * T-301-REIR: Algorithms
 * Assignment: S2 - Pattern Recognition
 * Brute force algorithm
 * Due: 15.09.2017
 * By eddasr15 and birgittab15
 */

package s2;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Brute {
	
	// Check if the foursum of the array points is collinear
	// Collinear if all slopes of the four points are equal
	 public static boolean areCollinear ( Point[] points ) {
		 
		 return	(	(points[0].slopeTo(points[1])) ==
				 	(points[0].slopeTo(points[2])) &&
			 		(points[0].slopeTo(points[2])) ==
			 		(points[0].slopeTo(points[3])) );
	 }
	 
	 // Adds array with four points to the megalist
	 public static void addToList ( List<Point> lis, Point[] points  ) {
		 for (int i = 0; i < 4; i++) { lis.add( points[i] ); }
	 }
	 
	 // Uses SELECTION SORT to sort arrays within the megalist
	 // Arrays are sorted by their first point
	 // Line slope is a tie breaker if points are equal
	 public static void printSorted ( Point[] lis ) {
		 
		 for( int i = 0; i < lis.length; i+= 4 ) {
			 
			 for( int j = i + 4; j < lis.length; j += 4 ) {
				 
				 // Comparison value between first point of both arrays
				 // Swap arrays if array beginning at index j is larger
				 int compare_pnts = lis[i].compareTo(lis[j]);
				 
				 if(compare_pnts == 1){ swaparr( lis, i, j ); }
				 
				 // If points are equal (thus compare value 0)
				 // Line slope of arrays are a tie breaker
				 else if(compare_pnts == 0) {
					 
					 if ( (lis[i].slopeTo(lis[i+1])) <
						  (lis[j].slopeTo(lis[j+1])) ) {
						 
						 swaparr( lis, i, j );
					 }
				 }
			 }
			 // Print lowest array in every (outer) iteration
			 StdOut.println(	lis[i]	+ " -> " +
					 		lis[i+1]	+ " -> " +
					 		lis[i+2]	+ " -> " +
					 		lis[i+3]	);
		 }
	 }
	 
	 // Swaps array of four elements within the megalist
	 // i denotes first index of first array
	 // j denotes first index of second array
	 public static void swaparr (Point[] lis, int i, int j) {
		 
		 // Create temp to preserve values before swap
		 // Holds values of the first array
		 Point[] tmp = new Point[4];
		 tmp[0] = lis[i]; tmp[1] = lis[i+1];
		 tmp[2] = lis[i+2]; tmp[3] = lis[i+3];
		 
		 // Swap first array with second array
		 // Swap is done element by element
		 lis[i] = lis[j]; lis[i+1] = lis[j+1];
		 lis[i+2] = lis[j+2]; lis[i+3] = lis[j+3];
		 
		 // Swap second array with first array
		 lis[j] = tmp[0]; lis[j+1] = tmp[1];
		 lis[j+2] = tmp[2]; lis[j+3] = tmp[3];
		 
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
	     }
	     
	     // Megalist: all points of all arrays of four collinear points
	     List<Point> allpts = new ArrayList<Point>();
	     
	     // Create empty array of four points
	     // Each foursum is pushed in array and worked with
	     Point[] fourpoints = new Point[4];
	     
	     int N = points.length;
	     
	     for (int i = 0; i < N; i++) {
	    	 	for (int j = i+1; j < N; j++) {
	    	 		for (int k = j+1; k < N; k++) {
	    	 			for (int l = k+1; l < N; l++) {
	    	 				
	    	 				fourpoints[0] = points[i];
	    	 				fourpoints[1] = points[j];
	    	 				fourpoints[2] = points[k];
	    	 				fourpoints[3] = points[l];
	    	 				
	    	 				// Check if lines are collinear
	    	 				// Sort array if so and store it
	                    		if( areCollinear( fourpoints ) ) {
	                    			
	                    			// Sort array with point comparator
	                    			// Then add array point by point to megalist
	                    			Arrays.sort( fourpoints );
	                    			addToList( allpts, fourpoints );
	                    		}
	                    }
	                }
	    	 		}
	        }
	     
	     // Create Point array from list after sorting
	     // Sorts "arrays" in megalist array
	     Point[] allpoints = allpts.toArray(new Point[0]);
	     printSorted( allpoints );
	     
	 }
	
}