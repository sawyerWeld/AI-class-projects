// Wow no imports
public class Hub {
	
	/*
	 * Representation of the cities in the travelling salesman
	 * problem. Stores name, x coord, y coor.
	 * Also has a method for printing its information, which
	 * proved useful for debugging.
	 */
	int x,y;
	String name;
	
	public Hub(String name_, int x_, int y_) {
		name = name_;
		x = x_;
		y = y_;
	}
	public void print() {
		System.out.print(name + "(" + x + "," + y + ")");
	}
}
