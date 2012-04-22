package world_viewer;


/**
 * @author Ross and Brett
 * @class INFO-I 400
 *
 */
public class MapNode {

	public double latitude;
	public double longitude;
	
	public MapNode(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String toString() {
		return " Lat: " + latitude + "Long: " + longitude;
	}
	
	public boolean equals(MapNode other) {
		return this.latitude == other.latitude && this.longitude == other.longitude;			
	}
}
