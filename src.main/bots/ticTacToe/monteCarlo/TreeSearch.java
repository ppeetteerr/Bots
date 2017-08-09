package bots.ticTacToe.monteCarlo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bots.ticTacToe.Constants;
import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.GameUtils;
import bots.ticTacToe.game.Position;
import bots.ticTacToe.time.Timer;
import bots.ticTacToe.utils.ArrayUtils;

public class TreeSearch {

  private static final Logger LOGGER = LoggerFactory.getLogger(TreeSearch.class);

  private final MovesProposer movesProposer;
  private final GameSimulator gameSimulator;
  private final Map<Long, Node> nodes = new HashMap<>();

  private int maxDepth = 0;
  private int depth = 0;

  public TreeSearch(final MovesProposer movesProposer, final GameSimulator gameSimulator) {
    this.movesProposer = movesProposer;
    this.gameSimulator = gameSimulator;
  }

  public Position findBestMove(final Game game, final int playerOnMove) {
    final Timer timer = new Timer(Constants.TURN_TIME);
    final Node root = new Node(0L, playerOnMove);

    int noGames = 0;
    timer.start();
    while (timer.hasTime()) {
      depth = 0;
      playFromNode(new Game(ArrayUtils.cloneArray(game.getGrid()), new HashSet<Position>(game.getValidMoves())), root);
      noGames++;
    }

    LOGGER.info("Simulated " + noGames + " games with monte carlo.");
    LOGGER.info("Maximal Depth " + maxDepth);

    final Position move = root.getBestMove();

//    LOGGER.info("Move " + move + " with score " + (1 - 1.0 * root.connections.get(move).score / root.connections.get(move).noVisits));

    return move;
  }

  /** Returns result of play from this node. */
  private double[] playFromNode(final Game game, final Node node) {
    double[] gameResult;
    depth++;

    if (node.connections.isEmpty()) {

      if (!game.ended()) {
        expandChilds(game, node);
        final Connection childConnection = node.getRandomChildConnection();
        game.makeMove(childConnection.getPosition(), node.playerOnMove);
        gameResult = gameSimulator.simulate(game, childConnection.getNode().playerOnMove);
        childConnection.getNode().processResult(gameResult);
      } else {
        gameResult = GameUtils.generateResult(game.gameWinner);
      }

    } else {
      final Connection bestChild = UCT.childWithBestUCT(node);
      game.makeMove(bestChild.getPosition(), node.playerOnMove);
      gameResult = playFromNode(game, bestChild.getNode());
    }

    node.processResult(gameResult);
    maxDepth = Math.max(maxDepth, depth);

    return gameResult;
  }

  private void expandChilds(final Game game, final Node parent) {
    final Collection<Position> moves = movesProposer.getMoves(game, parent.playerOnMove);

    for (final Position position : moves) {
      final long childHash =
          ZobristHashing.getInstance().updateHash(parent.hash, Position.unfoldPos(position, game.size), parent.playerOnMove);
      Node child = nodes.get(childHash);

      if (child == null) {
        child = new Node(childHash, 1 - parent.playerOnMove);
        nodes.put(childHash, child);
      }

      parent.connections.add(new Connection(position, child));
    }
  }

}
