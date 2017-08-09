package bots.ticTacToe.ENEMY_TEST;

import bots.ticTacToe.game.Position;
import bots.ticTacToe.player.Player;

public class EnemyPlayer implements Player {
  
  private final int playerId;
  
  public EnemyPlayer(final int playerId) {
    this.playerId = playerId;// TODO Auto-generated constructor stub
  }
  
  @Override
  public Position makeMove(final int[][] grid) {

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
          grid[i][j]++;
      }
    }
    
    final MonteCarloTreeSearch mcts = new MonteCarloTreeSearch();
    final int[][] newGrid = mcts.findNextMove(new Board(grid), playerId+1).boardValues;
    
    Position move = null;
    
    outerLoop:
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid.length; j++) {
        if (grid[i][j] != newGrid[i][j]) {
          move = new Position(i, j);
          break outerLoop;
        }
      }
    }
    
    // TODO Auto-generated method stub
    return move;
  }

}
