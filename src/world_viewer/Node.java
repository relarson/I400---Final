/**
 * 
 */
package world_viewer;

import java.util.ArrayList;

/**
 * @author Ross and Brett
 * @class INFO-I 400
 *
 */
public class Node {

	public Photo photo;
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public String id;
	public double distance = Double.MAX_VALUE; // Used for dijkstra's
	public Node pi = null;
	
	public Node(Photo photo) {
		this.photo = photo;
		this.id = photo.imageID;
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	public void reset() {
		distance = Double.MAX_VALUE;
		pi = null;
	}
	
	public String toString() {
		return "Node at: " + id;
	}
	
	public boolean equals(Node other) {
		return this.id.equals(other.id);			
	}
}
