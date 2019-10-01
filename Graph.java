/*
 * Author: George Klimiashvili
 * NetId: gklimias
 * Partner: Tinashe Marera
 */
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Pair;

public class Graph {
	private HashMap<String, ArrayList<String>> vertex;
	private HashMap<String, Node> intersections;
	private HashMap<Pair<String, String>, Edge> edges;
	
	
	// constructor
	Graph() {
		vertex = new HashMap<String, ArrayList<String>>();
		edges = new HashMap<>();
		intersections = new HashMap<>();
	}
	
	// return number of elements
	public int n() {
		return vertex.size();
	}
	
	// returns number of edges
	public int e() {
		return edges.size();
	}
	
	// adds intersections
	public void addIntersection(String ID, double latitude, double longitude) {
		intersections.put(ID, new Node(ID, latitude, longitude));
		vertex.put(ID, new ArrayList<>());
	}
	
	// returns all the neighbors of the intersection
	public ArrayList<String> getNeighbors(String intersection) {
		return vertex.get(intersection);
	}
	
	// sets a road
	public void addRoad(String roadID, String start, String end) {
		vertex.get(start).add(end);
		vertex.get(end).add(start);
		edges.put(new Pair<String, String>(start, end), new Edge(roadID, intersections.get(start), intersections.get(end)));
	}
	
	// return the road between two intersections
	public Edge getEdge(String start, String end) {
		if (edges.containsKey(new Pair<String, String>(start, end)))
			return edges.get(new Pair<String, String>(start, end));
		else
			return edges.get(new Pair<String, String>(end, start));
	}
	
	// returns the distance between two intersections
	public double getDist(String start, String end) {
		return edges.get(new Pair<String, String>(start, end)).getWeight();
	}
	
	// returns HashMap of intersections
	public HashMap<String, Node> getIntersections() {
		return intersections;
	}
	
	// return node associated with the intersection
	public Node getIntersection(String intersection) {
		return intersections.get(intersection);
	}
	
	// returns hashmap vertex
	public HashMap<String, ArrayList<String>> getVertex() {
		return vertex;
	}
	
	// returns HashMap edges
	public HashMap<Pair<String, String>, Edge> getEdges() {
		return edges;
	}
}
