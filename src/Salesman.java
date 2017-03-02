import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.Map.Entry;

public class Salesman {
	int mapSize;

	List<City> openSet = new ArrayList<City>();
	List<City> closedSet = new ArrayList<City>();

	

	String file;

	public Salesman(String filename) {
		file = filename;
	}

	public double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	public double dist(City a, City b) {
		return dist(a.getX(), a.getY(), b.getX(), b.getY());
	}

	public void printWorld() {
		System.out.println("World: ");
		for (City c : openSet) {
			c.print();
		}
	}

	
	public List<City> getClosedSet() {
		return closedSet;
	}

	public List<City> getOpenSet() {
		return openSet;
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

	public void processFile() {
		try {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				mapSize = Integer.parseInt(br.readLine());
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(" ");
					City c = new City(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
					openSet.add(c);
				}
				City start = openSet.get(0);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		Salesman sam = new Salesman("./problem/randTSP/4/instance_5.txt");
		sam.processFile();
		sam.printWorld();
		sam.expandNode(sam.getOpenSet().get(0));
		sam.getClosedSet().get(0).print();
		// sam.expandFully();
	}
}
