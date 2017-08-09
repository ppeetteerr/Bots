package monteCarlo;

public class MonteCarloTreeSearch<M extends Move> {

  public M findBestMove() {

    final Node root = new Node();

    return null;
  }

  private int playFromNode(final Node node) {
    if (node.childs.isEmpty()) {
      // generate childs and simulate randomly from one child
    } else {
      // choose most promising move, apply move to game and recursive call from child
    }

    return 0;
  }

}
