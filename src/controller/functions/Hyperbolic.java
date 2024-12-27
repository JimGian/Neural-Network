package controller.functions;

public class Hyperbolic implements ActivationFunction {
	public Hyperbolic() {}
	
	@Override
	public float calculate(float u) {
		return (float) Math.tanh(u);
	}
	
	@Override
	public float calculateDerivative(float u) {
		return (float) (1 - Math.pow(calculate(u), 2));
	}
	
}
