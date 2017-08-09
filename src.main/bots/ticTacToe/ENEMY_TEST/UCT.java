package bots.ticTacToe.ENEMY_TEST;

import java.util.Collections;
import java.util.Comparator;

public class UCT {

  public static double uctValue(final int totalVisit, final double nodeWinScore, final int nodeVisit) {
      if (nodeVisit == 0) {
          return Integer.MAX_VALUE;
      }
      return (nodeWinScore / nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / nodeVisit);
  }

  static Node findBestNodeWithUCT(final Node node) {
      final int parentVisit = node.getState().getVisitCount();
      return Collections.max(
        node.getChildArray(),
        Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
  }
}
