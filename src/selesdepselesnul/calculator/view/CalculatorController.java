package selesdepselesnul.calculator.view;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

import selesdepselesnul.calculator.UnaryOperationFactory;
import selesdepselesnul.calculator.Calculator;
import selesdepselesnul.calculator.BinaryOperationFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CalculatorController {

	@FXML
	TextField calculatorTextField;

	@FXML
	boolean isFirst = true;
	private Calculator calculator = new Calculator();
	private String operand = "";
	private boolean isFirstPeriod = true;

	@FXML
	public void onClickNumberButton(ActionEvent e) {
		Button numberButton = (Button) e.getSource();
		if (!numberButton.getText().equals(".")) {
			this.operand += numberButton.getText();
			this.calculatorTextField.setText(this.operand);
		} else if (this.isFirstPeriod) {
			this.operand += numberButton.getText();
			this.calculatorTextField.setText(this.operand);
			this.isFirstPeriod = false;
		}

	}

	@FXML
	public void onClickBinaryOperator(ActionEvent e) {
		if (this.isFirst && this.calculator.getResult() == 0) {
			this.calculator.setResult(Double.parseDouble(this.operand));
			this.isFirst = false;
		}
		Button operatorButton = (Button) e.getSource();
		BiFunction<Double, Double, Double> operation = BinaryOperationFactory
				.makeOperation(operatorButton.getText());
		registerOperation(operation);
		this.isFirstPeriod = true;

	}

	public void registerOperation(BiFunction<Double, Double, Double> operator) {
		if (this.calculator.getResult() != 0) {
			this.calculator.setOperation(operator);
			this.operand = "";
		}

	}

	@FXML
	public void onClickEqualSign() {
		if (this.calculator.getResult() != 0) {
			String stringResult = this.calculator.calculate(Double
					.parseDouble(this.operand)) + "";
			setTextWithFormating(stringResult);
		}
	}

	private void setTextWithFormating(String stringResult) {
		final String INTEGER_NUMBER = ".*\\.[0]$";

		if (stringResult.length() > 15) {
			this.calculatorTextField.setAlignment(Pos.CENTER_LEFT);
		} else if (stringResult.matches(INTEGER_NUMBER)) {
			stringResult = deletePeriod(stringResult);
		}
		this.calculatorTextField.setText(stringResult);
	}

	private String deletePeriod(String stringResult) {
		int dotIndex = stringResult.indexOf('.');
		stringResult = stringResult.substring(0, dotIndex);
		return stringResult;
	}

	@FXML
	public void onClickBackSpaceButton() {
		if (this.operand.length() == 0) {
			clearToZero();
		} else {
			this.operand = deleteLastChar(this.operand);
			this.calculatorTextField.setText(this.operand);
		}
	}

	private void clearToZero() {
		this.calculatorTextField.setText("0");
		this.calculatorTextField.setAlignment(Pos.CENTER_RIGHT);
	}

	private String deleteLastChar(String resultString) {
		return resultString.substring(0, this.operand.length() - 1);
	}

	@FXML
	public void onClickCE() {
		this.isFirst = true;
		this.operand = "";
		this.isFirstPeriod = true;
		clearToZero();
	}

	@FXML
	public void onClickC() {
		onClickCE();
		this.calculator = new Calculator();
	}

	@FXML
	public void onClickUnaryOperator(ActionEvent e) {
		Button operatorButton = (Button) e.getSource();
		Function<Double, Double> operation = UnaryOperationFactory
				.makeOperation(operatorButton.getText());
		if (this.isFirst && this.calculator.getResult() == 0) {
			this.calculator.setResult(operation.apply(Double
					.parseDouble(this.calculatorTextField.getText())));
		} else {
			this.calculator.setResult(operation.apply(this.calculator
					.getResult()));
		}
		setTextWithFormating(this.calculator.getResult() + "");

	}

	@FXML
	public void onClickPercentButton() {
		if (!this.isFirst) {
			double percentNumber = this.calculator.getResult()
					- (Double.parseDouble(this.calculatorTextField.getText()) / 100);
			setTextWithFormating(this.calculator.calculate(percentNumber) + "");
		}
	}

	@FXML
	public void onClickMC() {
		this.calculator = new Calculator();
	}

	@FXML
	public void onClickMS() {
		this.calculator.setResult(Double.parseDouble(this.calculatorTextField
				.getText()));
	}

	@FXML
	public void onClickMR() {
		setTextWithFormating(this.calculator.getResult() + "");
	}

	private void appendCalculatorMemory(String operator) {

		this.calculator.setOperation(BinaryOperationFactory
				.makeOperation(operator));
		this.calculator.calculate(Double.parseDouble(this.calculatorTextField
				.getText()));
		this.calculator.setOperation(null);

	}

	@FXML
	public void onClickMPlus() {
		appendCalculatorMemory("+");

	}

	@FXML
	public void onClickMMinus() {
		appendCalculatorMemory("-");
	}

	@FXML
	public void onClickClose() {
		Platform.exit();
	}

	@FXML
	public void onClickAbout() {
		Stage aboutStage = new Stage();
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource(
					"About.fxml"));
			AnchorPane root = (AnchorPane) fxml.load();
			AboutController aboutController = (AboutController) fxml
					.getController();
			aboutController.setStage(aboutStage);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(
					getClass().getResource("About.css").toExternalForm());
			aboutStage.setResizable(false);
			aboutStage.setTitle("About Pangetang");
			aboutStage.setScene(scene);
			aboutStage.getIcons().add(
					new Image(getClass().getResourceAsStream("images.jpg")));
			aboutStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
