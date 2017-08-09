package bots.ticTacToe.analyse;

public class Utils {
  
  public static int inputLayerSize(final int gridSize) {
    return 2 * gridSize * gridSize; 
  }
  
  public static int outputLayerSize(final int gridSize) {
    return 2 * gridSize * gridSize; 
  }
  
  public static int hiddenLayerSize(final int gridSize) {
    return 4 * gridSize * gridSize; 
  }
  
}
