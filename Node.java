/*
 * Author: George Klimiashvili
 * NetId: gklimias
 * Partner: Tinashe Marera
 */

// Implements Node (intersection) class
public class Node {
	private String NodeID; // intersection ID
    private double y, x; // x and y coordinates
    private boolean visited; // whether node has been visited
    
    // constructor
    Node(String Intersection_ID, double latitude_y, double longitude_x) {
    	NodeID = Intersection_ID;
    	y = latitude_y;
    	x = longitude_x;
    	visited = false;
    }
    
    // returns if node was visited
    public boolean isVisited() {
        return visited;
    }
    
    // sets visited status
    public void setVisited(boolean x) {
    	visited = x;
    }
    
    // returns intersection ID
    public String getIntersectionID() {
        return NodeID;
    }
    
    // returns X coordinates of intersection
    public double getX() {
    	return x;
    }
    
    // returns Y coordinates of intersection
    public double getY() {
    	return y;
    }
}