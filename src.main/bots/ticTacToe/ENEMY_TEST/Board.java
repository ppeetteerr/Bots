package bots.ticTacToe.ENEMY_TEST;

import java.util.ArrayList;
import java.util.List;

public class Board {
  int[][] boardValues;
  int totalMoves;

  public static final int DEFAULT_BOARD_SIZE = 3;

  public static final int IN_PROGRESS = -1;
  public static final int DRAW = 0;
  public static final int P1 = 1;
  public static final int P2 = 2;

  public Board() {
      boardValues = new int[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
  }

  public Board(final int boardSize) {
      boardValues = new int[boardSize][boardSize];
  }

  public Board(final int[][] boardValues) {
      this.boardValues = boardValues;
  }

  public Board(final int[][] boardValues, final int totalMoves) {
      this.boardValues = boardValues;
      this.totalMoves = totalMoves;
  }

  public Board(final Board board) {
      final int boardLength = board.getBoardValues().length;
      this.boardValues = new int[boardLength][boardLength];
      final int[][] boardValues = board.getBoardValues();
      final int n = boardValues.length;
      for (int i = 0; i < n; i++) {
          final int m = boardValues[i].length;
          for (int j = 0; j < m; j++) {
              this.boardValues[i][j] = boardValues[i][j];
          }
      }
  }

  public void performMove(final int player, final Position p) {
      this.totalMoves++;
      boardValues[p.getX()][p.getY()] = player;
  }

  public int[][] getBoardValues() {
      return boardValues;
  }

  public void setBoardValues(final int[][] boardValues) {
      this.boardValues = boardValues;
  }

  public int checkStatus() {
      final int boardSize = boardValues.length;
      final int maxIndex = boardSize - 1;
      final int[] diag1 = new int[boardSize];
      final int[] diag2 = new int[boardSize];
      
      for (int i = 0; i < boardSize; i++) {
          final int[] row = boardValues[i];
          final int[] col = new int[boardSize];
          for (int j = 0; j < boardSize; j++) {
              col[j] = boardValues[j][i];
          }
          
          final int checkRowForWin = checkForWin(row);
          if(checkRowForWin!=0)
              return checkRowForWin;
          
          final int checkColForWin = checkForWin(col);
          if(checkColForWin!=0)
              return checkColForWin;
          
          diag1[i] = boardValues[i][i];
          diag2[i] = boardValues[maxIndex - i][i];
      }

      final int checkDia1gForWin = checkForWin(diag1);
      if(checkDia1gForWin!=0)
          return checkDia1gForWin;
      
      final int checkDiag2ForWin = checkForWin(diag2);
      if(checkDiag2ForWin!=0)
          return checkDiag2ForWin;
      
      if (getEmptyPositions().size() > 0)
          return IN_PROGRESS;
      else
          return DRAW;
  }

  private int checkForWin(final int[] row) {
      boolean isEqual = true;
      final int size = row.length;
      int previous = row[0];
      for (int i = 0; i < size; i++) {
          if (previous != row[i]) {
              isEqual = false;
              break;
          }
          previous = row[i];
      }
      if(isEqual)
          return previous;
      else
          return 0;
  }

  public void printBoard() {
      final int size = this.boardValues.length;
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              System.out.print(boardValues[i][j] + " ");
          }
          System.out.println();
      }
  }

  public List<Position> getEmptyPositions() {
      final int size = this.boardValues.length;
      final List<Position> emptyPositions = new ArrayList<>();
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              if (boardValues[i][j] == 0)
                  emptyPositions.add(new Position(i, j));
          }
      }
      return emptyPositions;
  }

  public void printStatus() {
      switch (this.checkStatus()) {
      case P1:
          System.out.println("Player 1 wins");
          break;
      case P2:
          System.out.println("Player 2 wins");
          break;
      case DRAW:
          System.out.println("Game Draw");
          break;
      case IN_PROGRESS:
          System.out.println("Game In rogress");
          break;
      }
  }
}
