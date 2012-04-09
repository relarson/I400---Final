package final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.NodeList;

/**
 * @notes We made the decision to have no tags perform a Parameterless search of
 *        Flickr
 */

public class Flickr {

	private static String api_key = "e7027a5e1ea23330d7d79603cf366e35";

	public Flickr() {
		// Does nothing
	}

	/**
	 * @param tags - the tag(s) to look for in Flickr Database
	 * @return an ArrayList of Photos
	 * @throws IOException
	 * @throws DOMException
	 * @throws XPathExpressionException
	 */
	public ArrayList<Photo> findPhotosByTags(String tags) throws IOException,
			XPathExpressionException, DOMException {
		ArrayList<Photo> al = new ArrayList<Photo>();
		XPathReader xpath = null;

		int page = 1, pages = 1;

		//long start = System.currentTimeMillis();
		xpath = new XPathReader(flickrSearch(tags, 1));
		pages = Integer.parseInt((String) xpath.read("//photos/@pages",
				XPathConstants.STRING));
		//System.out.println(flickrSearch(tags, 1));

		while (page <= pages) {
			NodeList tag = (NodeList) xpath.read("//photo/@tags",	XPathConstants.NODESET);
			// NodeList titles = (NodeList) xpath.read("//photo/@title", XPathConstants.NODESET);
			NodeList lats = (NodeList) xpath.read("//photo/@latitude", XPathConstants.NODESET);
			NodeList longs = (NodeList) xpath.read("//photo/@longitude", XPathConstants.NODESET);
			NodeList urls = (NodeList) xpath.read("//photo/@url_m", XPathConstants.NODESET);
			
			for (int i = 0; i < tag.getLength(); i++) {
				double la, lg;
				la = Double.parseDouble(lats.item(i).getNodeValue());
				lg = Double.parseDouble(longs.item(i).getNodeValue());
				
				al.add(new Photo(tag.item(i).getNodeValue(), urls.item(i).getNodeValue(), la, lg));
			}
			// long curr = System.currentTimeMillis();
			// System.out.println(page + " / " + pages + " collected. Elapsed time: " + (curr - start) + " milliseconds.");
			page++;
			if (page <= pages) {
				xpath = new XPathReader(flickrSearch(tags, page));
			}
		}
		// long end = System.currentTimeMillis();
		// System.out.println(pages + " pages collected in " + (end - start) + " milliseconds."); 
		return al;
	}

	/**
	 * To search for photos by tags
	 * 
	 * @param tags - tags to search for
	 * @return the xml return from {@link #httpGet(String) httpGet(String)}
	 * @throws IOException
	 */
	private static String flickrSearch(String tags, int page) throws IOException {
		String url = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
		url += "&api_key=" + api_key;
		url += "&tags=" + tags;
		url += "&hasgeo=true";
		url += "&extras=geo,url_m";
		url += "&per_page=500";
		url += "&page=" + page;
		// System.out.println(url);
		return httpGet(url);
	}

	/**
	 * code for making a restful Get request
	 * 
	 * @param urlStr - the URL to get a connection too
	 * @return the responce from the URL from urlStr
	 * @throws IOException
	 */
	private static String httpGet(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		// Buffer the result into a string
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn
				.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		return sb.toString();
	}
}