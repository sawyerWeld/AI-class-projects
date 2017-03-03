import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AyyStar {

	WorldMap world;

	public AyyStar(String file) {
		world = new WorldMap();
		world.processFile(file);
		// world.printHubs();
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

		// how many nodes we've visited
		int count = 0;
		long start = System.currentTimeMillis();

		while (!open.isEmpty()) {
			// System.out.println("in loop\n\n");
			// the node in open having the lowest fScore
			Route current = world.getShortest(open);

			if (world.isAStarGoal(current)) {
				System.out.println("Best Route:");
				world.print(current);
				long end = System.currentTimeMillis();
				System.out.println("Visited " + count + " nodes in " + ((end - start) / 1000) + " seconds");
				return current;
			} else {
				count++;
				// System.out.println( " is not a goal state");
			}

			open.remove(current);
			closed.add(current);

			/*
			 * System.out.print("Currrent: "); world.print(current);
			 * System.out.println("open:"); world.print(open);
			 * System.out.println("closed:"); world.print(closed);
			 * System.out.println("	/:closed");
			 */
			for (Route neighbor : world.getAStarNeighbors(current)) {
				boolean goodNode = true;
				if (closed.contains(neighbor)) {
					goodNode = false;
					// System.out.println("contained already ");
				}

				double tempdist = neighbor.dist;

				if (!open.contains(neighbor)) {
					// System.out.println("adding neighbor " + neighbor.dist);
					open.add(neighbor);
				}
				if (tempdist >= current.dist) {
					// System.out.println("toofar");
					goodNode = false;
				}
				if (goodNode) {
					// System.out.println("good node");
					// this node is good!
					neighbor.parent = current;
					neighbor.hscore = world.heuristic(neighbor);
				}
			}
		}
		System.out.println("no route found");
		long end = System.currentTimeMillis();
		System.out.println("Took : " + ((end - start) / 1000) + " seconds");
		return null;
	}

	List<String> paths = new ArrayList<String>();
	
	List<String> getPaths(final File folder) {
		allTheTests(folder);
		return paths;
	}
	
	
	
	public void allTheTests(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				allTheTests(fileEntry);
			} else {
				//File 
				paths.add(fileEntry.getPath());
				System.out.println(fileEntry.getPath());
			}
		}
	}

	public static void main(String[] args) {
		AyyStar aStr = new AyyStar("./randTSP/1/instance_1.txt");
		try{
		    PrintWriter writer = new PrintWriter("AStar_Results.txt", "UTF-8");
		    final File folder = new File("./randTSP/");
			for (String s : aStr.getPaths(folder)) {
				AyyStar temp = new AyyStar(s);
				temp.findRoute();
			}
		    writer.println("The first line");
		    writer.println("The second line");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		//AyyStar aStr = new AyyStar("./randTSP/7/instance_4.txt");
		//aStr.findRoute();
	}
}
