package monteCarlo;

/**
 * Common parent for all moves returned by MonteCarloTreeSearch.
 */
public abstract class Move {
  
  public abstract void applyMove();
  public abstract void revertMove();
}
