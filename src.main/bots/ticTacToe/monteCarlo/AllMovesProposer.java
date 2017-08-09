package bots.ticTacToe.monteCarlo;

import java.util.Collection;

import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;

public class AllMovesProposer implements MovesProposer {

  @Override
  public Collection<Position> getMoves(final Game game, final int playerOnMove) {
    return game.getValidMoves();
  }

}
