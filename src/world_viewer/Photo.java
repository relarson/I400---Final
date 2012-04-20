package world_viewer;

/**
 * @author Ross Larson and Brett Poirier
 * @class INFO-I 400
 */
public class Photo {

	public String imageID = "";
	public String imageURL;

	public double latitude, longitude;

	/**
	 * Construct a photo object
	 * 
	 * @param imageURL - The photo's URL
	 * @param latitude - Latitude coordinate of the Photo
	 * @param longitude - Longitude coordinate of the Photo
	 */
	public Photo(String imageURL, double latitude, double longitude) {
		this.imageURL = imageURL;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public boolean equals(Photo other) {
		return this.imageURL.equals(other.getImageURL());
	}

	/**
	 * @return the image
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return " Lat: " + latitude + "\nLong: " + longitude + "\n URL: " + imageURL;
	}

}
