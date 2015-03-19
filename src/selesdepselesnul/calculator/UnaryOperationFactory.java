package selesdepselesnul.calculator;

import java.util.function.Function;

public class UnaryOperationFactory {

	public static Function<Double, Double> makeOperation(String operator) {
		switch (operator) {
		case "\u00B1": // +- sign
			return (operand) -> {
				String operandStr = operand + "";
				if (operandStr.contains("-")) {
					operandStr = operandStr.substring(1, operandStr.length());
				} else if (!operandStr.equals("0.0")) {
					operandStr = "-" + operandStr;
				}
				return Double.parseDouble(operandStr);
			};
		case "\u221A": // square root
			return (operand) -> Math.sqrt(operand);
		default:
			break;
		}
		return null;
	}
}
