import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.sun.javafx.collections.MappingChange.Map;

public class Salesman {
	int mapSize;
	List<City> world = new ArrayList<City>();
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
		for (City c : world) {
			c.print();
		}
	}

	public List<City> getCities() {
		return world;
	}
	
	public void expandFully(){
		int depth = 0;
		//while (depth < mapSize) {
			
		//}
		//for (City c : world) {
			expandNode(world.get(0));
		//}
	}

	LinkedHashMap<Double, City> expandNode(City c) {
		LinkedHashMap<Double, City > distMap = new LinkedHashMap<Double,City>(); // not sure why i cant declare it as just a map
		System.out.print("Expanding node: ");
		c.print();
		for (City i : world) { // and isnt in teh explored list
			if (!i.equals(c)) {
				
				double dist = dist(c,i);
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
					world.add(c);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		Salesman sam = new Salesman("./problem/randTSP/4/instance_2.txt");
		sam.processFile();
		//sam.printWorld();
		//sam.expandNode(sam.getCities().get(0));
		sam.expandFully();
	}
}
