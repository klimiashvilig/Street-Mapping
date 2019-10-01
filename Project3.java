/*
 * Author: George Klimiashvili
 * NetId: gklimias
 * Partner: Tinashe Marera
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.JFrame;

import javafx.util.Pair;

public class Project3 {
	public static void main(String[] args) {
		Graph G = new Graph(); // creates graph
		Pair<Double, ArrayList<String>> path = new Pair<Double, ArrayList<String>>(0.0, new ArrayList<String>()); // creates a pair with a length of the path and the arraylist of names of intersections
		try {
			Scanner scan; // scanner
			if (args.length > 0) // check if we are using terminal
				scan = new Scanner(new File(args[0])); // open the file
			else
				scan = new Scanner(new File("nys.txt")); // open default files
			while (scan.hasNextLine()) {
				String[] line = scan.nextLine().split("\t"); // split the input file

				if (line[0].equals("i")) { // intersection
					G.addIntersection(line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3])); // add intersection
				} else {
					G.addRoad(line[1], line[2], line[3]); // add road
				}
			}
			scan.close();// close the scanner
			ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(args)); // arguments from the command line
			if (arguments.contains("--directions")) { // requires finding direction
				String source = arguments.get(arguments.indexOf("--directions") + 1); // source intersection
				String destination = arguments.get(arguments.indexOf("--directions") + 2); // destination intersection
				path = findShortestPath(G, source, destination); // find the shortest path from source to destination
				System.out.println("Length = " + path.getKey()); // print length
				System.out.println("Path = " + path.getValue().toString()); // print path
			}
			if (arguments.contains("--show")) { // show the map
				MapGUI myMapGUI = new MapGUI(G, path.getValue()); // create Map GUI
	            JFrame frame = new JFrame("Map"); // create JFrame
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.getContentPane().add(myMapGUI);
	            frame.pack();
	            frame.setVisible(true);
			} else if (arguments.size() == 0) { // default operation for testing
				path = findShortestPath(G, "i247996", "i78312");
				System.out.println("Length = " + path.getKey());
				System.out.println("Path = " + path.getValue().toString());
				MapGUI myMapGUI = new MapGUI(G, path.getValue());
	            JFrame frame = new JFrame("Map");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.getContentPane().add(myMapGUI);
	            frame.pack();
	            frame.setVisible(true);
			}
		} catch (IOException e) { // catch an exception
			e.printStackTrace();
		}
	}

	// Implements modified version of the Dejkstra's algorithm to find shortest path between start and end
	private static Pair<Double, ArrayList<String>> findShortestPath(Graph G, String start, String end) {
		HashMap<String, String> prevNode = new HashMap<>(); // stores each node's parent
		HashMap<String, Double> costs = new HashMap<>(); // stores distance to each intersection
		for (String intersection : G.getIntersections().keySet()) { // initialize
			costs.put(intersection, Double.MAX_VALUE);
			prevNode.put(intersection, null);
		}
		costs.put(start, 0.0); // put source
		Comparator<String> costComparator = new Comparator<String>() { // create comparator

			@Override
			public int compare(String s1, String s2) {
				double cost1 = costs.get(s1);
				double cost2 = costs.get(s2);

				if (cost1 < cost2) {
					return -1;
				}
				if (cost1 == cost2) {
					return 0;
				}
				return 1;
			}

		};
		PriorityQueue<String> Q = new PriorityQueue<>(costComparator); // Priority queue
		Q.add(start); // add source
		String v;
		while (!Q.isEmpty()) { // implement Dejkstra's algorithm
			v = Q.remove();
			G.getIntersection(v).setVisited(true);
			if (v.equals(end)) // check if we found the shortest path to the destination
				return new Pair<Double, ArrayList<String>>(costs.get(v), getPath(prevNode, start, end));
			for (int i = 0; i < G.getNeighbors(v).size(); i++) {
				String n = G.getNeighbors(v).get(i);
				if (!G.getIntersection(n).isVisited()) {
					double cost = costs.get(v) + G.getEdge(v, n).getWeight();
					if (cost < costs.get(n)) {
						costs.put(n, cost);
						prevNode.put(n, v);
						Q.remove(n); // remove so we can update the value and not duplicate
						Q.add(n);
					}
				}
			}
		}
		return null;
	}

	// return the path from end to start
	private static ArrayList<String> getPath(HashMap<String, String> prevNode, String s, String e) {
		ArrayList<String> path = new ArrayList<>();
		while (e != s) {
			path.add(e);
			e = prevNode.get(e);
		}
		path.add(s);
		return path;
	}
}