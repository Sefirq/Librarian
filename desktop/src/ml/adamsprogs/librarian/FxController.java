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
    static Connection dbConnection;
    protected int currentLibrarian;
    protected String currentBranch;
    private Stage stage;

    Logger logger;

    {
        logger = Logger.getAnonymousLogger();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    void setCurrentLibrarian(int librarian) {
        currentLibrarian = librarian;
        logger.log(Level.INFO, "Setting current Librarian to " + librarian + ". Is " + currentLibrarian);
    }

    void setCurrentBranch(String branch) {
        currentBranch = branch;
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
        controller.setCurrentLibrarian(currentLibrarian);
        controller.setCurrentBranch(currentBranch);

        //tytuł okna
        stage.setTitle(windowTitle);

        stage.setScene(new Scene(root));
        stage.show();
    }
}
