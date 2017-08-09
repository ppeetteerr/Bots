package bots.ticTacToe.player;

import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;
import bots.ticTacToe.monteCarlo.AllMovesProposer;
import bots.ticTacToe.monteCarlo.GameSimulator;
import bots.ticTacToe.monteCarlo.MovesProposer;
import bots.ticTacToe.monteCarlo.RandomGameSimulator;
import bots.ticTacToe.monteCarlo.TreeSearch;

public class MonteCarloPlayer implements Player {
  
  private final int playerId;
  private final MovesProposer movesProposer;
  private final GameSimulator gameSimulator; 
  
  public MonteCarloPlayer(final int playerId) {
    movesProposer = new AllMovesProposer();
    gameSimulator = new RandomGameSimulator();
    
    this.playerId = playerId;
  }
  
  @Override
  public Position makeMove(final int[][] grid) {
    final TreeSearch monteCarlo = new TreeSearch(movesProposer, gameSimulator);
    return monteCarlo.findBestMove(new Game(grid), playerId);
  }

}
