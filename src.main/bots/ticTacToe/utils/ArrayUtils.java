package bots.ticTacToe.utils;

import java.util.Arrays;

public class ArrayUtils {
  
  public static int[][] cloneArray(final int[][] a){
    final int[][] clone = new int[a.length][];
    for (int i = 0; i < a.length; i++) {
      clone[i] = Arrays.copyOf(a[i], a[i].length);
    }
    
    return clone;
  }
  
  public static String toString(final double[][] score) {
    final int gameSize = score[0].length;
    final StringBuilder sb = new StringBuilder("\n");
    for (int i = 0; i < gameSize; i++) {
      for (int j = 0; j < gameSize; j++) {
        sb.append(String.format("%10f", score[i][j]));
      }
      sb.append("\n");
    }
    
    return sb.toString();
  }
  
  public static String toString(final double[] score) {
    final StringBuilder sb = new StringBuilder("\n");
    for (int i = 0; i < score.length; i++) {
        sb.append(String.format("%8d", i));
    }
    sb.append("\n");
    
    for (int i = 0; i < score.length; i++) {
      sb.append(String.format("%8f", score[i]));
    }
    sb.append("\n");
    
    
    return sb.toString();
  }
  
}
