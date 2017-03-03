import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class AyyStar {

	WorldMap world;

	int numCities = 0;
	int nodesExpanded = 0;
	long timeTake = 0;

	public AyyStar(String file) {
		world = new WorldMap();
		world.processFile(file);
		numCities = world.hubs.size();
	}

	public Route findRoute() {
		// nodes already evaluated.
		List<Route> closed = new ArrayList<Route>();
		// nodes that are not evaluated yet, but are discovered
		List<Route> open = new ArrayList<Route>();

		// Initially, only the start node is known.
		List<Hub> init = new ArrayList<>();
		init.add(world.hubs.get(0));
		open.add(new Route(init));

		// how many nodes we've visited, for logging purposes
		int count = 0;
		// time started, for logging
		long start = System.currentTimeMillis();

		// continue this loop until no more nodes to consider
		while (!open.isEmpty()) {
			// the node in open having the lowest fScore
			Route current = world.getShortest(open);

			// if we're at a goal state log it and exit
			if (world.isAStarGoal(current)) {
				System.out.println("Best Route:");
				world.print(current);
				long end = System.currentTimeMillis();
				timeTake = ((end - start));
				nodesExpanded = count;
				System.out.println("Visited " + count + " nodes in " + (end - start) + " milliseconds");
				return current;
			} else {
				// counting how many nodes we consider
				count++;
			}

			// remove the current node from the open list
			open.remove(current);
			closed.add(current);

			// consider going to every neighbor
			for (Route neighbor : world.getAStarNeighbors(current)) {

				// boolean for whether or not we should add this node to the
				// path
				boolean goodNode = true;

				// we've already been here
				if (closed.contains(neighbor)) {
					goodNode = false;
				}

				// what our path cost would be if we moved to the new node
				double tempdist = neighbor.dist;

				// we've never seen this node before
				if (!open.contains(neighbor)) {
					open.add(neighbor);
				}

				// this would make our path even longer!
				if (tempdist >= current.dist) {
					goodNode = false;
				}

				// we want to expand to this node
				if (goodNode) {
					neighbor.parent = current;
					neighbor.hscore = world.heuristic(neighbor);
				}
			}
		}

		// program failed if we reached here
		System.out.println("no route found");
		long end = System.currentTimeMillis();
		System.out.println("Took : " + ((end - start) / 1000) + " seconds");
		return null;
	}

	// file paths, used for getting all the tests
	List<String> paths = new ArrayList<String>();

	// same as above, it's recursive
	List<String> getPaths(final File folder) {
		allTheTests(folder);
		return paths;
	}

	public void allTheTests(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				allTheTests(fileEntry);
			} else {
				// File
				paths.add(fileEntry.getPath());
				System.out.println(fileEntry.getPath());
			}
		}
	}

	public static void main(String[] args) {
		
		// find shortest TSP for every file in every subdirectory of the folder
		// I moved depth 10-16 to another folder so that this only takes a few seconds
		
		AyyStar aStr = new AyyStar("./randTSP/1/instance_1.txt");
		try {
			PrintWriter writer = new PrintWriter("AStar_Results.txt", "UTF-8");
			writer.println("Num_Cities - Num_Nodes - Milliseconds");
			final File folder = new File("./randTSP/");
			for (String s : aStr.getPaths(folder)) {
				AyyStar temp = new AyyStar(s);
				temp.findRoute();
				writer.printf("%d %d %d%n", temp.numCities, temp.nodesExpanded, temp.timeTake);
			}
			writer.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
