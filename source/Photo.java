package final_project;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.xpath.XPathExpressionException;
/**
 * @author Ross Larson and Brett Poirier
 * @class INFO-I 400
 */
public class Photo {

	public String imageTags = "";
	public String imageID = "";
	public String imageURL;
	public String from = "";
	public String toward = "";

	public boolean isNode = false;

	public double latitude, longitude;
	
	private HashMap<String, String> badIDs = new HashMap<String, String>();

	private Flickr flick = new Flickr();

	/**
	 * Construct a photo object
	 * 
	 * @param imageTags - The tags on the photo
	 * @param imageURL - The photo's URL
	 * @param latitude - Latitude coordinate of the Photo
	 * @param longitude - Longitude coordinate of the Photo
	 */
	public Photo(String imageTags, String imageURL, double latitude, double longitude) {
		this.imageTags = imageTags;
		this.imageURL = imageURL;
		this.latitude = latitude;
		this.longitude = longitude;
		
		// populate badIDs
		badIDs.put("bryanhouse", "bx");
		badIDs.put("beck", "be");
		badIDs.put("mac", "mc");
		badIDs.put("greene", "gh");
		badIDs.put("sample", "gx");
		badIDs.put("lhback", "lh");
		

		parseTags();
	}

	public boolean equals(int otherID) {
		return this.imageID.equals(otherID);
	}

	/**
	 * @return the imageID
	 */
	public String getImageID() {
		return imageID;
	}

	/**
	 * @return the image
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public String getImageURL() throws XPathExpressionException, IOException {
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
	public int hashCode() {
		return imageID.hashCode();
	}

	private void parseTags() {
		String tags = imageTags;
		tags = tags.replace("c343fall2010", "").trim();
		// System.out.println(tags);
		int node = tags.indexOf("node") + 4;
		int f = tags.indexOf("from") + 4;
		int t = tags.indexOf("toward") + 6;
		// System.out.println("node: " + node + ", f: " + f + " t: " + t);
		if (node > 3) {
			//System.out.println(tags.indexOf(" "));
			int s = tags.indexOf(" ");
			if (s > 0) {
				System.out.println(s);
				imageID = tags.substring(node,s);
			}
			else {
				imageID = tags.substring(node, tags.length());
			}
			isNode = true;
			
			//System.out.println("node: " + imageID + "   tags: " + imageTags);
		}
		else if (f > 3 && t > 5) {
			if (tags.lastIndexOf(" ") < t) {
				from = tags.substring(f, tags.indexOf(" ", f));
				toward = tags.substring(t, tags.length());
			}
			else if (tags.lastIndexOf(" ") < f) {
				toward = tags.substring(t, tags.indexOf(" ", t));
				from = tags.substring(f, tags.length());
			}
			else {
				from = tags.substring(f, tags.indexOf(" ", f));
				toward = tags.substring(t, tags.indexOf(" ", t));
			}
			
			// System.out.println("f: " + from);
			// System.out.println("t: " + toward);
		}
		imageID = imageID.toLowerCase();
		from = from.toLowerCase();
		toward = toward.toLowerCase();
		
		if (badIDs.containsKey(imageID)) {
			imageID = badIDs.get(imageID);
		}
		if (badIDs.containsKey(from)) {
			from = badIDs.get(from);
		}
		if (badIDs.containsKey(toward)) {
			toward = badIDs.get(toward);
		}
	}

	/**
	 * @param imageID the imageID to set
	 */
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	/**
	 * @param image the image to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @param latitude - the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude - the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		if (isNode) {
			return "Node at " + this.imageID;
		}
		else {
			return "Path from " + this.from + " to " + this.toward;
		}
	}

}
