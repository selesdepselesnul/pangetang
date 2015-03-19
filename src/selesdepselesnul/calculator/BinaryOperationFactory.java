package selesdepselesnul.calculator;

import java.util.function.BiFunction;

public class BinaryOperationFactory {
	public static BiFunction<Double, Double, Double> makeOperation(
			String operator) {
		switch (operator) {
		case "+":
			return (operand1, operand2) -> operand1 + operand2;
		case "-":
			return (operand1, operand2) -> operand1 - operand2;
		case "*":
			return (operand1, operand2) -> operand1 * operand2;
		case "/":
			return (operand1, operand2) -> operand1 / operand2;
		default:
			return null;
		}
	}

}
