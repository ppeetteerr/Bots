package bots.ticTacToe.game;

public class Position {
  public int row;
  public int col;

  public Position(final int row, final int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public String toString() {
    return "[" + row + ", " + col + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + col;
    result = prime * result + row;
    return result;
  }
  
  public static int unfoldPos(final Position pos, final int gridSize) {
    return pos.row * gridSize + pos.col;
  }
  
  public static Position foldPos(final int pos, final int gridSize) {
    return new Position(pos / gridSize, pos % gridSize);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Position other = (Position) obj;
    if (col != other.col)
      return false;
    if (row != other.row)
      return false;
    return true;
  }
}
