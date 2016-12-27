package ml.adamsprogs.librarian;

import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

//!!!
// Proxy:
// Przed uruchomieniem javy, uruchom w terminalu `ssh -C2qTnN -D 2048 inf...@polluks.cs.put.poznan.pl`, wpisz hasło i zostaw.
// Wtedy proxy: localhost:2048
//!!!

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FxController controller = new FxController();
        controller.setStage(primaryStage);
        controller.setScene("ui/login.fxml", "Librarian: Log in");
    }

    @Override
    public void stop() {
        try {
            FxController.currentController.dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
