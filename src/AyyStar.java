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
			// the node in open having the lowest fScore
			Route current = world.getShortest(open);

			open.remove(current);
			closed.add(current);
			
			for (Route neighbor : world.getAStarNeighbors(current)) {
				boolean goodNode = true;
				if (closed.contains(neighbor)) {
					goodNode = false;
				}
				
				double tempdist = neighbor.dist;
				
				if (!open.contains(neighbor)) {
					open.add(neighbor);
				}
				if (tempdist >= current.dist) {
					goodNode = false;
				} 
				if (goodNode){
					// this node is good!
					neighbor.parent = current;
					//neighbor.hscore = world.heuristic(neighbor);
				}
			}
		}
	}

	public static void main(String[] args) {
		AyyStar aStr = new AyyStar("./randTSP/3/instance_1.txt");
		aStr.findRoute();
	}
}
