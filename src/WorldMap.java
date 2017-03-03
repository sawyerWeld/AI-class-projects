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
	
	public double dist(List<Hub> list)
	{
		double sum = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size()-1) {
				sum += dist(list.get(list.size()-1),list.get(0));
			} else {
				sum += dist(list.get(i), list.get(i+1));
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
	
	public void print(TreeMap<Double, List<Hub>> list) {
		for (Entry<Double, List<Hub>> i : list.entrySet()) {
			List<Hub> inner = i.getValue();
			Double val = i.getKey();
			System.out.print("{" + val + "} = { ");
			for (Hub j : inner) {
				j.print();
				System.out.print(" ");
			}
			System.out.println("}");
		}
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
