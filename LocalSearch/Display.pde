import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Random;

List<Node> myList;
Random rand = new Random();
PFont myFont;
long startTime = System.currentTimeMillis();
Route best, current;
Double temp = 1d;
int iters = 0;
boolean hasSelectedFile = false;
boolean showNeighbor = false;

void setup() {
  // seting up the image and processing the file
  size(500, 500);
  background(255);
  fill(0);
  myFont = createFont("Georgia", 16);
  textAlign(CENTER, CENTER);
  textFont(myFont);

  myList = new ArrayList<Node>();
  selectInput("Pick a file", "processFile");
  print("hi");
}

void draw() {
  // annealing stuff
  if (hasSelectedFile) {
    //print(myList.size());
    Route neighbor;
    if (iters % 2 == 0) {
      neighbor = randomNeighbor2(current);
    } else {
      neighbor = randomNeighbor(current);
    }
    if (neighbor.dist <= current.dist) {
      current = neighbor;
      if (neighbor.dist < best.dist) { // eq
        best = neighbor;
        System.out.println(best.dist + " ~ " + temp + " ~ " + iters + " ~ " + (System.currentTimeMillis()-startTime)/1000);
        temp *= .99; //this is changed as an expirement
      }
    } else {
      double checkTerm = Math.exp((current.dist - neighbor.dist) / temp);

      if (checkTerm > Math.random()) {
        current = neighbor;
      }

      iters++;
    }

    //drawing
    background(255);
    // display points
    for (int i = 0; i < myList.size(); i ++) {
      Node n = myList.get(i);
      int xpos = n.x * 5;
      int ypos = height - n.y * 5;

      ellipse(xpos, ypos, 5, 5);
      text(n.name, xpos, ypos-15);
    }
    // distance text
    String distf = String.format("%.3f", best.dist);
    text(distf, 50, 15);
    String secs = Long.toString((System.currentTimeMillis()-startTime)/1000);
    text(secs, 50, height-15);


    List<Node> list = current.path;
    if (showNeighbor) {
       strokeWeight(1);
      //stroke(#D626E0);
      stroke(150);
      list = neighbor.path;
      for (int i = 0; i < list.size(); i++) {
        if (i != list.size() - 1) {
          Node a = list.get(i);
          Node b = list.get(i+1);
          //line(drawX(a), drawY(a), drawX(b), drawY(b));
        } else {
          Node a = list.get(i);
          Node b = list.get(0);
          //line(drawX(a), drawY(a), drawX(b), drawY(b));
        }
      }
      //stroke(0);
    }

    // display line
    //List<Node> list = current.path;
    stroke(0);
    strokeWeight(2);
    for (int i = 0; i < list.size(); i++) {
      if (i != list.size() - 1) {
        Node a = list.get(i);
        Node b = list.get(i+1);
        line(drawX(a), drawY(a), drawX(b), drawY(b));
      } else {
        Node a = list.get(i);
        Node b = list.get(0);
        line(drawX(a), drawY(a), drawX(b), drawY(b));
      }
    }
  }
  
  if(hasSelectedFile)
  saveFrame("movie/frame_####.png");
}

void keyPressed() {
  if (key == 'f'){
    showNeighbor = !showNeighbor;
  }
}

Route randomNeighbor2(Route r) {
  List<Node> start = new ArrayList<Node>(r.path);
  int indexA = rand.nextInt(start.size());
  int indexB = rand.nextInt(start.size());
  Node temp = start.get(indexB);
  start.set(indexB, start.get(indexA));
  start.set(indexA, temp);
  indexA = rand.nextInt(start.size()); 
  indexB = rand.nextInt(start.size()); 
  temp = start.get(indexB); 
  start.set(indexB, start.get(indexA)); 
  start.set(indexA, temp);
  return new Route(start);
}

Route randomNeighbor(Route r) {
  List<Node> start = new ArrayList<Node>(r.path); 
  int indexA = rand.nextInt(start.size()); 
  int indexB = rand.nextInt(start.size()); 
  Node temp = start.get(indexB); 
  start.set(indexB, start.get(indexA)); 
  start.set(indexA, temp); 

  return new Route(start);
}

int drawX(Node a) {
  return a.x * 5;
}

int drawY(Node b) {
  return height -  b.y * 5;
}

public void processFile(File selection) {
  try {
    BufferedReader br = new BufferedReader(new FileReader(selection)); 
    String line; 
    br.readLine(); 
    while ((line = br.readLine()) != null) {
      // println(line);
      String[] parts = line.split(" "); 
      addPoint(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    br.close(); 
    println("read in file"); 
    List<Node> allNodes = new ArrayList<Node>(myList); 
    //Collections.shuffle(allNodes);
    current = new Route(allNodes); 
    best = current; 
    hasSelectedFile = true;
  } 
  catch (Exception e) {
    System.err.println(e);
  }
}



void addPoint(String name, int a, int b) {
  //println("boop");
  myList.add(new Node(name, a, b));
}