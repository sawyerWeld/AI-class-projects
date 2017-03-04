import java.util.ArrayList;
import java.util.List;

public class Annealing {

	WorldMap world;
	
	int numCities = 0;

	public Annealing(String file) {
		world = new WorldMap();
		world.processFile(file);
		numCities = world.hubs.size();
	}
	
	public void demonStrateSwaps(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == s.length() - 1) {
				String temp = s.charAt(s.length()-1) + s.substring(1,s.length()-1) + s.charAt(0);
				System.out.println("  " + temp);
			} else {
				String temp = s.substring(0, i) + s.substring(i+1,i+2) + s.substring(i,i+1) + s.substring(i+2);
				System.out.println(temp);
			}
		}
	}

	void findRoute() {
		// The set of nodes already evaluated.
		//List<Route> closed = new ArrayList<Route>();
		// The set of currently discovered nodes that are not evaluated yet.
		List<Route> open = new ArrayList<Route>();
		// Initially, only the start node is known.
		open.add(new Route(world.hubs));
		//world.print(open);

		while (!open.isEmpty()) {
			//the node in open having the lowest fScore 
			//Route current = world.getShortest(open);
		}
	}

	public static void main(String[] args) {
		Annealing ann = new Annealing("./randTSP/3/instance_1.txt");
		//ann.findRoute();
		ann.demonStrateSwaps("ABCDEDFG");
	}
}
