package selesdepselesnul.calculator.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController {
	private Stage stage;

	@FXML
	public void onClickOkButton() {
		this.stage.close();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
