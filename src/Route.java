import java.util.List;

public class Route {
	List<Hub> path;
	double dist;
	double hscore;
	Route parent;
	
	
	public Route(List<Hub> route) {
		WorldMap wm = new WorldMap();
		path = route;
		dist = wm.dist(path);
		hscore = 0; //not sure about heuristic yet
	}
}
