package bots.ticTacToe.monteCarlo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.neuroph.core.NeuralNetwork;

import bots.ticTacToe.analyse.NeuralNetworkFactory;
import bots.ticTacToe.analyse.converter.NNGridConverter;
import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;

public class NNMovesProposer implements MovesProposer {
  
  private final int MAX_NO_PROPOSES = 5;
  
  private final NNGridConverter gridConverter = new NNGridConverter();

  @Override
  public Collection<Position> getMoves(final Game game, final int playerOnMove) {
    final NeuralNetwork<?> neuralNetwork = NeuralNetworkFactory.getInstance().getNeuralNetwork(game.size);
    
    final double[] nnInput = gridConverter.convertInput(game.getGrid());
    neuralNetwork.setInput(nnInput);
    neuralNetwork.calculate();
    final double[] nnOutput = neuralNetwork.getOutput();
    
    final Map<Double, Position> map = new TreeMap<>(Comparator.reverseOrder());

    int index = playerOnMove;
    while (index < nnOutput.length) {
      map.put(nnOutput[index], Position.foldPos(index >> 1, game.size));
      index += 2;
    }
    
    final List<Position> proposed = new ArrayList<>();
    int counter = 1;
    for (final Entry<Double, Position> entry : map.entrySet()) {
      if(counter <= MAX_NO_PROPOSES) {
        proposed.add(entry.getValue());
        counter++;
      } else {
        break;
      }
    }
    
    return proposed;
  }

}
