package bots.ticTacToe.monteCarlo;

import bots.ticTacToe.game.Position;

public class Connection {
  
  /** Move done to access node.*/
  private final Position position;
  private final Node node;
  
  public Connection(final Position pos, final Node node) {
    this.position = pos;
    this.node = node;
  }

  public Position getPosition() {
    return position;
  }

  public Node getNode() {
    return node;
  }
  
  
  
} 
