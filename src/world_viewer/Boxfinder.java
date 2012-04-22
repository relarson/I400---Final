package world_viewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.JOptionPane;
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
	

	@SuppressWarnings("unchecked")
	public Box[][] photoGrid = new Box[36][36];	


	public Boxfinder() {
		createBoxes();
		try {
			getPhotos();
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
	
	public void createBoxes() {
		for (int i = 0 ; i < 36 ; i++) {
			for (int j = 0 ; j < 36 ; j++) {
				photoGrid[i][j] = new Box(i + 36*j, new MapNode(getLat(j), getLong(i)));
			}
		}
	}
	
	public double getLat(int j) {
		return 5*(j-18);
	}
	
	public double getLong(int i) {
		return 10*(i-18);
	}

	public void getPhotos() throws XPathExpressionException, DOMException, IOException {
		Long s = System.currentTimeMillis();
		photos = flick.uncache();
		Long p = System.currentTimeMillis();
		assignPhotosToBoxes();
		Long e = System.currentTimeMillis();
		System.out.println("Photos: " + (p - s) + "\nAll assignments: " + (e - p) + "\nTotal: " + (e - s));
	}

	public void assignPhotosToBoxes() {
		for (Photo p : photos) {
			int i, j;
			i = (int) ((p.longitude + 180) % 36);
			j = (int) ((p.latitude + 90) % 36);
			photoGrid[i][j].add(p);
		}
	}
}
