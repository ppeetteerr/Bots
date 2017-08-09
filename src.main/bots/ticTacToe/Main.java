package bots.ticTacToe;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bots.ticTacToe.analyse.NeuralNetworkProcessor;
import bots.ticTacToe.analyse.ResultProcessor;
import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Referee;
import bots.ticTacToe.player.MonteCarloPlayer;
import bots.ticTacToe.player.Player;

public class Main {

  private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(final String[] args) {
//    final Player player1 = new RandomComputerPlayer(0);
     final Player player1 = new MonteCarloPlayer(0);
    // final Player player1 = new EnemyPlayer(0);
    final Player player2 = new MonteCarloPlayer(1);
    // final Player player2 = new RandomComputerPlayer(1);
//    final Player player2 = new HumanPlayer();

    final ResultProcessor processor = new NeuralNetworkProcessor();

    int gameNo = 1;
    final int[] score = new int[3];
    while (true) {
      LOGGER.info("STARTING GAME " + gameNo);
      final Game game = new Game(Constants.GRID_SIZE, Constants.WIN_LENGTH);

      final Referee referee = new Referee(player1, player2, game);
      referee.playGame();
      score[referee.getRecorder().winner]++;

      LOGGER.info("Score is " + Arrays.toString(score));

      processor.analyse(referee.getRecorder());

      gameNo++;
    }
  }

}
