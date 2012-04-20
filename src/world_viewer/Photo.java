package world_viewer;

/**
 * @author Ross Larson and Brett Poirier
 * @class INFO-I 400
 */
public class Photo {

	public String title = "";
	public String imageURL;

	public double latitude, longitude;

	/**
	 * Construct a photo object
	 * 
	 * @param imageURL - The photo's URL
	 * @param latitude - Latitude coordinate of the Photo
	 * @param longitude - Longitude coordinate of the Photo
	 */
	public Photo(String title, String imageURL, double latitude, double longitude) {
		this.title = title;
		this.imageURL = imageURL;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public boolean equals(Photo other) {
		return this.imageURL.equals(other.imageURL);
	}

	@Override
	public String toString() {
		return "Title: " + title + "\n  Lat: " + latitude + "\n Long: " + longitude + "\n URL: " + imageURL;
	}

}
