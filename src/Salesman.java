import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.Map.Entry;

public class Salesman {

	int mapSize;

	List<City> allNodes = new ArrayList<City>();
	List<City> openSet = new ArrayList<City>();
	List<City> closedSet = new ArrayList<City>();

	City start;
	// City goal;

	String file;

	public Salesman(String filename) {
		file = filename;
		processFile();
		setHeuristics();
		setParents();
	}

	public double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	/**
	 * Returns euclidean distance between a and b
	 */
	public double dist(City a, City b) {
		return dist(a.getX(), a.getY(), b.getX(), b.getY());
	}

	/**
	 * Print important information.
	 */
	public void printStatus() {
		System.out.println("~~~~~~~~~~~~~");
		System.out.println("Open: ");
		for (City c : openSet) {
			c.print();
		}
		System.out.println("Closed Set: ");
		for (City c : closedSet) {
			c.print();
		}
		System.out.println("~~~~~~~~~~~~~");
	}

	/**
	 * Sets hVal for all nodes. hVal is distance to goal.
	 */
	void setHeuristics() {
		for (City c : openSet) {
			c.setHeuristic(dist(c, start));
		}
		for (City c : closedSet) {
			c.setHeuristic(dist(c, start));
		}
	}

	/**
	 * Sets parents of all nodes to start
	 */
	void setParents() {
		for (City c : openSet) {
			c.setParent(start);
		}
		start.setParent(start);
	}

	void closeNode(City c) {
		if (openSet.contains(c)) {
			openSet.remove(c);
			closedSet.add(c);
		} else {
			System.err.println("error on closeNode " + c.getName());
			printStatus();
		}
	}

	void openNode(City c) {
		if (closedSet.contains(c)) {
			closedSet.remove(c);
			openSet.add(c);
		} else {
			System.err.println("error on openNode " + c.getName());
		}
	}

	TreeMap<Double, City> scoreOpenSet() {
		TreeMap<Double, City> scoreMap = new TreeMap<Double, City>();
		for (City c : openSet) {
			scoreMap.put(c.getFScore(), c);
		}
		return scoreMap;
	}

	TreeMap<Double, City> expandNode(City c) {
		closedSet.add(c);
		TreeMap<Double, City> distMap = new TreeMap<Double, City>(); // liskov
																		// sub
																		// principal?
		System.out.print("Expanding node: ");
		c.print();
		for (City i : openSet) { // and isnt in teh explored list
			if (!i.equals(c) && !closedSet.contains(i)) {
				double dist = dist(c, i);
				distMap.put(dist, i);
				System.out.println(i.info() + " -- " + dist);
			}
		}
		return distMap;
	}

	/**
	 * Reads from file. Sets initial openSet, closedSet
	 */
	public void processFile() {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			mapSize = Integer.parseInt(br.readLine());
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				City c = new City(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
				openSet.add(c);
				allNodes.add(c);
			}
			if (mapSize > 2) { // 0 or 1 value means A* is dumb
				start = openSet.get(0);
			} else if (mapSize == 2) {
				start = openSet.get(0);
				openSet.get(1).setParent(start);
				start.setParent(openSet.get(1));
				optimalPath(start);
			} else if (mapSize == 1) {
				start = openSet.get(0);
				System.out.println("Path: " + start.getName());
			} else {
				System.err.println("No Nodes detected");
			}
			start.setGScore(0);

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Runner Method
	 */
	public void AStar() {
		while (!openSet.isEmpty()) {
			// current node is node in open set with lowest score
			City node_current = openSet.get(0);
			// close the current node
			closeNode(node_current);
			//if we are on the last node
			if (openSet.isEmpty()) {
				start.setParent(node_current);
				optimalPath(start);
				return;
			}
			// expand nodes
			for (City node_successor : allNodes) {
				System.out.println("Considering: " + node_successor.getName());
				// cost to add successor to path
				double successor_current_cost = node_current.getGScore() + dist(node_current, node_successor);

				// Whether or not we should branch the path to the successor,
				// default true
				Boolean pathNode = true;

				// If the successor is already closed
				if (closedSet.contains(node_successor)) {
					System.out.println("node was closed " + node_successor.getName());
					pathNode = false;
				} else if (successor_current_cost >= node_successor.getGScore()) {
					System.out.println("node was expensive: " + node_successor.getName());
					System.out.println(
							"Expense: " + successor_current_cost + ", Successor G: " + node_successor.getGScore());
					pathNode = false;
				}
				if (pathNode) {
					System.out.println("good node");
					node_successor.setParent(node_current);
					node_successor.setGScore(successor_current_cost);
					node_successor.setFScore(node_successor.getGScore() + dist(node_successor, start));
					printStatus();
				}
				// printStatus();

			}
		}
		//optimalPath(start);
		// call optimalPath
	}

	public void optimalPath(City c) {
		System.out.println("Path:");
		City current = c;
		for (int i = 0; i < mapSize; i++) {
			current.print();
			current = current.getParent();
		}
		System.out.println("~~~");
	}

	public static void main(String[] args) {
		Salesman sam = new Salesman("./randTSP/4/instance_1.txt");		
		sam.AStar();
		// sam.printStatus();
	}
}
