package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by willi on 10/6/2016.
 */
public class SubmitReportController {
    @FXML
    private Button submitBtn;
    @FXML
    private Button CancelBtn;

    public void submit() {

    }

    public void cancel() {
        ((Stage)submitBtn.getScene().getWindow()).close();
    }
}
