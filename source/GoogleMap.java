package final_project;
/**
 * @author Ross Larson, Jon gold, Sara Wimmer and Kyle Sprouls
 * @class CSCI-C 343
 */
public class GoogleMap {
	
	private double centerLat;
	private double centerLon;
	private int width;
	private int height;
	private int zoom;
	
	private double pixelsPerDegree;
	private double pixelsPerRadian;
	
	public GoogleMap()
	{
		this.centerLat = 39.17259213050035;
		this.centerLon = -86.51741981506348;
		this.width = 640;
		this.height = 640;
		this.zoom = 15;

		double realWidth = 256.00 * Math.pow(2, zoom);

		pixelsPerDegree = realWidth / 360.00;
		pixelsPerRadian = realWidth / (2.00 * Math.PI);
	}
	
	private double atanh(double rad)
	{
		return Math.log((1 + rad) / (1 - rad)) / 2;
	}
	
	public int longitudeToX(double lon)
	{
		return (int)Math.floor((lon - centerLon) * pixelsPerDegree) + (width / 2);		
	}
	
	public int latitudeToY(double lat)
	{
		double centerY = atanh(Math.sin(Math.toRadians(centerLat))) * pixelsPerRadian;
		double localAtanh = atanh(Math.sin(Math.toRadians(lat)));
		return (int)Math.floor(centerY - (localAtanh * pixelsPerRadian)) + (height / 2);
	}

}
		