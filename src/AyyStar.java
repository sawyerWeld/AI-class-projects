import java.util.ArrayList;
import java.util.List;

public class AyyStar {

	WorldMap world;

	public AyyStar(String file) {
		world = new WorldMap();
		world.processFile(file);
		world.printHubs();
	}

	void findRoute() {
		// The set of nodes already evaluated.
		List<Route> closed = new ArrayList<Route>();
		// The set of currently discovered nodes that are not evaluated yet.
		List<Route> open = new ArrayList<Route>();
		// Initially, only the start node is known.
		open.add(new Route(world.hubs));
		world.print(open);

		while (!open.isEmpty()) {
			Route current = world.getShortest(open);
		}
	}

	public static void main(String[] args) {
		AyyStar aStr = new AyyStar("./randTSP/3/instance_1.txt");
		aStr.findRoute();
	}
}
