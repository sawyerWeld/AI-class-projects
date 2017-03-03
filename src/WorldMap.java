import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WorldMap {

	/*
	 * The purpose of WorldMap is to seperate out code I'm using for A* that I
	 * think may be useful for Simulated Annealing
	 *
	 */
	int mapSize;

	// all the hubs (hubs are my representation of cities)
	List<Hub> hubs = new ArrayList<Hub>();

	// euclidean distance
	public double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	// distance between two hubs
	public double dist(Hub a, Hub b) {
		return dist(a.x, a.y, b.x, b.y);
	}

	// Distance between all the nodes in a list
	// including wrapping back to the first
	public double dist(List<Hub> list) {
		double sum = 0;
		for (Hub b : list) {
			if (list.indexOf(b) != list.size() - 1) {
				double dist = dist(b, list.get(list.indexOf(b) + 1));
				sum += dist;
			} else {
				double dist = dist(b, list.get(0));
				sum += dist;
			}
		}
		return sum;
	}

	// My heuristic is the size of the path
	public double heuristic(Route r) {
		return hubs.size() - r.path.size();
	}

	// Useful for displaying the final path
	public void print(Route r) {
		String distf = String.format("%.3f", r.dist);
		System.out.print("{" + distf + "} { ");
		for (Hub i : r.path) {
			i.print();
			System.out.print(" ");
		}
		System.out.println("}");
	}

	// Does Route r satisfy the goal state of A*???
	public boolean isAStarGoal(Route r) {
		List<Hub> list = new ArrayList<Hub>(r.path);
		if (list.size() != hubs.size()) {
			return false;
		}
		for (Hub i : hubs) {
			if (!list.contains(i)) {
				return false;
			}
		}
		return true;
	}

	// Gets all the neighbors for our A* search
	// Works by adding one node to input route
	// Example: our Route is A, B, C
	// Returns (A,B,C,D),(A,B,C,E),(A,B,C,F),etc.
	public List<Route> getAStarNeighbors(Route r) {

		List<Route> neighbors = new ArrayList<Route>();
		List<Hub> list = r.path;

		// NotHit is all the hubs we haven't visited (hit)
		List<Hub> notHit = new ArrayList<Hub>(hubs);
		notHit.removeAll(list);

		// add each character not hit to a new route
		for (Hub i : notHit) {
			List<Hub> tempList = new ArrayList<Hub>(list);
			tempList.add(i);
			Route tempRoute = new Route(tempList);
			neighbors.add(tempRoute);
		}
		return neighbors;
	}

	public List<Route> getAnnealingNeighbors(Route r) {
		List<Route> mutations = new ArrayList<Route>();
		// yeah
		return mutations;
	}

	// Returns the node in the list with the shortest path
	// This is probably a terrible way of sorting
	public Route getShortest(List<Route> list) {
		TreeMap<Double, Route> map = new TreeMap<>();
		for (Route i : list) {
			map.put(i.dist + i.hscore, i);
		}

		// TreeMap sorts itself
		return map.firstEntry().getValue();
	}

	// Reads in a file
	public void processFile(String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			
			// sets number of cities
			mapSize = Integer.parseInt(br.readLine());
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				Hub c = new Hub(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
				hubs.add(c);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
