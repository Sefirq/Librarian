package ml.adamsprogs.librarian;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.SQLException;

public class AppController extends FxController{

    @FXML
    public void onButtonPressed(ActionEvent actionEvent) {
        try {
            dbConnection.close();
            Platform.exit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
