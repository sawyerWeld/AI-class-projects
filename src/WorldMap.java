import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class WorldMap {
	
	int mapSize;
	
	List<Hub> hubs = new ArrayList<Hub>();

	public void printHubs() {
		for (Hub i : hubs) {
			i.print();
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
