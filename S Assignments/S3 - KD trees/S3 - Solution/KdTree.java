/*
 * T-301-REIR: Algorithms
 * Assignment: S3 - KD-Trees
 * KD-Trees implementation version
 * Due: 29.09.2017
 * By eddasr15 and birgittab15
 */

package S3;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;


public class KdTree {
	
	
	
	/*********************************************************** 
	 * 
	 *					API PUBLIC FUNCTIONS
	 *
	 ***********************************************************/
	
	
	
	// Returns all points in the set that are inside the rectangle
	// Worst-case time complexity: lg(N)
	Iterable <Point2D> range ( RectHV rect ) { return KDtree_find_range( rect ); }
	
	// Returns the nearest neighbor in the set to p
	// (Returns null if set is empty)
	// Worst-case time complexity: lg(N)
	public Point2D nearest ( Point2D p ) { return KDtree_find_nearest( p ); }
	
	// Add point p to set if not already in set
	// SET supports only unique keys so it's add function works
	// Worst-case time complexity: lg(N)
	public void insert ( Point2D p ) { KDtree_put( p ); }
	
	// Set is empty when no points are in it
	public boolean isEmpty ( ) { return KDtree_size( ) == 0; }
	
	// Returns number of points in set
	public int size ( ) { return KDtree_size( ); }
	
	// Returns true if set contains point p
	// Worst-case time complexity: lg(N)
	public boolean contains ( Point2D p ) { return KDtree_contains(p); }
	
	// Draws all points to standard draw
	public void draw ( ) { KDtree_draw( ); }
	
	
	
	
	
	
	/************************************************************* 
	 * 
	 *				KD-TREE IMPLEMENTATION METHODS
	 *
	 *************************************************************/
	
	
	/** NODE CLASS BASE FOR KD TREE **/
	
	// Tiny structure of a node
	// Has left and right links and Point2D key value
	// Has rectangle object corresponding to the rectangle it makes
	// When perpendicular line is drawn from key point to parent key point line
	private static class Node {
		
		// Member variables
		Node left; Node right; Point2D key; RectHV rect;
		
		// Constructor - node needs to be initialized with key
		public Node	( Point2D key_) { key = key_;} 
		
	}
	

	/** KD TREE IMPLEMENTATION VARIABLES **/
	
	// KD tree variables - root and number of points
	// Orientation boolean variable for each depth level of tree
	// (Determines whether to compare horizontally or vertically)
	int num_points; Node root;
	private static boolean HORIZONTAL = true, VERTICAL = false;
		
	
	/** KD TREE CONSTRUCTOR **/
	
	// Initializes an empty KD-tree
	public KdTree ( ) { root = null; num_points = 0; }
	
	
	/** KD TREE IMPLEMENATION METHODS **/
	
	// Inserts into the KD-tree using a recursive function
	private void KDtree_put ( Point2D point ) {
		
		// Calls for recursive put function (with horizontal orientation as first depth level
		// If tree was previously empty, new node becomes the root node in tree
		// Number of nodes then incremented by one
		if ( num_points == 0 ) { root = new Node ( point ); root.rect = new RectHV ( 0,0,1,1 ); num_points++; }
		else { KDtree_recursive_put ( root, point, HORIZONTAL); }
	}
	
	// Node is the compare node we use to walk through tree, key is insertion value
	// Orient variable tells us how to compare two points (horizontally/true or vertically/false)
	private void KDtree_recursive_put ( Node node, Point2D key, boolean orient) {
		
		// KD-tree does not support double values; return if values are equal
    		if( node.key.equals(key) ) { return; }
    	
    		// Compare key to current node to navigate through tree
    		double cmp = compare_points(node.key, key, orient);
    	
    		// If key is smaller than node index should be in the left subtree - look there
    		if ( cmp > 0 ) {
    		
    			// If slot is empty we have the correct slot and insert
    			// New node has the inserted point as key and num points is increased
    			if( node.left == null ) {
    			
    				node.left = new Node( key ); num_points++;
			
    				// Now we create corresponding rectangles the points denote
    				// According to position in tree, alignment and orientation
    				// (The concept is that each point "draws a line" perpendicular
					// on one side of "parent node line"-rectangle and the rectangle they make
					// with that line is stored in node with point)
    				if ( orient == HORIZONTAL ) {
    					node.left.rect = new RectHV(	node.rect.xmin(),	node.rect.ymin(),
														node.key.x(),		node.rect.ymax() );	
													// LEFT HORIZONTAL - PARTITION ON X MAXIMUM VALUE
    				} else if ( orient == VERTICAL ) {
    					node.left.rect = new RectHV(	node.rect.xmin(),	node.rect.ymin(),
    													node.rect.xmax(),	node.key.y() );	
													// LEFT VERTICAL - PARTITION ON Y MAXIMUM VALUE
    				}
			
    				// If slot was not found, we continue walking through tree recursively to find correct index
    				// Now searching only the left subtree of current node, as index should be there
    			} else { KDtree_recursive_put(node.left, key, !orient); }
    		
    		// If key is larger than node index should be in right subtree of current node - look there
    		} else {
    		
    			// If slot is empty we have the correct slot and insert
    			// New node has the inserted point as key and num points is increased
    			if( node.right == null ) {
    				
    				node.right = new Node( key ); num_points++;
					
    				// Now we create corresponding rectangles the points denote
    				// Created according to position in tree, alignment and orientation
    				// (The concept is that each point "draws a line" perpendicular
					// on one side of "parent node line"-rectangle and the rectangle they make
					// with that line is stored in node with point)
    				if ( orient == HORIZONTAL ) {
    					node.right.rect = new RectHV( node.key.x(),		node.rect.ymin(),
    												  node.rect.xmax(),	node.rect.ymax() );
													 // RIGHT HORIZONTAL - PARTITION ON X MINIMUM VALUE
    				} else if ( orient == VERTICAL ) {
    					node.right.rect = new RectHV( node.rect.xmin(),	node.key.y(),		
												 	  node.rect.xmax(),	node.rect.ymax() );
													// RIGHT VERTICAL - PARTITION ON Y MIN VALUE
    				}
		// If slot was not found, we continue walking through tree recursively to find correct index
		// Now searching only the right subtree of current node, as index should be there
		} else { KDtree_recursive_put ( node.right, key, !orient ); }
	}
}
	
	// Recursively finds nearest neighbor
	private Point2D KDtree_find_nearest ( Point2D point ) { 
		
		// Special cases: empty tree returns null
		// If tree contains point it is it's own nearest neighbor
		if( isEmpty() )				{ return null; }
		else if ( contains(point) )	{ return point; }
		
		// Otherwise recursive call for a function to find nearest neighbor
		else { return ( KDtree_find_nearest_recursive ( root, root, point, HORIZONTAL) ).key; }
	}
	
	// Determines a champion point - nearest point so far - and recursively walks through tree
	// Uses a pruning rule for efficiency
	private Node KDtree_find_nearest_recursive ( Node node, Node champ, Point2D point, boolean orient ) { 
		
		// Base case 1: return champion when we reach end of tree
		// Base case 2: If rectangle of current node is equal or less than champion node
		// there is no need to explore that node or its subtrees (PRUNING RULE!)
		if ( node == null ) { return champ; }
		if ( node.rect.distanceSquaredTo(point) >=
			 champ.key.distanceSquaredTo(point) ) {
			return champ;
		}
		
		// Check if point in current node is nearer than the previous champion - Update champ if so
		if (	node.key.distanceSquaredTo(point) <
				champ.key.distanceSquaredTo(point) ) { champ = node; }
		
		// Compare point and node to see which subtree to explore first:
		// Subtree on the same side of splitting line as query point chosen first
		double cmp = compare_points ( node.key, point, orient );
		if( cmp > 0 ) { 
			// Point is on left side of splitting line - search left subtree first
			champ = KDtree_find_nearest_recursive ( node.left, champ, point, !orient);
			champ = KDtree_find_nearest_recursive ( node.right, champ, point, !orient);
		} else {
			// Point is on right side of splitting line - search right subtree first
			champ = KDtree_find_nearest_recursive ( node.right, champ, point, !orient);
			champ = KDtree_find_nearest_recursive ( node.left, champ, point, !orient);
		}
		
		// Finally return champion node containing nearest neighbor point
		return champ;
		
	}
	
	// Functions that recursively check weather tree contains a certain point
	// Orientation initialized as horizontal (true) as root is considered horizontal
	private boolean KDtree_contains ( Point2D point ) { return KDtree_contains_recursive ( root, point, HORIZONTAL ); }
	private boolean KDtree_contains_recursive ( Node node, Point2D key, boolean orient) {
		
		// Base cases : once we reach end of tree point was not found in tree
		// If point is found in node we return true 
		if( node == null )				{ return false; }
		if( node.key.equals( key ) )	{ return true; }
		
		// Recursively walk through tree
		// Going into right subtree if point is to the right of compare node
		// Going into left subtree if point is to the left of compare node
		double cmp = compare_points ( node.key, key, orient );
		
		// When we go deeper into tree we need to flip orientation to which we compare
		// ( compare_points relies on orientation to compare points )
		if ( cmp > 0 )	{ return KDtree_contains_recursive ( node.left,  key, !orient); }
		else 			{ return KDtree_contains_recursive ( node.right, key, !orient); }
		
	}
	
	// Initializes list and makes a call to another recursive function 
	private Iterable <Point2D> KDtree_find_range ( RectHV rect ) {
		
		Queue<Point2D> in_range = new Queue<Point2D>( );
		KDtree_find_range_recursive ( in_range, root, rect );
		return in_range;
	}
	
	// Recursively finds range within rectangle
	// Uses a pruning rule for efficiency
	// Changes value of parameter list in_range when executed
	private void KDtree_find_range_recursive ( Queue<Point2D> in_range, Node node, RectHV rect ){
		
		// Base case: if query rectangle does not intersect current node rectangle
		// there is no need to explore that node or it's subtrees (PRUNING RULE!)
		if ( !node.rect.intersects( rect ) ) { return; }
		
		// Add nodes to list if they are in rectangle
		if ( rect.contains( node.key ) ) { in_range.enqueue( node.key ); }
		
		// Search both subtrees, no preference which first
		// Pruning rule will enable pruning as appropriate
		if ( node.left != null )	{ KDtree_find_range_recursive ( in_range, node.left, rect );  }
		if ( node.right != null )	{ KDtree_find_range_recursive ( in_range, node.right, rect ); }
	}
	
	// Draw points of KD-tree
	private void KDtree_draw( ) { KDtree_draw_recursive( root ); }
	private void KDtree_draw_recursive ( Node node ) {
		
		// Iterates through tree and draws points
		// Point has a method to draw itself
		if(node == null){ return; }
		KDtree_draw_recursive ( node.left );
		KDtree_draw_recursive ( node.right );
		node.key.draw();
	}
	
	// Compare node with point based on tree-depth's orientation and it's corresponding axis
	// Orientation: true for horizontal, false for vertical
	// Returns 0 for equal, > 0 when point1 is to the left of point2 and < 0 when point1 is to the right
	private double compare_points( Point2D point1, Point2D point2, boolean orient ) {
		
		// Horizontal points mean comparing x-axis values
		// Vertical points mean comparing y-axis values
		if ( orient == HORIZONTAL )	{ return ( point1.x( ) - point2.x( ) ); }
		else 						{ return ( point1.y( ) - point2.y( ) ); }
	}
	
	// Returns number of points in KD-Tree
	private int KDtree_size ( ) { return num_points; }
	
	
	
	
	
	
	/***********************************
	 *		MAIN (PRIVATE TESTS)
	 ***********************************/
	
	
	
	// ( Private testing )
	public static void main(String[] args) {
		
		/**** TESTING CONTAINS AND INSERT ****/
		
		/*KdTree kd = new KdTree ( );
		Point2D p1 = new Point2D(0.1, 0.3); Point2D p2 = new Point2D(0.2, 0.3);
		Point2D p3 = new Point2D(0.32, 0.34); Point2D p4 = new Point2D(0.37, 0.3);
		Point2D p5 = new Point2D(0.23, 0.98); Point2D p6 = new Point2D(0.22, 0.3);
		Point2D p7 = new Point2D(0.23, 0.12); Point2D p8 = new Point2D(0.314, 0.944);
		kd.insert(p1); kd.insert(p1); kd.insert(p2);
		kd.insert(p3); kd.insert(p4);
		kd.insert(p5); kd.insert(p6);
		kd.insert(p7); kd.insert(p8);
		if (kd.contains(p1)) StdOut.println( "Root node correctly inserted!" );
		if (kd.contains(p2)) StdOut.println( "Child node correctly inserted!" );
		if(	kd.contains(p1) && kd.contains(p2) && kd.contains(p3) &&
			kd.contains(p4) && kd.contains(p5) && kd.contains(p6) &&
			kd.contains(p7) && kd.contains(p8) ) {
			StdOut.println("Contain function FINALLY works!");
		}*/
	}

	
}
