package ml.adamsprogs.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class LoginController extends FxController {

    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private TextField librarianID;
    @FXML
    private CheckBox proxyToggler;
    @FXML
    private TextField proxyAddr;
    @FXML
    private TextField proxyPort;
    @FXML
    private Button loginButton;
    @FXML
    private Text errorText;
    @FXML
    private ChoiceBox<String> branch;
    @FXML
    private Button enterButton;

    @FXML
    void onProxyToggled(ActionEvent actionEvent) {
        proxyAddr.setDisable(!proxyAddr.isDisabled());
        proxyPort.setDisable(!proxyPort.isDisabled());
        proxyAddr.setText(proxyAddr.isDisabled() ? "" : "localhost");
        proxyPort.setText(proxyAddr.isDisabled() ? "" : "2048");
    }

    @FXML
    void onLoginPressed(ActionEvent actionEvent) {
        errorText.setText("Logowanie. Proszę czekać…");
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

            Preferences preferences = Preferences.userNodeForPackage(this.getClass());
            try {
                preferences.clear();
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }

            if (librarianID.getText().equals("")) {
                currentLibrarian = 0;
                currentBranch = "0:0";
                logger.log(Level.INFO, "Entered as guest");

                setScene("ui/app.fxml", "Librarian");
            } else {

                currentLibrarian = Integer.parseInt(librarianID.getText());

                branch.setDisable(false);
                enterButton.setDisable(false);

                populateBranches();

                login.setDisable(true);
                password.setDisable(true);
                librarianID.setDisable(true);
                proxyToggler.setDisable(true);
                proxyAddr.setDisable(true);
                proxyPort.setDisable(true);
                loginButton.setDisable(true);
                errorText.setText("");
            }

        } catch (SQLException e) {
            errorText.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    private void populateBranches() throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT NAZWA, NUMER, '(' || L_FILIE.ADRES || ')'" +
                "AS ADRES_FILII FROM INF122446.L_FILIE JOIN INF122446.L_BIBLIOTEKI ON(INF122446.L_FILIE.BIBLIOTEKI_ID = ID)" +
                "JOIN INF122446.L_ETATY ON(INF122446.L_ETATY.BIBLIOTEKI_ID = ID AND INF122446.L_ETATY.FILIE_NUMER = " +
                "NUMER) JOIN INF122446.L_BIBLIOTEKARZE ON(BIBLIOTEKARZE_ID = INF122446.L_BIBLIOTEKARZE.ID) " +
                "WHERE BIBLIOTEKARZE_ID = " + currentLibrarian);
        ObservableList<String> items = FXCollections.observableArrayList();
        while (resultSet.next()) {
            items.add(resultSet.getString("Nazwa") + ", filia nr " + resultSet.getInt("Numer") + " " +
                    resultSet.getString("Adres_Filii"));
        }
        branch.setItems(items);
    }

    @FXML
    void onEnterButtonPressed(ActionEvent actionEvent) {
        String[] splitted = branch.getValue().split(",");
        String libraryPart = splitted[0];
        String branchNumber = splitted[1].split("\\(")[0].replaceAll("[a-zA-Z ]", "");

        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM INF122446.L_BIBLIOTEKI WHERE NAZWA = '" +
                    libraryPart + "'");
            resultSet.next();
            currentBranch = resultSet.getString("ID") + ":" + branchNumber;
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "Entered as " + currentLibrarian + " into " + currentBranch);

        setScene("ui/app.fxml", "Librarian");
    }
}