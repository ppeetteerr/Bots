package bots.ticTacToe.game;

import bots.ticTacToe.game.history.HistoryManager;
import bots.ticTacToe.player.Player;
import bots.ticTacToe.ui.UIPanel;

public class Referee {

  private final Game game;
  private final Player[] player;
  private final HistoryManager recorder;

  public Referee(final Player player1, final Player player2, final Game game) {
    this.game = game;
    player = new Player[] { player1, player2 };
    recorder = new HistoryManager(game.size);
  }

  public void playGame() {
    final UIPanel panel =  new UIPanel(game.size);
    panel.setVisible(true);
    
    int playerId = 0;
    int moveNo = 0;
    while (!game.ended()) {
      final Position move = player[playerId % 2].makeMove(game.getGrid());
      recorder.recordMove(move, moveNo, playerId%2);
      game.makeMove(move, playerId % 2);
      
      panel.render(game.getGrid());
      
      if (playerId % 2 == 1) {
        moveNo++;
      }
      
      playerId++;
    }
    
    recorder.winner = game.gameWinner;
    panel.dispose();
  }
  
  public HistoryManager getRecorder() {
    return recorder;
  }

}
