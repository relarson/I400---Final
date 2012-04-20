/**
 * 
 */
package world_viewer;

import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * @author Ross and Brett
 *
 */
public class Box {

	public int ID;
	public Node point;
	public Comparator<Photo> comparator = new Comparator<Photo>() {
		public int compare(Photo a, Photo b) {;
			//System.out.println("Edge comparator: " + c);
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
	 * 
	 * @param ID
	 * @param point
	 */
	public Box (int ID, Node point) {
		this.ID = ID;
		this.point = point;
	}
	
	public void add(Photo p) {
		photos.add(p);
	}
	
	public double calcLength(Node s, Photo e) {
		//System.out.println(s + "   " + e);
		double dx = s.latitude - e.latitude;
		double dy = s.longitude - e.longitude;
		double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return d;
	}
	
	public boolean equals(Box e) {
		return this.ID == e.ID;
	}
	
	public String toString() {
		return "Box #: " + ID + "\n" + point.toString();
	}
}
