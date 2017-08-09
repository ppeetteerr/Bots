package bots.ticTacToe.ENEMY_TEST;

import java.util.List;

import bots.ticTacToe.Constants;

public class MonteCarloTreeSearch {

  private static final int WIN_SCORE = 10;
  private int level;
  private int oponent;

  public MonteCarloTreeSearch() {
      this.level = 3;
  }

  public int getLevel() {
      return level;
  }

  public void setLevel(final int level) {
      this.level = level;
  }

  private int getMillisForCurrentLevel() {
      return 2 * (this.level - 1) + 1;
  }

  public Board findNextMove(final Board board, final int playerNo) {
      final long start = System.currentTimeMillis();
      final long end = start + Constants.TURN_TIME;

      oponent = 3 - playerNo;
      final Tree tree = new Tree();
      final Node rootNode = tree.getRoot();
      rootNode.getState().setBoard(board);
      rootNode.getState().setPlayerNo(oponent);

      while (System.currentTimeMillis() < end) {
          // Phase 1 - Selection
          final Node promisingNode = selectPromisingNode(rootNode);
          // Phase 2 - Expansion
          if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
              expandNode(promisingNode);

          // Phase 3 - Simulation
          Node nodeToExplore = promisingNode;
          if (promisingNode.getChildArray().size() > 0) {
              nodeToExplore = promisingNode.getRandomChildNode();
          }
          final int playoutResult = simulateRandomPlayout(nodeToExplore);
          // Phase 4 - Update
          backPropogation(nodeToExplore, playoutResult);
      }
      
      System.err.println("Simulated " + rootNode.state.getVisitCount() + " games with score " + rootNode.state.getWinScore());
      
      final Node winnerNode = rootNode.getChildWithMaxScore();
      tree.setRoot(winnerNode);
      return winnerNode.getState().getBoard();
  }

  private Node selectPromisingNode(final Node rootNode) {
      Node node = rootNode;
      while (node.getChildArray().size() != 0) {
          node = UCT.findBestNodeWithUCT(node);
      }
      return node;
  }

  private void expandNode(final Node node) {
      final List<State> possibleStates = node.getState().getAllPossibleStates();
      possibleStates.forEach(state -> {
          final Node newNode = new Node(state);
          newNode.setParent(node);
          newNode.getState().setPlayerNo(node.getState().getOpponent());
          node.getChildArray().add(newNode);
      });
  }

  private void backPropogation(final Node nodeToExplore, final int playerNo) {
      Node tempNode = nodeToExplore;
      while (tempNode != null) {
          tempNode.getState().incrementVisit();
          if (tempNode.getState().getPlayerNo() == playerNo)
              tempNode.getState().addScore(WIN_SCORE);
          tempNode = tempNode.getParent();
      }
  }

  private int simulateRandomPlayout(final Node node) {
      final Node tempNode = new Node(node);
      final State tempState = tempNode.getState();
      int boardStatus = tempState.getBoard().checkStatus();

      if (boardStatus == oponent) {
          tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
          return boardStatus;
      }
      while (boardStatus == Board.IN_PROGRESS) {
          tempState.togglePlayer();
          tempState.randomPlay();
          boardStatus = tempState.getBoard().checkStatus();
      }

      return boardStatus;
  }

}
