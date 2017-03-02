public class City {
	String name;
	int x, y;
	double hVal,gVal,fVal;
	City parent;
	
	public City(String name_, int x_, int y_) {
		name = name_;
		x = x_;
		y = y_;
	}
	
	public void setHeuristic(double hVal_) {
		hVal = hVal_;
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
