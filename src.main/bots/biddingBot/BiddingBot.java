package bots.biddingBot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BiddingBot {

  private long start;
  private int myId;

  public static void main(final String[] args) throws Exception {
    new BiddingBot().play();
    // System.out.println(play(""));
  }

  public int play() throws Exception {
    start = System.currentTimeMillis();
    final Game game = readGame();

    if (game.money[myId] == 0) {
      System.out.println(0);
    } else if (game.money[1 - myId] == 0) {
      System.out.println(1);
    } else {
      final BidEvaluator myEvaluator = new BidEvaluator(game, myId);
      final BidEvaluator oppEvaluator = new BidEvaluator(game, 1 - myId);
      final int myBestBid = getBestBid(myEvaluator, game.money[myId]);
      final int oppBestBid = getBestBid(oppEvaluator, game.money[1 - myId]);
      
      double bestValue = -Double.MAX_VALUE;
      int normBestBid = myBestBid;
      for (int myBid = Math.max(1, myBestBid - 1); myBid <= Math.min(game.money[myId], myBestBid + 1); myBid++) {
        final double value = 0.5*myEvaluator.getBidValue(myBid) 
                      + 0.25*myEvaluator.evaluate(myBid, oppBestBid)
                      + 0.25*(Game.GRID_END-oppEvaluator.evaluate(oppBestBid, myBid));
        
        if(value>bestValue) {
          bestValue = value;
          normBestBid = myBid;
        }
      }
      
      System.err.println("Normalised best value is "+bestValue + " for bid "+normBestBid);
      
      System.out.println(normBestBid);
    }
    System.err.println("TIME:" + time());
    return 0;
  }

  private int getBestBid(final BidEvaluator evaluator, final int maxBid) {
    if (maxBid == 0) {
      return 0;
    } else {
      int bestBid = 1;
      double bestValue = -Double.MAX_VALUE;
      for (int myBid = 1; myBid <= maxBid; myBid++) {
        final double value = evaluator.getBidValue(myBid);
        if (value > bestValue) {
          bestValue = value;
          bestBid = myBid;
        }
      }

      System.err.print("Value is " + bestValue + " -> ");

      for (int myBid = 1; myBid <= maxBid; myBid++) {
        if (bestValue == evaluator.getBidValue(myBid)) {
          System.err.print(myBid + "  ");
        }
      }
      System.err.println();

      return bestBid;
    }
  }

  private Game readGame() {
    final Game game = new Game();
    final Scanner in = new Scanner(System.in);
    myId = in.nextInt() - 1; // 0 if first player 1 if second
    game.bottlePos = in.nextInt(); // position of the scotch
    final int[][] bids = readBids(in);
    in.close();

    game.drawWinner = drawWinner(bids[0], bids[1]);
    game.money = calculateMoney(bids[0], bids[1]);

    System.err.println("Game status: " + game);
    
//    myId = 0;
//    game.bottlePos=9;
//    game.drawWinner = 0;
//    game.money = new int[] {14,2};
    return game;
  }

  private static int drawWinner(final int[] bids0, final int[] bids1) {
    int noDraws = 0;
    for (int i = 0; i < bids0.length; i++) {
      if (bids0[i] == bids1[i]) {
        noDraws++;
      }
    }

    return noDraws % 2;
  }

  private static int[] calculateMoney(final int[] bids0, final int[] bids1) {
    int noDraws = 0;
    final int[] money = new int[] { Game.INITIAL_MONEY, Game.INITIAL_MONEY };

    for (int i = 0; i < bids0.length; i++) {
      if (bids0[i] < bids1[i]) {
        money[1] -= bids1[i];
      } else if (bids0[i] > bids1[i]) {
        money[0] -= bids0[i];
      } else {
        if (noDraws++ % 2 == 1) {
          money[1] -= bids1[i];
        } else {
          money[0] -= bids0[i];
        }
      }
    }

    return money;
  }

  private int[][] readBids(final Scanner in) {
    final int[][] moves = new int[2][];
    moves[0] = new int[0];
    moves[1] = moves[0];
    if (in.hasNext()) {
      in.nextLine();
      final String first_move = in.nextLine();
      final String[] move_1 = first_move.split(" ");
      final String second_move = in.nextLine();
      final String[] move_2 = second_move.split(" ");
      final int[] first_moves = new int[move_1.length];
      final int[] second_moves = new int[move_2.length];
      if (!first_move.equals("")) {
        for (int i = 0; i < move_1.length; i++) {
          first_moves[i] = Integer.parseInt(move_1[i]);
          second_moves[i] = Integer.parseInt(move_2[i]);
        }
      }

      moves[0] = first_moves;
      moves[1] = second_moves;
    }

    return moves;
  }

  private long time() {
    return System.currentTimeMillis() - start;
  }
}

class Game {
  public static final int INITIAL_MONEY = 100;
  public static final int GRID_END = 10;

  int[] money;
  int drawWinner;
  int bottlePos;

  @Override
  public String toString() {
    return "Money: " + Arrays.toString(money) + ", draw winner: " + drawWinner + ", bottle position: " + bottlePos;
  }
}

/**
 * Evaluates bid based on following evaluation: - I bid - enemy react on my bid with his best counter bid - repeat
 * 
 */
class BidEvaluator {
  private static int DRAW_WINNER = 1;
  private static int DRAW_LOSER = 0;
  private static double DEFAULT_VALUE = Double.MAX_VALUE;

  // private static double WIN_VALUE = Math.pow(10, 5);

  // value[i][j][k][l] -> value if I have i money, other player has j money
  // bottle is at pos k and player with id l is draw winner
  private double[][][][] boardValue;
  private final Map<Integer, Double> bidValue = new HashMap<>();
  private final int myMoney;
  private final int oppMoney;
  boolean winDraw;
  int bottlePos;

  // note that game is always oriented in such way that I am on left side
  public BidEvaluator(final Game game, final int myId) {
    myMoney = game.money[myId];
    oppMoney = game.money[1 - myId];
    winDraw = myId == game.drawWinner;
    bottlePos = Math.abs(game.bottlePos - Game.GRID_END * myId);

    initBoardValue();
  }

  private void initBoardValue() {
    boardValue = new double[myMoney + 1][oppMoney + 1][Game.GRID_END + 1][2];
    for (int myMoney = 0; myMoney <= this.myMoney; myMoney++) {
      for (int oppMoney = 0; oppMoney <= this.oppMoney; oppMoney++) {
        for (int bottlePos = 0; bottlePos <= Game.GRID_END; bottlePos++) {
          boardValue[myMoney][oppMoney][bottlePos][DRAW_WINNER] = DEFAULT_VALUE;
          boardValue[myMoney][oppMoney][bottlePos][DRAW_LOSER] = DEFAULT_VALUE;
        }
      }
    }
  }

  public double getBidValue(final int bid) {
    if (bid == 0)
      throw new IllegalArgumentException();

    if (bidValue.containsKey(bid))
      return bidValue.get(bid);
    else {
      final double bidValue = bidValue(bid, myMoney, oppMoney, bottlePos, winDraw);
      this.bidValue.put(bid, bidValue);
      return bidValue;
    }
  }

  public double evaluate(final int myBid, final int oppBid) {
    double value;
    if (oppBid < myBid) {
      value = getBoardValue(myMoney - myBid, oppMoney, bottlePos - 1, winDraw);
    } else if (oppBid == myBid) {
      if (winDraw)
        value = getBoardValue(myMoney - myBid, oppMoney, bottlePos - 1, false);
      else
        value = getBoardValue(myMoney, oppMoney - oppBid, bottlePos + 1, true);
    } else {
      value = getBoardValue(myMoney, oppMoney - oppBid, bottlePos + 1, winDraw);
    }

    return value;
  }

  // bid must be at least 1
  private double bidValue(final int myBid, final int myMoney, final int oppMoney, final int bottlePos, final boolean iWinDraw) {
    final int oppMinBid = oppMinBid(myBid, oppMoney, iWinDraw);
    final int oppMaxBid = oppMaxBid(myBid, oppMoney, iWinDraw);

    double minBoardValue = Double.MAX_VALUE;
    for (int oppBid = oppMinBid; oppBid <= oppMaxBid; oppBid++) {
      double boardValue;
      if (oppBid < myBid) {
        boardValue = getBoardValue(myMoney - myBid, oppMoney, bottlePos - 1, iWinDraw);
      } else if (oppBid == myBid) {
        if (iWinDraw)
          boardValue = getBoardValue(myMoney - myBid, oppMoney, bottlePos - 1, false);
        else
          boardValue = getBoardValue(myMoney, oppMoney - oppBid, bottlePos + 1, true);
      } else {
        boardValue = getBoardValue(myMoney, oppMoney - oppBid, bottlePos + 1, iWinDraw);
      }

      if (boardValue < minBoardValue)
        minBoardValue = boardValue;
    }

    return minBoardValue;
  }

  private double getBoardValue(final int myMoney, final int oppMoney, final int bottlePos, final boolean iWinDraw) {
    if (boardValue[myMoney][oppMoney][bottlePos][iWinDraw ? DRAW_WINNER : DRAW_LOSER] != DEFAULT_VALUE) {
      return boardValue[myMoney][oppMoney][bottlePos][iWinDraw ? DRAW_WINNER : DRAW_LOSER];
    } else if (myMoney == 0) {
      final double value = evaluateBottlePos(Math.min(Game.GRID_END, bottlePos + oppMoney));
      boardValue[myMoney][oppMoney][bottlePos][iWinDraw ? DRAW_WINNER : DRAW_LOSER] = value;
      return value;
    } else if (bottlePos == 0 || bottlePos == Game.GRID_END) {
      final double value = evaluateBottlePos(bottlePos);
      boardValue[myMoney][oppMoney][bottlePos][iWinDraw ? DRAW_WINNER : DRAW_LOSER] = value;
      return value;
    }

    double value = -Double.MAX_VALUE;
    for (int myBid = 1; myBid <= myMoney; myBid++) {
      final double bidValue = bidValue(myBid, myMoney, oppMoney, bottlePos, iWinDraw);
      if (bidValue > value)
        value = bidValue;
    }

    boardValue[myMoney][oppMoney][bottlePos][iWinDraw ? DRAW_WINNER : DRAW_LOSER] = value;

    return value;
  }

  private double evaluateBottlePos(final int pos) {
    return Game.GRID_END - pos;
  }

  private int oppMinBid(final int myBid, final int oppMoney, final boolean iWinDraw) {
    return Math.min(iWinDraw ? myBid : Math.max(myBid - 1, 1), oppMoney);
  }

  private int oppMaxBid(final int myBid, final int oppMoney, final boolean iWinDraw) {
    return Math.min(myBid + 1, oppMoney);
  }

}
