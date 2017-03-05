import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Annealing {

	WorldMap world;
	Random rand = new Random();

	int numCities = 0;

	public Annealing(String file) {
		world = new WorldMap();
		world.processFile(file);
		numCities = world.hubs.size();
	}

	public void demonStrateSwaps(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == s.length() - 1) {
				String temp = s.charAt(s.length() - 1) + s.substring(1, s.length() - 1) + s.charAt(0);
				System.out.println(temp);
			} else {
				String temp = s.substring(0, i) + s.substring(i + 1, i + 2) + s.substring(i, i + 1)
						+ s.substring(i + 2);
				System.out.println(temp);
			}
		}
	}

	public Route randomNeighbor(Route r) {
		List<Route> neighbors = new ArrayList<>();
		List<Hub> current = r.path;
		for (int i = 0; i < current.size(); i++) {
			List<Hub> temp = new ArrayList<>(current);
			if (i == current.size() - 1) {
				Collections.swap(temp, i, 0);
			} else {
				Collections.swap(temp, i, i + 1);
			}
			Route tempRoute = new Route(temp);
			neighbors.add(new Route(temp));
			// world.print(tempRoute);
		}

		int randIndex = rand.nextInt(neighbors.size());
		Route randRoute = neighbors.get(randIndex);
		// System.out.print("~~");
		// world.print(randRoute);
		return randRoute;
	}

	double calculateTemp(int in) {
		return in;
	}

	void findRoute(int maxIterations) {
		// Starting state. Alphabetical
		List<Hub> allHubs = new ArrayList(world.hubs);
		Collections.shuffle(allHubs);
		Route current = new Route(allHubs);
		Route best = current;

		int iters = 0;
		while (true) {
			Route neighbor = randomNeighbor(current);

			double tempCurr = calculateTemp(iters / maxIterations);
			if (neighbor.dist <= current.dist) {
				current = neighbor;
				if (neighbor.dist <= best.dist) {
					best = neighbor;
					System.out.print("~");
					world.print(best);
					System.out.println(tempCurr);

				}
			} else {
				double checkTerm = Math.exp((neighbor.dist - current.dist) / tempCurr);
				System.out.println(checkTerm);
				if (checkTerm > Math.random()) {
					//System.out.println("accepting worse path: " + checkTerm);
					current = neighbor;
				}
			}
			
			
			if (!(iters == maxIterations))
				iters++;
		}

	}

	public static void main(String[] args) {
		Annealing ann = new Annealing("./problem36");
		ann.findRoute(1000);
		// ann.demonStrateSwaps("ABCDEDFG");
		// ann.annealingNeighbors(r);
	}
}
