/**
 * 
 */
package world_viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.JComponent;

/**
 * @author Ross and Brett
 *
 */
public class Edge {

	public double length;
	public Node start;
	public Node end;
	public Comparator<Photo> comparator = new Comparator<Photo>() {
		public int compare(Photo a, Photo b) {
			Node d = new Node(a);
			Node e = new Node(b);
			Node c = Pathfinder.nodes.get(a.from);
			//System.out.println("Edge comparator: " + c);
			if (calcLength(c, d) < calcLength(c, e)) {
				return -1;
			}
			else if (calcLength(c, d) > calcLength(c, e)) {
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
	 * @param start
	 * @param End
	 */
	public Edge (Node start, Node end) {
		this.start = start;
		this.end = end;
		this.length = calcLength(this.start, this.end);
	}
	
	/**
	 * FOR TESTING ONLY!!!
	 * @param start
	 * @param end
	 * @param length
	 */
	public Edge (Node start, Node end, double length) {
		this.start = start;
		this.end = end;
		this.length = length;
	}
	
	public double calcLength(Node s, Node e) {
		//System.out.println(s + "   " + e);
		double dx = s.photo.latitude - e.photo.latitude;
		double dy = s.photo.longitude - e.photo.longitude;
		double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return d;
	}
	
	public boolean equals(Edge e) {
		if (this.start.equals(e.start) && this.end.equals(e.end)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "Edge from " + start.id + " to " + end.id + ".";
	}
}
