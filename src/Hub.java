
public class Hub {
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
