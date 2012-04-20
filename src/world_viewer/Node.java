package world_viewer;


/**
 * @author Ross and Brett
 * @class INFO-I 400
 *
 */
public class Node {

	public double latitude;
	public double longitude;
	
	public Node(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String toString() {
		return " Lat: " + latitude + "Long: " + longitude;
	}
	
	public boolean equals(Node other) {
		return this.latitude == other.latitude && this.longitude == other.longitude;			
	}
}
