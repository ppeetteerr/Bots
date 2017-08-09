package bots.ticTacToe.game.history;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;

public class HistoryManagerTest {

  private HistoryManager manager;

  @Before
  public void before() {
    final int gameSize = 3;

    manager = new HistoryManager(gameSize);
    manager.winner = 0;

    manager.recordMove(new Position(0, 2), 0, 0);
    manager.recordMove(new Position(2, 1), 0, 1);
    manager.recordMove(new Position(1, 2), 1, 0);
    manager.recordMove(new Position(1, 0), 1, 1);
    manager.recordMove(new Position(0, 0), 2, 0);
    manager.recordMove(new Position(0, 1), 2, 1);
  }

  @Test
  public void testGetGridAfterTurn() {
    final int empty = Game.EMPTY;

    int[][] expected = new int[][] { { empty, empty, 0 }, { empty, empty, 0 }, { empty, 1, empty } };
    Assert.assertArrayEquals(expected, manager.getGridAfterTurn(1, 0));

    expected = new int[][] { { empty, empty, 0 }, { 1, empty, 0 }, { empty, 1, empty } };
    Assert.assertArrayEquals(expected, manager.getGridAfterTurn(1, 1));
  }
}
