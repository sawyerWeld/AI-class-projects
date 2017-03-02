public class City {
	String name;
	
	//cartesian coordinates
	int x, y;
	
	//heuristic score, path-to-get-here cost, A* function cost (hval + gval)
	double hVal,gVal,fVal;
	City parent;
	
	public City(String name_, int x_, int y_) {
		name = name_;
		x = x_;
		y = y_;
	}
	
	/**
	 * sets hVal and fVal to heuristic.
	 */
	public void setHeuristic(double hVal_) {
		hVal = hVal_;
		fVal = hVal_;
	}
	
	public void print() {
		System.out.println(info());
	}
	
	public String info(){
		return name + ": (" + x + "," + y + ")" + " hVal: " + hVal;
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
