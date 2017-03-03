import java.util.ArrayList;
import java.util.List;

public class AyyStar {

	WorldMap world;

	public AyyStar(String file) {
		world = new WorldMap();
		world.processFile(file);
		world.printHubs();
	}

	 public Route findRoute() {
		// The set of nodes already evaluated.
		List<Route> closed = new ArrayList<Route>();
		// The set of currently discovered nodes that are not evaluated yet.
		List<Route> open = new ArrayList<Route>();
		// Initially, only the start node is known.
		
		List<Hub> init = new ArrayList<>();
		init.add(world.hubs.get(0));
		open.add(new Route(init));
		world.print(open);

		while (!open.isEmpty()) {
			//System.out.println("in loop\n\n");
			// the node in open having the lowest fScore
			Route current = world.getShortest(open);

			if (world.isAStarGoal(current)) {
				System.out.println("yay");
				world.print(current);
				return current;
			} else {
				world.print(current);
				System.out.println( " is not a goal state");
			}
			
			open.remove(current);
			closed.add(current);
			
			/*
			System.out.print("Currrent: ");
			world.print(current);
			System.out.println("open:");
			world.print(open);
			System.out.println("closed:");
			world.print(closed);
			System.out.println("	/:closed");*/
			for (Route neighbor : world.getAStarNeighbors(current)) {
				boolean goodNode = true;
				if (closed.contains(neighbor)) {
					goodNode = false;
					//System.out.println("contained already ");
				}
				
				double tempdist = neighbor.dist;
				
				if (!open.contains(neighbor)) {
					//System.out.println("adding neighbor "  + neighbor.dist);
					open.add(neighbor);
				}
				if (tempdist >= current.dist) {
					//System.out.println("toofar");
					goodNode = false;
				}
				if (goodNode){
					//System.out.println("good node");
					// this node is good!
					neighbor.parent = current;
					neighbor.hscore = world.heuristic(neighbor);
				}
			}
		}
		System.out.println("no route found");
		return null;
	}

	public static void main(String[] args) {
		AyyStar aStr = new AyyStar("./randTSP/5/instance_10.txt");
		aStr.findRoute();
	}
}
