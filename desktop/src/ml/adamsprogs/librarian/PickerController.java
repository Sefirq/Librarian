package ml.adamsprogs.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
        ObservableList<String> items = pickerList.getItems();
        for (String item : items)
            if (item.equals("*Nowy"))
                items.remove(item);
        items.add("*Nowy");
        pickerList.setItems(items);
        pickerList.getSelectionModel().select("*Nowy");
    }

    @FXML
    void onBackButtonPressed(ActionEvent event) {
        returnToPreviousScreen("OK");
    }

    @FXML
    void onChooseAuthorButtonPressed(ActionEvent event) {
        preferences.put("forename", authorForename.getText());
        preferences.put("surname", authorSurname.getText());
        preferences.put("nationality", authorNationality.getText());

        returnToPreviousScreen("OK");
    }

    private void returnToPreviousScreen(@NotNull String result) {
        String previousScreenPath = preferences.get("callerScreenPath", "");
        String previousScreenTitle = preferences.get("callerScreenTitle", "");
        preferences.put("error", result);

        setScene(previousScreenPath, previousScreenTitle);
    }

    @FXML
    void onChooseTranslatorButtonPressed(ActionEvent event) {
        preferences.put("forename", translatorForename.getText());
        preferences.put("surname", translatorSurname.getText());

        returnToPreviousScreen("OK");
    }

    @FXML
    void onSaveAuthorButtonPressed(ActionEvent event) {
        try {
            String selectedItem = pickerList.getSelectionModel().getSelectedItem();
            PreparedStatement update;
            PreparedStatement select = dbConnection.prepareStatement("SELECT ID FROM INF122446.L_AUTORZY " +
                    "WHERE IMIĘ = ? AND NAZWISKO = ?");
            select.setString(1, selectedItem.split(" ")[0]);
            try {
                select.setString(2, selectedItem.split(" ")[1]);
            } catch (IndexOutOfBoundsException e) {
                select.setString(2, "");
            }
            ResultSet result = select.executeQuery();
            if (result.next()) {
                update = dbConnection.prepareStatement("UPDATE INF122446.L_AUTORZY SET IMIĘ = ?, " +
                        "NAZWISKO = ?, NARODOWOŚĆ = ? WHERE ID = ?");
                update.setInt(4, result.getInt("ID"));
            } else {
                update = dbConnection.prepareStatement("INSERT INTO INF122446.L_AUTORZY(IMIĘ, NAZWISKO, NARODOWOŚĆ) " +
                        "VALUES(?, ?, ?)");
            }
            update.setString(1, authorForename.getText());
            update.setString(2, authorSurname.getText());
            update.setString(3, authorNationality.getText());
            update.executeUpdate();
            String newLabel = authorForename.getText() + " " + authorSurname.getText();
            searchBar.setText("");
            pickerList.getSelectionModel().select(newLabel);
            update.close();
            select.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSavePublisherButtonPressed(ActionEvent event) {
        try {
            String selectedItem = pickerList.getSelectionModel().getSelectedItem();
            PreparedStatement update;
            PreparedStatement select = dbConnection.prepareStatement("SELECT VAT_ID FROM INF122446.L_WYDAWCY " +
                    "WHERE NAZWA = ?");
            select.setString(1, selectedItem);
            ResultSet result = select.executeQuery();
            if (result.next()) {
                update = dbConnection.prepareStatement("UPDATE INF122446.L_WYDAWCY SET VAT_ID = ?, NAZWA = ?, " +
                        "ROK_ZALOZENIA = ? WHERE VAT_ID = ?");
                update.setInt(4, result.getInt("VAT_ID"));
            } else {
                update = dbConnection.prepareStatement("INSERT INTO INF122446.L_WYDAWCY(VAT_ID, NAZWA, ROK_ZALOZENIA) " +
                        "VALUES(?, ?, ?)");
            }
            update.setString(1, nip.getText());
            update.setString(2, publisherName.getText());
            update.setString(3, publisherFoundDate.getText());
            update.executeUpdate();
            String newLabel = publisherName.getText();
            searchBar.setText("");
            pickerList.getSelectionModel().select(newLabel);
            update.close();
            select.close();
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSaveTranslatorButtonPressed(ActionEvent event) {
        try {
            String selectedItem = pickerList.getSelectionModel().getSelectedItem();
            PreparedStatement update;
            PreparedStatement select = dbConnection.prepareStatement("SELECT ID FROM INF122446.L_TŁUMACZE " +
                    "WHERE IMIE = ? AND NAZWISKO = ?");
            select.setString(1, selectedItem.split(" ")[0]);
            try {
                select.setString(2, selectedItem.split(" ")[1]);
            } catch(IndexOutOfBoundsException e) {
                select.setString(2, "");
            }
            ResultSet result = select.executeQuery();
            if (result.next()) {
                update = dbConnection.prepareStatement("UPDATE INF122446.L_TŁUMACZE SET ID = ?, IMIE = ?, " +
                        "NAZWISKO = ? WHERE ID = ?");
                update.setInt(3, result.getInt("ID"));
            } else {
                update = dbConnection.prepareStatement("INSERT INTO INF122446.L_TŁUMACZE(IMIE, NAZWISKO) " +
                        "VALUES(?, ?)");
            }
            update.setString(1, translatorForename.getText());
            update.setString(2, translatorSurname.getText());
            update.executeUpdate();
            String newLabel = translatorForename.getText() + " " + translatorSurname.getText();
            searchBar.setText("");
            pickerList.getSelectionModel().select(newLabel);
            update.close();
            select.close();
            result.close();
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

    @FXML
    void initialize() {
        preferences = Preferences.userNodeForPackage(this.getClass());
        String request = preferences.get("callerRequest", "");

        if (request.equals(""))
            returnToPreviousScreen("No request received");

        publisherFoundDate.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null));

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> onSearchTextChanged(newValue));

        pickerList.getSelectionModel()
                .selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> onPickerListSelected(newValue)));

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

    private void onPickerListSelected(String newValue) {
        try {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            switch (relation) {
                case "INF122446.L_WYDAWCY":
                    //noinspection SqlResolve
                    statement = dbConnection.prepareStatement("SELECT * FROM " + relation + " WHERE " +
                            "NAZWA = ?");
                    statement.setString(1, newValue);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        publisherFoundDate.setText(resultSet.getString("ROK_ZALOZENIA"));
                        publisherName.setText(resultSet.getString("NAZWA"));
                    } else {
                        publisherFoundDate.setText("");
                        publisherName.setText("");
                    }
                    break;
                case "INF122446.L_AUTORZY":
                    //noinspection SqlResolve
                    statement = dbConnection.prepareStatement("SELECT * FROM " + relation + " WHERE " +
                            "IMIĘ = ? AND NAZWISKO = ?");
                    statement.setString(1, newValue.split(" ")[0]);
                    try {
                        statement.setString(2, newValue.split(" ")[1]);
                    } catch (IndexOutOfBoundsException e) {
                        statement.setString(2, "");
                    }
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        authorForename.setText(resultSet.getString("IMIĘ"));
                        authorSurname.setText(resultSet.getString("NAZWISKO"));
                        authorNationality.setText(resultSet.getString("NARODOWOŚĆ"));
                    } else {
                        authorForename.setText("");
                        authorSurname.setText("");
                        authorNationality.setText("");
                    }
                    break;
                case "INF122446.L_TŁUMACZE":
                    translatorForename.setText(newValue.split(" ")[0]);
                    try {
                        translatorSurname.setText(newValue.split(" ")[1]);
                    } catch (IndexOutOfBoundsException e) {
                        translatorSurname.setText("");
                    }
                    break;
            }
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateList(@Nullable String filter) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + relation);
        String listItem;
        ObservableList<String> listItems = FXCollections.observableArrayList();
        while (resultSet.next()) {
            switch (relation) {
                case "INF122446.L_WYDAWCY":
                    listItem = resultSet.getString("Nazwa");
                    break;
                case "INF122446.L_TŁUMACZE":
                    listItem = resultSet.getString("Imie") + " " + resultSet.getString("Nazwisko");
                    break;
                case "INF122446.L_AUTORZY":
                    listItem = resultSet.getString("Imię") + " " + resultSet.getString("Nazwisko");
                    break;
                default:
                    listItem = "";
                    break;
            }
            if (filter == null || listItem.contains(filter))
                listItems.add(listItem);
        }
        pickerList.setItems(listItems);

        resultSet.close();
        statement.close();
    }


}
