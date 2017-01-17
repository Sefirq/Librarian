package ml.adamsprogs.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.prefs.Preferences;

public class ReaderPickerController extends FxController {

    @FXML
    TextField searchBar;

    @FXML
    ListView<String> readerList;
    private Preferences preferences;

    @FXML
    void onChooseReaderButtonPressed(ActionEvent actionEvent) {
        String selectedItem = readerList.getSelectionModel().getSelectedItem();
        String pesel = selectedItem.split("\\(")[1].split("\\)")[0];
        String signature = preferences.get("signature", "");
        try {
            PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO INF122446.L_WYPOŻYCZENIA" +
                    "(CZYTELNICY_PESEL, BIBLIOTEKARZE_ID, KOPIE_SYGNATURA, DATA_WYPOZYCZENIA) " +
                    "VALUES (?, ?, ?, ?)");
            logger.log(Level.INFO, "Lent by " + currentLibrarian);
            statement.setString(1, pesel);
            statement.setInt(2, currentLibrarian);
            statement.setString(3, signature);
            statement.setDate(4, Date.valueOf(LocalDate.now()));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        preferences.put("error", "OK");

        String previousScreenPath = preferences.get("callerScreenPath", "");
        String previousScreenTitle = preferences.get("callerScreenTitle", "");

        setScene(previousScreenPath, previousScreenTitle);
    }

    @FXML
    void initialize() {
        preferences = Preferences.userNodeForPackage(this.getClass());
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> onSearchTextChanged(newValue));
        try {
            populateList(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onSearchTextChanged(String searchText) {
        try {
            populateList(searchText);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateList(@Nullable String filter) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_CZYTELNICY");
        String listItem;
        ObservableList<String> listItems = FXCollections.observableArrayList();
        while (resultSet.next()) {
            listItem = resultSet.getString("Imie") + " " + resultSet.getString("Nazwisko") + " (" +
                    resultSet.getString("Pesel") + ")";
            if (filter == null || listItem.contains(filter))
                listItems.add(listItem);
        }
        readerList.setItems(listItems);

        resultSet.close();
        statement.close();
    }
}
