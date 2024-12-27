package controller.functions;

public class Rectified implements ActivationFunction {
	public Rectified() {}
	
	
	@Override
	public float calculate(float u) {
		return Math.max(0, u);
	}
	
	@Override
	public float calculateDerivative(float u) {
		if(u < 0) return 0f;
		if(u > 0) return 1f;
		return (Float) null;
	}
}
