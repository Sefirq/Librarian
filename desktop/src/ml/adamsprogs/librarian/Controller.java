package ml.adamsprogs.librarian;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private TextField proxyAddr;
    @FXML private TextField proxyPort;

    public interface onLoginRequestListener{
        void onLoginRequest(String login, String password, String proxyAddress, String proxyPort);
    }

    private onLoginRequestListener olrL = new Main();

    public void onProxyToggled(ActionEvent actionEvent) {
        proxyAddr.setDisable(!proxyAddr.isDisabled());
        proxyPort.setDisable(!proxyPort.isDisabled());
    }
    public void onLoginPressed(ActionEvent actionEvent) {
        olrL.onLoginRequest(login.getText(), password.getText(), proxyAddr.getText(), proxyPort.getText());
    }
}