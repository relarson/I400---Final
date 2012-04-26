package world_viewer;

/**
 * @author Ross Larson and Brett Poirier
 * @class INFO-I 400
 */
public class Photo {
	public int ID;
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
	public Photo(int ID, String title, String imageURL, double latitude, double longitude) {
		this.ID = ID;
		this.title = title;
		this.imageURL = imageURL;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Checks equality of photos.
	 * 
	 * @param other -Photo the user wishes to compare to
	 * 
	 * @return boolean
	 */
	public boolean equals(Photo other) {
		return this.imageURL.equals(other.imageURL);
	}

	@Override
	/**
	 * Prints out string representation of the photo's properties.
	 * 
	 * @return String
	 * 	-Useful information about the photo's properties.
	 */
	public String toString() {
		return "  ID: " + ID + "\n Lat: " + latitude + ", Long: " + longitude + "\n URL: "
				+ imageURL;
	}

}
