package bots.ticTacToe.analyse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.exceptions.NeurophException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bots.ticTacToe.analyse.converter.NNGridConverter;
import bots.ticTacToe.game.Game;
import bots.ticTacToe.game.Position;
import bots.ticTacToe.game.history.HistoryManager;

public class NeuralNetworkProcessor implements ResultProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(NeuralNetworkProcessor.class);

  private static final String SIZE = "%size%";
  private static final String FILE_PATH = "C:\\Users\\Peter.Hudak\\nn_" + SIZE + ".txt";

  private final NNGridConverter converter = new NNGridConverter();

  @Override
  public void analyse(final HistoryManager historyManager) {
    if (historyManager.winner != Game.DRAW) {
      final NeuralNetwork<?> neuralNetwork = getNeuralNetwork(historyManager.gameSize);

      final DataSet dataSet = generateDataSet(historyManager);
      neuralNetwork.learn(dataSet);

//      testNN(historyManager.gameSize);

      saveNeuralNetwork(neuralNetwork, historyManager.gameSize);
    }
  }

  private DataSet generateDataSet(final HistoryManager historyManager) {
    final int gridSize = historyManager.gameSize;
    final DataSet data = new DataSet(Utils.inputLayerSize(gridSize), Utils.outputLayerSize(gridSize));

    final int winner = historyManager.winner;
    int turn = 0;

    while (historyManager.moveExists(turn, winner)) {
      final int[][] grid = historyManager.getGridAfterTurn(turn, winner);
      final Position move = historyManager.getMove(turn, winner);
      data.addRow(converter.convertInput(grid), converter.convertOutput(move, winner, gridSize));

      turn++;
    }

    return data;
  }

  private NeuralNetwork<?> getNeuralNetwork(final int gridSize) {
    return NeuralNetworkFactory.getInstance().getNeuralNetwork(gridSize);
  }

  private void saveNeuralNetwork(final NeuralNetwork<?> nn, final int gridSize) {
    ObjectOutputStream out = null;
    try {
      final File file = new File(FILE_PATH.replace(SIZE, Integer.toString(gridSize)));
      out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
      out.writeObject(nn.getWeights());
      out.flush();

      // System.err.println("Weights saved.");
    } catch (final IOException ioe) {
      throw new NeurophException("Could not write neural network to file!", ioe);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (final IOException e) {
        }
      }
    }
  }

//  private void testNN(final int gridSize) {
//    final int length = Utils.inputLayerSize(gridSize);
//    final double[] input = new double[length];
//
//    neuralNetwork.setInput(input);
//    neuralNetwork.calculate();
//    logBestMoves(neuralNetwork.getOutput(), 0, gridSize);
//    
//    input[length/2 - (length%2)] = 1;
//    neuralNetwork.setInput(input);
//    neuralNetwork.calculate();
//    logBestMoves(neuralNetwork.getOutput(), 1, gridSize);
//  }
//
//  private void logBestMoves(final double[] output, final int player, final int gridSize) {
//    final TreeMap<Double, Position> map = new TreeMap<>(Comparator.reverseOrder());
//
//    int index = player;
//    while (index < output.length) {
//      map.put(output[index], Position.foldPos(index >> 1, gridSize));
//      index += 2;
//    }
//
//    LOGGER.info(map.toString());
//
//  }

}
