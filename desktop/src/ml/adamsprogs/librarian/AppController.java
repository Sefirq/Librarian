package ml.adamsprogs.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;


public class AppController extends FxController {

    //todo feedback
    //todo all statements to prepared
    private List<String> posts = new ArrayList<>();
    private int index;
    private int selectedLibrarianID;
    private ObservableList<Lend> lendsData = FXCollections.observableArrayList();
    private ObservableList<Lend> borrowsData = FXCollections.observableArrayList();
    private ObservableList<Reader> readersData = FXCollections.observableArrayList();
    private ObservableList<Reader> filteredData = FXCollections.observableArrayList();
    private Preferences preferences;

    @FXML
    private TabPane tabs;

    /*
    ====================================================================================================================
    Library/Branch screen
    */

    @FXML
    private TreeView<String> libraryTree;

    @FXML
    private CheckBox editLibraryToggle;

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
    private TextField searchLibrariesBar;

    @FXML
    private TextField libraryBossForename;

    @FXML
    private TextField libraryBossSurname;

    @FXML
    private TextField libraryWebsite;

    @FXML
    private VBox branchBox;

    @FXML
    private TextField branchNumber;

    @FXML
    private TextField branchAddress;

    @FXML
    private TextField branchType;

    @FXML
    private Button saveLibraryButton;

    @FXML
    private Button saveBranchButton;

    /*
    ====================================================================================================================
    Librarian/Post screen
    */

    @FXML
    private TreeView<String> librarianTree;

    @FXML
    private VBox librarianBox;

    @FXML
    private TextField librarianForename;

    @FXML
    private TextField librarianSurname;

    @FXML
    private VBox postBox;

    @FXML
    private TextField postFraction;

    @FXML
    private TextField postSalary;

    @FXML
    private TextField searchLibrarianBar;

    @FXML
    private ChoiceBox<String> postLibraryChoiceBox;

    @FXML
    private ChoiceBox<String> postBranchChoiceBox;

    @FXML
    private VBox librarianLendBox;

    @FXML
    private TableView<Lend> librarianLends;

    @FXML
    private TableColumn<Lend, String> librarianLendTitle;

    @FXML
    private TableColumn<Lend, String> librarianLendAuthor;

    @FXML
    private TableColumn<Lend, String> librarianLendReader;

    @FXML
    private TableColumn<Lend, String> librarianLendSince;

    @FXML
    private TableColumn<Lend, String> librarianLendTill;

    @FXML
    private TableColumn<Lend, String> librarianLendLibrary;

    @FXML
    private TableColumn<Lend, String> librarianLendBranch;

    @FXML
    private TableColumn<Lend, String> librarianLendSignature;

    @FXML
    private TableColumn<Lend, String> librarianLendISBN;

    @FXML
    private Button nextPostButton;

    @FXML
    private Button previousPostButton;

    @FXML
    private Button addPostButton;

    /*
    ====================================================================================================================
    Reader screen
    */

    @FXML

    private Button saveReaderButton;
    @FXML
    private CheckBox editReaderToggle;

    @FXML
    private TableView<Reader> readersTable;

    @FXML
    private TableColumn<Reader, String> readerForenameColumn;

    @FXML
    private TableColumn<Reader, String> readerSurnameColumn;

    @FXML
    private TableColumn<Reader, String> readerPeselColumn;

    @FXML
    private VBox readerBox;

    @FXML
    private TextField readerPESEL;

    @FXML
    private TextField readerForename;

    @FXML
    private TextField readerSurname;

    @FXML
    private TextField searchBar;

    @FXML
    private VBox readerBorrowBox;

    @FXML
    private TableView<Lend> readerBorrowsTable;

    @FXML
    private TableColumn<Lend, String> readerBorrowTitle;

    @FXML
    private TableColumn<Lend, String> readerBorrowAuthor;

    @FXML
    private TableColumn<Lend, String> readerBorrowSince;

    @FXML
    private TableColumn<Lend, String> readerBorrowTill;

    @FXML
    private TableColumn<Lend, String> readerBorrowLibrary;

    @FXML
    private TableColumn<Lend, String> readerBorrowBranch;

    @FXML
    private TableColumn<Lend, String> readerBorrowSignature;

    @FXML
    private TableColumn<Lend, String> readerBorrowISBN;

    @FXML
    private TableColumn<Lend, String> readerBorrowLibrarian;

    /*
    ====================================================================================================================
    Books screen
    */

    @FXML
    private Tab bookTab;

    @FXML
    private CheckBox bookEditToggle;

    @FXML
    private TextField bookTreeSearchBar;

    @FXML
    private TreeView<String> bookTree;

    @FXML
    private VBox bookBox;

    @FXML
    private TextField bookTitle;

    @FXML
    private TextField bookFinishDate;

    @FXML
    private TextField bookLanguage;

    @FXML
    private TextField bookGenre;

    @FXML
    private TextField bookStream;

    @FXML
    private TableView<Writer> bookAuthorshipTable;

    @FXML
    private TableColumn<Writer, String> authorshipForenameColumn;

    @FXML
    private TableColumn<Writer, String> authorshipSurnameColumn;

    @FXML
    private TableColumn<Writer, String> authorshipNationalityColumn;

    @FXML
    private VBox editionBox;

    @FXML
    private TextField editionIsbn;

    @FXML
    private TextField editionTitle;

    @FXML
    private TextField editionNumber;

    @FXML
    private TextField editionReleaseDate;

    @FXML
    private TextField editionLanguage;

    @FXML
    private ChoiceBox<String> editionPublisher;

    @FXML
    private TableView<Writer> editionTranslationTable;

    @FXML
    private TableColumn<Writer, String> translationForenameColumn;

    @FXML
    private TableColumn<Writer, String> translationSurnameColumn;

    @FXML
    private VBox copyBox;

    @FXML
    private TextField copySignature;

    @FXML
    private ChoiceBox<String> copyLibrary;

    @FXML
    private ChoiceBox<String> copyBranch;

    @FXML
    private TableView<Material> copyMaterialTable;

    @FXML
    private TableColumn<Material, String> copyMaterialTypeColumn;

    @FXML
    private TableColumn<Material, String> copyMaterialDescriptionColumn;

    @FXML
    private TextField bookBorrowTableSearchBar;

    @FXML
    private VBox bookBorrowBox;

    @FXML
    private TableView<Lend> bookBorrowsTable;

    @FXML
    private TableColumn<Lend, String> bookBorrowReader;

    @FXML
    private TableColumn<Lend, String> bookBorrowSince;

    @FXML
    private TableColumn<Lend, String> bookBorrowTill;

    @FXML
    private TableColumn<Lend, String> bookBorrowSignature;

    @FXML
    private TableColumn<Lend, String> bookBorrowISBN;

    @FXML
    private TableColumn<Lend, String> bookBorrowLibrarian;

    @FXML
    private Button saveBookButton;

    @FXML
    private Button saveEditionButton;

    @FXML
    private Button saveCopyButton;

    @FXML
    private Button lendButton;

    @FXML
    private Button returnButton;

    /*
    ====================================================================================================================
    ====================================================================================================================
    Library/Branch screen
    */

    private void makeLibraryAndBranchTextBoxesDisabledOrEnabled(boolean value) {
        libraryName.setDisable(value);
        libraryFoundDate.setDisable(value);
        libraryType.setDisable(value);
        libraryAddress.setDisable(value);
        libraryBossForename.setDisable(value);
        libraryBossSurname.setDisable(value);
        libraryWebsite.setDisable(value);
        branchAddress.setDisable(value);
        branchNumber.setDisable(value);
        branchType.setDisable(value);
    }

    private TreeItem<String> updateFilteredTree(TreeItem<String> oldRoot, String filterText) {
        if (filterText == null || filterText.isEmpty())
            return oldRoot;
        TreeItem<String> newRoot = new TreeItem<>("Biblioteki");
        for (TreeItem<String> child : oldRoot.getChildren()) {
            TreeItem<String> newChild = new TreeItem<>(child.getValue());
            boolean ifToBeAdded = false;
            for (TreeItem<String> grandchild : child.getChildren()) {
                if (grandchild.getValue().contains(filterText)) {
                    newChild.getChildren().add(grandchild);
                    ifToBeAdded = true;
                }
            }
            if (child.getValue().contains(filterText)) {
                System.out.println(filterText + " " + child.getValue());
                newRoot.getChildren().add(child);
            }
            if (ifToBeAdded) {
                newRoot.getChildren().add(newChild);
            }
        }
        return newRoot;
    }

    @FXML
    void onBranchTabSelected(Event event) {
        clearTree(libraryTree);
        makeLibraryAndBranchTextBoxesDisabledOrEnabled(true);
        editLibraryToggle.setDisable(true);
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
                    TreeItem<String> branchItem = new TreeItem<>(composeBranchItemName(subResult.getInt("Numer"), subResult.getString("Adres")));
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
        searchLibrariesBar.textProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<String> newRoot = updateFilteredTree(rootItem, searchLibrariesBar.getText());
            newRoot.setExpanded(true);
            for (TreeItem<String> child : newRoot.getChildren()) {
                child.setExpanded(true);
            }
            libraryTree.setRoot(newRoot);

        });
    }

    private void clearTree(@NotNull TreeView<String> tree) {
        tree.setRoot(null);
    }

    @NotNull
    private String composeBranchItemName(int number, String address) {
        return "Filia nr " + number + " (" + address + ")";
    }

    @FXML
    void onAddLibraryButtonPressed(ActionEvent event) {
        TreeItem<String> root = libraryTree.getRoot();
        TreeItem<String> newLibrary = new TreeItem<>("*Nowa Biblioteka");
        makeLibraryAndBranchTextBoxesDisabledOrEnabled(false);
        root.getChildren().add(newLibrary);
        libraryTree.getSelectionModel().select(newLibrary);
        try {
            setLibraryData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        branchBox.setVisible(false);
        libraryBox.setVisible(true);
    }

    private void setLibraryData(@Nullable ResultSet data) throws SQLException {
        libraryName.setText(data != null ? data.getString("Nazwa") : "");
        Calendar foundDate = new GregorianCalendar();
        foundDate.setTime(data != null ? data.getDate("Data_założenia") : GregorianCalendar.getInstance().getTime());
        libraryFoundDate.setValue(LocalDate.of(foundDate.get(Calendar.YEAR), foundDate.get(Calendar.MONTH) + 1,
                foundDate.get(Calendar.DAY_OF_MONTH)));
        libraryType.setText(data != null ? data.getString("Typ") : "");
        libraryAddress.setText(data != null ? data.getString("Adres_biura") : "");
        libraryBossForename.setText(data != null ? data.getString("Imie_dyrektora") : "");
        libraryBossSurname.setText(data != null ? data.getString("Nazwisko_dyrektora") : "");
        libraryWebsite.setText(data != null ? data.getString("Adres_www") : "");
    }

    private void onLibraryTreeItemSelected(@Nullable TreeItem<String> selectedItem) {
        if (selectedItem == null || selectedItem.getValue() == null || selectedItem.getValue().charAt(0) == '*')
            return;
        editLibraryToggle.setDisable(false);
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

        removeEmptyItems(libraryTree);
    }

    private void setBranchData(@Nullable ResultSet data) throws SQLException {
        branchNumber.setText(data != null ? data.getString("Numer") : "");
        branchAddress.setText(data != null ? data.getString("Adres") : "");
        branchType.setText(data != null ? data.getString("Typ") : "");
    }

    private void removeEmptyItems(TreeView<String> tree) {
        TreeItem<String> root = tree.getRoot();
        if (root == null)
            return;
        ObservableList<TreeItem<String>> firstLevel = root.getChildren();
        Iterator<TreeItem<String>> firstLevelIterator = firstLevel.iterator();
        while (firstLevelIterator.hasNext()) {
            TreeItem<String> firstLevelItem = firstLevelIterator.next();
            if (firstLevelItem.getValue().charAt(0) == '*' &&
                    !tree.getSelectionModel().getSelectedItem().equals(firstLevelItem))
                firstLevelIterator.remove();
            else {
                ObservableList<TreeItem<String>> secondLevel = firstLevelItem.getChildren();
                Iterator<TreeItem<String>> secondLevelIterator = secondLevel.iterator();
                while (secondLevelIterator.hasNext()) {
                    TreeItem<String> secondLevelItem = secondLevelIterator.next();
                    if (secondLevelItem.getValue().charAt(0) == '*' &&
                            !(tree.getSelectionModel().getSelectedItem().equals(secondLevelItem) ||
                                    tree.getSelectionModel().getSelectedItem().equals(secondLevelItem.getParent())))
                        secondLevelIterator.remove();
                    else {
                        ObservableList<TreeItem<String>> thirdLevel = secondLevelItem.getChildren();
                        thirdLevel.removeIf(thirdLevelItem -> thirdLevelItem.getValue().charAt(0) == '*' &&
                                !(tree.getSelectionModel().getSelectedItem().equals(thirdLevelItem) ||
                                        tree.getSelectionModel().getSelectedItem().equals(thirdLevelItem.getParent()) ||
                                        tree.getSelectionModel().getSelectedItem().equals(thirdLevelItem.getParent().getParent())));
                    }
                }
            }
        }
    }

    @FXML
    void onSaveLibraryButtonPressed(ActionEvent event) {
        Statement statement;
        PreparedStatement update;
        String oldName = getLibraryNameFromSelectedItem(libraryTree);
        String name = libraryName.getText();
        LocalDate foundDate = libraryFoundDate.getValue();
        String type = libraryType.getText();
        String address = libraryAddress.getText();
        String bossForename = libraryBossForename.getText();
        String bossSurname = libraryBossSurname.getText();
        String website = libraryWebsite.getText();

        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM inf122446.L_BIBLIOTEKI "
                    + "WHERE Nazwa = '" + oldName + "'");
            if (resultSet.next()) {
                update = dbConnection.prepareStatement("UPDATE INF122446.L_BIBLIOTEKI SET nazwa = ?, "
                        + "Data_założenia = ?, Typ = ?, ADRES_BIURA = ?, Imie_dyrektora = ?, Nazwisko_dyrektora = ?, "
                        + "Adres_www = ? WHERE ID = ?");
                update = insertIntoUpdate(update, name, foundDate, type, address, bossForename, bossSurname, website);
                update.setInt(8, resultSet.getInt("ID"));
                executeRefreshSelect(update, name);
            } else {
                update = dbConnection.prepareStatement("INSERT INTO INF122446.L_BIBLIOTEKI(Nazwa, Data_założenia,"
                        + "Typ, ADRES_BIURA, Imie_dyrektora, Nazwisko_dyrektora, Adres_www)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)");
                update = insertIntoUpdate(update, name, foundDate, type, address, bossForename, bossSurname, website);
                executeRefreshSelect(update, name);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo show user what’s wrong
        }
    }

    private String getLibraryNameFromSelectedItem(@NotNull TreeView<String> tree) {
        TreeItem<String> selectedItem = tree.getSelectionModel().getSelectedItem();
        if (selectedItem.getValue().split(" ")[0].equals("Filia"))
            selectedItem = selectedItem.getParent();
        return selectedItem.getValue();
    }

    private PreparedStatement insertIntoUpdate(PreparedStatement update, String name, LocalDate foundDate, String type,
                                               String address, String bossForename, String bossSurname, String website)
            throws SQLException {
        update.setString(1, name);
        update.setDate(2, java.sql.Date.valueOf(foundDate));
        update.setString(3, type);
        update.setString(4, address);
        update.setString(5, bossForename);
        update.setString(6, bossSurname);
        update.setString(7, website);

        return update;
    }

    private void executeRefreshSelect(PreparedStatement update, String selectionName) throws SQLException {
        update.executeUpdate();
        onBranchTabSelected(null);
        libraryTree.getSelectionModel().select(findItemByName(libraryTree, selectionName));
        update.close();
    }

    @Nullable
    private TreeItem<String> findItemByName(@NotNull TreeView<String> tree, @NotNull String name) {
        ObservableList<TreeItem<String>> firstLevel = tree.getRoot().getChildren();
        for (TreeItem<String> firstLevelItem : firstLevel) {
            if (firstLevelItem.getValue().equals(name))
                return firstLevelItem;
            ObservableList<TreeItem<String>> secondLevel = firstLevelItem.getChildren();
            for (TreeItem<String> secondLevelItem : secondLevel) {
                if (secondLevelItem.getValue().equals(name))
                    return secondLevelItem;
                ObservableList<TreeItem<String>> thirdLevel = secondLevelItem.getChildren();
                for (TreeItem<String> thirdLevelItem : thirdLevel) {
                    if (thirdLevelItem.getValue().equals(name))
                        return thirdLevelItem;
                }
            }
        }
        return null;
    }

    @FXML
    void onAddBranchButtonPressed(ActionEvent event) {
        String libName = getLibraryNameFromSelectedItem(libraryTree);
        if (libName.equals("*Nowa Biblioteka")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dodać filii do tej biblioteki!",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        TreeItem<String> library = findItemByName(libraryTree, libName);
        if (library == null)
            return;
        TreeItem<String> newBranch = new TreeItem<>("*Nowa Filia");
        library.getChildren().add(newBranch);
        libraryTree.getSelectionModel().select(newBranch);
        try {
            setBranchData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        branchBox.setVisible(true);
    }

    @FXML
    void onSaveBranchButtonPressed(ActionEvent event) {
        Statement statement;
        PreparedStatement update;
        String libname = libraryName.getText();
        int oldNumber;
        try {
            oldNumber = Integer.parseInt(libraryTree.getSelectionModel().getSelectedItem().getValue().split(" ")[2]);
        } catch (@NotNull IndexOutOfBoundsException | NumberFormatException e) {
            oldNumber = -1;
        }
        int number = Integer.parseInt(branchNumber.getText());
        String address = branchAddress.getText();
        String type = branchType.getText();

        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM INF122446.L_FILIE JOIN INF122446.L_BIBLIOTEKI "
                    + "ON(BIBLIOTEKI_ID = ID) WHERE NUMER = " + oldNumber + " AND NAZWA = '" + libname + "'");
            if (editLibraryToggle.isSelected()) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt("ID");
                    statement.close();
                    update = dbConnection.prepareStatement("UPDATE INF122446.L_FILIE SET NUMER = ?, ADRES = ?, TYP = ?"
                            + " WHERE BIBLIOTEKI_ID = ? AND NUMER = ?");
                    update.setInt(1, number);
                    update.setString(2, address);
                    update.setString(3, type);
                    update.setInt(4, ID);
                    update.setInt(5, oldNumber);
                    update.executeUpdate();
                    refresh(update, number, address);
                }
            } else { //if not in edit mode
                statement.close();
                statement = dbConnection.createStatement();
                ResultSet subResult = statement.executeQuery("SELECT ID FROM INF122446.L_BIBLIOTEKI WHERE NAZWA = '"
                        + libname + "'");
                subResult.next();
                update = dbConnection.prepareStatement("INSERT INTO INF122446.L_FILIE(NUMER, ADRES, TYP, BIBLIOTEKI_ID)"
                        + "VALUES(?, ?, ?, ?)");
                update.setInt(1, number);
                update.setString(2, address);
                update.setString(3, type);
                update.setInt(4, subResult.getInt("ID"));
                update.executeUpdate();
                subResult.close();
                statement.close();
                refresh(update, number, address);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo show user what’s wrong
        }

    }

    @FXML
    private void onEditLibraryToggled(ActionEvent actionEvent) {
        System.out.println(!editLibraryToggle.isSelected());
        makeLibraryAndBranchTextBoxesDisabledOrEnabled(!editLibraryToggle.isSelected());
        if (editLibraryToggle.isSelected()) {
            saveLibraryButton.setText("Edytuj");
            saveBranchButton.setText("Edytuj");
        } else {
            saveLibraryButton.setText("Zapisz");
            saveBranchButton.setText("Zapisz");
        }
    }

    @FXML
    void onDeletePositionFromLibraryTreeButtonPressed() {

    }

    private void refresh(PreparedStatement update, int number, String address) throws SQLException {
        update.close();
        onBranchTabSelected(null);
        libraryTree.getSelectionModel().select(findItemByName(libraryTree, composeBranchItemName(number, address)));
    }

    /*
    ====================================================================================================================
    Librarian/Post screen
    */

    @FXML
    void onLibrarianTabSelected(Event event) {
        clearTree(librarianTree);
        librarianBox.setVisible(false);
        postBox.setVisible(false);
        librarianLendBox.setVisible(false);
        postBranchChoiceBox.setDisable(true);
        postLibraryChoiceBox.setDisable(true);
        setPostLibraryChoiceBox();
        TreeItem<String> rootItem = new TreeItem<>("Bibliotekarze");
        rootItem.setExpanded(true);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, imie, nazwisko FROM INF122446.L_BIBLIOTEKARZE");
            while (resultSet.next()) {
                TreeItem<String> librarianItem = new TreeItem<>(resultSet.getString("ID")
                        + " " + resultSet.getString("imie")
                        + " " + resultSet.getString("nazwisko"));
                rootItem.getChildren().add(librarianItem);
            }
            resultSet.close();
            statement.close();
            librarianTree.setRoot(rootItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchLibrarianBar.textProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<String> newRoot = updateFilteredTree(rootItem, searchLibrarianBar.getText());
            newRoot.setExpanded(true);
            librarianTree.setRoot(newRoot);
        });
    }

    private void setPostLibraryChoiceBox() {
        ObservableList<String> choices = FXCollections.observableArrayList();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nazwa FROM INF122446.L_BIBLIOTEKI");
            while (resultSet.next()) {
                choices.add(resultSet.getString("nazwa"));
            }
            postLibraryChoiceBox.setItems(choices);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onAddLibrarianButtonPressed(ActionEvent event) {
        //clearLibrarianTextBoxes();
        postBox.setVisible(false);
        librarianLendBox.setVisible(false);
        addPostButton.setDisable(true);
        postSalary.setDisable(true);
        postFraction.setDisable(true);
        TreeItem<String> root = librarianTree.getRoot();
        TreeItem<String> newLibrarian = new TreeItem<>("*Nowy Bibliotekarz");
        root.getChildren().add(newLibrarian);
        libraryTree.getSelectionModel().select(newLibrarian);
        try {
            setLibrarianData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        librarianBox.setVisible(true);
    }

    private void clearLibrarianTextBoxes() {

    }

    private void clearPostBoxes() {
        postLibraryChoiceBox.getSelectionModel().clearSelection();
        postLibraryChoiceBox.setValue(null);
        postBranchChoiceBox.getSelectionModel().clearSelection();
        postBranchChoiceBox.setValue(null);
        postFraction.setText("");
        postSalary.setText("");
    }

    private void onLibrarianTreeItemSelected(TreeItem<String> selectedItem) {
        if (selectedItem == null || selectedItem.getValue() == null || selectedItem.getValue().charAt(0) == '*')
            return;
        posts.clear();
        addPostButton.setDisable(false);
        clearPostBoxes();
        removeEmptyItems(librarianTree);
        lendsData.clear();
        postFraction.setDisable(true);
        postSalary.setDisable(true);
        postLibraryChoiceBox.setDisable(true);
        postBranchChoiceBox.setDisable(true);
        String[] label = selectedItem.getValue().split(" ");
        selectedLibrarianID = Integer.parseInt(label[0]);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_BIBLIOTEKARZE WHERE ID = '" + selectedLibrarianID + "'");
            resultSet.next();
            setLibrarianData(resultSet);
            resultSet.close();
            statement.close();
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM INF122446.L_ETATY WHERE BIBLIOTEKARZE_ID = '" + selectedLibrarianID + "'");
            resultSetToArrayListOfPosts(resultSet);
            resultSet.close();
            statement.close();
            index = 0;
            if (posts.size() > 0) {
                previousPostButton.setDisable(true);
                setPostData(index);
                if (posts.size() == 1) {
                    nextPostButton.setDisable(true);
                } else {
                    nextPostButton.setDisable(false);
                }
            }
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM INF122446.L_WYPOŻYCZENIA WHERE BIBLIOTEKARZE_ID = '" + selectedLibrarianID + "'");
            while (resultSet.next()) {
                lendsData.add(makeLendObjectFromResultSet(resultSet));
            }
            librarianLends.setItems(lendsData);
            librarianBox.setVisible(true);
            postBox.setVisible(true);
            librarianLendBox.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Lend makeLendObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT Wydania_ISBN FROM inf122446.L_Kopie WHERE SYGNATURA = '" + resultSet.getString("Kopie_Sygnatura") + "'");
        rs.next();
        String Wydania_ISBN = rs.getString("Wydania_ISBN");
        System.out.println(Wydania_ISBN);
        statement.close();
        rs.close();
        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT tytuł_tłumaczenia FROM inf122446.L_Kopie JOIN inf122446.L_Wydania ON '" + Wydania_ISBN + "' = inf122446.L_Wydania.ISBN");
        rs.next();
        String title = rs.getString("tytuł_tłumaczenia"); // from Kopie JOIN Wydania
        statement.close();
        rs.close();

        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT Książki_ID FROM INF122446.L_Wydania WHERE ISBN = '" + Wydania_ISBN + "'");
        rs.next();
        int bookID = Integer.parseInt(rs.getString("Książki_ID"));
        statement.close();
        rs.close();
        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT Autorzy_ID FROM inf122446.L_autorstwa WHERE Książki_ID = " + bookID);
        rs.next();
        int authorID = Integer.parseInt(rs.getString("Autorzy_ID"));
        statement.close();
        rs.close();
        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT Imię, Nazwisko FROM inf122446.L_Autorzy WHERE ID = " + authorID);
        rs.next();
        String author = rs.getString("Imię") + " " + rs.getString("Nazwisko");
        statement.close();
        rs.close();

        String reader = resultSet.getString("Czytelnicy_PESEL"); //todo może imię i nazwisko też?
        String since = resultSet.getString("DATA_WYPOZYCZENIA");
        String till = resultSet.getString("DATA_ODDANIA");
        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT Biblioteki_ID, Filie_Numer FROM inf122446.L_Kopie WHERE Sygnatura = '" + resultSet.getString("Kopie_Sygnatura") + "'");
        rs.next();
        String libraryID = rs.getString("Biblioteki_ID");
        String branchNumber = rs.getString("Filie_Numer");
        statement.close();
        rs.close();
        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT nazwa FROM inf122446.L_Biblioteki WHERE ID = " + libraryID);
        rs.next();
        String library = rs.getString("nazwa"); // Library_ID from book JOIN Kopie and Biblioteki
        statement.close();
        rs.close();
        String signature = resultSet.getString("KOPIE_SYGNATURA");
        String librarian = resultSet.getString("Bibliotekarze_ID"); //todo może imię i nazwisko
        return new Lend(title, author, reader, since, till, library, branchNumber, signature, Wydania_ISBN, librarian);
    }

    private void setLibrarianData(@Nullable ResultSet data) throws SQLException {
        librarianForename.setText(data != null ? data.getString("imie") : "");
        librarianSurname.setText(data != null ? data.getString("nazwisko") : "");
    }

    private void resultSetToArrayListOfPosts(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (resultSet.next()) {
            String row = "";
            for (int i = 1; i <= columnCount; i++) {
                row += resultSet.getString(i) + " ";
            }
            posts.add(row);
        }
    }

    private void setPostData(int index) {
        String[] arr = posts.get(index).split(" ");
        postFraction.setText(arr[0]);
        postSalary.setText(arr[1]);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nazwa FROM INF122446.L_BIBLIOTEKI WHERE ID = " + Integer.parseInt(arr[4]));
            resultSet.next();
            setPostBranchChoiceBox(resultSet.getString("nazwa"));
            postLibraryChoiceBox.setValue(resultSet.getString("nazwa"));
            postBranchChoiceBox.setValue(arr[2]);
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSaveLibrarianButtonPressed(ActionEvent event) {
        PreparedStatement update;
        String name = librarianForename.getText();
        String surname = librarianSurname.getText();

        try {
            update = dbConnection.prepareStatement("INSERT INTO inf122446.L_Bibliotekarze(Imie, Nazwisko)"
                    + "VALUES(?, ?)");
            update.setString(1, name);
            update.setString(2, surname);
            update.executeUpdate();
            onLibrarianTabSelected(null);
            librarianTree.getSelectionModel().select(findItemByName(libraryTree, name));
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo show user what’s wrong
        }
    }

    @FXML
    void onAddPostButtonPressed(ActionEvent event) {
        clearPostBoxes();
        addPostButton.setDisable(true);
        postSalary.setDisable(false);
        postFraction.setDisable(false);
        postFraction.requestFocus();
        postLibraryChoiceBox.setDisable(false);
        postBranchChoiceBox.setDisable(true);
        postLibraryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
                postBranchChoiceBox.setDisable(false);
                String LibraryName = postLibraryChoiceBox.getItems().subList(newValue.intValue(), newValue.intValue() + 1).get(0);
                setPostBranchChoiceBox(LibraryName);
            }
        });
    }

    private void setPostBranchChoiceBox(String LibName) {
        postBranchChoiceBox.getSelectionModel().clearSelection();
        postBranchChoiceBox.setValue(null);
        ObservableList<String> choices = FXCollections.observableArrayList();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT numer FROM INF122446.L_FILIE WHERE Biblioteki_ID = (SELECT ID FROM INF122446.L_Biblioteki WHERE Nazwa = '" + LibName + "')");
            while (resultSet.next()) {
                choices.add(resultSet.getString("numer"));
            }
            postBranchChoiceBox.setItems(choices);
            //if(branchFromAPost != -1){
            // System.out.println("ljhwkfhwjhfkw " + Integer.toString(branchFromAPost));
            //  postBranchChoiceBox.setValue(Integer.toString(branchFromAPost));
            //   branchFromAPost = -1;
            //}
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSavePostButtonPressed(ActionEvent event) {
        PreparedStatement update;
        String postFrac = postFraction.getText();
        int postSalar = Integer.parseInt(postSalary.getText());
        int branchNumber = Integer.parseInt(postBranchChoiceBox.getSelectionModel().getSelectedItem());
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID FROM INF122446.L_BIBLIOTEKI WHERE nazwa = '" + postLibraryChoiceBox.getSelectionModel().getSelectedItem() + "'");
            resultSet.next();
            int libraryID = Integer.parseInt(resultSet.getString("ID"));
            update = dbConnection.prepareStatement("INSERT INTO inf122446.L_Etaty(Część_etatu, Pensja, Filie_Numer," +
                    "Bibliotekarze_ID, Biblioteki_ID)"
                    + "VALUES(?, ?, ?, ?, ?)");
            update.setString(1, postFrac);
            update.setInt(2, postSalar);
            update.setInt(3, branchNumber);
            update.setInt(4, selectedLibrarianID);
            update.setInt(5, libraryID);
            selectedLibrarianID = -1;
            update.executeUpdate();
            onLibrarianTabSelected(null);
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo show user what’s wrong
        }
    }

    @FXML
    private void onReturnLibrarianLendButtonPressed(ActionEvent actionEvent) {

    }

    @FXML
    private void onNextPostButtonPressed(ActionEvent actionEvent) {
        clearPostBoxes();
        if (index < posts.size() - 1) {
            index++;
            if (index == 1) {
                previousPostButton.setDisable(false);
            }
            if (index == posts.size() - 1) {
                nextPostButton.setDisable(true);
            }
            setPostData(index);

        }
    }

    @FXML
    private void onPreviousPostButtonPressed(ActionEvent actionEvent) {
        clearPostBoxes();
        if (index > 0) {
            index--;
            if (index == posts.size() - 2) {
                nextPostButton.setDisable(false);
            }
            if (index == 0) {
                previousPostButton.setDisable(true);
            }
            setPostData(index);
        }
    }


    /*
    ====================================================================================================================
    Reader screen
    */

    private boolean matchesFilter(Reader reader) {
        String filterString = searchBar.getText();
        return (filterString == null || filterString.isEmpty() || reader.getForename().contains(filterString) || reader.getSurname().contains(filterString)
                || reader.getPesel().contains(filterString));
    }

    private void updateFilteredData() {
        filteredData.clear();
        for (Reader r : readersData) {
            if (matchesFilter(r)) {
                filteredData.add(r);
            }
        }
    }

    @FXML
    void onReaderTabSelected(Event event) {
        filteredData.addAll(readersData);
        readerBox.setVisible(false);
        readerBorrowBox.setVisible(false);
        editReaderToggle.setDisable(true);
        readersData.clear();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredData();
            readersTable.setItems(filteredData);
        });
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT PESEL, imie, nazwisko FROM INF122446.L_CZYTELNICY");
            while (resultSet.next()) {
                Reader reader = new Reader(resultSet.getString("imie"),
                        resultSet.getString("nazwisko"), resultSet.getString("PESEL"));
                readersData.add(reader);
                System.out.println(reader.getForename() + " " + reader.getSurname() + " " + reader.getPesel());
            }
            resultSet.close();
            statement.close();
            readersTable.setItems(readersData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearReaderBoxes() {
        readerForename.setText("");
        readerSurname.setText("");
        readerPESEL.setText("");
    }

    @FXML
    void onAddReaderButtonPressed(ActionEvent event) {//todo
        readerPESEL.setDisable(false);
        readerSurname.setDisable(false);
        readerForename.setDisable(false);
        readerBox.setVisible(true);
        clearReaderBoxes();
    }

    private void makeReaderTextBoxesDisabledOrEnabled(boolean value) {
        readerForename.setDisable(value);
        readerSurname.setDisable(value);
        readerPESEL.setDisable(value);
    }

    private void onReaderTableItemSelected(Reader reader) {//todo
        makeReaderTextBoxesDisabledOrEnabled(true);
        readerBorrowBox.setVisible(true);
        readerBox.setVisible(true);
        editReaderToggle.setDisable(false);
        readerForename.setText(reader.getForename());
        readerSurname.setText(reader.getSurname());
        readerPESEL.setText(reader.getPesel());
        borrowsData.clear();
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_Wypożyczenia WHERE Czytelnicy_PESEL = '"
                    + reader.getPesel() + "'");
            while (resultSet.next()) {
                borrowsData.add(makeLendObjectFromResultSet(resultSet));
            }
            readerBorrowsTable.setItems(borrowsData);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // todo show user what's wrong
        }
    }

    @FXML
    void onSaveReaderButtonPressed(ActionEvent event) {
        PreparedStatement update;
        String readersPESEL = readerPESEL.getText();
        String readersName = readerForename.getText();
        String readersSurname = readerSurname.getText();
        try {
            update = dbConnection.prepareStatement("INSERT INTO inf122446.L_Czytelnicy(PESEL, Imie, Nazwisko)"
                    + "VALUES(?, ?, ?)");
            update.setString(1, readersPESEL);
            update.setString(2, readersName);
            update.setString(3, readersSurname);
            update.executeUpdate();
            onReaderTabSelected(null);
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo show user what’s wrong
        }
    }

    @FXML
    private void onEditReaderToggled(ActionEvent actionEvent) {
        System.out.println(!editLibraryToggle.isSelected());
        makeReaderTextBoxesDisabledOrEnabled(!editReaderToggle.isSelected());
        if (editReaderToggle.isSelected()) {
            saveReaderButton.setText("Edytuj");
        } else {
            saveReaderButton.setText("Zapisz");
        }
    }

    @FXML
    private void onReturnReaderBorrowButtonPressed(ActionEvent actionEvent) {//todo

    }

    /*
    ====================================================================================================================
    Book screen
    */

    @FXML
    private void onBookTabSelected(Event event) {
        bookEditToggle.selectedProperty().setValue(false);
        bookBox.setVisible(false);
        editionBox.setVisible(false);
        copyBox.setVisible(false);
        bookBorrowBox.setVisible(false);

        bookBox.setDisable(true);
        editionBox.setDisable(true);
        copyBox.setDisable(true);
        bookBorrowBox.setDisable(true);

        populateBookTree(null);

        if (preferences.get("error", "").equals("OK")) {
            switch (preferences.get("callerRequest", "")) {
                case "author":
                    if (preferences.get("selectedBook", "").equals("*Nowa Książka")) {
                        onAddBookButtonPressed(null);
                    }
                    restoreBookEditing();
                    ObservableList<Writer> authors = bookAuthorshipTable.getItems();
                    Writer newAuthor = new Writer(preferences.get("forename", ""), preferences.get("surname", ""),
                            preferences.get("nationality", ""));
                    authors.add(newAuthor);
                    bookAuthorshipTable.setItems(authors);
                    break;
                case "translator":
                    if (preferences.get("selectedEdition", "").equals("*Nowe Wydanie")) {
                        bookTree.getSelectionModel().select(findItemByName(bookTree, preferences.get("selectedBook", "")));
                        onAddEditionButtonPressed(null);
                    }
                    restoreEditionEditing();
                    ObservableList<Writer> translators = editionTranslationTable.getItems();
                    if (translators == null)
                        translators = FXCollections.observableArrayList();

                    Writer newTranslator = new Writer(preferences.get("forename", ""), preferences.get("surname", ""),
                            "");

                    translators.add(newTranslator);
                    editionTranslationTable.setItems(translators);
                    break;
                case "publisher":
                    if (preferences.get("selectedEdition", "").equals("*Nowe Wydanie")) {
                        bookTree.getSelectionModel().select(findItemByName(bookTree, preferences.get("selectedBook", "")));
                        onAddEditionButtonPressed(null);
                    }
                    restoreEditionEditing();
                    ObservableList<String> publishers = editionPublisher.getItems();
                    publishers.add(preferences.get("publisherR", ""));
                    editionPublisher.setItems(publishers);
                    editionPublisher.getSelectionModel().select(preferences.get("publisherR", ""));
                    break;
                case "reader":
                    String signature = preferences.get("signature", "");
                    bookTree.getSelectionModel().select(findItemByName(bookTree, signature));
                    break;
            }
        }
        preferences.put("error", "");
    }

    private void populateBookTree(@Nullable String filter) {
        boolean shouldAddBook;
        boolean shouldAddEdition = filter == null;
        boolean shouldAddCopy = filter == null;
        TreeItem<String> rootItem = new TreeItem<>("Książki");
        rootItem.setExpanded(true);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, TYTUŁ FROM INF122446.L_KSIĄŻKI");
            while (resultSet.next()) {
                shouldAddBook = filter == null;
                String itemString = resultSet.getString("TYTUŁ") + " (";
                Statement authorship = dbConnection.createStatement();
                ResultSet authorshipResults = authorship.executeQuery("SELECT IMIĘ, NAZWISKO FROM INF122446.L_KSIĄŻKI JOIN " +
                        "INF122446.L_AUTORSTWA ON(ID = KSIĄŻKI_ID) JOIN INF122446.L_AUTORZY ON(INF122446.L_AUTORZY.ID=AUTORZY_ID) " +
                        "WHERE INF122446.L_KSIĄŻKI.ID = " + resultSet.getInt("ID"));
                while (authorshipResults.next())
                    itemString += authorshipResults.getString("IMIĘ") + " " +
                            authorshipResults.getString("NAZWISKO") + ", ";

                itemString = itemString.substring(0, itemString.length() - 2);
                itemString = itemString + ')';
                shouldAddBook = shouldAddBook || itemString.contains(filter);
                TreeItem<String> bookItem = new TreeItem<>(itemString);
                Statement editions = dbConnection.createStatement();
                ResultSet editionsResult = editions.executeQuery("SELECT TYTUŁ_TŁUMACZENIA, NAZWA, ISBN FROM INF122446.L_WYDANIA JOIN " +
                        "INF122446.L_WYDAWCY ON(WYDAWCY_VAT_ID = VAT_ID) WHERE KSIĄŻKI_ID = " + resultSet.getInt("ID"));
                while (editionsResult.next()) {
                    String editionString = composeEditionName(editionsResult.getString("Tytuł_tłumaczenia"),
                            editionsResult.getString("Nazwa"), editionsResult.getString("ISBN"));
                    shouldAddEdition = shouldAddBook || editionString.contains(filter);
                    TreeItem<String> editionItem = new TreeItem<>(editionString);
                    Statement copies = dbConnection.createStatement();
                    ResultSet copiesResult = copies.executeQuery("SELECT SYGNATURA FROM INF122446.L_KOPIE WHERE" +
                            " WYDANIA_ISBN = " + editionsResult.getString("ISBN"));
                    while (copiesResult.next()) {
                        String copyString = copiesResult.getString("Sygnatura");
                        shouldAddCopy = shouldAddEdition || copyString.contains(filter);
                        TreeItem<String> copyItem = new TreeItem<>(copyString);
                        if (shouldAddCopy)
                            editionItem.getChildren().add(copyItem);
                    }
                    shouldAddEdition = shouldAddEdition || shouldAddCopy;
                    if (shouldAddEdition)
                        bookItem.getChildren().add(editionItem);
                    copies.close();
                    copiesResult.close();
                }
                editionsResult.close();
                editions.close();
                shouldAddBook = shouldAddBook || shouldAddEdition;
                if (shouldAddBook)
                    rootItem.getChildren().add(bookItem);
            }
            resultSet.close();
            statement.close();
            bookTree.setRoot(rootItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void restoreBookEditing() {
        String selectedBook = preferences.get("selectedBook", "");
        if (!selectedBook.equals("*Nowa Książka")) {
            bookTree.getSelectionModel().select(findItemByName(bookTree, selectedBook));
        }
        bookTitle.setText(preferences.get("title", ""));
        bookFinishDate.setText(preferences.get("finishDate", ""));
        bookGenre.setText(preferences.get("genre", ""));
        bookLanguage.setText(preferences.get("language", ""));
        bookStream.setText(preferences.get("stream", ""));

        ObservableList<Writer> authors = FXCollections.observableArrayList();
        if (!preferences.get("authorship", "").equals("[]")) {
            for (String authorString : preferences.get("authorship", "").split(";")) {
                authors.add(Writer.fromJson(authorString));
            }
        }
        bookAuthorshipTable.setItems(authors);

        bookBox.setVisible(true);
    }

    private void restoreEditionEditing() {
        String selectedEdition = preferences.get("selectedEdition", "");
        if (!selectedEdition.equals("*Nowe Wydanie")) {
            bookTree.getSelectionModel().select(findItemByName(bookTree, selectedEdition));
        }
        editionNumber.setText(preferences.get("number", ""));
        editionPublisher.getSelectionModel().select(preferences.get("publisher", ""));
        editionIsbn.setText(preferences.get("isbn", ""));
        editionLanguage.setText(preferences.get("editionLanguage", ""));
        editionReleaseDate.setText(preferences.get("releaseDate", ""));
        editionTitle.setText(preferences.get("editionTitle", ""));

        ObservableList<Writer> translators = FXCollections.observableArrayList();
        if (!preferences.get("translations", "").equals("[]")) {
            for (String translatorString : preferences.get("translations", "").split(";")) {
                translators.add(Writer.fromJson(translatorString));
            }
        }
        editionTranslationTable.setItems(translators);

        bookBox.setVisible(true);
        editionBox.setVisible(true);
    }

    private String composeEditionName(String tytul_tlumaczenia, String nazwa, String isbn) {
        return tytul_tlumaczenia + ", " + nazwa + " (" + isbn + ")";
    }

    @FXML
    void onBookEditToggled(ActionEvent event) {
        bookBox.setDisable(!bookEditToggle.isSelected());
        editionBox.setDisable(!bookEditToggle.isSelected());
        copyBox.setDisable(!bookEditToggle.isSelected());
        bookBorrowBox.setDisable(!bookEditToggle.isSelected());

        /*if (bookEditToggle.isSelected()) {
            saveBookButton.setText("Edytuj");
            saveCopyButton.setText("Edytuj");
            saveEditionButton.setText("Edytuj");
        } else {
            saveBookButton.setText("Zapisz");
            saveCopyButton.setText("Zapisz");
            saveEditionButton.setText("Zapisz");
        }*/

        lendButton.setDisable(currentLibrarian == 0);
        returnButton.setDisable(currentLibrarian == 0);
    }

    @FXML
    void onAddBookButtonPressed(ActionEvent event) {
        TreeItem<String> root = bookTree.getRoot();
        TreeItem<String> newBook = new TreeItem<>("*Nowa Książka");
        root.getChildren().add(newBook);
        bookTree.getSelectionModel().select(newBook);
        try {
            setBookData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        editionBox.setVisible(false);
        copyBox.setVisible(false);
        bookBorrowBox.setVisible(false);
        bookBox.setVisible(true);
    }

    private void setBookData(@Nullable ResultSet data) throws SQLException {
        bookTitle.setText(data != null ? data.getString("Tytuł") : "");
        bookFinishDate.setText(data != null ? data.getString("Rok_ukończenia") : "");
        bookGenre.setText(data != null ? data.getString("Gatunek") : "");
        bookLanguage.setText(data != null ? data.getString("Język") : "");
        bookStream.setText(data != null ? data.getString("Nurt") : "");

        if (data != null) {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IMIĘ, NAZWISKO, NARODOWOŚĆ FROM INF122446.L_KSIĄŻKI " +
                    "JOIN INF122446.L_AUTORSTWA ON(ID = KSIĄŻKI_ID) JOIN INF122446.L_AUTORZY " +
                    "ON(INF122446.L_AUTORZY.ID = AUTORZY_ID) WHERE INF122446.L_KSIĄŻKI.ID = " + data.getInt("ID"));
            ObservableList<Writer> items = FXCollections.observableArrayList();
            while (resultSet.next()) {
                items.add(new Writer(resultSet.getString("IMIĘ"), resultSet.getString("NAZWISKO"),
                        resultSet.getString("NARODOWOŚĆ")));
            }
            bookAuthorshipTable.setItems(items);
        } else
            bookAuthorshipTable.setItems(null);
    }

    private void onBookTreeItemSelected(TreeItem<String> selectedItem) {
        if (selectedItem == null || selectedItem.getValue() == null || selectedItem.getValue().charAt(0) == '*')
            return;

        String label = selectedItem.getValue();
        int bookId = -1;
        if (label.matches("(.*)\\(\\d*\\)") || label.equals("*Nowe Wydanie")) {
            String isbn = label.split("\\(")[1].split("\\)")[0];
            try {
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_KSIĄŻKI JOIN INF122446.L_WYDANIA" +
                        " ON(KSIĄŻKI_ID=ID) JOIN INF122446.L_WYDAWCY ON(WYDAWCY_VAT_ID = VAT_ID) WHERE ISBN = '" + isbn + "'");
                resultSet.next();
                bookId = resultSet.getInt("ID");
                setBookData(resultSet);
                setEditionData(resultSet);
                resultSet.close();
                statement.close();
                bookBox.setVisible(true);
                editionBox.setVisible(true);
                copyBox.setVisible(false);
                bookBorrowBox.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (label.contains("(")) {
            String[] splat = label.split(Pattern.quote(" ("));
            String title = splat[0];
            String author = splat[1].split(",")[0];
            String authorName = author.split(" ")[0];
            String authorSurname = author.split(" ")[1].split("\\)")[0];
            try {
                PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM INF122446.L_KSIĄŻKI JOIN " +
                        "INF122446.L_AUTORSTWA ON(KSIĄŻKI_ID = ID) JOIN INF122446.L_AUTORZY ON(AUTORZY_ID = " +
                        "INF122446.L_AUTORZY.ID) WHERE TYTUŁ = ? AND IMIĘ = ? AND " +
                        "INF122446.L_AUTORZY.NAZWISKO = ?");
                statement.setString(1, title);
                statement.setString(2, authorName);
                statement.setString(3, authorSurname);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                bookId = resultSet.getInt("ID");
                setBookData(resultSet);
                resultSet.close();
                statement.close();
                bookBox.setVisible(true);
                editionBox.setVisible(false);
                copyBox.setVisible(false);
                bookBorrowBox.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_KSIĄŻKI JOIN " +
                        "INF122446.L_WYDANIA ON(ID = KSIĄŻKI_ID) JOIN INF122446.L_KOPIE ON(ISBN = WYDANIA_ISBN) " +
                        "JOIN INF122446.L_WYDAWCY ON(WYDAWCY_VAT_ID = VAT_ID) WHERE " +
                        "SYGNATURA = '" + label + "'");
                resultSet.next();
                bookId = resultSet.getInt("ID");
                setBookData(resultSet);
                setEditionData(resultSet);
                setCopyData(resultSet);
                resultSet.close();
                statement.close();
                bookBox.setVisible(true);
                editionBox.setVisible(true);
                copyBox.setVisible(true);
                bookBorrowBox.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        removeEmptyItems(bookTree);
        if (bookId != -1)
            populateBorrows(bookId, null);
    }

    private void populateBorrows(int bookId, @Nullable String filter) {
        try {
            PreparedStatement select = dbConnection.prepareStatement("SELECT * FROM INF122446.L_WYPOŻYCZENIA " +
                    "JOIN INF122446.L_KOPIE ON(SYGNATURA = KOPIE_SYGNATURA) JOIN INF122446.L_WYDANIA ON(ISBN = WYDANIA_ISBN) " +
                    "JOIN INF122446.L_CZYTELNICY ON(PESEL = CZYTELNICY_PESEL) WHERE KSIĄŻKI_ID = ?");
            select.setInt(1, bookId);
            ResultSet result = select.executeQuery();
            ObservableList<Lend> lends = FXCollections.observableArrayList();
            while (result.next()) {
                Lend lend = makeLendObjectFromResultSet(result);
                if (filter == null || lend.toString().contains(filter))
                    lends.add(lend);
            }
            bookBorrowsTable.setItems(lends);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCopyData(ResultSet data) throws SQLException {
        copySignature.setText(data != null ? data.getString("sygnatura") : "");

        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT TYP, OPIS FROM INF122446.L_KOPIE JOIN " +
                "INF122446.L_MATERIAŁY_DODATKOWE ON(SYGNATURA = KOPIE_SYGNATURA) WHERE SYGNATURA = '" +
                copySignature.getText() + "'");
        ObservableList<Material> materials = FXCollections.observableArrayList();
        while (resultSet.next()) {
            materials.add(new Material(resultSet.getString("TYP"), resultSet.getString("OPIS")));
        }
        copyMaterialTable.setItems(materials);
        resultSet.close();
        statement.close();

        statement = dbConnection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM INF122446.L_KOPIE JOIN INF122446.L_WYPOŻYCZENIA " +
                "ON(SYGNATURA = KOPIE_SYGNATURA) WHERE SYGNATURA = '" + copySignature.getText() + "'");
        ObservableList<Lend> borrows = FXCollections.observableArrayList();
        while (resultSet.next()) {
            borrows.add(makeLendObjectFromResultSet(resultSet));
        }
        bookBorrowsTable.setItems(borrows);
        resultSet.close();
        statement.close();

        statement = dbConnection.createStatement();
        resultSet = statement.executeQuery("SELECT NAZWA FROM INF122446.L_BIBLIOTEKI");
        ObservableList<String> items = FXCollections.observableArrayList();
        while (resultSet.next()) {
            items.add(resultSet.getString("Nazwa"));
        }
        copyLibrary.setItems(items);
        resultSet.close();
        statement.close();

        statement = dbConnection.createStatement();
        resultSet = statement.executeQuery("SELECT NAZWA FROM INF122446.L_BIBLIOTEKI JOIN INF122446.L_KOPIE " +
                "ON(BIBLIOTEKI_ID = ID) WHERE SYGNATURA = '" + copySignature.getText() + "'");
        if (resultSet.next())
            copyLibrary.getSelectionModel().select(resultSet.getString("NAZWA"));
        resultSet.close();
        statement.close();
    }

    private void onCopyLibrarySelected(String selectedItem) {
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_BIBLIOTEKI JOIN " +
                    "INF122446.L_FILIE ON(ID = BIBLIOTEKI_ID) WHERE NAZWA = '" + selectedItem + "'");
            ObservableList<String> branches = FXCollections.observableArrayList();
            while (resultSet.next()) {
                branches.add("Filia nr " + resultSet.getInt("Numer") + " (" + resultSet.getString("Adres") + ")");
            }
            copyBranch.setItems(branches);
            resultSet.close();
            statement.close();

            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM INF122446.L_BIBLIOTEKI JOIN INF122446.L_FILIE " +
                    "ON(ID = BIBLIOTEKI_ID) JOIN INF122446.L_KOPIE ON(INF122446.L_KOPIE.BIBLIOTEKI_ID = ID " +
                    "AND FILIE_NUMER = NUMER) WHERE SYGNATURA = '" + copySignature.getText() + "'");
            resultSet.next();
            copyBranch.getSelectionModel().select("Filia nr " + resultSet.getInt("Numer") + " (" +
                    resultSet.getString("Adres") + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddBookAuthorButtonPressed(ActionEvent event) {
        preferences.put("callerRequest", "author");
        preferences.put("callerScreenPath", "ui/app.fxml");
        preferences.put("callerScreenTitle", "Librarian");

        preferences.put("title", safeGetText(bookTitle));
        preferences.put("finishDate", safeGetText(bookFinishDate));
        preferences.put("genre", safeGetText(bookGenre));
        preferences.put("language", safeGetText(bookLanguage));
        preferences.put("stream", safeGetText(bookStream));
        String authorship = "[";

        ObservableList<Writer> authors = bookAuthorshipTable.getItems();
        if (authors == null)
            authors = FXCollections.emptyObservableList();

        for (Writer w : authors) {
            authorship += w.toJson() + "; ";
        }
        if (!authors.isEmpty())
            authorship = authorship.substring(0, authorship.length() - 2);
        authorship += "]";
        preferences.put("authorship", authorship);

        preferences.put("selectedBook", bookTree.getSelectionModel().getSelectedItem().getValue());

        setScene("ui/picker.fxml", "Wybór autora");
    }

    @NotNull
    private String safeGetText(TextField field) {
        String text = field.getText();
        return text == null ? "" : text;
    }

    @FXML
    void onRemoveBookAuthorButtonPressed(ActionEvent event) {
        Writer selectedAuthor = bookAuthorshipTable.getSelectionModel().getSelectedItem();
        ObservableList<Writer> authors = bookAuthorshipTable.getItems();
        authors.removeAll(selectedAuthor);
        bookAuthorshipTable.setItems(authors);
    }

    @FXML
    void onSaveBookButtonPressed(ActionEvent event) {
        if (bookAuthorshipTable.getItems() == null || bookAuthorshipTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można zapisać książki bez autorów",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        String selectedBook = getBookNameFromSelectedItem(bookTree);
        String[] splat;
        String title;
        String authorForename;
        String authorSurname;
        try {
            splat = selectedBook.split(Pattern.quote(" ("));
            title = splat[0];
            String author = splat[1].split(",")[0];
            authorForename = author.split(" ")[0];
            authorSurname = author.split(" ")[1].split("\\)")[0];
        } catch (IndexOutOfBoundsException ignored) {
            title = "";
            authorForename = "";
            authorSurname = "";
        }

        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT INF122446.L_KSIĄŻKI.ID FROM INF122446.L_KSIĄŻKI JOIN INF122446.L_AUTORSTWA " +
                    "ON(ID = KSIĄŻKI_ID) JOIN INF122446.L_AUTORZY ON(INF122446.L_AUTORZY.ID = AUTORZY_ID) " +
                    "WHERE TYTUŁ = '" + title + "' AND IMIĘ = '" + authorForename + "' " +
                    "AND INF122446.L_AUTORZY.NAZWISKO = '" + authorSurname + "'");
            if (resultSet.next()) {
                PreparedStatement update = dbConnection.prepareStatement("UPDATE INF122446.L_KSIĄŻKI SET TYTUŁ = ?, " +
                        "GATUNEK = ?, ROK_UKOŃCZENIA = ?, JĘZYK = ?, NURT = ? " +
                        "WHERE ID = ?");
                update.setString(1, bookTitle.getText());
                update.setString(2, bookGenre.getText());
                update.setInt(3, Integer.parseInt(bookFinishDate.getText()));
                update.setString(4, bookLanguage.getText());
                update.setString(5, bookStream.getText());
                update.setInt(6, resultSet.getInt("ID"));
                update.execute();
                update.close();
            } else {
                PreparedStatement update = dbConnection.prepareStatement("INSERT INTO INF122446.L_KSIĄŻKI(TYTUŁ, " +
                        "GATUNEK, ROK_UKOŃCZENIA, JĘZYK, NURT) VALUES(?, ?, ?, ?, ?)");
                update.setString(1, bookTitle.getText());
                update.setString(2, bookGenre.getText());
                update.setInt(3, Integer.parseInt(bookFinishDate.getText()));
                update.setString(4, bookLanguage.getText());
                update.setString(5, bookStream.getText());
                update.execute();
                update.close();
            }
            resultSet.close();
            statement.close();

            PreparedStatement ps = dbConnection.prepareStatement("SELECT ID FROM INF122446.L_KSIĄŻKI " +
                    "WHERE TYTUŁ = ? AND GATUNEK = ? AND INF122446.L_KSIĄŻKI.ROK_UKOŃCZENIA = ? AND JĘZYK = ?");
            ps.setString(1, bookTitle.getText());
            ps.setString(2, bookGenre.getText());
            ps.setInt(3, Integer.parseInt(bookFinishDate.getText()));
            ps.setString(4, bookLanguage.getText());
            resultSet = ps.executeQuery();
            resultSet.next();
            int bookID = resultSet.getInt("ID");
            ps.close();
            resultSet.close();

            statement = dbConnection.createStatement();
            statement.execute("DELETE FROM INF122446.L_AUTORSTWA WHERE KSIĄŻKI_ID = " + bookID);
            statement.close();

            for (Writer w : bookAuthorshipTable.getItems()) {
                Statement s = dbConnection.createStatement();
                ResultSet r = s.executeQuery("SELECT ID FROM INF122446.L_AUTORZY WHERE IMIĘ = '" + w.getForename() + "' " +
                        "AND NAZWISKO ='" + w.getSurname() + "' AND NARODOWOŚĆ = '" + w.getNationality() + "'");
                r.next();
                int authorID = r.getInt("ID");
                r.close();
                s.close();

                PreparedStatement update = dbConnection.prepareStatement("INSERT INTO INF122446.L_AUTORSTWA" +
                        "(KSIĄŻKI_ID, AUTORZY_ID) VALUES (?, ?)");
                update.setInt(1, bookID);
                update.setInt(2, authorID);
                update.execute();
                update.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String itemString = bookTitle.getText() + " (";
        for (Writer author : bookAuthorshipTable.getItems())
            itemString += author.getForename() + " " +
                    author.getSurname() + ", ";

        itemString = itemString.substring(0, itemString.length() - 2);
        itemString = itemString + ')';
        onBookTabSelected(null);
        bookTree.getSelectionModel().select(findItemByName(bookTree, itemString));
    }

    @FXML
    void onAddEditionButtonPressed(ActionEvent event) {
        String selectedBookName = getBookNameFromSelectedItem(bookTree);
        if (selectedBookName.equals("*Nowa Książka")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dodać wydania do nieistniejącej książki",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        TreeItem<String> selectedBook = findItemByName(bookTree, selectedBookName);
        if (selectedBook == null)
            return;
        TreeItem<String> newEdition = new TreeItem<>("*Nowe Wydanie");
        selectedBook.getChildren().add(newEdition);
        bookTree.getSelectionModel().select(newEdition);
        try {
            setEditionData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        copyBox.setVisible(false);
        bookBorrowBox.setVisible(false);
        bookBox.setVisible(true);
        editionBox.setVisible(true);
    }

    private void setEditionData(ResultSet data) throws SQLException {
        ObservableList<String> publishers = FXCollections.observableArrayList();
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT NAZWA FROM INF122446.L_WYDAWCY");
        while (resultSet.next()) {
            publishers.add(resultSet.getString("Nazwa"));
        }
        editionPublisher.setItems(publishers);
        resultSet.close();
        statement.close();

        editionNumber.setText(data != null ? data.getString("Numer") : "");
        editionPublisher.getSelectionModel().select(data != null ? data.getString("Nazwa") : "");
        editionIsbn.setText(data != null ? data.getString("ISBN") : "");
        editionLanguage.setText(data != null ? data.getString("Język") : "");
        editionReleaseDate.setText(data != null ? data.getString("Rok_wydania") : "");
        editionTitle.setText(data != null ? data.getString("Tytuł_tłumaczenia") : "");

        if (data != null) {
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery("SELECT IMIE, NAZWISKO FROM INF122446.L_WYDANIA " +
                    "JOIN INF122446.L_TŁUMACZENIA ON(ISBN = WYDANIA_ISBN) JOIN INF122446.L_TŁUMACZE " +
                    "ON(INF122446.L_TŁUMACZE.ID = TŁUMACZE_ID) WHERE INF122446.L_WYDANIA.ISBN = '"
                    + data.getString("ISBN") + "'");
            ObservableList<Writer> items = FXCollections.observableArrayList();
            while (resultSet.next()) {
                items.add(new Writer(resultSet.getString("IMIE"), resultSet.getString("NAZWISKO"), ""));
            }
            editionTranslationTable.setItems(items);
            resultSet.close();
            statement.close();
        } else
            editionTranslationTable.setItems(null);
    }

    private String getBookNameFromSelectedItem(@NotNull TreeView<String> tree) {
        TreeItem<String> selectedItem = tree.getSelectionModel().getSelectedItem();
        if (selectedItem.getValue().equals("*Nowa Książka"))
            return selectedItem.getValue();
        if (selectedItem.getValue().equals("*Nowe Wydanie"))
            return selectedItem.getParent().getValue();
        if (!selectedItem.getValue().contains("("))
            selectedItem = selectedItem.getParent().getParent();
        else if (selectedItem.getValue().split("\\(")[1].split("\\)")[0].matches("[0-9]{13}"))
            selectedItem = selectedItem.getParent();
        return selectedItem.getValue();
    }

    @FXML
    void onNewPublisherButtonPressed(ActionEvent event) {
        preferences.put("callerRequest", "publisher");
        putEditionToPreferences();

        setScene("ui/picker.fxml", "Wybór wydawcy");
    }

    private void putEditionToPreferences() {
        preferences.put("callerScreenPath", "ui/app.fxml");
        preferences.put("callerScreenTitle", "Librarian");

        preferences.put("editionTitle", editionTitle.getText());
        preferences.put("releaseDate", editionReleaseDate.getText());
        preferences.put("editionLanguage", editionLanguage.getText());
        preferences.put("number", editionNumber.getText());
        preferences.put("isbn", editionIsbn.getText());
        preferences.put("publisher", editionPublisher.getSelectionModel().getSelectedItem());
        String translation = "[";

        ObservableList<Writer> translators = editionTranslationTable.getItems();
        if (translators == null)
            translators = FXCollections.emptyObservableList();

        for (Writer w : translators) {
            translation += w.toJson() + "; ";
        }
        if (!translators.isEmpty())
            translation = translation.substring(0, translation.length() - 2);
        translation += "]";
        preferences.put("translations", translation);

        preferences.put("selectedEdition", bookTree.getSelectionModel().getSelectedItem().getValue());
        preferences.put("selectedBook", bookTree.getSelectionModel().getSelectedItem().getParent().getValue());
    }

    @FXML
    void onAddEditionTranslatorButtonPressed(ActionEvent event) {
        preferences.put("callerRequest", "translator");
        putEditionToPreferences();

        setScene("ui/picker.fxml", "Wybór tłumacza");
    }

    @FXML
    void onRemoveEditionTranslatorButtonPressed(ActionEvent event) {
        Writer selectedTranslator = editionTranslationTable.getSelectionModel().getSelectedItem();
        ObservableList<Writer> translators = editionTranslationTable.getItems();
        translators.removeAll(selectedTranslator);
        editionTranslationTable.setItems(translators);
    }

    @FXML
    void onSaveEditionButtonPressed(ActionEvent event) {
        String selectedBook = getBookNameFromSelectedItem(bookTree);
        String title;
        String authorForename;
        String authorSurname;
        String selectedEdition = getEditionNameFromSelectedItem(bookTree);
        String isbn;
        String[] splat = selectedBook.split(Pattern.quote(" ("));
        title = splat[0];
        String author = splat[1].split(",")[0];
        authorForename = author.split(" ")[0];
        authorSurname = author.split(" ")[1].split("\\)")[0];
        try {
            isbn = selectedEdition.split("\\(")[1].split("\\)")[0];
        } catch (IndexOutOfBoundsException ignored) {
            isbn = "";
        }
        String publisherId;
        int bookId;
        try {
            Statement s = dbConnection.createStatement();
            ResultSet r = s.executeQuery("SELECT VAT_ID FROM INF122446.L_WYDAWCY WHERE NAZWA = '" + editionPublisher.getValue() + "'");
            r.next();
            publisherId = r.getString("VAT_ID");
            r.close();
            s.close();
            PreparedStatement ps = dbConnection.prepareStatement("SELECT INF122446.L_KSIĄŻKI.ID " +
                    "FROM INF122446.L_KSIĄŻKI JOIN INF122446.L_AUTORSTWA ON(ID = KSIĄŻKI_ID) JOIN INF122446.L_AUTORZY " +
                    "ON(INF122446.L_AUTORZY.ID = AUTORZY_ID) WHERE TYTUŁ = ? AND IMIĘ = ? AND NAZWISKO = ?");
            ps.setString(1, title);
            ps.setString(2, authorForename);
            ps.setString(3, authorSurname);
            r = ps.executeQuery();
            r.next();
            bookId = r.getInt("ID");
            r.close();
            ps.close();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_WYDANIA " +
                    "WHERE ISBN = '" + isbn + "'");
            if (resultSet.next()) {
                PreparedStatement update = dbConnection.prepareStatement("UPDATE INF122446.L_WYDANIA SET TYTUŁ_TŁUMACZENIA = ?, " +
                        "ISBN = ?, NUMER = ?, ROK_WYDANIA = ?, JEZYK = ?, WYDAWCY_VAT_ID = ? WHERE ISBN = ?");
                update.setString(1, editionTitle.getText());
                update.setString(2, editionIsbn.getText());
                update.setInt(3, Integer.parseInt(editionNumber.getText()));
                update.setInt(4, Integer.parseInt(editionReleaseDate.getText()));
                update.setString(5, editionLanguage.getText());
                update.setString(6, publisherId);
                update.setString(7, editionIsbn.getText());
                update.execute();
                update.close();
            } else {
                PreparedStatement update = dbConnection.prepareStatement("INSERT INTO INF122446.L_WYDANIA(TYTUŁ_TŁUMACZENIA, " +
                        "ISBN, NUMER, ROK_WYDANIA, JEZYK, WYDAWCY_VAT_ID, KSIĄŻKI_ID) VALUES(?, ?, ?, ?, ?, ?, ?)");
                update.setString(1, editionTitle.getText());
                update.setString(2, editionIsbn.getText());
                update.setInt(3, Integer.parseInt(editionNumber.getText()));
                update.setInt(4, Integer.parseInt(editionReleaseDate.getText()));
                update.setString(5, editionLanguage.getText());
                update.setString(6, publisherId);
                update.setInt(7, bookId);
                update.execute();
                update.close();
            }
            resultSet.close();
            statement.close();

            statement = dbConnection.createStatement();
            statement.execute("DELETE FROM INF122446.L_TŁUMACZENIA WHERE WYDANIA_ISBN = '" + isbn + "'");
            statement.close();

            if (editionTranslationTable.getItems() != null) {
                for (Writer w : editionTranslationTable.getItems()) {
                    s = dbConnection.createStatement();
                    r = s.executeQuery("SELECT ID FROM INF122446.L_TŁUMACZE WHERE IMIE = '" + w.getForename() + "' " +
                            "AND NAZWISKO ='" + w.getSurname() + "'");
                    r.next();
                    int translatorID = r.getInt("ID");
                    r.close();
                    s.close();

                    PreparedStatement update = dbConnection.prepareStatement("INSERT INTO INF122446.L_TŁUMACZENIA" +
                            "(WYDANIA_ISBN, TŁUMACZE_ID) VALUES (?, ?)");
                    update.setString(1, isbn);
                    update.setInt(2, translatorID);
                    update.execute();
                    update.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String editionItem = composeEditionName(editionTitle.getText(), editionPublisher.getValue(), editionIsbn.getText());
        onBookTabSelected(null);
        bookTree.getSelectionModel().select(findItemByName(bookTree, editionItem));
    }

    @FXML
    void onAddCopyButtonPressed(ActionEvent event) {
        String selectedEditionName = getEditionNameFromSelectedItem(bookTree);
        if (selectedEditionName.equals("*Nowe Wydanie")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie można dodać kopii do nieistniejącego wydania",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        TreeItem<String> selectedEdition = findItemByName(bookTree, selectedEditionName);
        if (selectedEdition == null)
            return;
        TreeItem<String> newCopy = new TreeItem<>("*Nowa Kopia");
        selectedEdition.getChildren().add(newCopy);
        bookTree.getSelectionModel().select(newCopy);
        try {
            setCopyData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        copyBox.setVisible(true);
        bookBorrowBox.setVisible(true);
        bookBox.setVisible(true);
        editionBox.setVisible(true);
    }

    private String getEditionNameFromSelectedItem(TreeView<String> tree) {
        TreeItem<String> selectedItem = tree.getSelectionModel().getSelectedItem();
        if (!selectedItem.getValue().matches("(.*)\\((.*)")) //assumption signature cannot contain (
            selectedItem = selectedItem.getParent();
        return selectedItem.getValue();
    }

    @FXML
    void onAddCopyMaterialButtonPressed(ActionEvent event) {
        if (copySignature.getText() == null || Objects.equals(copySignature.getText(), "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz najpierw wpisać sygnaturę",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        ObservableList<Material> materials = copyMaterialTable.getItems();
        materials.add(new Material("*Nowy", "Pusty materiał"));
        copyMaterialTable.setItems(materials);
        try {
            PreparedStatement s = dbConnection.prepareStatement("INSERT INTO INF122446.L_MATERIAŁY_DODATKOWE" +
                    "(TYP, OPIS, KOPIE_SYGNATURA) VALUES(?, ?, ?)");
            s.setString(1, "*Nowy");
            s.setString(2, "Pusty materiał");
            s.setString(3, copySignature.getText());
            s.execute();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDeleteCopyMaterialButtonPressed(ActionEvent event) {
        Material selectedMaterial = copyMaterialTable.getSelectionModel().getSelectedItem();
        ObservableList<Material> materials = copyMaterialTable.getItems();
        materials.removeAll(selectedMaterial);
        copyMaterialTable.setItems(materials);
        try {
            PreparedStatement s = dbConnection.prepareStatement("DELETE FROM INF122446.L_MATERIAŁY_DODATKOWE " +
                    "WHERE TYP = ? AND OPIS = ? AND KOPIE_SYGNATURA = ?");
            s.setString(1, selectedMaterial.getType());
            s.setString(2, selectedMaterial.getDescription());
            s.setString(3, copySignature.getText());
            s.execute();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSaveCopyButtonPressed(ActionEvent event) {

        String selectedEdition = getEditionNameFromSelectedItem(bookTree);
        String isbn = selectedEdition.split("\\(")[1].split("\\)")[0];
        String signature = copySignature.getText();

        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_KOPIE " +
                    "WHERE SYGNATURA = '" + signature + "'");
            if (resultSet.next()) {
                PreparedStatement update = dbConnection.prepareStatement("UPDATE INF122446.L_KOPIE " +
                        "SET SYGNATURA = ?, BIBLIOTEKI_ID = ?, FILIE_NUMER = ?, WYDANIA_ISBN = ? " +
                        "WHERE SYGNATURA = ?");
                PreparedStatement librarySelect = dbConnection.prepareStatement("SELECT ID FROM INF122446.L_BIBLIOTEKI " +
                        "WHERE NAZWA = ?");
                librarySelect.setString(1, copyLibrary.getValue());
                ResultSet librarySelectResult = librarySelect.executeQuery();
                librarySelectResult.next();
                if (librarySelectResult.getInt("ID") != Integer.parseInt(currentBranch.split(":")[0]) ||
                        !copyBranch.getValue().split(" ")[2].equals(currentBranch.split(":")[1]))
                    throw new IllegalStateException("Nie można edytować kopii do filii, w której się nie jest");
                update.setString(1, signature);
                update.setInt(2, librarySelectResult.getInt("ID"));
                update.setInt(3, Integer.parseInt(copyBranch.getValue().split(" ")[2]));
                update.setString(4, isbn);
                update.setString(5, signature);
                update.execute();
                update.close();
                librarySelectResult.close();
                librarySelect.close();
            } else {
                PreparedStatement library = dbConnection.prepareStatement("SELECT ID FROM INF122446.L_BIBLIOTEKI WHERE " +
                        "NAZWA = ?");
                library.setString(1, copyLibrary.getValue());
                ResultSet libraryResult = library.executeQuery();
                libraryResult.next();
                if (libraryResult.getInt("ID") != Integer.parseInt(currentBranch.split(":")[0]) ||
                        !copyBranch.getValue().split(" ")[2].equals(currentBranch.split(":")[1]))
                    throw new IllegalStateException("Nie można dodać kopii do filii, w której się nie jest");
                PreparedStatement update = dbConnection.prepareStatement("INSERT INTO INF122446.L_KOPIE(SYGNATURA, " +
                        "BIBLIOTEKI_ID, FILIE_NUMER, WYDANIA_ISBN) VALUES(?, ?, ?, ?)");
                update.setString(1, signature);
                update.setInt(2, libraryResult.getInt("ID"));
                update.setInt(3, Integer.parseInt(copyBranch.getValue().split(" ")[2]));
                update.setString(4, isbn);
                update.execute();
                update.close();
                library.close();
                libraryResult.close();
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        onBookTabSelected(null);
        bookTree.getSelectionModel().select(findItemByName(bookTree, signature));
    }

    private void onBookBorrowTableSearchTextChanged(String newValue) {
        String label = getBookNameFromSelectedItem(bookTree);
        String[] splat = label.split(Pattern.quote(" ("));
        String title = splat[0];
        String author = splat[1].split(",")[0];
        String authorName = author.split(" ")[0];
        String authorSurname = author.split(" ")[1].split("\\)")[0];
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM INF122446.L_KSIĄŻKI JOIN " +
                    "INF122446.L_AUTORSTWA ON(KSIĄŻKI_ID = ID) JOIN INF122446.L_AUTORZY ON(AUTORZY_ID = " +
                    "INF122446.L_AUTORZY.ID) WHERE TYTUŁ = ? AND IMIĘ = ? AND " +
                    "INF122446.L_AUTORZY.NAZWISKO = ?");
            statement.setString(1, title);
            statement.setString(2, authorName);
            statement.setString(3, authorSurname);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int bookId = resultSet.getInt("ID");
            populateBorrows(bookId, newValue);
            resultSet.close();
            statement.close();
        } catch (SQLException ignored) {

        }
    }

    @FXML
    void onLendButtonPressed(ActionEvent event) {
        if (bookTree.getSelectionModel().getSelectedItem().getValue().contains("*") ||
                bookTree.getSelectionModel().getSelectedItem().getValue().contains("(")) { //assumption signature cannot contain *
            Alert alert = new Alert(Alert.AlertType.ERROR, "Musisz najpierw wybrać istniejącą kopię",
                    ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        try {
            PreparedStatement s = dbConnection.prepareStatement("SELECT * FROM INF122446.L_WYPOŻYCZENIA " +
                    "WHERE KOPIE_SYGNATURA = ? AND DATA_WYPOZYCZENIA IS NOT NULL AND " +
                    "DATA_ODDANIA IS NULL");
            s.setString(1, copySignature.getText());
            ResultSet r = s.executeQuery();
            if (r.next())
                throw new IllegalStateException("Ta kopia jest już wypożyczona");
            PreparedStatement library = dbConnection.prepareStatement("SELECT ID FROM INF122446.L_BIBLIOTEKI WHERE " +
                    "NAZWA = ?");
            library.setString(1, copyLibrary.getValue());
            ResultSet libraryResult = library.executeQuery();
            libraryResult.next();
            if (libraryResult.getInt("ID") != Integer.parseInt(currentBranch.split(":")[0]) ||
                    !copyBranch.getValue().split(" ")[2].equals(currentBranch.split(":")[1]))
                throw new IllegalStateException("Nie można wypożyczyć kopii z filii, w której ona się nie znajduje");
            library.close();
            libraryResult.close();
        } catch (SQLException ignored) {
        } catch (IllegalStateException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
            return;
        }
        preferences.put("signature", bookTree.getSelectionModel().getSelectedItem().getValue());
        preferences.put("callerRequest", "reader");
        preferences.put("callerScreenPath", "ui/app.fxml");
        preferences.put("callerScreenTitle", "Librarian");

        setScene("ui/readerPicker.fxml", "Wybór czytelnika");
    }

    @FXML
    void onReturnBookBorrowButtonPressed(ActionEvent event) {
        Lend selected = bookBorrowsTable.getSelectionModel().getSelectedItem();
        if (selected == null)
            return;
        try {
            PreparedStatement library = dbConnection.prepareStatement("SELECT NAZWA FROM INF122446.L_BIBLIOTEKI WHERE " +
                    "ID = ?");
            library.setInt(1, Integer.parseInt(currentBranch.split(":")[0]));
            ResultSet libraryResult = library.executeQuery();
            libraryResult.next();
            if (!selected.getBranch().equals(currentBranch.split(":")[1]) ||
                    !selected.getLibrary().equals(libraryResult.getString("Nazwa"))) {
                throw new IllegalStateException("Nie można oddać kopii w filli, w której nie była wypożyczona");
            }
            PreparedStatement update = dbConnection.prepareStatement("UPDATE INF122446.L_WYPOŻYCZENIA " +
                    "SET DATA_ODDANIA = ? WHERE KOPIE_SYGNATURA = ?");
            update.setDate(1, Date.valueOf(LocalDate.now()));
            update.setString(2, selected.getSignature());
            update.execute();
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
            }
        }

        TreeItem<String> lastSelected = bookTree.getSelectionModel().getSelectedItem();
        bookTree.getSelectionModel().select(bookTree.getRoot());
        bookTree.getSelectionModel().select(lastSelected);
    }

    /*
    ====================================================================================================================
    Initialiser
    */

    @FXML
    void initialize() {
        branchNumber.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*") ? change : null));
        postFraction.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*(/\\d*)?") ? change : null));
        postSalary.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*(,\\d{0,2})?") ? change : null));
        readerPESEL.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*") ? change : null));
        editionIsbn.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*") ? change : null));
        editionNumber.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d*") ? change : null));
        editionReleaseDate.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null));
        bookFinishDate.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null));

        libraryTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onLibraryTreeItemSelected(newValue));
        librarianTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onLibrarianTreeItemSelected(newValue));
        readersTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onReaderTableItemSelected(newValue));
        bookTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onBookTreeItemSelected(newValue));

        copyLibrary.getSelectionModel()
                .selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> onCopyLibrarySelected(newValue)));

        readerForenameColumn.setCellValueFactory(
                new PropertyValueFactory<>("forename")
        );

        readerForenameColumn.setCellFactory(column -> new TableCell<Reader, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("surname")
        );

        readerSurnameColumn.setCellFactory(column -> new TableCell<Reader, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerPeselColumn.setCellValueFactory(
                new PropertyValueFactory<>("pesel")
        );

        readerPeselColumn.setCellFactory(column -> new TableCell<Reader, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        readerBorrowTitle.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowAuthor.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );

        readerBorrowAuthor.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowSince.setCellValueFactory(
                new PropertyValueFactory<>("since")
        );

        readerBorrowSince.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowTill.setCellValueFactory(
                new PropertyValueFactory<>("till")
        );

        readerBorrowTill.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowLibrary.setCellValueFactory(
                new PropertyValueFactory<>("library")
        );

        readerBorrowLibrary.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowBranch.setCellValueFactory(
                new PropertyValueFactory<>("branch")
        );

        readerBorrowBranch.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowSignature.setCellValueFactory(
                new PropertyValueFactory<>("signature")
        );

        readerBorrowSignature.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowISBN.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );

        readerBorrowISBN.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        readerBorrowLibrarian.setCellValueFactory(
                new PropertyValueFactory<>("librarian")
        );

        readerBorrowLibrarian.setCellFactory
                (column ->
                        new TableCell<Lend, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(item);
                                try {
                                    Statement statement = dbConnection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("SELECT imie, nazwisko FROM " +
                                            "INF122446.L_Bibliotekarze WHERE ID = " + item);
                                    resultSet.next();
                                    setTooltip(new Tooltip(resultSet.getString("imie") + " " + resultSet.getString("nazwisko")));
                                    resultSet.close();
                                    statement.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

        librarianLendTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        librarianLendTitle.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendAuthor.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );

        librarianLendAuthor.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendReader.setCellValueFactory(
                new PropertyValueFactory<>("reader")
        );

        librarianLendReader.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendSince.setCellValueFactory(
                new PropertyValueFactory<>("since")
        );

        librarianLendSince.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendTill.setCellValueFactory(
                new PropertyValueFactory<>("till")
        );

        librarianLendTill.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendLibrary.setCellValueFactory(
                new PropertyValueFactory<>("library")
        );

        librarianLendLibrary.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendBranch.setCellValueFactory(
                new PropertyValueFactory<>("branch")
        );

        librarianLendBranch.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendSignature.setCellValueFactory(
                new PropertyValueFactory<>("signature")
        );

        librarianLendSignature.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        librarianLendISBN.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );

        librarianLendISBN.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        bookBorrowReader.setCellValueFactory(
                new PropertyValueFactory<>("reader")
        );

        bookBorrowReader.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        bookBorrowSince.setCellValueFactory(
                new PropertyValueFactory<>("since")
        );

        bookBorrowSince.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        bookBorrowTill.setCellValueFactory(
                new PropertyValueFactory<>("till")
        );

        bookBorrowTill.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        bookBorrowSignature.setCellValueFactory(
                new PropertyValueFactory<>("signature")
        );

        bookBorrowSignature.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        bookBorrowISBN.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );

        bookBorrowISBN.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        bookBorrowLibrarian.setCellValueFactory(
                new PropertyValueFactory<>("librarian")
        );

        bookBorrowLibrarian.setCellFactory(column -> new TableCell<Lend, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        authorshipForenameColumn.setCellValueFactory(
                new PropertyValueFactory<>("forename")
        );

        authorshipForenameColumn.setCellFactory(column -> new TableCell<Writer, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        authorshipSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("surname")
        );

        authorshipSurnameColumn.setCellFactory(column -> new TableCell<Writer, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        authorshipNationalityColumn.setCellValueFactory(
                new PropertyValueFactory<>("nationality")
        );

        authorshipNationalityColumn.setCellFactory(column -> new TableCell<Writer, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTooltip(new Tooltip(item));
            }
        });

        translationForenameColumn.setCellValueFactory(
                new PropertyValueFactory<>("forename")
        );
        translationSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("surname")
        );

        copyMaterialTypeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );

        copyMaterialTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        copyMaterialTypeColumn.setOnEditCommit((t -> {
            String signature = copySignature.getText();
            String description = t.getTableView().getItems().get(t.getTablePosition().getRow()).getDescription();
            try {
                PreparedStatement s = dbConnection.prepareStatement("UPDATE INF122446.L_MATERIAŁY_DODATKOWE " +
                        "SET TYP = ? WHERE TYP = ? AND OPIS = ? AND KOPIE_SYGNATURA = ?");
                s.setString(1, t.getNewValue());
                s.setString(2, t.getOldValue());
                s.setString(3, description);
                s.setString(4, signature);
                s.executeUpdate();
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

        copyMaterialDescriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );

        copyMaterialDescriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        copyMaterialDescriptionColumn.setOnEditCommit((t -> {
            String signature = copySignature.getText();
            String type = t.getTableView().getItems().get(t.getTablePosition().getRow()).getType();
            try {
                PreparedStatement s = dbConnection.prepareStatement("UPDATE INF122446.L_MATERIAŁY_DODATKOWE " +
                        "SET OPIS = ? WHERE TYP = ? AND OPIS = ? AND KOPIE_SYGNATURA = ?");
                s.setString(1, t.getNewValue());
                s.setString(2, type);
                s.setString(3, t.getOldValue());
                s.setString(4, signature);
                s.executeUpdate();
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

        bookBorrowTableSearchBar.textProperty()
                .addListener((observable, oldValue, newValue) -> onBookBorrowTableSearchTextChanged(newValue));

        bookTreeSearchBar.textProperty()
                .addListener(((observable, oldValue, newValue) -> populateBookTree(newValue)));

        preferences = Preferences.userNodeForPackage(this.getClass());

        if (preferences.get("error", "").equals("OK")) {
            tabs.getSelectionModel().select(bookTab);
        }
    }
}
