class Route {
  List<Node> path;
  double dist;
  
  double calc(){
    return dist(path);
  }
  
  // euclidean distance
  double dist(int x1, int y1, int x2, int y2) {
    return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
  }

  // distance between two Nodes
  double dist(Node a, Node b) {
    return dist(a.x, a.y, b.x, b.y);
  }

  
  // Distance between all the nodes in a list
  // including wrapping back to the first
  public double dist(List<Node> list) {
    double sum = 0;
    for (Node b : list) {
      if (list.indexOf(b) != list.size() - 1) {
        double dist = dist(b, list.get(list.indexOf(b) + 1));
        sum += dist;
      } else {
        double dist = dist(b, list.get(0));
        sum += dist;
      }
    }
    return sum;
  }

  
  Route(List<Node> list) {
    path = list;
    dist = calc();
  }
}