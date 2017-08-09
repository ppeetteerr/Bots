package bots.ticTacToe.analyse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.NeurophException;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

public class NeuralNetworkFactory {

  private static final String SIZE = "%size%";
  private static final String FILE_PATH = "C:\\Users\\Peter.Hudak\\nn_" + SIZE + ".txt";

  private final Map<Integer, NeuralNetwork<?>> neuralNetworks = new HashMap<>();

  private static NeuralNetworkFactory instance;

  private NeuralNetworkFactory() {
  }

  public static NeuralNetworkFactory getInstance() {
    if (instance == null) {
      instance = new NeuralNetworkFactory();
    }

    return instance;
  }

  public NeuralNetwork<?> getNeuralNetwork(final int gridSize) {
    if (neuralNetworks.containsKey(gridSize)) {
      return neuralNetworks.get(gridSize);
    }

    final File file = new File(FILE_PATH.replace(SIZE, Integer.toString(gridSize)));

    final MultiLayerPerceptron nn = new MultiLayerPerceptron(Utils.inputLayerSize(gridSize), Utils.hiddenLayerSize(gridSize),
        Utils.hiddenLayerSize(gridSize), Utils.outputLayerSize(gridSize));
    final BackPropagation learn = new BackPropagation();
    learn.setMaxError(Double.MAX_VALUE);
    // learn.setBatchMode(true);
    learn.setLearningRate(0.01);
    nn.setLearningRule(learn);

    if (file.exists()) {
      ObjectInputStream oistream = null;

      try {
        oistream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
        final Double[] loadedWeights = (Double[]) oistream.readObject();
        final double[] wieghts = new double[loadedWeights.length];
        for (int i = 0; i < wieghts.length; i++) {
          wieghts[i] = loadedWeights[i];
        }
        nn.setWeights(wieghts);

        System.err.println("Succesufully loaded saved weights.");

      } catch (IOException | ClassNotFoundException ioe) {
        throw new NeurophException("Could not read neural network file!", ioe);
      } finally {
        if (oistream != null) {
          try {
            oistream.close();
          } catch (final IOException ioe) {
          }
        }
      }

    }

    neuralNetworks.put(gridSize, nn);
    return nn;
  }

}
