package bots.ticTacToe.game;

import java.util.Arrays;

public final class GameUtils {

  public static int[][] createEmptyGrid(final int size) {
    final int [][] grid = new int[size][size];
    
    for (int i = 0; i < size; i++) {
      Arrays.fill(grid[i], Game.EMPTY);
    }
  
    return grid;
  }
  
  public static double[] generateResult(final int gameResult) {
    final double[] result = new double[2];
    
    if(gameResult==Game.DRAW) {
      result[0] = result[1] = 0.5;
    } else {
      result[gameResult] = 1;
    }
    
    return result;
  }
  
}
