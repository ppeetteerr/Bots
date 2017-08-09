package bots.ticTacToe.monteCarlo;

import java.util.Random;

import bots.ticTacToe.Constants;

public class ZobristHashing {
  
  /** randomBits[i][j] -> random bits for "position" i and player j. */
  private final long[][] randomBits;
  private final int gridSize;
  private static ZobristHashing instance;
  
  private ZobristHashing(final int gridSize) {
    this.gridSize = gridSize;
    final int noSquares = gridSize * gridSize;
    
    final Random random = new Random(555_555_555);
    randomBits = new long[noSquares][2];

    for (int i = 0; i < noSquares; i++) {
      //TODO better random method for long
      randomBits[i][0] = random.nextLong();
      randomBits[i][1] = random.nextLong();
    }
  }
  
  public static ZobristHashing getInstance() {
    if(instance==null) {
      instance = new ZobristHashing(Constants.GRID_SIZE);
    }
    
    return instance;
  }
  
  public long updateHash (final long hash, final int unfoldPos, final int player) {
    return hash ^ randomBits[unfoldPos][player];
  }
  
  
  
}
