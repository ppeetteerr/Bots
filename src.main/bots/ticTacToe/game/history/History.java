package bots.ticTacToe.game.history;

import bots.ticTacToe.game.Position;

public class History {
  
  private final Position[][] moves;
  private final int maxNoMoves;
  
  public History (final int maxNoMoves) {
    this.maxNoMoves = maxNoMoves;
    moves = new Position[2][maxNoMoves];
  }
  
  public void recordMove(final Position move, final int moveNo, final int playerId) {
    moves[playerId][moveNo] = move;
  }
  
  public boolean moveExists(final int moveNo, final int playerId) {
    return moveNo < maxNoMoves && moves[playerId][moveNo] != null;
  }
  
  public Position getMove(final int moveNo, final int playerId) {
    return moves[playerId][moveNo];
  }
  
}
