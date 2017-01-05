package ml.adamsprogs.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PickerController extends FxController {

    private Preferences preferences;
    private String relation;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> pickerList;

    @FXML
    private VBox publisherBox;

    @FXML
    private TextField nip;

    @FXML
    private TextField publisherName;

    @FXML
    private TextField publisherFoundDate;

    @FXML
    private VBox translatorBox;

    @FXML
    private TextField translatorForename;

    @FXML
    private TextField translatorSurname;

    @FXML
    private VBox authorBox;

    @FXML
    private TextField authorForename;

    @FXML
    private TextField authorSurname;

    @FXML
    private TextField authorNationality;

    @FXML
    void onAddToPickerListButtonPressed(ActionEvent event) {

    }

    @FXML
    void onBackButtonPressed(ActionEvent event) {
        returnToPreviousScreen();
    }

    @FXML
    void onChooseAuthorButtonPressed(ActionEvent event) {
        preferences.put("forename", authorForename.getText());
        preferences.put("surname", authorSurname.getText());
        preferences.put("nationality", authorNationality.getText());

        returnToPreviousScreen();
    }

    private void returnToPreviousScreen() {
        String previousScreenPath = preferences.get("callerScreenPath", "");
        String previousScreenTitle = preferences.get("callerScreenTitle", "");

        setScene(previousScreenPath, previousScreenTitle);
    }

    @FXML
    void onChooseTranslatorButtonPressed(ActionEvent event) {
        preferences.put("forename", translatorForename.getText());
        preferences.put("surname", translatorSurname.getText());

        returnToPreviousScreen();
    }

    @FXML
    void onSaveAuthorButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSavePublisherButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSaveTranslatorButtonPressed(ActionEvent event) {

    }

    private void onSearchTextChanged(String searchText) {
        try {
            populateList(searchText);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        publisherFoundDate.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null));

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> onSearchTextChanged(newValue));
        preferences = Preferences.userNodeForPackage(this.getClass());

        String request = preferences.get("callerRequest", "");

        if (request.equals("")) {
            preferences.put("error", "No request received");
        }

        HashMap<String, String> relations = new HashMap<>();
        relations.put("publisher", "INF122446.L_WYDAWCY");
        relations.put("translator", "INF122446.L_TŁUMACZE");
        relations.put("author", "INF122446.L_AUTORZY");
        relation = relations.get(request);


        publisherBox.setVisible(request.equals("publisher"));
        publisherBox.setManaged(request.equals("publisher"));
        translatorBox.setVisible(request.equals("translator"));
        translatorBox.setManaged(request.equals("translator"));
        authorBox.setVisible(request.equals("author"));
        authorBox.setManaged(request.equals("author"));

        try {
            populateList(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateList(@Nullable String filter) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446." + relation);
        String listItem;
        ObservableList<String> listItems = FXCollections.observableArrayList();
        while (resultSet.next()) {
            if (relation.equals("L_WYDAWCY"))
                listItem = resultSet.getString("Nazwa");
            else
                listItem = resultSet.getString("Imie") + " " + resultSet.getString("Nazwisko");
            if (filter == null || listItem.contains(filter))
                listItems.add(listItem);
        }
        pickerList.setItems(listItems);

        resultSet.close();
        statement.close();
    }


}
