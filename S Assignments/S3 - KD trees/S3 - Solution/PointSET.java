/*
 * T-301-REIR: Algorithms
 * Assignment: S3 - KD-Trees
 * Brute force algorithm
 * Due: 29.09.2017
 * By eddasr15 and birgittab15
 */

package S3;

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;

public class PointSET {
	
	// Private variables to form the data type
	private int num_points;
	private SET<Point2D> points;
	
	// 	Constructor: builds an empty LLRB tree of points
	public PointSET ( ) {
		
		// Creates the empty set
		// No points in set
		points = new SET<Point2D> ( );
		num_points = 0;
	}

	// Draws all points to standard draw
	public void draw ( ) {
		
		// Iterate through point set
		for(Point2D point : points){
			
			// Point2D has a method to draw itself
			point.draw( );
		}
	}

	// Returns all points in the set that are inside the rectangle
	// Worst-case time complexity: linear
	Iterable <Point2D> range ( RectHV rect ) {
		
		// Initialize points iterable
		// Contains zero objects (points)
		Queue<Point2D> in_range = new Queue<Point2D>( );
		
		// Iterate through points
		for(Point2D point : points) {
			
			// Add point to list if it is within rectangle
			if ( rect.contains(point) ) { in_range.enqueue(point); }
		}
		
		// Returns list object (can contain no points)
		return in_range;
		
	}
	
	// Returns the nearest neighbor in the set to p
	// (Returns null if set is empty)
	// Worst-case time complexity: linear
	public Point2D nearest ( Point2D p ) {
		
		// Empty set returns null
		if(isEmpty()) { return null; }
		
		// Search the LLRB tree otherwise
		// Initialize minimum distance point as maximum point
		// (Any random point in SET would do really)
		Point2D min_dst_pt = points.max();
		
		// Iterate through point set
		for(Point2D point : points) {
			
			// Change the minimum distance point when less distance is found
			// between current point and compare point (can't be same point!)
			if ( p != point &&
				(p.distanceSquaredTo(point) <
				 p.distanceSquaredTo(min_dst_pt)) ) {
				min_dst_pt = point;
			}
			
		}
		
		// Return minimum distance point
		return min_dst_pt;
	}
	
	// Add point p to set if not already in set
	// SET supports only unique keys so it's add function works
	// Worst-case time complexity: lg(N)
	public void insert ( Point2D p ) { points.add(p); num_points++; }
	
	// Set is empty when no points are in it
	public boolean isEmpty ( ) { return num_points == 0; }
	
	// Returns number of points in set
	public int size ( ) { return num_points; }
	
	// Returns true if set contains point p
	// Worst-case time complexity: lg(N)
	public boolean contains ( Point2D p ) { return points.contains(p); }
	
	
	
	
	// ( Private testing )
	public static void main ( String[] args ) {
	
		/*Point2D p1 = new Point2D(0.14, 0.9);
		Point2D p2 = new Point2D(0.0, 0.3);
		Point2D p3 = new Point2D(0.0, 0.4);
		Point2D p4 = new Point2D(0.1, 0.3);
		Point2D p5 = new Point2D(0.4, 0.8);
		Point2D p6 = new Point2D(0.4, 0.8);
		
		PointSET ps = new PointSET( );
		
		ps.insert(p1); ps.insert(p2); ps.insert(p3);
		ps.insert(p4); ps.insert(p5); ps.insert(p6);
		
		 ps.draw();*/
		
	}
	
}
