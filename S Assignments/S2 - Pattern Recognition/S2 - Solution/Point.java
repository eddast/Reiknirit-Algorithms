/*
 * T-301-REIR: Algorithms
 * Assignment: S2 - Pattern Recognition
 * Point object class
 * Due: 15.09.2017
 * By eddasr15 and birgittab15
 */

package s2;

import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdDraw;

/*************************************************************************
 * Compilation: javac Point.java Execution: Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 * @author Magnus M. Halldorsson, email: mmh@ru.is
 *************************************************************************/
public class Point implements Comparable<Point> {

    public final int x, y;

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>(){
    		
    		// A new compare method that compares current point to slope of two other points
    		// Returns 1 if point1 and this point's slope is greater, otherwise 1
    		public int compare(Point P1, Point P2){
    			
    			// Check slope of this point vs point 1 and this point vs point 2
    			// "Edge cases" handled in slopeTo
    			if( slopeTo(P1) > slopeTo(P2) ) { return 1; }
    			
    			else if( slopeTo(P2) > slopeTo(P1) ) { return -1; }
    			
    			// Equal slopes return 0
    			else { return 0; }
    		}
    };

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	
    		// A degenerate line returns negative infinity
    		if(x == that.x && y == that.y){
    			return Double.NEGATIVE_INFINITY;
    		}
    	
    		// A vertical line segment returns positive infinity
    		else if(x == that.x){
    			return Double.POSITIVE_INFINITY;
    		}
    	
    		// Horizontal line returns a positive zero
    		else if(y == that.y){ return 0.0; }
        
    		else {
    			// Use lexiographical comparison to chose values
    			// We don't want negative slopes
    			Point larger, smaller;
    		
    			if(this.compareTo(that) == -1) {
    				larger = that; smaller = this;
    			} else {
    				larger = this; smaller = that;
    			}
        
    			// Use m = (y1-y0)/(x1-x0) to calculate slope
    			return	( (double) larger.y - smaller.y) /
    					( (double) larger.x - smaller.x);
    		}
    }

    /**
     * Is this point lexicographically smaller than that one? comparing
     * y-coordinates and breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
        
    		// If current point is higher on the x axis
    		// it is considered lexicographically larger
    		if(y < that.y) {return -1; }
    	
    		// If this point's y coordinate is larger
    		// it is lexicographically larger
        else if (y > that.y) { return 1; }
    		
    		// If y coordinates are the same
    		// ties are broken by the x coordinate
        else  {
        	
        		if(that.x > x) { return -1;}
        		else if (x > that.x){ return 1; }
        		
        }
    		
    		// Returns zero if points are completely equal
    		return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        /*
         * Do not modify
         */
        In in = new In();
        Out out = new Out();
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt(), y = in.readInt();
            points[i] = new Point(x, y);
        }
        out.printf("Testing slopeTo method...\n");
        for (int i = 1; i < points.length; i++) {
            out.println(points[i].slopeTo(points[i - 1]));
        }
        out.printf("Testing compareTo method...\n");
        for (int i = 1; i < points.length; i++) {
            out.println(points[i].compareTo(points[i - 1]));
        }
        out.printf("Testing SLOPE_ORDER comparator...\n");
        for (int i = 2; i < points.length; i++) {
            out.println(points[i].SLOPE_ORDER.compare(points[i - 1],
                    points[i - 2]));
        }
    }
}
