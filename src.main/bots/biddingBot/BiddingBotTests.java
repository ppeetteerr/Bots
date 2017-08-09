package bots.biddingBot;

public class BiddingBotTests {
  
  public void BidEvaluatorOppMinBid() {
    
  }
  
  public static void main(final String[] a) {
    final Game game = new Game();
    final int money = 100;
    game.bottlePos = 1;
    game.drawWinner = 1;
    game.money = new int[] {2, 11};
    
    final BidEvaluator evaluator = new BidEvaluator(game, 0);
    for (int myBid = 1; myBid <= 2; myBid++) {
      System.err.println(myBid + " -> " + evaluator.getBidValue(myBid));
    }
  }
}
