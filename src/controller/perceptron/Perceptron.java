package controller.perceptron;

import java.util.ArrayList;
import java.util.Arrays;

import controller.classifications.ThreeClass;
import controller.layers.Layer;
import controller.neurons.Neuron;
import controller.points.Points;

public class Perceptron {
	private ThreeClass threeClass;
	private int d;
	private int K;
	private int H1;
	private int H2;
	private int H3;
	private String funcType;
	private int[] layersEnum;
	private int[] hiddenLayersEnum;
	public static float n; //learningRate
	private int outputNeurons;
	private ArrayList<Layer> nn;
	private int terminationLoops;
	private float terminationError;
	private ArrayList<ArrayList<Float>> train;
	private ArrayList<ArrayList<Float>> test;
	private float[] classifiedTrain;
	private float[] classifiedTest;
	
	public Perceptron(ThreeClass threeClassController) {
		this.threeClass = threeClassController;
	}
	
	public void define(int d, int K, int H1, int H2, int H3, String funcType) {
		this.d = d;
		this.K = K;
		this.H1 = H1;
		this.H2 = H2;
		this.H3 = H3;
		this.layersEnum = new int[] {d, H1, H2, H3, outputNeurons};
		this.hiddenLayersEnum = new int[] {H1, H2, H3};
	}
	
	public void loadData(ArrayList<ArrayList<Float>> train, ArrayList<ArrayList<Float>> test) {
		this.train = train;
		this.test = test;
		this.classifiedTrain = threeClass.classify(this.train);
		this.classifiedTest = threeClass.classify(this.test);
	}
	
	public void createNN() {
		ArrayList<Layer> layers = new ArrayList<>();
		
		Layer tmpLayer = new Layer();
		Neuron neuron;
		for(int i = 0;i<layersEnum[0];i++) { // input layer
			neuron = new Neuron(1, "logistic");
			tmpLayer.append(neuron);
		}
		layers.add(tmpLayer);
		
		for(int i = 0;i<hiddenLayersEnum.length;i++) { // hidden layers
			tmpLayer = new Layer();
			for(int j = 0;j<hiddenLayersEnum[i];j++) {
				neuron = new Neuron(layers.get(layers.size()-1).getNeurons().size(), "logistic");
				tmpLayer.append(neuron);
			}
			layers.add(tmpLayer);
		}
		
		tmpLayer = new Layer();
		for(int i = 0;i<outputNeurons;i++) { // output layer
			neuron = new Neuron(layers.get(layers.size()-1).getNeurons().size(), "logistic");
			tmpLayer.append(neuron);
		}
		layers.add(tmpLayer);
		
		for(int i = 0;i<layers.size()-1;i++) {
			layers.get(i).setNextLayer(layers.get(i+1));
		}
		
		this.nn = layers;
	}
	
	public void printNN(ArrayList<Layer> layers) {
		for(int i = 0;i<layers.size();i++) {
			System.out.println("No " + (i+1)+ " layer neurons count: " + layers.get(i).getNeurons().size());
			for(int j = 0;j<layers.get(i).getNeurons().size();j++) {
				System.out.println("	-neuron d=" + layers.get(i).getNeurons().get(j).getW().size());
			}
		}
	}
	
	public float[] forwardPass(ArrayList<ArrayList<Float>> inputs) {
		float[] inputsX = Points.getX(inputs);
		float[] inputsY = Points.getY(inputs);
		
		for(int i = 0;i<this.nn.size();i++) {
			Layer layer = this.nn.get(i);
			if(i == 0) { // input Layer
				for(int j = 0;j<layer.getNeurons().size();j++) {
					Neuron neuron = layer.getNeurons().get(j);
					if(j == 0) neuron.setInputs(inputsX);
					if(j == 1) neuron.setInputs(inputsY);
					neuron.setOutput(neuron.calcOutput());
				}
			}else {
				Layer prevLayer = this.nn.get(i-1);
				for(int j = 0;j<layer.getNeurons().size();j++) {
					Neuron neuron = layer.getNeurons().get(j);
					neuron.setInputs(prevLayer.getLayerOutputs());
					neuron.setOutput(neuron.calcOutput());
				}
			}
		}
		
		return this.nn.get(this.nn.size()-1).getLayerOutputs();
	}
	
	public ArrayList<ArrayList<Float>> calcCorrect(float[] categories) {
		ArrayList<ArrayList<Float>> t = new ArrayList<>();
		
		for(int i = 0;i<categories.length;i++) {
			ArrayList<Float> tmpList = new ArrayList<>();
			for(int j = 0;j<outputNeurons;j++) {
				if(j == categories[i] - 1) {
					tmpList.add(1f);
				}else {
					tmpList.add(0f);
				}
			}
			t.add(tmpList);
		}
		return t;
	}
	
	public void backpropagation(ArrayList<Float> t) {
		for(int i = 0;i<this.outputNeurons;i++) { // output layer
			this.nn.get(this.nn.size()-1).getNeurons().get(i).calcDelta(t.get(i));
		}
		
		calcHiddenLayersDeltas();
		
	}
	
	public float calcError(float[] x, Float[] correct) {
		float err = 0;
		
		for(int i = 0;i<x.length;i++) {
			err +=  Math.pow(correct[i]-x[i], 2);
		}
		
		return err/2;
	}
	
	public void calcHiddenLayersDeltas() {
		for(int i = 1;i<this.nn.size()-1;i++) {
			Layer layer = this.nn.get(i);
			Layer nextLayer = layer.getNextLayer();
			
			for(int j =  0;j<layer.getNeurons().size();j++) {
				Neuron neuron = layer.getNeurons().get(j);
				float[] weights = new float[nextLayer.getNeurons().size()];
				float[] deltas = nextLayer.getLayerDeltas();
				
				for(int o =  0;o<nextLayer.getNeurons().size();o++) {
					weights[o] = nextLayer.getNeurons().get(o).getW().get(j);
				}
				
				float sum = 0;
				for(int k = 0;k<nextLayer.getNeurons().size();k++) {
					sum += weights[k] + deltas[k];
				}
				
				neuron.setDelta(neuron.gderivative()*sum);
			}
		}
	}
	
	public void start() {
		int t = 0;
		float error = (float) Double.POSITIVE_INFINITY;
		float prevError = 0;
		int N = this.classifiedTrain.length;
		int B = 1;
		int batches = N/B;
		ArrayList<ArrayList<Float>> correct = calcCorrect(this.classifiedTrain);
		
		while(!checkTermination(t, Math.abs(prevError - error))) {
			prevError = error;
			error = 0;
			
			for(int i =  0;i<batches;i++) {
				for(int batch = 0;batch<B;batch++) {
					float[] res = forwardPass(new ArrayList<ArrayList<Float>>(Arrays.asList(train.get(i*B + batch))));
					Float[] resCorrect = new Float[res.length];
					resCorrect = correct.get(i*B + batch).toArray(resCorrect);
					
					error += calcError(res, resCorrect);
					backpropagation(correct.get(i*B + batch));
					updateWeights();
				}
				updateBatchWeights(B);
			}
			System.out.println("MSE (t=" + t + "): "+ Math.abs(prevError - error));
			t += 1;
		}
	}
	
	public void updateWeights() {
		for(int i = 0;i<this.nn.size();i++) {
			Layer layer = this.nn.get(i);
			for(int j = 0;j<layer.getNeurons().size();j++) {
				Neuron neuron = layer.getNeurons().get(j);
				neuron.updateWeights();
			}
		}
	}
	
	public void updateBatchWeights(int B) {
		for(int i = 0;i<this.nn.size();i++) {
			Layer layer = this.nn.get(i);
			for(int j = 0;j<layer.getNeurons().size();j++) {
				Neuron neuron = layer.getNeurons().get(j);
				neuron.updateBatchWeights(B);
			}
		}
	}
	
	public boolean checkTermination(int currentIteration, float error) {
		if(currentIteration > this.terminationLoops || error < this.terminationError) {
			return true;
		}
		
		return false;
	}
	
	public int getTerminationLoops() {
		return this.terminationLoops;
	}

	public void setTerminationLoops(int terminationLoops) {
		this.terminationLoops = terminationLoops;
	}
	
	public float getTerminationError() {
		return this.terminationError;
	}
	
	public void setTerminationError(float terminationError) {
		this.terminationError = terminationError;
	}
	
	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	public int getH1() {
		return H1;
	}

	public void setH1(int h1) {
		H1 = h1;
	}

	public int getH2() {
		return H2;
	}

	public void setH2(int h2) {
		H2 = h2;
	}

	public int getH3() {
		return H3;
	}

	public void setH3(int h3) {
		H3 = h3;
	}

	public String getType() {
		return funcType;
	}

	public void setType(String funcType) {
		this.funcType = funcType;
	}
	
	public void setLearningRate(float learningRate) {
		n = learningRate;
	}

	public float getLearningRate() {
		return n;
	}
	
	public void setOutputNeurons(int outputNeurons) {
		this.outputNeurons = outputNeurons;
	}

	public float getOutputNeurons() {
		return outputNeurons;
	}
	
	public void setNN(ArrayList<Layer> nn) {
		this.nn = nn;
	}

	public ArrayList<Layer> getNN() {
		return nn;
	}

	public ArrayList<ArrayList<Float>> getTrain() {
		return train;
	}

	public void setTrain(ArrayList<ArrayList<Float>> train) {
		this.train = train;
	}

	public ArrayList<ArrayList<Float>> getTest() {
		return test;
	}

	public void setTest(ArrayList<ArrayList<Float>> test) {
		this.test = test;
	}
	
	public ThreeClass getThreeClass() {
		return threeClass;
	}

	public void setThreeClass(ThreeClass threeClass) {
		this.threeClass = threeClass;
	}

	public float[] getClassifiedTrain() {
		return classifiedTrain;
	}

	public void setClassifiedTrain(float[] classifiedTrain) {
		this.classifiedTrain = classifiedTrain;
	}

	public float[] getClassifiedTest() {
		return classifiedTest;
	}

	public void setClassifiedTest(float[] classifiedTest) {
		this.classifiedTest = classifiedTest;
	}
	
	
}