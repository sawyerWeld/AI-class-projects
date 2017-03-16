public class City {
	String name;

	// cartesian coordinates
	int x, y;

	// heuristic score, path-to-get-here cost, A* function cost (hval + gval)
	double hVal, gVal, fVal;
	City parent;

	public City(String name_, int x_, int y_) {
		name = name_;
		x = x_;
		y = y_;
		gVal = Double.POSITIVE_INFINITY;
	}

	public double getGScore() {
		return gVal;
	}

	public void setGScore(double d) {
		gVal = d;
	}

	public double getFScore() {
		return fVal;
	}

	public void setFScore(double d) {
		fVal = d;
	}

	public void setHeuristic(double hVal_) {
		hVal = hVal_;
	}

	public City getParent() {
		return parent;
	}

	public void setParent(City c) {
		parent = c;
	}

	public void print() {
		System.out.println(info());
	}

	public String info() {
		return name + ": (" + x + "," + y + ")" + " hVal: " + hVal + " p: " + parent.getName() + " g: " + gVal;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
