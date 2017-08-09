package bots.ticTacToe.ENEMY_TEST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node {
  State state;
  Node parent;
  List<Node> childArray;

  public Node() {
      this.state = new State();
      childArray = new ArrayList<>();
  }

  public Node(final State state) {
      this.state = state;
      childArray = new ArrayList<>();
  }

  public Node(final State state, final Node parent, final List<Node> childArray) {
      this.state = state;
      this.parent = parent;
      this.childArray = childArray;
  }

  public Node(final Node node) {
      this.childArray = new ArrayList<>();
      this.state = new State(node.getState());
      if (node.getParent() != null)
          this.parent = node.getParent();
      final List<Node> childArray = node.getChildArray();
      for (final Node child : childArray) {
          this.childArray.add(new Node(child));
      }
  }

  public State getState() {
      return state;
  }

  public void setState(final State state) {
      this.state = state;
  }

  public Node getParent() {
      return parent;
  }

  public void setParent(final Node parent) {
      this.parent = parent;
  }

  public List<Node> getChildArray() {
      return childArray;
  }

  public void setChildArray(final List<Node> childArray) {
      this.childArray = childArray;
  }

  public Node getRandomChildNode() {
      final int noOfPossibleMoves = this.childArray.size();
      final int selectRandom = (int) (Math.random() * ((noOfPossibleMoves - 1) + 1));
      return this.childArray.get(selectRandom);
  }

  public Node getChildWithMaxScore() {
      return Collections.max(this.childArray, Comparator.comparing(c -> {
          return c.getState().getVisitCount();
      }));
  }

}
