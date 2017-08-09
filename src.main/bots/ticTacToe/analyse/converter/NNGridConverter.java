package bots.ticTacToe.analyse.converter;

import bots.ticTacToe.analyse.Utils;
import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;

public class NNGridConverter {

  /**
   * Converts grid into input array for neural network. Every position of grid is converted into two inputs for NN (these inputs
   * denotes whether position is occupied by corresponding player).
   * 
   * @param grid
   * @return
   */
  public double[] convertInput(final int[][] grid) {
    final int gridSize = grid[0].length;
    final double[] input = new double[Utils.inputLayerSize(gridSize)];

    int pos = 0;
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        if (grid[row][col] != Game.EMPTY) {
          input[pos + grid[row][col]] = 1;
        }

        pos += 2;
      }
    }

    return input;
  }

  public double[] convertOutput(final Position pos, final int playerId, final int gridSize) {
    final double[] output = new double[Utils.outputLayerSize(gridSize)];
    output[2 * (pos.row * gridSize + pos.col) + playerId] = 1;

    return output;
  }

}
