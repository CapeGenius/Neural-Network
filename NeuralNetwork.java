/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
  public static double learningRate = 3;
  public static int iterationSize = 10;
  public static double threshold = 0.5;

  public static void main(String[] args)
  {
    
    
  }

   class Network
  {

    private double[][] inputHiddenMatrix;
    private double[][] hiddenOutputMatrix;
    private double[] sigmoid;

    //declares the input hidden layer class as a constructor
    public Network()
    {
      //initializes the matrix's size
      inputHiddenMatrix = new double[featureLength][hiddenLength];
      hiddenOutputMatrix = new double[hiddenLength][outputLength];
      sigmoid = new double[hiddenLength];
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
          inputHiddenMatrix[i][j] = Math.random();
        }
      }
      //nested loop to place random values in the matrix
      for (int j = 0; j < this.hiddenOutputMatrix.length; j++)
      {
        //loop to set random values for the matrix
        for (int k = 0; k < this.hiddenOutputMatrix[j].length; k++)
        {
          hiddenOutputMatrix[j][k] = Math.random();
        }
      }
    }
    
    public void trainNetwork(int[] input, int[] expectedOutput)
    {
      //declare predict output
      double[] predictedOutput = new double[outputLength];
      double[] error = new double[outputLength];
      double[] predictedHiddenOutput = new double[hiddenLength];
      
      //predicted hidden output represent g(x)
      //forward propagation to calculate values
      predictedHiddenOutput = forwardPropagate(Arrays.stream(input).asDoubleStream().toArray(), predictedHiddenOutput, inputHiddenMatrix);
      predictedOutput = forwardPropagate(predictedHiddenOutput, predictedOutput, hiddenOutputMatrix);
      
      //back propagation to adjust weights
      backPropagation(predictedOutput, predictedHiddenOutput, expectedOutput, error);
    }
    
    //forward propagates in both the hidden and output layers
    public double[] forwardPropagate(double [] input, double [] predictedOutput, double[][] matrix)
    {
      //receive input and create sigmoid function
      for (int j = 0; j < matrix[0].length; j++)
      {
        double localSigmoid = 0;
        
        for (int i = 0; i < matrix.length; i++)
        {
          localSigmoid += matrix[i][j]*input[i];
        }
        
        //calculates the local sigmoid
        sigmoid[j] = localSigmoid;
        
        //if a perceptron's sigmoid function exceeds a threshold, g(x) --> 1 
        if (sigmoid[j] >= threshold)
        {
          predictedOutput[j] = 1;
          predictedOutput[j] = (1) / (1 + Math.exp(-1 * predictedOutput[j] ) );
        }
        //if a perceptron's sigmoid function is below a threshold, g(x) --> 0
        else
        {
          predictedOutput[j] = 0;
          predictedOutput[j] = (1) / (1 + Math.exp(-1 * predictedOutput[j] ) );
        }
      }
      return predictedOutput;
    }
    
    
    //backpropagation from input to hidden
    public void backPropagation(int[] input, double [] errors, double [] predictedHiddenOutput)
    {
      //starts with the hidden layer
      for (int j = 0; j < inputHiddenMatrix.length; j++)
      {
        //moves down each row
        for (int i = 0; i < hiddenOutputMatrix.length; i++)
        {
          double deltaWeight = 0;
          double alpha = calculateAlpha(j, errors);
          
        }
      }
    }
    
    public double calculateAlpha(int hiddenIndex, double [] errors)
    {
      
      return 0;
    }
    
    //overloaded backpropagation from hidden to output
    public void backPropagation(double[] predictedOutput, double [] predictedHidden, int[] expectedOutput, double [] error)
    {
      //rows represent hidden layers, columns represent output layers
      for (int j = 0; j < hiddenOutputMatrix[0].length; j++)
      {
        double localSigmoid = 0;
        
        //adjusts the weights fro the predicted hidden
        for (int i = 0; i < hiddenOutputMatrix.length; i++)
        {
          //calculates the delta weight
          double deltaWeight = 0;
          deltaWeight = learningRate*predictedHidden[j];
          
          //calculates the error
          double outputError = (expectedOutput[j]-predictedOutput[j])*(1-expectedOutput[j])*learningRate;
          //error[j] = outputError;
          
          //adds the change to the weight itself
          deltaWeight = deltaWeight*outputError;
          hiddenOutputMatrix[i][j] += deltaWeight;
        }
      }
      
    }
    
    //adjust current weights
    public void adjustWeights()
    {
      double deltaWeight = learningRate;
      double actualOutput = 0;
      double expectedOutput = 0;
      
      //adjust the weight of each 
      
    }

  }
}
