package bots.ticTacToe.monteCarlo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.GameUtils;
import bots.ticTacToe.game.Position;

public class RandomGameSimulator implements GameSimulator {


  @Override
  public double[] simulate(final Game game, int playerOnTurn) {
    final List<Position> pos = new ArrayList<>(game.getValidMoves());
    Collections.shuffle(pos);
    final Iterator<Position> iterator = pos.iterator();
    while (!game.ended()) {
      game.makeMove(iterator.next(), playerOnTurn % 2);
      playerOnTurn++;
    }

    return GameUtils.generateResult(game.gameWinner);
  }

}
