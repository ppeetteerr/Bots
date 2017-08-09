package bots.ticTacToe.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import bots.ticTacToe.utils.ArrayUtils;

public class Game {

  public static final int EMPTY = -1;
  public static final int DRAW = 2;

  public static int WIN_LENGTH = 3;

  public int size;
  private final int[][] grid;
  public int gameWinner = DRAW;

  private final Set<Position> moves;

  public Game(final int size, final int winLength) {
    this.size = size;
    WIN_LENGTH = winLength;
    grid = GameUtils.createEmptyGrid(size);
    
    moves = new HashSet<>();
    generateMoves();
  }

  public Game(final int[][] grid, final Set<Position> moves) {
    this.size = grid.length;
    this.grid = grid;

    this.moves = moves;
  }
  
  public Game(final int[][] grid) {
    this.size = grid.length;
    this.grid = grid;

    moves = new HashSet<>();;
    generateMoves();
  }

  public Collection<Position> getValidMoves() {
    return moves;
  }

  public int[][] getGrid() {
    return ArrayUtils.cloneArray(grid);
  }

  private void generateMoves() {
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (grid[row][col] == EMPTY) {
          moves.add(new Position(row, col));
        }
      }
    }
  }

  // TODO
  public boolean ended() {
    return gameWinner != DRAW || getValidMoves().isEmpty();
  }

  public void makeMove(final Position pos, final int playerId) {
    grid[pos.row][pos.col] = playerId;
    moves.remove(pos);
    updateGameStatus(pos);
  }

  private void updateGameStatus(final Position pos) {
    final int value = grid[pos.row][pos.col];
    final int row = pos.row;
    final int col = pos.col;

    //////////////////////////////////////////////////////
    int newRow = row - 1;
    int length = 1;
    while (newRow >= 0 && grid[newRow][col] == value) {
      newRow--;
      length++;
    }

    newRow = row + 1;
    while (newRow < size && grid[newRow][col] == value) {
      newRow++;
      length++;
    }

    if (length >= WIN_LENGTH) {
      gameWinner = value;
      return;
    }
    ///////////////////////////////////////////////////////
    int newCol = col - 1;
    length = 1;
    while (newCol >= 0 && grid[row][newCol] == value) {
      newCol--;
      length++;
    }

    newCol = col + 1;
    while (newCol < size && grid[row][newCol] == value) {
      newCol++;
      length++;
    }

    if (length >= WIN_LENGTH) {
      gameWinner = value;
      return;
    }
    //////////////////////////////////////////////////////
    newRow = row - 1;
    newCol = col - 1;
    length = 1;
    while (newRow >= 0 && newCol >= 0 && grid[newRow][newCol] == value) {
      newRow--;
      newCol--;
      length++;
    }

    newRow = row + 1;
    newCol = col + 1;
    while (newRow < size && newCol < size && grid[newRow][newCol] == value) {
      newRow++;
      newCol++;
      length++;
    }

    if (length >= WIN_LENGTH) {
      gameWinner = value;
      return;
    }
    //////////////////////////////////////////////////////
    newRow = row - 1;
    newCol = col + 1;
    length = 1;
    while (newRow >= 0 && newCol < size && grid[newRow][newCol] == value) {
      newRow--;
      newCol++;
      length++;
    }

    newRow = row + 1;
    newCol = col - 1;
    while (newRow < size && newCol >= 0 && grid[newRow][newCol] == value) {
      newRow++;
      newCol--;
      length++;
    }

    if (length >= WIN_LENGTH) {
      gameWinner = value;
      return;
    }

  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        sb.append(grid[row][col] + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}