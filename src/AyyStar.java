import java.util.List;
import java.util.TreeMap;

public class AyyStar {
	
	WorldMap world;
	
	public AyyStar(String file) {
		world = new WorldMap();
		world.processFile(file);
		world.printHubs();
	}
	
	void findRoute() {
		TreeMap<Double, List<Hub>> openSet = new TreeMap<Double, List<Hub>>(); 
		TreeMap<Double, List<Hub>> closedSet = new TreeMap<Double, List<Hub>>();
		
		openSet.put(world.dist(world.hubs), world.hubs);
		world.print(openSet);
	}
	
	
	
	public static void main (String[] args) {
		AyyStar aStr = new AyyStar("./randTSP/3/instance_1.txt");
		aStr.findRoute();
	}
}
