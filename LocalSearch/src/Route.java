import java.util.List;

public class Route {
	/* 
	 * Routes are an easier way of storing a list of nodes
	 * Also stores a parent, distance, and hscore so they
	 * don't have to be calculated super often.
	 * Alternative was a mapping of these to scores etc.
	 * That was clunky I much prefer this.
	 */
	
	List<Hub> path;
	double dist;
	double hscore;
	Route parent;
	
	
	public Route(List<Hub> route) {
		WorldMap wm = new WorldMap();
		path = route;
		dist = wm.dist(path);
		hscore = 0;
	}
}
