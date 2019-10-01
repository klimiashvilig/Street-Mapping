/*
 * Author: George Klimiashvili
 * NetId: gklimias
 * Partner: Tinashe Marera
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.geom.Line2D;

import javax.swing.*;

import javafx.util.Pair;

@SuppressWarnings("serial")

// Implements GUI for the map
public class MapGUI extends JPanel {
	private HashMap<String, Node> intersections; // intersection
	private HashMap<Pair<String, String>, Edge> edges; // edges
	private ArrayList<String> path; // path

	private double maxY; // maximum coordinate of Y
	private double minY; // minimum coordinate of Y
	private double maxX; // maximum coordinate of X
	private double minX; // minimum coordinate of X
	private double xScale; // scaling factor for X
	private double yScale; // scaling factor for Y

	// Constructor
	MapGUI(Graph G, ArrayList<String> path) {
		this.intersections = G.getIntersections();
		this.edges = G.getEdges();
		this.path = path;
		setPreferredSize(new Dimension(1600, 1200)); // set the size of the window
		this.setBackground(Color.BLACK); // background color

	}

	// Adds roads and intersections to the map
	public void paintComponent(Graphics p) {
		setParameters(); // sets the parameters (minX, minY,...)
		Graphics2D p2 = (Graphics2D) p; // use Graphics2D
		super.paintComponent(p2);
		p2.setStroke(new BasicStroke(1)); // set the width of the lines
		double x1, y1, x2, y2;
		for (Edge edge : edges.values()) {
			if (edge.getWeight() >= 0.2) {
				p2.setColor(Color.YELLOW);
				p2.setStroke(new BasicStroke((float)1.5));
			} else {
				p2.setColor(Color.WHITE);
				p2.setStroke(new BasicStroke((float)1));
			}
			x1 = edge.getStartInter().getX(); // X coordinates of first intersection
			y1 = edge.getStartInter().getY(); // Y coordinates of first intersection;
			x2 = edge.getEndInter().getX(); // X coordinates of second intersection
			y2 = edge.getEndInter().getY(); // Y coordinates of second intersection

			p2.draw(new Line2D.Double((x1 - minX) * xScale, getHeight() - ((y1 - minY) * yScale),
					(x2 - minX) * xScale, getHeight() - ((y2 - minY) * yScale))); // draw the line
		}

		if (!path.isEmpty() && path.size() > 1) { // if we have a path
			p2.setColor(Color.GREEN); // change color
			p2.setStroke(new BasicStroke(5)); // make path thicker
			for (int i = 1; i < path.size(); i++) { // got through all roads
				Node prev = intersections.get(path.get(i - 1)); // previous intersection
				x1 = prev.getX(); // x coordinates
				y1 = prev.getY(); // y coordinates
				Node curr = intersections.get(path.get(i)); // current intersection
				x2 = curr.getX(); // x coordinates
				y2 = curr.getY(); // y coordinates
				p2.draw(new Line2D.Double((x1 - minX) * xScale, getHeight() - ((y1 - minY) * yScale),
						(x2 - minX) * xScale, getHeight() - ((y2 - minY) * yScale))); // draw a path
			}

		}

	}

	// Sets the parameters for the board
	public void setParameters() {
		Iterator<String> it = intersections.keySet().iterator(); // iterator
		Node n; // node
		if (it.hasNext()) { // if there is an intersection
			n = intersections.get(it.next()); // get intersection
			minX = maxX = n.getX(); // set min and max X
			minY = maxY = n.getY(); // set min and max Y
		}
		while (it.hasNext()) { // iterate of all the intersection
			n = intersections.get(it.next()); // get intersection
			if (n.getX() > maxX)
				maxX = n.getX(); // new maxX
			if (n.getX() < minX)
				minX = n.getX(); // new minX
			if (n.getY() > maxY)
				maxY = n.getY(); // new maxY
			if (n.getY() < minY)
				minY = n.getY(); // new minY
		}
		xScale = this.getWidth() / (maxX - minX); // set x scale
		yScale = this.getHeight() / (maxY - minY); // set y scale
	}

}
