package bots.ticTacToe.analyse;

import bots.ticTacToe.game.history.HistoryManager;

public interface ResultProcessor {

  void analyse(HistoryManager recorder);
  
}
