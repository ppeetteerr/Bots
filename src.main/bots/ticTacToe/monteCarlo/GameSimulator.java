package bots.ticTacToe.monteCarlo;

import bots.ticTacToe.game.Game;

public interface GameSimulator {
  
  double[] simulate(Game game, int playerOnTurn);
  
}
