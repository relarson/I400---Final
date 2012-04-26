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

	/* Brett's comment */
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

	private double atanh(double rad) {
		return Math.log((1 + rad) / (1 - rad)) / 2;
	}

	public int longitudeToX(double lon) {
		return (int) Math.floor((lon - centerLon) * pixelsPerDegree) + (width / 2);
	}

	public int latitudeToY(double lat) {
		double centerY = atanh(Math.sin(Math.toRadians(centerLat))) * pixelsPerRadian;
		double localAtanh = atanh(Math.sin(Math.toRadians(lat)));
		return (int) Math.floor(centerY - (localAtanh * pixelsPerRadian)) + (height / 2);
	}

}
