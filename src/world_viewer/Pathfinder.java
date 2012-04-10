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
public class Pathfinder {

	ArrayList<Photo> results;
	ArrayList<Node> nodeList;
	ArrayList<Photo> edges = new ArrayList<Photo>();
	ArrayList<Node> path;
	static HashMap<String, Node> nodes = new HashMap<String, Node>();
	Flickr flick = new Flickr();

	boolean repeat = true;
	Scanner scan = new Scanner(System.in);
	private int edgesAdded = 0, dups = 0, nDups = 0, missingN = 0;
	private String mode = "normal";

	public Comparator<Node> comparator = new Comparator<Node>() {
		public int compare(Node a, Node b) {
			if (a.distance < b.distance) {
				return -1;
			}
			else if (a.distance > b.distance) {
				return 1;
			}
			else {
				return 0;
			}
		}
	};

	public PriorityQueue<Node> dijkstra = new PriorityQueue<Node>(1, comparator);
	public HashMap<String, Node> settled = new HashMap<String, Node>();
	public ArrayList<Photo> pathPhotos = new ArrayList<Photo>();
	public HashMap<String, double[]> nodeLocations = new HashMap<String, double[]>();


	public Pathfinder(String mode) {
		this.mode = mode;
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
		
		try {
			nodeLocations = parseJSON();
		}
		catch (NumberFormatException nfe) { }
		catch (IOException ioe) { }
		//System.out.println(nodeLocations.size());
	}
	
	
	public HashMap<String, double[]> parseJSON() throws NumberFormatException, IOException{
		HashMap<String, double[]> json = new HashMap<String, double[]>();
		
		String line = "";
		File inputFile = new File("nodes.json");
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(inputFile));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println("inputFile succesfully loaded into BufferedReader as: "	+ inputFile.getAbsolutePath());

		while ((line = inputStream.readLine()) != null	&& !line.equals("")) {
			line = line.trim();
			if (line.indexOf("name") >= 0) {
				String key = line.substring(line.indexOf(":") + 3, line.indexOf(",") - 1).trim().toLowerCase();
				double[] coords = new double[2];
				line = inputStream.readLine();
				line = line.trim();
				coords[0] = Double.parseDouble(line.substring(line.indexOf(":") + 2, line.indexOf(",")).trim());
				line = inputStream.readLine();
				line = line.trim();
				coords[1] = Double.parseDouble(line.substring(line.indexOf(":") + 2));
				json.put(key, coords);
				//System.out.println("Added: " + key + " | " + "(" + coords[0] + ", " + coords[1] + ")");
			}
		}

		inputStream.close();
		
		return json;
	}


	public void getPhotos() throws XPathExpressionException, DOMException, IOException {
		// Long s = System.currentTimeMillis();
		results = flick.findPhotosByTags("c343_fall2010");
		if (mode.equals("test")) {
			System.out.println(results.size() + " photos collected from Flickr.");
		}
		// Long p = System.currentTimeMillis();
		assignNodes();
		// Long n = System.currentTimeMillis();
		assignEdges();
		// Long e = System.currentTimeMillis();
		// System.out.println("Photos: " + (p - s) + "\nNodes: " + (n - p) + "\nEdges: " + (e - n) + "\nAll assignments: " + (e - p) + "\nTotal: " + (e - s));
	}

	public void assignNodes() {
		for (Photo p : results) {
			if (p.isNode) {
				Node n = new Node(p);
				if (nodes.containsKey(n.id)) {
					nDups++;
				}
				// Correct coordinates
				if (nodeLocations.containsKey(n.id)) {
					n.photo.latitude = nodeLocations.get(n.id)[0];
					n.photo.longitude = nodeLocations.get(n.id)[1];
				}
				if (!n.id.equals("subway")) {
					nodes.put(n.id, n);
				}
			}
			else {
				edges.add(p);
			}
		}
		
		if (mode.equals("test")) {
			System.out.println(nodes.size() + " nodes created with " + nDups + " duplicate Nodes overwritten.");
		}
	}
	
	public void assignEdges() {
		for (Photo p : edges) {
			String f = p.from;
			String t = p.toward;
			
			if (nodes.get(t) == null && t.length() >= 1) {
				t = t.substring(1, t.length());
			}

			if (nodes.get(f) != null && nodes.get(t) != null) {
				Edge e = new Edge(nodes.get(f), nodes.get(t));
				boolean inserted = false;
				for (Edge ed : nodes.get(f).edges) {
					if (ed.equals(e)) {
						ed.photos.add(p);
						dups++;
						inserted = true;
					}
				}
				if (!inserted) {
					nodes.get(f).addEdge(e);
					edgesAdded++;
				}
			}
			else {
				missingN++;
			}
		}
		
		if (mode.equals("test")) {
			System.out.println(edgesAdded + " unique edges and "	+ (edgesAdded + dups) + " total edges.");
			System.out.println(missingN + " edge photos are missing one or both nodes");
			System.out.println(nodes.size() + nDups + edgesAdded + dups + missingN + " out of " + results.size() + " photos accounted for.");
			System.out.println("I should have " + edges.size() + " total edges.");
			
			System.out.println("more Test prints?");
			if (scan.nextLine().toLowerCase().charAt(0) == 'y') {
				moreTestPrints();
			}
			
			textDijkstra();
		}
	}
	
	public void moreTestPrints() {
		
		nodeList = new ArrayList<Node>(nodes.values());
		
		for (Node n : nodeList) {
			System.out.println(n.id);
			for (Edge e : n.edges) {
				System.out.println("   " + e.toString());
			}
			scan.nextLine();
		}
	}
	
	public void textDijkstra() {
		System.out.print("From: ");
		String start = scan.nextLine();
		System.out.print("\nTo: ");
		String end = scan.nextLine();
		if (nodes.containsKey(start) && nodes.containsKey(end)) {
			dijkstra(nodes.get(start), nodes.get(end));
		}
		
		System.out.println("Again?");
		if (scan.nextLine().toLowerCase().charAt(0) == 'y') {
			textDijkstra();
		}
	}

	public void dijkstra(Node start, Node end) {	
		/*
		 * NOTE!!
		 * I am adding an edge with a 0 length between FV and I2 in order to make sure dijkstra 
		 * takes this shortcut since my edge mapping is currently only finding a severely limited amount of edges
		 */
		//nodes.get("fv").addEdge(new Edge(nodes.get("fv"), nodes.get("i2"), 0.0));
		
		// /* Create Path using Dijkstra's Algorithm

		// System.out.println("find path from " + start + " to " + end);
		if (!nodes.containsKey(start.id) || !nodes.containsKey(end.id)) {
			System.err.println("Error, one of your locations was not found.");
			System.exit(0);
		}
		
		/**
		 * FOR REFERENCE:
		 * public PriorityQueue<Node> dijkstra = new PriorityQueue<Node>(1, comparator);
		 * public HashMap<String, Node> settled = new HashMap<String, Node>();
		 * public ArrayList<Photo> pathPhotos = new ArrayList<Photo>();
		 * public HashMap<String, double[]> nodeLocations = new HashMap<String, double[]>();
		 */

		
		// make a node out of the start location and set its distance to 0, then assign to a temp Node, add it to the priority queue (dijkstra)
		Node begin = start;
		begin.distance = 0;
		Node prev = begin;
		dijkstra.clear();
		settled.clear();
		dijkstra.add(prev);
		// while there are things in the priority queue, grab the lowest distance node, as long as that isnt the target (if it is stop)
		// add it to the HashMap of settled vertices and then adjust the distances of its neighbors (relaxNeighbors)
		while (!dijkstra.isEmpty()) {
			Node u = dijkstra.poll();
			if (!u.equals(end)) {
				// System.out.println("Checking the neighbors of: " + u);
				settled.put(u.id, u);
				relaxNeighbors(u);
				//System.out.println(dijkstra.isEmpty());
			}
			else {
				// System.out.println("Done. " + u + " = " + end);
				dijkstra.clear();
			}
		}
		
		Node curr = end;
		path = new ArrayList<Node>();
		while (curr != null) {
			path.add(curr);
			curr = curr.pi;
		}
		
		Collection<Node> nodesIterable = nodes.values();
		
		Iterator<Node> itr = nodesIterable.iterator();

		// resets all the nodes
	   while(itr.hasNext()) {
	      itr.next().reset();
	  	}
	   
		if (!path.get(path.size()-1).equals(start) && !start.equals(end)){
			//System.out.println("Path Not found - Sorry.");
			JOptionPane.showMessageDialog(null, "Unfortunately we could not find a path from " + start.id + " to " + end.id + 
					"\n at this time. We are working on getting more photos\n to provide for more paths."
               , "Sorry, no path found.", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			if (mode.equals("test")) {
				for (int i = path.size() - 1 ; i >0 ; i--) {
					System.out.print(path.get(i).id + " --> ");
				}
				System.out.print(path.get(0).id + "\n");
			}
			pathPhotos.clear();
			for (int j = path.size() - 1; j>0; j--) {
				// this will cycle through all the edges for that Node, this way you can check the ends
				for (Edge e : path.get(j).edges) {	
					if (e.end.equals(path.get(j-1))) {
						pathPhotos.addAll(e.photos);
					}
				}
			}
			if (pathPhotos.size() == 0) {
				JOptionPane.showMessageDialog(null, "While we did find a path from " + start.id + "\nto " + end.id + 
						", unforunately there were no photos in out catalog to\n guide you. We apologize for the inconvenience."
	               , "Sorry, no photos for this path.", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Adjusts the distances of the neighbors of n
	 * @param n - the node to adjust the neighbor distances for
	 */
	public void relaxNeighbors(Node n) {
		// go through all the edges that originate at n to find its neighbors
		for (Edge e : n.edges) {
			// grab the neighbor node
			Node v = e.end;
			
			// ---Test prints---
			// System.out.println(v);
			// System.out.println(v + " - " + settled.containsValue(v));
			
			// provided its NOT settled, check to see if the route to it through n and [n,v] is cheaper than what it was before
			// if so, set its distance to that, than set its "pi" or previous node in path to n. finally add it to the Priority queue
			// to have its neighbors analyzed
			if (!settled.containsValue(v)) {
				if (v.distance > n.distance + e.length) {
					v.distance = n.distance + e.length;
					v.pi = n;
					// System.out.println(v + ".pi = " + n);
					dijkstra.add(v);
				}
			}
		}
	}
	
	public static void main (String[] args) {
		Pathfinder pF = new Pathfinder("test");
	}
}
