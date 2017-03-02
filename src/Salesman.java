import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

	public void printWorld() {
		for (City c : world) {
			c.print();
		}
	}
	
	public List<City> getCities() {
		return world;
	}
	
	List<City> expandNode(City c){
		List<City> cities = new ArrayList<City>();
		for (City i : world) {
			if (!i.equals(c)) {
				cities.add(i);
			}
		}
		System.out.print("Expanding node: ");
		c.print();
		for (City j : cities) {
			j.print();
		}
		return cities;
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
		Salesman sam = new Salesman("./problem/randTSP/8/instance_8.txt");
		sam.processFile();
		sam.printWorld();
		sam.expandNode(sam.getCities().get(1));
	}
}
