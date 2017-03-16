import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Random;

int[][] grid = new int[9][9];
boolean readInFile = false;

void setup() {
  // seting up the image and processing the file
  frameRate(1);
  size(500, 500);
  background(255);
  stroke(0);
  strokeWeight(4);
  fill(255);
  textAlign(CENTER, CENTER);
  textFont(createFont("Georgia", 32));

  selectInput("Pick a file", "processFile");
}
public void processFile(File selection) {
  try {
    BufferedReader br = new BufferedReader(new FileReader(selection)); 
    String line; 
    int lineCount = 0;
    while ((line = br.readLine()) != null) {
      //println(line);
      String[] parts = line.split(" ");
      if (parts.length == 9) {
        for (int i = 0; i < parts.length; i++) {
          grid[i][lineCount] = Integer.parseInt(parts[i]);
        }
        lineCount++;
      }
    }

    br.close();
    readInFile = true;
    println("read in file: " + selection.getName());
    boolean wantToSolve = true;
    if (wantToSolve) {
      sudoku_solver solver = new sudoku_solver();
      solver.solve(grid);
    }
  } 
  catch (Exception e) {
    System.err.println(e);
  }
}

public void draw() {
  if (!readInFile) {
    return;
  }
  for (int i = 0; i < grid.length; i++) {
    for (int j = 0; j < grid[i].length; j++) {
      strokeWeight(2);
      fill(255);
      rect(i*50+23, j*50+2, 50, 50);
      fill(0);
      int t = grid[i][j];
      if (t!=0)
        text(t, i*50+48, j*50+23);
      strokeWeight(6);
      if (i == 8 && j%3 == 0)
      {
        line(23, 50*j+2, 473, 50*j+2);
      }
    }

    if (i%3 == 0) {
      line(i*50+23, 4, i*50+23, 450);
    }
  }
  line(9*50+23, 4, 9*50+23, 450);
  line(23, 50*9+2, 473, 50*9+2);
}