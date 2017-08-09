package bots.ticTacToe.player;

import java.util.Scanner;

import bots.ticTacToe.game.Position;
import bots.ticTacToe.ui.UIPanel;

public class HumanPlayer implements Player {
  
  private UIPanel panel;
  
  @Override
  public Position makeMove(final int[][] grid) {
    if(panel==null) {
      panel = new UIPanel(grid.length);
      panel.setVisible(true);
    }
    
    panel.render(grid);
    final Position pos = panel.getClickPos();
    System.err.println(pos);
    return pos;
  }
  
  private void output(final int[][] grid) {
    final StringBuilder sb = new StringBuilder("  ");
    
    for (int i = 0; i < grid.length; i++) {
      sb.append(i);
    }
    sb.append("\n");
    
    for (int row = 0; row < grid.length; row++) {
      sb.append(row+ " ");
      for (int col = 0; col < grid.length; col++) {
        sb.append(convert(grid[row][col]));
      }
      sb.append("\n");
    }
    
    System.out.println(sb);
  }
  
  private int readInt(){
    final Scanner sc = new Scanner(System.in);
    return sc.nextInt();
  }
  
  private char convert(final int i) {
    switch (i) {
    case 0:
      return 'X';
    case 1:
      return 'O';
    default:
      return '_';
    }
  }

}
