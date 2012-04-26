package world_viewer;

/**
 * @author Ross Larson and Brett Poirier
 * @class INFO-I 400
 */
public class GoogleMap {

	private double centerLat;
	private double centerLon;
	private int width;
	private int height;
	private int zoom;

	private double pixelsPerDegree;
	private double pixelsPerRadian;

	/**
	 * Creates GoogleMap object.
	 * 
	 * @param scale -Scaling factor for adjustment to different screen resolutions.
	 */
	public GoogleMap(double scale) {
		this.centerLat = 0;
		this.centerLon = 0;
		this.width = (int) (500 * scale);
		this.height = (int) (400 * scale);
		this.zoom = 1;

		double realWidth = 250.00 * scale * Math.pow(2, zoom);

		pixelsPerDegree = realWidth / 360.00;
		pixelsPerRadian = realWidth / (2.00 * Math.PI);
	}

	/**
	 * Takes the arc-tangent of a given double.
	 * 
	 * @param rad
	 * 
	 * @return double
	 */
	private double atanh(double rad) {
		return Math.log((1 + rad) / (1 - rad)) / 2;
	}

	/**
	 * Converts a longitude coordinate to an integer x to be plotted on WorldView GUI.
	 * 
	 * @param lon -longitude coordinate
	 * 
	 * @return int
	 */
	public int longitudeToX(double lon) {
		return (int) Math.floor((lon - centerLon) * pixelsPerDegree) + (width / 2);
	}

	/**
	 * Converts a latitude coordinate to an integer y to be plotted on WorldView GUI.
	 * 
	 * @param lat -latitude coordinate
	 * 
	 * @return int
	 */
	public int latitudeToY(double lat) {
		double centerY = atanh(Math.sin(Math.toRadians(centerLat))) * pixelsPerRadian;
		double localAtanh = atanh(Math.sin(Math.toRadians(lat)));
		return (int) Math.floor(centerY - (localAtanh * pixelsPerRadian)) + (height / 2);
	}

}
