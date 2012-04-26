/**
 * 
 */
package world_viewer;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Ross Larson
 * @author Brett Poirier
 * 
 * @class INFO-I400
 */
public class Box {

	public int ID;
	public MapNode point;
	public Comparator<Photo> comparator = new Comparator<Photo>() {

		/**
		 * Compares two photos and returns -1 if photo a is closer to box center, 1 if photo b is
		 * closer, and 0 if the distances are equal.
		 * 
		 * @param a -Photo
		 * @param b -Photo
		 * 
		 * @return int
		 */
		public int compare(Photo a, Photo b) {
			;
			if (calcLength(point, a) < calcLength(point, b)) {
				return -1;
			}
			else if (calcLength(point, a) > calcLength(point, b)) {
				return 1;
			}
			else {
				return 0;
			}
		}
	};
	public PriorityQueue<Photo> photos = new PriorityQueue<Photo>(1, comparator);

	/**
	 * Creates a Box object
	 * 
	 * @param ID -Integer Identification number for Box.
	 * @param point -MapNode corresponding to the Box's point.
	 */
	public Box(int ID, MapNode point) {
		this.ID = ID;
		this.point = point;
	}

	/**
	 * Adds a photo to the global photo list.
	 * 
	 * @param p -Photo to be added.
	 */
	public void add(Photo p) {
		photos.add(p);
	}

	/**
	 * Calculates distance between photo and Box center.
	 * 
	 * @param s -MapNode at center of Box.
	 * @param e
	 * 
	 * @return double
	 */
	public double calcLength(MapNode s, Photo e) {
		// System.out.println(s + "   " + e);
		double dx = s.latitude - e.latitude;
		double dy = s.longitude - e.longitude;
		double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return d;
	}

	/**
	 * Checks for equality of boxes.
	 * 
	 * @param e -Box to be checked for equality.
	 * 
	 * @return boolean
	 */
	public boolean equals(Box e) {
		return this.ID == e.ID;
	}

	/**
	 * returns a useful string representation of the Box's properties
	 * 
	 * @return String
	 */
	public String toString() {
		return "Box #: " + ID + " holds " + photos.size() + " photos.\n" + point.toString();
	}
}
