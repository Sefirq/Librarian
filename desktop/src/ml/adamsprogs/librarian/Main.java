package ml.adamsprogs.librarian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//!!!
// Proxy:
// Przed uruchomieniem javy, uruchom w terminalu `ssh -C2qTnN -D 2048 inf...@polluks.cs.put.poznan.pl`, wpisz hasło i zostaw.
// Wtedy proxy: localhost:2048
//
// Zrobimy potem relacje u jednego i damy pełne uprawnienia drugiemu.
//!!!

public class Main extends Application implements Controller.onLoginRequestListener, AppController.onCloseButtonListener {

    private Logger l;
    private static Connection dbConnection;
    private static Stage stage;
    {
        l = Logger.getAnonymousLogger();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        stage = primaryStage;
        setFxmlScene("ui/login.fxml", "Librarian: Log in");
    }

    private void setFxmlScene(String scenePath, String windowTitle) {
        //ładowanie pliku z UI
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
            l.log(Level.SEVERE, "Cannot load scene " + scenePath);
            return;
        }
        //tytuł okna
        stage.setTitle(windowTitle);

        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }

    @Override
    public void onLoginRequest(String login, String password, String proxyAddress, String proxyPort) {
        //ustawienie proxy, ponieważ baza jest dostępna tylko z sieci PUT
        if(!proxyAddress.equals("") && !proxyPort.equals("")) {
            System.setProperty("socksProxyHost", proxyAddress);
            System.setProperty("socksProxyPort", proxyPort);
        }
        //połączenie z DB
        try {
            dbConnection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2-main.cs.put.poznan.pl:1521/dblab01.cs.put.poznan.pl",
                    login, password);
        } catch (SQLException e) {
            e.printStackTrace();
            l.log(Level.SEVERE, "Cannot login");
        }
        l.log(Level.INFO, "Logged in");
        setFxmlScene("ui/helloWorld.fxml", "Librarian");
    }

    @Override
    public void onCloseButtonPressed() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
