package bots.ticTacToe.monteCarlo;

import java.util.Collection;

import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;

public interface MovesProposer {
  
  Collection<Position> getMoves(Game game, int playerOnMove);
  
}
