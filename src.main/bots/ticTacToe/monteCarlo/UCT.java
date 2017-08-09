package bots.ticTacToe.monteCarlo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

import bots.ticTacToe.game.Position;

public class UCT {

  private static final double[] logs = new double[100_000];

  static {
    Arrays.fill(logs, -1);
  }

  public static Entry<Position, Node> childWithBestUCT(final Node parent) {
    final int parentVisits = parent.noVisits;
    return Collections.max(parent.childs.entrySet(),
        Comparator.comparing(entry -> uctValue(parentVisits, entry.getValue().score, entry.getValue().noVisits)));
  }

  public static double uctValue(final int totalVisit, final double nodeWinScore, final int nodeVisit) {
    // prefer opp nodes with zero score
    if (nodeWinScore == 0) {
      return nodeVisit > 0 ? Integer.MAX_VALUE : Integer.MAX_VALUE - 5;
    } 

    // TODO cache counted logs
    // node belongs to opp so we want to minimalize his win score
    return -1.0 * nodeWinScore / nodeVisit + 1.41 * Math.sqrt(Math.log(totalVisit) / nodeVisit);

    // return (nodeWinScore / nodeVisit) + 1.41 * Math.sqrt(log(totalVisit) / nodeVisit);
  }

  private static double log(final int n) {
    if (logs[n] == -1) {
      logs[n] = Math.log(n);
    }

    return logs[n];
  }

}
