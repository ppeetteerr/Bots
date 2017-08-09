package bots.ticTacToe.ENEMY_TEST;

import java.util.ArrayList;
import java.util.List;

public class State {
  private Board board;
  private int playerNo;
  private int visitCount;
  private double winScore;

  public State() {
      board = new Board();
  }

  public State(final State state) {
      this.board = new Board(state.getBoard());
      this.playerNo = state.getPlayerNo();
      this.visitCount = state.getVisitCount();
      this.winScore = state.getWinScore();
  }

  public State(final Board board) {
      this.board = new Board(board);
  }

  Board getBoard() {
      return board;
  }

  void setBoard(final Board board) {
      this.board = board;
  }

  int getPlayerNo() {
      return playerNo;
  }

  void setPlayerNo(final int playerNo) {
      this.playerNo = playerNo;
  }

  int getOpponent() {
      return 3 - playerNo;
  }

  public int getVisitCount() {
      return visitCount;
  }

  public void setVisitCount(final int visitCount) {
      this.visitCount = visitCount;
  }

  double getWinScore() {
      return winScore;
  }

  void setWinScore(final double winScore) {
      this.winScore = winScore;
  }

  public List<State> getAllPossibleStates() {
      final List<State> possibleStates = new ArrayList<>();
      final List<Position> availablePositions = this.board.getEmptyPositions();
      availablePositions.forEach(p -> {
          State newState = new State(this.board);
          newState.setPlayerNo(3 - this.playerNo);
          newState.getBoard().performMove(newState.getPlayerNo(), p);
          possibleStates.add(newState);
      });
      return possibleStates;
  }

  void incrementVisit() {
      this.visitCount++;
  }

  void addScore(final double score) {
      if (this.winScore != Integer.MIN_VALUE)
          this.winScore += score;
  }

  void randomPlay() {
      final List<Position> availablePositions = this.board.getEmptyPositions();
      final int totalPossibilities = availablePositions.size();
      final int selectRandom = (int) (Math.random() * ((totalPossibilities - 1) + 1));
      this.board.performMove(this.playerNo, availablePositions.get(selectRandom));
  }

  void togglePlayer() {
      this.playerNo = 3 - this.playerNo;
  }
}
