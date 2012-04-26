package world_viewer;

/**
 * @author Ross and Brett
 * @class INFO-I 400
 * 
 */
public class MapNode {

	public double latitude;
	public double longitude;

	/**
	 * Creates a MapNode object.
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public MapNode(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Returns a useful string representation of the MapNode's properties.
	 * 
	 * @return String
	 * 	-Useful representation of MapNode's properties.
	 */
	public String toString() {
		return "Lat: " + latitude + ", Long: " + longitude;
	}

	/**
	 * Checks for equality of MapNodes.
	 * 
	 * @param other
	 * 	-MapNode to be compared
	 * 
	 * @return boolean
	 */
	public boolean equals(MapNode other) {
		return this.latitude == other.latitude && this.longitude == other.longitude;
	}
}
