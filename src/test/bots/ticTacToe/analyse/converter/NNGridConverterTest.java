package bots.ticTacToe.analyse.converter;

import org.junit.Assert;
import org.junit.Test;

import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;

public class NNGridConverterTest {

  private final double DELTA = Math.pow(10, -8);
  private final NNGridConverter converter = new NNGridConverter();

  @Test
  public void convertInputTest() {
    final int empty = Game.EMPTY;
    int[][] grid;
    double[] result;

    grid = new int[][] { { empty, empty }, { empty, empty } };
    result = new double[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    Assert.assertArrayEquals(result, converter.convertInput(grid), DELTA);

    grid = new int[][] { { 0, empty }, { empty, empty } };
    result = new double[] { 1, 0, 0, 0, 0, 0, 0, 0 };
    Assert.assertArrayEquals(result, converter.convertInput(grid), DELTA);

    grid = new int[][] { { 0, empty }, { 1, empty } };
    result = new double[] { 1, 0, 0, 0, 0, 1, 0, 0 };
    Assert.assertArrayEquals(result, converter.convertInput(grid), DELTA);

    grid = new int[][] { { 0, 1 }, { 1, 0 } };
    result = new double[] { 1, 0, 0, 1, 0, 1, 1, 0 };
    Assert.assertArrayEquals(result, converter.convertInput(grid), DELTA);
  }

  @Test
  public void convertOutputTest() {
    double[] output;

    output = new double[] { 0, 0, 0, 0, 1, 0, 0, 0 };
    Assert.assertArrayEquals(output, converter.convertOutput(new Position(1, 0), 0, 2), DELTA);

    output = new double[] { 0, 0, 0, 0, 0, 0, 1, 0 };
    Assert.assertArrayEquals(output, converter.convertOutput(new Position(1, 1), 0, 2), DELTA);

    output = new double[] { 0, 0, 0, 0, 0, 1, 0, 0 };
    Assert.assertArrayEquals(output, converter.convertOutput(new Position(1, 0), 1, 2), DELTA);

    output = new double[] { 0, 0, 0, 0, 0, 0, 0, 1 };
    Assert.assertArrayEquals(output, converter.convertOutput(new Position(1, 1), 1, 2), DELTA);
  }

}
