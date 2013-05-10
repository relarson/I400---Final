package world_viewer;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;

/**
 * @author Ross Larson and Brett Poirier
 * @class INFO-I 400
 */
public class Boxfinder {

	ArrayList<Box> boxList;
	ArrayList<Photo> photos = new ArrayList<Photo>();
	Flickr flick = new Flickr();

	public Box[][] photoGrid = new Box[36][36];

	/**
	 * Creates Boxfinder object.
	 * 
	 * @param useCache -boolean value that indicates whether or not to use cache.
	 * @param pages -integer value indicating the number of pages cached.
	 */
	public Boxfinder(String tags, boolean useCache, int pages) {
		createBoxes();
		try {
			getPhotos(tags, useCache, pages);
		}
		catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		catch (DOMException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates boxes for photoGrid.
	 */
	public void createBoxes() {
		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 36; j++) {
				photoGrid[i][j] = new Box(i + 36 * j, new MapNode(getLat(j), getLong(i)));
			}
		}
	}

	/**
	 * Gets the latitude of Box.
	 * 
	 * @param j -Integer value to be converted to latitude
	 * @return double
	 */
	public double getLat(int j) {
		return 5 * (j - 18);
	}

	/**
	 * Gets the longitude of Box
	 * 
	 * @param i -Integer value to be converted to longitude
	 * @return double
	 */
	public double getLong(int i) {
		return 10 * (i - 18);
	}

	/**
	 * Calls to Flickr or cache to retreive photos and assigns them to boxes.
	 * 
	 * @param useCache -boolean value indicating whether or not to use cache to retrieve photos.
	 * @param pages -integer value indicating number of pages
	 * 
	 * @throws XPathExpressionException
	 * @throws DOMException
	 * @throws IOException
	 */
	public void getPhotos(String tags, boolean useCache, int pages) throws XPathExpressionException,
			DOMException, IOException {

		// Long s = System.currentTimeMillis();
		if (!useCache) {
		    flick.cache(tags, pages);
		}
		photos = flick.uncache(tags+".txt");

		// Long p = System.currentTimeMillis();
		assignPhotosToBoxes();
		// Long e = System.currentTimeMillis();
		// System.out.println("Photos: " + (p - s) + "\nAll assignments: " + (e
		// - p) + "\nTotal: "
		// + (e - s));
	}

	/**
	 * Assigns photos to boxes based on latitude and longitude.
	 */
	public void assignPhotosToBoxes() {
		for (Photo p : photos) {
			int i, j;
			i = (int) ((p.longitude + 180) / 10);
			j = (int) ((p.latitude + 90) / 5);
			photoGrid[i][j].add(p);
		}
	}
}
