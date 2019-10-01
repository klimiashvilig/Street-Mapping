/*
 * Author: George Klimiashvili
 * NetId: gklimias
 * Partner: Tinashe Marera
 */

// Implements Edge (Road) class
public class Edge {
	private String RoadID; // road ID
    private Node startInter, endInter; // two intersections that are connected by this road
    private double weight; //distance

    // Constructor
    Edge(String roadID, Node start, Node end) {
        this.RoadID = roadID;
        this.startInter = start;
        this.endInter = end;
        this.weight = distance(start.getY(), start.getX(), end.getY(), end.getX());
    }
    
    // returns road ID
    public String getRoadID() {
        return RoadID;
    }

    // returns first intersection that road connects
    public Node getStartInter() {
        return startInter;
    }

    // returns second intersection that road connects
    public Node getEndInter() {
        return endInter;
    }

    // returns the length of the road
    public double getWeight() {
        return weight;
    }
    
    // calculates the length of the road using latitudes and longitudes in miles
    private double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
    	double x = Math.toRadians((latitude2 - latitude1))/2;
    	double y = Math.toRadians((longitude2 - longitude1))/2;
    	double distance = Math.sin(x) * Math.sin(x)+ Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.sin(y) * Math.sin(y);
        double distancee = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1 - distance))*3963;
    	return distancee;
    }
}