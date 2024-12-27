package controller.layers;

import java.util.ArrayList;

import controller.neurons.Neuron;

public class Layer {
	private ArrayList<Neuron> Neurons;
	private Layer nextLayer;
	
	public Layer() {
		Neurons = new ArrayList<>();
	}
	
	public void append(Neuron neuron) {
		Neurons.add(neuron);
	}
	
	public ArrayList<Neuron> getNeurons(){
		return Neurons;
	}
	
	public float[] getLayerOutputs() {
		float[] outputs = new float[Neurons.size()];
		
		for(int i = 0;i<Neurons.size();i++) {
			outputs[i] = Neurons.get(i).getOutput();
		}
		
		return outputs;
	}
	
	public float[] getLayerDeltas() {
		float[] deltas = new float[Neurons.size()];
		
		for(int i = 0;i<Neurons.size();i++) {
			deltas[i] = Neurons.get(i).getDelta();
		}
		
		return deltas;
	}
	
	public void setNextLayer(Layer layer) {
		this.nextLayer = layer;
	}
	
	public Layer getNextLayer() {
		return nextLayer;
	}

}
