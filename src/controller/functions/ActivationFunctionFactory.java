package controller.functions;

public class ActivationFunctionFactory {
	public static ActivationFunction getActivationFunction(String funcType) {
		switch (funcType) {
			case "logistic":
				return new Logistic();
			case "tanh":
				return new Hyperbolic();
			case "relu":
				return new Rectified();
			default:
				return null;
		}
	}
}
