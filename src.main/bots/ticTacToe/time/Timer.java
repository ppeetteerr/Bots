package bots.ticTacToe.time;

public class Timer {

  private long startTime = -1;
  private final long timeLimit;

  public Timer(final long timeLimit) {
    this.timeLimit = timeLimit;
  }

  public void start() {
    startTime = System.currentTimeMillis();
  }

  public long time() {
    return System.currentTimeMillis() - startTime;
  }

  public boolean hasTime() {
    return startTime + timeLimit > System.currentTimeMillis();
  }

}
