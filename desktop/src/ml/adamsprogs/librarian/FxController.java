package ml.adamsprogs.librarian;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

class FxController {
    Connection dbConnection;
    private Stage stage;
    static FxController currentController;

    Logger logger;

    {
        logger = Logger.getAnonymousLogger();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    void setScene(String scenePath, String windowTitle) {
        //ładowanie pliku z UI
        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource(scenePath));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Cannot load scene " + scenePath);
            return;
        }

        FxController controller = loader.getController();
        controller.setStage(stage);
        controller.setConnection(dbConnection);
        currentController = controller;

        //tytuł okna
        stage.setTitle(windowTitle);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
