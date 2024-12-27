import java.util.ArrayList;

import controller.FileProcessor.ReadFromFile;
import controller.classifications.ThreeClass;
import controller.perceptron.Perceptron;
import controller.points.Points;

class NeuralNetworks{
	
	public NeuralNetworks() {
		
	}
	
	public static void main(String[] args) {
		System.out.println("init");
		ArrayList<ArrayList<Float>> points = null;
		ArrayList<ArrayList<Float>> trainPoints;
		ArrayList<ArrayList<Float>> testPoints;
		
		int pointsNum = 8000;
		float learningRate = (float)0.01;
		
		Points pointsController = new Points();
		Perceptron perceptron = new Perceptron(new ThreeClass());
		
		// termination params
		perceptron.setTerminationLoops(800);
		perceptron.setTerminationError(0.01f);
		
		perceptron.setOutputNeurons(3);
		perceptron.setLearningRate(learningRate);
		perceptron.define(2, 3, 3, 2, 2, "tanh"); // 1
		
		// generatePoints
//		points = pointsController.generatePoints(pointsNum, -1, 1, -1, 1);
//		trainPoints = new ArrayList<ArrayList<Float>>(points.subList(0, points.size()/2));
//		testPoints = new ArrayList<ArrayList<Float>>(points.subList(points.size()/2, points.size()));
		
		trainPoints = ReadFromFile.parse("train.txt");
		testPoints = ReadFromFile.parse("test.txt");
	
		perceptron.loadData(trainPoints, testPoints);
		
		perceptron.createNN();
		
		perceptron.start();
	}
}