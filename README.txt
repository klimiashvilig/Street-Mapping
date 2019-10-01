-- CSC 172: Data Structures and Algorithms --
Project 3: Street Mapping
Authors: George Klimiashvili, Tinashe Marera
Email: klimiashvilig@gmail.com

Files: Project3.java, MapGUI.java, Graph.java, Node.java, Edge.java

Synopsis: Our code first reads the files and adds the intersection and roads to the object of Graph class
	  using addIntersection and addRoad functions (explained later). Next, for simplicity, our cose 
	  create ArrayList with all the arguments inputed from the terminal and checks whether user wants 
	  to find directions and/or display the map. If the user wants to display the map, our program 
	  creates a MapGUI object (discussed later). If the user wants to find a directions between two 
	  intersections, our program executes findShortestPath method, which used a slightly modified 
	  version of the Dejkstra's algorithm to find the shortest path between intersections (more 
	  details about the algorithm will follow later). Once the path is found, our code prints the 
	  distance and intersections taken in the shortest path. The workload was fully shared between my 
	  partner and I, as we first discussed the algorithm that would allow us to find the shortest path 
	  and then typed the code together. Note: we did not partition any work between us, and all of the 
	  work was done together. We believe that we deserve an extra credit because our map is beautiful 
	  and also it works pretty fast.

Node.java: implements the node or intersection class. Private variables include String NodeID, double y, x 
	   boolean visited. NodeID, stored the intersection ID, x and y store the longitude and latitude 
	   of the intersection and visited stores whether node has been visited or not.
	Constructor Node(String Intersection_ID, double latitude_y, double longitude_x) takes the 
	the intersectio ID, longitude and latitude as arguments and assigns these values to private members.
	public boolean isVisited() allows to check if the node/intersection was visited.
	public void setVisited(boolean x) allows to change the visited status of a node to x.
	public String getIntersectionID() returns intersection ID
	public double getX() returns the longitude of the intersection
	public double getY() returns the latitude of the intersection

Edge.java: implements the edge or road class. Private variables include String RoadId, Node startInter, 
	   endInter, double weight. RoadId stores the road ID, startInter stores the node at the one end 
	   of the intersection, endInter stores the node at the other end of the intersection and weight 
	   stores the length of the road.
	Constructor Edge(String roadID, Node start, Node end) takes the road ID, and two intersections 
	that this road connects. Constructor call private function distance that calculates the length 
	of the road.
	public String getRoadID() returns road ID.
	public Node getStartInter() returns intersection on one end of the road.
	public Node getEndInter() returns intersection on other end of the road.
	public double getWeight() returns the length of the road.
	private double distance(double latitude1, double longitude1, double latitude2, double longitude2) 
	takes two pairs of latitudes and longitudes and returns the distiance between them in miles.
	Sources used:
		“Haversine Formula.” Wikipedia, Wikimedia Foundation, 15 Apr. 2019,
en.wikipedia.org/wiki/Haversine_formula?fbclid=IwAR0Xy6NDcea_zosllARvf49vAUZVjkjLVGvBpzdm92rsQxVk5GsNDNFWbpU.
		Minto, Robin MintoRobin. “Calculate Distance between Two Latitude-Longitude Points? 
(Haversine Formula).” Stack Overflow, stackoverflow.com/questions/27928/calculate-distance-between-two-latitude
-longitude-points-haversine-formula?fbclid=IwAR1VsPewFgo1kCO1YBr3Cr3fQl2b-CxZCjnL8xrBX6DV72gMgXNuS-QfPr4.

Graph.java: implements the graph class. Private members include HashMap<String, ArrayList<String>> vertex, 
	    HashMap<String, Node> intersections, HashMap<Pair<String, String>, Edge> edges. Vertex stores 
	    the adjaceny list for each intersection. Intersections stores the intersection ID and the node 
	    assosicated with each intersection ID. Edges stores the Edge object associated with each pair 
	    of intersection IDs connected by a road. We used HashMaps for the hast access to the data that 
	    we needed.
	constructor Graph() initializes 3 HashMaps.
	public int n() returns number of intersections in the graph.
	public int e() returns number of roads in the graph.
	public void addIntersection(String ID, double latitude, double longitude) takes intersection ID, 
	latitude and longitude and adds it to the graph.
	public ArrayList<String> getNeighbors(String intersection) returns the ArrayList of all the 
	intersections that are directly connected to a given intersection.
	public void addRoad(String roadID, String start, String end) takes road ID, and intersection IDs 
	of two intersection connected by the road and adds it to the graph.
	public Edge getEdge(String start, String end) takes IDs of intersections connected by a road and 
	returns Edge object storing the road.
	public double getDist(String start, String end) takes IDs of two intersection and returns distance
	between them.
	public HashMap<String, Node> getIntersections() returns HashMap storing intersections.
	public Node getIntersection(String intersection) returns node storing the intersection with a given
	ID.
	public HashMap<String, ArrayList<String>> getVertex() returns HashMap storing intersections and 
	list of their neighboars.
	public HashMap<Pair<String, String>, Edge> getEdges() returns HashMap storing Edges between pairs of
	intersections.

MapGUI.java: Implements GUI for the map. Private member include HashMap<String, Node> intersections (same as 
	     in graph class), HashMap<Pair<String, String>, Edge> edges (same as in  graph class), 
	     ArrayList<String> path, double maxY, double minY, double maxX, double minX, double xScale and 
	     double yScale. Path stores the path between two intersections. minX and minY store minimum 
	     longitude and minimum latitude respecitvely. maxX and maxY store the maximum longitude and
	     maximum latitude respectively. xScale and yScale store the scalling factor such that map fits 
	     the graphics window fully.
	Constructor MapGUI(Graph G, ArrayList<String> path) takes the graph and the path and initializes 
	private variables.
	public void paintComponent(Graphics p) prints the map. It first sets the parameters by finding 
	minimum and maximum latitide and longitudes, as well as, scalling factors. Afterwards, it iterates 
	over all the edges in the graph and draws them on the map in white color. If the length of the road 
	is longer than 0.2 miles, it draws it in yellow color. Finally, it draws the path if need by 
	iterating over all the elements in the path ArrayList. It makes the roads in the path thicker and 
	in green color.
	public void setParameters() iterates over all the intersections and find the ones with minimum and 
	maximum latitudes and longitudes. Moreover, it finds a scalling factor.

Project3.java: As I mentioned in the synopsis, this is the main class which handles reading the file and 
	       the input.
	private static Pair<Double, ArrayList<String>> findShortestPath(Graph G, String start, String end)
	takes the graph object and IDs of two intersection and finds the shortest path using modified version 
	of the Dejkstra's algorithm. It returns a pair containing a length of the path and the intersection 
	taken in the shortest path. In order to keep track of the path, we used HashMap<String, String> prevNode 
	that stored the parent of each node. In order to store the cost for reaching each node, we used 
	HashMap<String, Double> costs. The only modification that we had to do to Dejkstra's algorithm was 
	checking after dequeue whether the node that we dequeued is the node that we are trying to reach. If
	it is, we get the distance from costs HashMap and get the path from the prevNode HashMap using getPath
	function.
	private static ArrayList<String> getPath(HashMap<String, String> prevNode, String s, String e) 
	takes the HashMap storing the parent of ecah node and intersection ID of each node and returns 
	the ArrayList of intersection IDs taken in the shortest path.

Complexity:
	The complexity of drawing the Map is O(|E|) as we iterate over the list of edges and draw them on 
	the map.
	The complexity of finding the shortest path is O(|E|log(|V|)) as we used an adjacency list 
	representation of the graph and it is known that with adjacency list, the complexity of Dejkstra's 
	algorithm is O(|E|log(|V|)). 