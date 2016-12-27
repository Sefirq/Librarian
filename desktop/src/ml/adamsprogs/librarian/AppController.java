package ml.adamsprogs.librarian;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppController extends FxController {

    @FXML
    private Tab branchTab;

    @FXML
    private Button addLibraryButton;

    @FXML
    private Button clearLibrariesButton;

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
    private TextArea branchAddress;

    @FXML
    private TextField branchType;

    @FXML
    private Button saveBranchButton;

    @FXML
    private Tab librarianTab;

    @FXML
    private Button addLibrarianButton;

    @FXML
    private Button clearLibrariansButton;

    @FXML
    private TreeView<?> librarianTree;

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
    void addLibraryButtonPressed(ActionEvent event) {

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
    void onBranchTabSelected(ActionEvent event) {
        TreeItem<String> rootItem = new TreeItem<>("Biblioteki");
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, Nazwa FROM L_BIBLIOTEKI");
            while (resultSet.next()) {
                TreeItem<String> libraryItem = new TreeItem<>(resultSet.getString("Nazwa"));
                Statement subStatement = dbConnection.createStatement();
                ResultSet subResult = subStatement.executeQuery("SELECT Numer, Adres FROM L_Filie WHERE Biblioteki_ID = "
                        + resultSet.getInt("ID"));
                while (subResult.next()) {
                    TreeItem<String> branchItem = new TreeItem<>("Filia nr " + subResult.getInt("Numer") +
                            " (" + subResult.getString("Adres") + ")");
                    libraryItem.getChildren().add(branchItem);
                }
                rootItem.getChildren().add(libraryItem);
            }
            libraryTree.setRoot(rootItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClearLibrariansButtonPressed(ActionEvent event) {

    }

    @FXML
    void onClearLibraryButtonPressed(ActionEvent event) {

    }

    @FXML
    void onLibrarianTabSelected(ActionEvent event) {

    }

    @FXML
    void onLibrarianTreeClicked(MouseEvent event) {

    }

    @FXML
    void onLibraryTreeClicked(MouseEvent event) {

    }

    @FXML
    void onSaveBranchButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSaveLibrarianButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSaveLibraryButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSavePostButtonPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        onBranchTabSelected(null);
    }
}
