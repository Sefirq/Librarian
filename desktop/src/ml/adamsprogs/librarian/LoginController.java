package ml.adamsprogs.librarian;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class LoginController {

    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private TextField proxyAddr;
    @FXML private TextField proxyPort;
    @FXML private Text errorText;

    public interface onLoginRequestListener{
        void onLoginRequest(String login, String password, String proxyAddress, String proxyPort) throws SQLException;
    }

    private onLoginRequestListener olrL = new Main();

    public void onProxyToggled(ActionEvent actionEvent) {
        proxyAddr.setDisable(!proxyAddr.isDisabled());
        proxyPort.setDisable(!proxyPort.isDisabled());
    }
    public void onLoginPressed(ActionEvent actionEvent) {
        try {
            olrL.onLoginRequest(login.getText(), password.getText(), proxyAddr.getText(), proxyPort.getText());
        } catch (SQLException e) {
            errorText.setText(e.getMessage());
        }
    }
}