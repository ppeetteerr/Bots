package bots.ticTacToe.monteCarlo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bots.ticTacToe.Constants;
import bots.ticTacToe.game.Position;

public class Node {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Node.class);

  public final long hash;
  public final int playerOnMove;
  // public final List<Node> childs = new ArrayList<>();
  public final Map<Position, Node> childs = new HashMap<>();

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

  public Entry<Position, Node> getRandomChild() {
    // TODO change to random :)
    return childs.entrySet().iterator().next();
  }

  public Position getBestMove() {
    final double[][] score = new double[Constants.GRID_SIZE][Constants.GRID_SIZE];
    for (final Entry<Position, Node> child : childs.entrySet()) {
      score[child.getKey().row][child.getKey().col] = 1 - 1.0 * child.getValue().score / child.getValue().noVisits;
    }
    
//    LOGGER.info(ArrayUtils.toString(score));
    
    return Collections.min(childs.entrySet(), Comparator.comparing(entry -> {
      final Node node = entry.getValue();
      // ignore not visited nodes
      return node.noVisits == 0 ? 10 : 1.0 * node.score / node.noVisits;
    })).getKey();
  }

  @Override
  public String toString() {
    return "Played " + noVisits + " times with score " + score;
  }

}
