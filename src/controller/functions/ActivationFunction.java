package controller.functions;

public interface ActivationFunction {
	public float calculate(float u);
	public float calculateDerivative(float u);
}
