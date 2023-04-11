/*
Rohan Bendapudi - 3/22/23
 */
package neuralnetwork;

import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 */
public class NeuralNetwork
{

  public static int featureLength = 15;
  public static int hiddenLength = 12;
  public static int outputLength = 10;
  public static double learningRate = 0.02;
  public static double threshold = 0.5;
  public ArrayList<Integer[]> inputLists = new ArrayList<>();

  public NeuralNetwork()
  {

  }

  public static void main(String[] args)
  {

  }

  class Network
  {

    private double[][] inputHiddenMatrix;
    private double[][] hiddenOutputMatrix;
    //private double[] sigmoid;

    //declares the input hidden layer class as a constructor
    public Network()
    {
      //initializes the matrix's size
      inputHiddenMatrix = new double[featureLength][hiddenLength];
      hiddenOutputMatrix = new double[hiddenLength][outputLength];
      //sigmoid = new double[hiddenLength];
    }

    //initializes random weight values for the matrix
    public void initializeMatrices()
    {
      //nested loop to place random values in the matrix
      for (int i = 0; i < this.inputHiddenMatrix.length; i++)
      {
        //loop to set random values for the matrix
        for (int j = 0; j < this.inputHiddenMatrix[i].length; j++)
        {
          inputHiddenMatrix[i][j] = Math.random() * 0.2 - Math.random() * 0.2;
        }
      }

      //nested loop to place random values in the matrix
      for (int j = 0; j < this.hiddenOutputMatrix.length; j++)
      {
        //loop to set random values for the matrix
        for (int k = 0; k < this.hiddenOutputMatrix[j].length; k++)
        {
          hiddenOutputMatrix[j][k] = Math.random() * 0.2 - Math.random() * 0.2;
        }
      }
    }

    //forward propagates to find the results 
    public void validationOutput(int[] input)
    {
      double[] predictedOutput = new double[outputLength];
      double[] predictedHiddenOutput = new double[hiddenLength];
      
      //forward propagates to get results
      predictedHiddenOutput = forwardPropagate(Arrays.stream(input).asDoubleStream().toArray(), predictedHiddenOutput, inputHiddenMatrix);
      predictedOutput = forwardPropagate(predictedHiddenOutput, predictedOutput, hiddenOutputMatrix);
      
      //prints each test data
      System.out.println("");
      for (int i = 0; i < predictedOutput.length; i++)
      {
        System.out.print(Math.round(predictedOutput[i]));
        System.out.print(", ");
      }
    }

    //trains the network using forward propagation and backpropagation
    public void trainNetwork(int[] input, int[] expectedOutput)
    {
      //declare predict output
      double[] predictedOutput = new double[outputLength];
      double[] predictedHiddenOutput = new double[hiddenLength];

      //predicted hidden output represent g(x)
      //forward propagation to calculate values 
      predictedHiddenOutput = forwardPropagate(Arrays.stream(input).asDoubleStream().toArray(), predictedHiddenOutput, inputHiddenMatrix);
      //System.out.println(Arrays.toString(predictedHiddenOutput));
      predictedOutput = forwardPropagate(predictedHiddenOutput, predictedOutput, hiddenOutputMatrix);
      
      //calls a back propagate algorithm to change the weights
      backPropagate(Arrays.stream(input).asDoubleStream().toArray(), predictedOutput, predictedHiddenOutput, expectedOutput);
    }

    //forward propagates in both the hidden and output layers
    public double[] forwardPropagate(double[] input, double[] predictedOutput, double[][] matrix)
    {
      //System.out.println("Forward propagation");
      //receive input and create sigmoid function
      for (int j = 0; j < matrix[0].length; j++)
      {
        double localSigmoid = 0;

        //System.out.println("The ");
        for (int i = 0; i < matrix.length; i++)
        {
//          System.out.println("The weight at this cell is " + inputHiddenMatrix[i][j]);
          localSigmoid += matrix[i][j] * input[i];
        }
        //System.out.println("The local sigmoid for this perceptron is " + localSigmoid);

        localSigmoid = 1 / (1 + Math.exp(-localSigmoid));
        
        predictedOutput[j] = localSigmoid;
        //System.out.println("The local sigmoid" + localSigmoid);

      }
      return predictedOutput;
    }
    
    //backpropagates to adjust weights and calculates error
    public void backPropagate(double[] input, double[] predictedOutput, double[] predictedHiddenOutput, int [] expectedOutput)
    {
      //calculates the error and adjusts the weight
      double[] outputError = outputError(predictedOutput, expectedOutput);
      adjustWeights(hiddenOutputMatrix, outputError, predictedHiddenOutput);
      
      //calculates the hidden error and adjusts the weight
      double[] hiddenError = hiddenError(predictedHiddenOutput, outputError);
      adjustWeights(inputHiddenMatrix, hiddenError, input);
    }
    //adjusts the weights
    public void adjustWeights(double [][] matrix, double[] error, double[] priorInput)
    {
      for (int j = 0; j < matrix[0].length;j++)
      {
        for (int i = 0; i < matrix.length; i++)
        {
          //adjusts the weights using the learning rate, error, and previous input
          double deltaWeight = learningRate*error[j]*priorInput[i];
          matrix[i][j] += deltaWeight;
        }
      }
    }
    //finds the error with the output using the standard formula for error
    public double[] outputError(double[] predictedOutput, int [] expectedOutput)
    {
      double[] outputError = new double[hiddenOutputMatrix[0].length];
      //finds the output error using the error formula
      for (int i = 0; i < hiddenOutputMatrix[0].length; i++)
      {
        outputError[i] = (predictedOutput[i])*(1-predictedOutput[i])*(expectedOutput[i] - predictedOutput[i]);
      }
      
      return outputError;
    }
    //finds the error in the hidden output using alpha values
    public double[] hiddenError(double[] predictedHiddenOutput, double[] outputError)
    {
      double[] hiddenError = new double[inputHiddenMatrix[0].length];
      
      for (int i = 0; i < inputHiddenMatrix[0].length; i++)
      {
        //calculates the alpha value
        double alpha = calculateAlpha(i, outputError);
        hiddenError[i] = predictedHiddenOutput[i]*(1-predictedHiddenOutput[i])*alpha;
      }
      
      return hiddenError;
    }

    //calculates the alpha value from hidden to output matrix in order to find the error
    public double calculateAlpha(int hiddenIndex, double[] errors)
    {
      double alpha = 0;
      
      //System.out.println("The error is " + Arrays.toString(errors));

      for (int j = 0; j < hiddenOutputMatrix[0].length; j++)
      {
        //System.out.println(errors[j]);
        alpha += hiddenOutputMatrix[hiddenIndex][j] * errors[j];
      }
      return alpha;
    }

    public void print2D()
    {
      System.out.println(Arrays.deepToString(inputHiddenMatrix));
      System.out.println(Arrays.deepToString(hiddenOutputMatrix));
    }
  }
}
