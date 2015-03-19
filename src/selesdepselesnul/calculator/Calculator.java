package selesdepselesnul.calculator;

import java.util.function.BiFunction;

public class Calculator {

	private BiFunction<Double, Double, Double> operation;
	private double result;

	public Calculator(double result) {
		this.result = result;
	}

	public Calculator() {
		this.result = 0;
		this.operation = null;
	}

	public BiFunction<Double, Double, Double> getOperation() {
		return operation;
	}

	public void setOperation(BiFunction<Double, Double, Double> operation) {
		this.operation = operation;
	}

	public double calculate(double operand) {
		this.result = operation.apply(this.result, operand);
		return this.result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public double getResult() {
		return this.result;
	}

}
