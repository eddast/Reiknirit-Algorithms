/*
 * T-301-REIR: Algorithms
 * Assignment: D0 - StdDraw
 * Due: 20.08.2017
 * By eddasr15
 */

package D0;

import edu.princeton.cs.algs4.StdDraw;


public class Drawing {
	
	// Part I - StdDraw
	public static void main(String[] args) {
		
		// Head - unfilled ellipse
		// At center of canvas
		double head_center = 0.5;
		double head_width = 0.25;
		double head_size = 0.2;
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.ellipse(head_center, head_center,
						head_width, head_size);
		
		// Whiskers - simple lines
		// Eminate from "cheeks" (center + width of head)
		double right_cheek = head_center + head_width;
		double left_cheek = head_center - head_width;
		double whiskers_len = 0.15;
		double whiskers_slope = 0.02;
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		StdDraw.line(right_cheek + whiskers_len,
					 head_center + whiskers_slope,
					 right_cheek, head_center);
		StdDraw.line(right_cheek + whiskers_len,
				 	 head_center - whiskers_slope,
				 	 right_cheek, head_center);
		StdDraw.line(left_cheek - whiskers_len,
				 	 head_center + whiskers_slope,
				 	 left_cheek, head_center);
		StdDraw.line(left_cheek - whiskers_len,
			 	 	 head_center - whiskers_slope,
			 	 	 left_cheek, head_center);
	
		// Nose - a filled red square
		// At center of head ellipse
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledSquare(head_center, head_center, 0.03);
		
		// Eyes - filled gray ellipses
		double dist_from_center = 0.1;
		double eye_size = 0.02;
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.ellipse(head_center + dist_from_center,
				 		head_center + dist_from_center,
				 		eye_size*2, eye_size);
		StdDraw.ellipse(head_center - dist_from_center,
		 				head_center + dist_from_center,
		 				eye_size*2, eye_size);
		
		// Eye Balls - filled green circles
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledCircle(head_center + dist_from_center,
							 head_center + dist_from_center,
							 eye_size);
		StdDraw.filledCircle(head_center - dist_from_center,
							 head_center + dist_from_center,
							 eye_size);
		
		// Mouth - A simple line
		// A tad below canvas' center
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		double mouth_loc = head_center - dist_from_center;
		StdDraw.line(mouth_loc, mouth_loc,
					 head_center+0.1, head_center-0.1);
		
		// Ears - filled brownish triangles
		StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
		double right_ear = head_center + dist_from_center + 0.01;
		double left_ear = head_center - dist_from_center - 0.01;
		double[] xr = { right_ear, right_ear+0.1, right_ear+0.1 };
		double[] yr = { right_ear+0.1, right_ear+0.01, right_ear+0.1 };
		double[] xl = { left_ear, left_ear-0.1, left_ear-0.1 };
		StdDraw.filledPolygon(xr, yr);
		StdDraw.filledPolygon(xl, yr);
		
		// CAT SAYS MEOW!
		// An unfilled rectangle frames text
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.text(0.5, 0.1, "Hello I am Cat. MEOW!");
		StdDraw.rectangle(0.5, 0.1, 0.3, 0.05);
		
	}

}