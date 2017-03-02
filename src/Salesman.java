
public class Salesman {
	public static void main (String[] args) {
		WorldMap world = new WorldMap();
		world.processFile("./randTSP/4/instance_1.txt");
		world.printCities();
	}
}
