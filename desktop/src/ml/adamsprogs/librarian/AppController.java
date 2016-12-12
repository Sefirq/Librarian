package ml.adamsprogs.librarian;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AppController {
    public interface onCloseButtonListener{
        void onCloseButtonPressed();
    }

    private onCloseButtonListener ocbL = new Main();

    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {
        ocbL.onCloseButtonPressed();
    }
}
