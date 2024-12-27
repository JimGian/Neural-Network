package controller.neurons;

import java.util.ArrayList;
import java.util.Random;

import controller.functions.ActivationFunction;
import controller.functions.ActivationFunctionFactory;
import controller.perceptron.Perceptron;

public class Neuron {
	private ArrayList<Float> w;
	private ArrayList<Float> tmpW;
	private float bias;
	private float inputs[];
	private float output;
	private float delta;
	private float deltaSum = 0;
	private ActivationFunction actFunc;
	
	
	public Neuron() {
		
	}
	
	public Neuron(int d, String funcType) {
		Random randomFloat = new Random();
		w = new ArrayList<>();
		int min = -1;
		int max = 1;
		
		for(int i = 0;i<d;i++) {
			w.add(randomFloat.nextFloat() * (max - min) + min);
		}
		this.bias = randomFloat.nextFloat() * (max - min) + min;
		this.actFunc = ActivationFunctionFactory.getActivationFunction(funcType);
	}
	
	public float calcTotalInput() {
		float res = bias;
		for(int i = 0;i<w.size();i++) {
			res += w.get(i)*inputs[i];
		}
		
		return res;
	}
	
	public float calcOutput() {
		return actFunc.calculate(this.calcTotalInput());
	}
	
	public float gderivative() {
		return actFunc.calculateDerivative(this.calcTotalInput());
	}
	
	public void calcDelta(float expected) {  // output Layer
		this.delta = gderivative() * (this.output - expected);
	}
	
	public void updateWeights() {
		this.tmpW = new ArrayList<>();
		this.deltaSum += delta;
		
		for(int i = 0;i<w.size();i++) {
			tmpW.add(Perceptron.n*delta*inputs[i]);
		}
	}
	
	public void updateBatchWeights(int B) {
		ArrayList<Float> newW = new ArrayList<>();
		this.bias += Perceptron.n*((deltaSum)/B);
		
		for(int i = 0;i<tmpW.size();i++) {
			newW.add(tmpW.get(i)/B);
		}
		
		this.tmpW = newW;
		this.deltaSum = 0;
	}

	public ArrayList<Float> getW() {
		return w;
	}

	public void setW(ArrayList<Float> w) {
		this.w = w;
	}

	public float getBias() {
		return bias;
	}

	public void setBias(float bias) {
		this.bias = bias;
	}
	
	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public float[] getInputs() {
		return inputs;
	}

	public void setInputs(float[] inputs) {
		this.inputs = inputs;
	}

	public float getOutput() {
		return output;
	}

	public void setOutput(float output) {
		this.output = output;
	}
	
	public ActivationFunction getActivationFunction() {
		return this.actFunc;
	}
}