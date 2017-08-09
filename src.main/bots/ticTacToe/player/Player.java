package bots.ticTacToe.player;

import bots.ticTacToe.game.Position;

public interface Player {
  
  Position makeMove(int[][] grid);
}
