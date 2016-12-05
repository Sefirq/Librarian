package ml.adamsprogs.librarian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

//!!!
// Proxy:
// Przed uruchomieniem javy, uruchom w terminalu `ssh -C2qTnN -D 2048 inf...@polluks.cs.put.poznan.pl`, wpisz hasło i zostaw.
//
// W katalogu domowym stwórz plik .librarian i wpisz w nim login i hasło (oddzielone spacją albo enterem) do konta w bazie (to, które wpisujemy na zajęciach).
// Zrobimy potem relacje u jednego i damy pełne uprawnienia drugiemu.
// Może potem zrobimy okno logowania…
//!!!

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //login i hasło do bazy
        File f = new File(System.getProperty("user.home")+"/.librarian");
        Scanner s = new Scanner(f);
        String login = s.next();
        String password = s.next();
        //ustawienie proxy, ponieważ baza jest dostępna tylko z sieci PUT
        System.setProperty("socksProxyHost", "localhost");
        System.setProperty("socksProxyPort", "2048");
        //połączenie z DB
        Connection dbConnection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//admlab2-main.cs.put.poznan.pl:1521/dblab01.cs.put.poznan.pl",
                login, password);
        //ładowanie pliku z UI
        Parent root = FXMLLoader.load(getClass().getResource("ui/helloWorld.fxml"));
        //tytuł okna
        primaryStage.setTitle("Hello World");

        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        dbConnection.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
