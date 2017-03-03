import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class WorldMap {

	int mapSize;

	List<Hub> hubs = new ArrayList<Hub>();

	public double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	/**
	 * Returns euclidean distance between a and b
	 */
	public double dist(Hub a, Hub b) {
		return dist(a.x, a.y, b.x, b.y);
	}

	public double dist(List<Hub> list) {
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sum += dist(list.get(list.size() - 1), list.get(0));
			} else {
				sum += dist(list.get(i), list.get(i + 1));
			}
		}
		return sum;
	}

	public void printHubs() {
		for (Hub i : hubs) {
			i.print();
			System.out.println("");
		}
	}

	public void print(TreeMap<Double, Route> list) {

		for (Entry<Double, Route> i : list.entrySet()) {
			List<Hub> inner = i.getValue().path;
			Double val = i.getKey();
			System.out.print("{" + val + "} = { ");
			for (Hub j : inner) {
				j.print();
				System.out.print(" ");
			}
			System.out.println("}");
		}
	}

	public void print(List<Route> list) {
		for (Route i : list) {
			System.out.print("{" + i.dist + "} { ");
			for (Hub j : i.path) {
				j.print();
				System.out.print(" ");
			}
			System.out.println("}");
		}
	}

	public boolean isAStarGoal(Route r) {
		List<Hub> list = r.path;
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
	
	public List<Route> getAStarNeighbors(Route r) {
		List<Route> neighbors = new ArrayList<Route>();
		List<Hub> list = r.path;
		for (Hub i : hubs) {
			if (!r.path.contains(i)) {
				List<Hub> tempList = list;
				tempList.add(i);
				neighbors.add(new Route(tempList));
			}
		}
		return neighbors;
	}
	
	public List<Route> getAnnealingNeighbors(Route r) {
		List<Route> mutations = new ArrayList<Route>();
		List<Hub> list = r.path;
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				//swap
				List<Hub> tempList  = list;
				Hub tempHub = tempList.get(i);
				tempList.set(i, tempList.get(j));
				tempList.set(j, tempHub);
				mutations.add(new Route(tempList));
			}
		}
		return mutations;
	}

	public Route getShortest(List<Route> list) {
		TreeMap<Double, Route> map = new TreeMap<>();
		for (Route i : list) {
			map.put(i.dist + i.hscore, i);
		}
		return map.firstEntry().getValue();
	}
	
	/**
	 * Reads from file. Sets initial openSet, closedSet
	 */
	public void processFile(String filename) {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
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
