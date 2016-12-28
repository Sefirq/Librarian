package ml.adamsprogs.librarian;

import com.sun.istack.internal.Nullable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AppController extends FxController {

    @FXML
    private Tab branchTab;

    @FXML
    private Button addLibraryButton;

    @FXML
    private TreeView<String> libraryTree;

    @FXML
    private VBox libraryBox;

    @FXML
    private TextField libraryName;

    @FXML
    private DatePicker libraryFoundDate;

    @FXML
    private TextField libraryType;

    @FXML
    private TextField libraryAddress;

    @FXML
    private TextField libraryBossForename;

    @FXML
    private TextField libraryBossSurname;

    @FXML
    private TextField libraryWebsite;

    @FXML
    private Button saveLibraryButton;

    @FXML
    private Button addBranchButton;

    @FXML
    private VBox branchBox;

    @FXML
    private Text libName;

    @FXML
    private TextField branchNumber;

    @FXML
    private TextField branchAddress;

    @FXML
    private TextField branchType;

    @FXML
    private Button saveBranchButton;

    @FXML
    private Tab librarianTab;

    @FXML
    private Button addLibrarianButton;

    @FXML
    private TreeView<String> librarianTree;

    @FXML
    private VBox librarianBox;

    @FXML
    private TextField librarianForename;

    @FXML
    private TextField librariansSurname;

    @FXML
    private Button saveLibrarianButton;

    @FXML
    private Button addPostButton;

    @FXML
    private VBox positionBox;

    @FXML
    private TextField postFraction;

    @FXML
    private TextField postSalary;

    @FXML
    private Text postLibrary;

    @FXML
    private Text postBranch;

    @FXML
    private Button savePostButton;

    @FXML
    private TableView<?> librarianLends;

    @FXML
    void onAddLibraryButtonPressed(ActionEvent event) {
        TreeItem<String> root = libraryTree.getRoot();
        TreeItem<String> newLibrary = new TreeItem<>("Nowa Biblioteka");
        root.getChildren().add(newLibrary);
        libraryTree.getSelectionModel().select(newLibrary);
        try {
            setLibraryData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        branchBox.setVisible(false);
    }

    @FXML
    void onAddBranchButtonPressed(ActionEvent event) {

    }

    @FXML
    void onAddLibrarianButtonPressed(ActionEvent event) {

    }

    @FXML
    void onAddPostButtonPressed(ActionEvent event) {

    }

    @FXML
    void onBranchTabSelected(Event event) {
        libraryBox.setVisible(false);
        branchBox.setVisible(false);
        TreeItem<String> rootItem = new TreeItem<>("Biblioteki");
        rootItem.setExpanded(true);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, Nazwa FROM INF122446.L_BIBLIOTEKI");
            while (resultSet.next()) {
                TreeItem<String> libraryItem = new TreeItem<>(resultSet.getString("Nazwa"));
                Statement subStatement = dbConnection.createStatement();
                ResultSet subResult = subStatement.executeQuery("SELECT Numer, Adres FROM INF122446.L_FILIE WHERE Biblioteki_ID = "
                        + resultSet.getInt("ID"));
                while (subResult.next()) {
                    TreeItem<String> branchItem = new TreeItem<>("Filia nr " + subResult.getInt("Numer") +
                            " (" + subResult.getString("Adres") + ")");
                    libraryItem.getChildren().add(branchItem);
                }
                subResult.close();
                subStatement.close();
                rootItem.getChildren().add(libraryItem);
            }
            resultSet.close();
            statement.close();
            libraryTree.setRoot(rootItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLibrarianTabSelected(Event event) {

    }

    @FXML
    void onLibrarianTreeClicked(MouseEvent event) {

    }

    @FXML
    void onLibraryTreeClicked(MouseEvent event) {  //todo selected not clicked
        TreeItem<String> root = libraryTree.getRoot();
        ObservableList<TreeItem<String>> libraries = root.getChildren();
        for (TreeItem<String> library : libraries) {
            if (library.getValue().equals("Nowa Biblioteka"))
                libraries.remove(library);
            else {
                ObservableList<TreeItem<String>> branches = library.getChildren();
                for (TreeItem<String> branch : branches) {
                    if (branch.getValue().equals("Nowa Filia"))
                        branches.remove(branch);
                }
            }
        }

        TreeItem<String> selectedItem = libraryTree.getSelectionModel().getSelectedItem();
        String[] label = selectedItem.getValue().split(" ");
        if (label[0].equals("Filia")) {
            int branchNumber = Integer.parseInt(label[2]);
            String branchParentName = selectedItem.getParent().getValue();
            try {
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_BIBLIOTEKI WHERE Nazwa = '"
                        + branchParentName + "'");
                resultSet.next();
                int libraryID = resultSet.getInt("ID");
                setLibraryData(resultSet);
                resultSet.close();
                statement.close();

                statement = dbConnection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM INF122446.L_FILIE WHERE Biblioteki_ID = " + libraryID
                        + " AND Numer = " + branchNumber);
                resultSet.next();
                setBranchData(resultSet);
                resultSet.close();
                statement.close();
                libraryBox.setVisible(true);
                branchBox.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_BIBLIOTEKI WHERE Nazwa = '"
                        + selectedItem.getValue() + "'");
                resultSet.next();
                setLibraryData(resultSet);
                resultSet.close();
                statement.close();
                libraryBox.setVisible(true);
                branchBox.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void setLibraryData(@Nullable ResultSet data) throws SQLException {
        if (data == null) {
            libraryName.setText("");
            libraryFoundDate.setValue(LocalDate.now());
            libraryType.setText("");
            libraryAddress.setText("");
            libraryBossForename.setText("");
            libraryBossSurname.setText("");
            libraryWebsite.setText("");
        } else {
            libraryName.setText(data.getString("Nazwa"));
            Calendar foundDate = new GregorianCalendar();
            foundDate.setTime(data.getDate("Data_założenia"));
            libraryFoundDate.setValue(LocalDate.of(foundDate.get(Calendar.YEAR), foundDate.get(Calendar.MONTH) + 1,
                    foundDate.get(Calendar.DAY_OF_MONTH)));
            libraryType.setText(data.getString("Typ"));
            libraryAddress.setText(data.getString("Adres_biura"));
            libraryBossForename.setText(data.getString("Imie_dyrektora"));
            libraryBossSurname.setText(data.getString("Nazwisko_dyrektora"));
            libraryWebsite.setText(data.getString("Adres_www"));
        }
    }

    private void setBranchData(ResultSet data) throws SQLException {
        branchNumber.setText(data.getString("Numer"));
        branchAddress.setText(data.getString("Adres"));
        branchType.setText(data.getString("Typ"));
    }

    @FXML
    void onSaveBranchButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSaveLibrarianButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSaveLibraryButtonPressed(ActionEvent event) {
        Statement statement;
        PreparedStatement update;
        TreeItem<String> selectedItem = libraryTree.getSelectionModel().getSelectedItem();
        String name =  libraryName.getText();
        LocalDate foundDate = libraryFoundDate.getValue();
        String type = libraryType.getText();
        String address = libraryAddress.getText();
        String bossForename = libraryBossForename.getText();
        String bossSurname = libraryBossSurname.getText();
        String website = libraryWebsite.getText();

        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM inf122446.L_BIBLIOTEKI WHERE Nazwa = '" + name + "'");
            if(resultSet.next()) {
                update = dbConnection.prepareStatement("UPDATE INF122446.L_BIBLIOTEKI SET nazwa = ?, Data_założenia = ?, "
                +"Typ = ?, ADRES_BIURA = ?, Imie_dyrektora = ?, Nazwisko_dyrektora = ?, Adres_www = ? where ID = ?");
                update.setString(1, name);
                update.setDate(2, Date.valueOf(foundDate));
                update.setString(3, type);
                update.setString(4, address);
                update.setString(5, bossForename);
                update.setString(6, bossSurname);
                update.setString(7, website);
                update.setInt(8, resultSet.getInt("ID"));
                update.executeUpdate();
                selectedItem.setValue(name);
                update.close();
            }
            else {
                update = dbConnection.prepareStatement("INSERT INTO INF122446.L_BIBLIOTEKI(Nazwa, Data_założenia, Typ,"
                        + "ADRES_BIURA, Imie_dyrektora, Nazwisko_dyrektora, Adres_www)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                update.setString(1, name);
                update.setDate(2, Date.valueOf(foundDate));
                update.setString(3, type);
                update.setString(4, address);
                update.setString(5, bossForename);
                update.setString(6, bossSurname);
                update.setString(7, website);
                update.executeUpdate();
                selectedItem.setValue(name);
                update.close();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo show user whats wrong
        }
    }

    @FXML
    void onSavePostButtonPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        branchNumber.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*") ? change : null));
    }
}
