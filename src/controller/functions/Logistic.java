package controller.functions;

public class Logistic implements ActivationFunction {
	public Logistic() {}
	
	@Override
	public float calculate(float u) {
		return (float) (1 / (1 + Math.exp(-u)));
	}
	
	@Override
	public float calculateDerivative(float u) {
		return (float) (calculate(u)*(1-calculate(u)));
	}
	
}
