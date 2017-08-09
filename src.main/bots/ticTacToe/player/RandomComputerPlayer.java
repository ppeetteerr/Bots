package bots.ticTacToe.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bots.ticTacToe.Constants;
import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;
import bots.ticTacToe.time.Timer;
import bots.ticTacToe.utils.ArrayUtils;

public class RandomComputerPlayer implements Player {

  private static Logger LOGGER = LoggerFactory.getLogger(RandomComputerPlayer.class);

  private Timer timer;
  private final Random random = new Random();
  private final int id;

  public RandomComputerPlayer(final int id) {
    this.id = id;
  }

  @Override
  public Position makeMove(final int[][] grid) {
    timer = new Timer(Constants.TURN_TIME);
    timer.start();

    final Game game = new Game(grid);
    final int[][] results = new int[game.size * game.size][3];
    final Collection<Position> startMoves = game.getValidMoves();

    int noGames = 0;
    while (timer.hasTime()) {
      for (final Position position : startMoves) {
        final Game randomGame = new Game(ArrayUtils.cloneArray(grid), new HashSet<Position>(game.getValidMoves()));
        randomGame.makeMove(position, id);
        results[position.row * game.size + position.col][play(randomGame, id + 1)]++;
      }
      noGames++;
    }

    final int bestResultId = getBestResultId(results);
    final Position move = new Position(bestResultId / game.size, bestResultId % game.size);
//    LOGGER.info("Simulated " + (noGames * game.getValidMoves().size()) + " games with random moves.");
//    LOGGER.info("Move " + move + " with score "
//        + (1.0 * (results[move.row * game.size + move.col][id] + results[move.row * game.size + move.col][Game.DRAW]) / noGames));

    return move;
  }

  private int getBestResultId(final int[][] results) {
    final int[] score = new int[results.length];
    for (int i = 0; i < score.length; i++) {
      score[i] = results[i][id]
          // - 100 * results[i][1 - id]
          + results[i][Game.DRAW] / 2;
    }

    final int gameSize = (int) Math.sqrt(score.length);
    final StringBuilder sb = new StringBuilder("\n");
    for (int i = 0; i < gameSize; i++) {
      for (int j = 0; j < gameSize; j++) {
        sb.append(String.format("%10d", score[gameSize * i + j]));
      }
      sb.append("\n");
    }

    // System.out.println(sb);

    int max = Integer.MIN_VALUE;
    int bestId = -1;
    for (int j = 0; j < score.length; j++) {
      if (score[j] != 0 && score[j] > max) {
        max = score[j];
        bestId = j;
      }
    }

    return bestId;
  }

  private int play(final Game game, int playerOnTurn) {
    final List<Position> pos = new ArrayList<>(game.getValidMoves());
    Collections.shuffle(pos);
    final Iterator<Position> iterator = pos.iterator();
    while (!game.ended()) {
      game.makeMove(iterator.next(), playerOnTurn % 2);
      playerOnTurn++;
    }

    return game.gameWinner;
  }

}
