package bots.ticTacToe.ENEMY_TEST;

public class Tree {
  Node root;

  public Tree() {
      root = new Node();
  }

  public Tree(final Node root) {
      this.root = root;
  }

  public Node getRoot() {
      return root;
  }

  public void setRoot(final Node root) {
      this.root = root;
  }

  public void addChild(final Node parent, final Node child) {
      parent.getChildArray().add(child);
  }

}