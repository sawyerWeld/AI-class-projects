public class City {
	String name;
	int x, y;

	public City(String name_, int x_, int y_) {
		name = name_;
		x = x_;
		y = y_;
	}

	public void print() {
		System.out.println(name + ": (" + x + "," + y + ")");
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
