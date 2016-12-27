package ml.adamsprogs.librarian;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class LoginController extends FxController {

    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField proxyAddr;
    @FXML
    private TextField proxyPort;
    @FXML
    private Text errorText;

    public void onProxyToggled(ActionEvent actionEvent) {
        proxyAddr.setDisable(!proxyAddr.isDisabled());
        proxyPort.setDisable(!proxyPort.isDisabled());
    }

    public void onLoginPressed(ActionEvent actionEvent) {
        try {
            //ustawienie proxy, ponieważ baza jest dostępna tylko z sieci PUT
            if (!proxyAddr.getText().equals("") && !proxyPort.getText().equals("")) {
                System.setProperty("socksProxyHost", proxyAddr.getText());
                System.setProperty("socksProxyPort", proxyPort.getText());
            }
            //połączenie z DB
            dbConnection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2-main.cs.put.poznan.pl:1521/dblab01.cs.put.poznan.pl",
                    login.getText(), password.getText());
            logger.log(Level.INFO, "Logged in");
            setScene("ui/app.fxml", "Librarian");
        } catch (SQLException e) {
            errorText.setText(e.getMessage());
        }
    }
}