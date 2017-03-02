import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Salesman {
	int mapSize;
	List<City> world = new ArrayList<City>();

	public Salesman(String filename){
		processFile(filename);
	}
	
	public double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}

	public void processFile(String filename) {
		try {
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			       System.out.println(line);
			    }
			}
			//String first = in.nextLine();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		Salesman sam = new Salesman("./src/City.java");
		System.out.println("hello world");
		System.out.println(sam.dist(0, 0, 5, 5));
	}
}
