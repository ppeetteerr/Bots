package bots.ticTacToe.game.history;

import bots.ticTacToe.game.GameUtils;
import bots.ticTacToe.game.Position;

public class HistoryManager {

  private final History history;
  public final int gameSize;
  public int winner;

  public HistoryManager(final int gameSize) {
    this.gameSize = gameSize;

    history = new History(gameSize * gameSize);
  }

  public void recordMove(final Position move, final int moveNo, final int playerId) {
    history.recordMove(move, moveNo, playerId);
  }

  public boolean moveExists(final int moveNo, final int playerId) {
    return history.moveExists(moveNo, playerId);
  }
  
  public Position getMove(final int moveNo, final int playerId) {
    return history.getMove(moveNo, playerId);
  }

  /** Turn starts from 0. */
  public int[][] getGridAfterTurn(final int turn, final int playerId) {
    final int[][] grid = GameUtils.createEmptyGrid(gameSize);
    applyMoves(grid, 0, 0, turn, playerId);

    return grid;
  }

  public int[][] applyMoves(final int[][] grid, final int fromTurn, final int startPlayerId, final int toTurn,
      final int endPlayerId) {
    for (int turn = fromTurn, playerId = startPlayerId; turn <= toTurn && (turn != toTurn || playerId <= endPlayerId);) {

      if (!history.moveExists(turn, playerId)) {
        break;
      }

      final Position movePos = history.getMove(turn, playerId);
      grid[movePos.row][movePos.col] = playerId;

      playerId = (playerId + 1) % 2;
      if (playerId == 0) {
        turn++;
      }
    }

    return grid;
  }

  /** Apply all moves till end of game. */
  public int[][] applyMoves(final int[][] grid, final int fromTurn, final int startPlayerId) {
    final int turn = fromTurn;
    final int playerId = startPlayerId;

    while (history.moveExists(playerId, turn)) {
      final Position movePos = history.getMove(turn, playerId);
      grid[movePos.row][movePos.col] = playerId;
    }

    return grid;

  }

}
