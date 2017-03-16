import java.util.Arrays;

public class sudoku_solver {

  int opCount = 0;

  int[][] startingGrid = new int[9][9]; 

  public void solve(int[][] grid) {
    // deep copy of array
    for (int i = 0; i < 9; i++) {
      startingGrid[i] = Arrays.copyOf(grid[i], 9);
    }

    for (int i = 1; i < 9; i++) {
      if (solve(0, 1, i, grid)) {
        println("Solved in " + opCount + " ops");
        return;
      }
    }
    System.out.println("Not possible.");
  }

  private boolean solve(int x, int y, int num, int[][] grid) {
    opCount ++;
    grid[x][y] = num;
    if (isComplete(grid)) {
      return true;
    }

    Point point = new Point(0, 0);
    while (!isValidBlock(point.x, point.y, grid)) {
      opCount++;
      point = getNextBlock(point.x, point.y, grid);
    }

    for (int i = 1; i < 10; i++) {
      if (isValidMove(point.x, point.y, i, grid)) {
        if (!solve(point.x, point.y, i, grid)) {
          grid[point.x][point.y] = 0;
        } else {
          return true;
        }
      }
    }
    return false;
  }

  // is the inputed square empty
  private boolean isValidBlock(int x, int y, int[][] grid) {
    return grid[x][y] == 0;
  }

  // gets next square, going down then right
  private Point getNextBlock(int x, int y, int[][] grid) {
    // we're at the bottom right corner
    if (x == grid.length - 1 && y == grid.length - 1) {
      return null;
    }
    // go down first
    if (y < grid.length - 1) {
      return new Point(x, y + 1);
    } else {
      // then to the right
      return new Point(x + 1, 0);
    }
  }

  // Is every square filled?
  private boolean isComplete(int[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] == 0)
          return false;
      }
    }
    return true;
  }

  // Tests to see if a proposed move is valid
  private boolean isValidMove(int x, int y, int num, int[][] grid) {

    // Basic Checks
    if (x < 0 || x >= grid.length || y < 0 || y >= grid.length) {
      return false;
    }
    // Check if num is in this row already
    for (int i = 0; i < grid.length; i++) {
      if (grid[x][i] == num) {
        return false;
      }
    }
    // Check if num is in this column already
    for (int i = 0; i < grid.length; i++) {
      if (grid[i][y] == num) {
        return false;
      }
    }
    // Check if num is in this subdivision already
    for (int i = (x / 3) * 3; i < (x / 3) * 3 + 3; i++) {
      for (int j = (y / 3) * 3; j < (y / 3) * 3 + 3; j++) {
        if (grid[i][j] == num)
          return false;
      }
    }
    return true;
  }

  // Tests if the proposed solution has changed any starting values
  // Test driven development!
  public void testCorrectGrid(int[][] start, int[][] finish) {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (start[i][j] != 0) {
          assert(finish[i][j] == start[i][j]);
        }
      }
    }
  }
}

// helper class
public class Point {
  int x;
  int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}