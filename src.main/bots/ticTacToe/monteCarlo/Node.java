package bots.ticTacToe.monteCarlo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bots.ticTacToe.game.Position;

public class Node {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);

  public final long hash;
  public final int playerOnMove;
  // public final List<Node> childs = new ArrayList<>();
  public final List<Connection> connections = new ArrayList<>();

  public int noVisits;
  public double score;

  public Node(final long hash, final int playerOnMove) {
    this.hash = hash;
    this.playerOnMove = playerOnMove;
  }

  public void processResult(final double[] result) {
    noVisits++;
    score += result[playerOnMove];
  }

  public Connection getRandomChildConnection() {
    // TODO change to random :)
    return connections.get(0);
  }

  public Position getBestMove() {
//    final double[][] score = new double[Constants.GRID_SIZE][Constants.GRID_SIZE];
//    for (final Entry<Position, Node> child : connections.entrySet()) {
//      score[child.getKey().row][child.getKey().col] = 1 - 1.0 * child.getValue().score / child.getValue().noVisits;
//    }
    
//    LOGGER.info(ArrayUtils.toString(score));
    
    return Collections.min(connections, Comparator.comparing(connection -> {
      final Node node = connection.getNode();
      // ignore not visited nodes
      return node.noVisits == 0 ? 10 : 1.0 * node.score / node.noVisits;
    })).getPosition();
  }

  @Override
  public String toString() {
    return "Played " + noVisits + " times with score " + score;
  }

}
