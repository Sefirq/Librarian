package ml.adamsprogs.librarian;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AppController extends FxController {

    //todo feedback

    /*
    ====================================================================================================================
    Library/Branch screen
    */

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
    private VBox branchBox;

    @FXML
    private TextField branchNumber;

    @FXML
    private TextField branchAddress;

    @FXML
    private TextField branchType;

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
    private TextField librariansSurname;

    @FXML
    private VBox postBox;

    @FXML
    private TextField postFraction;

    @FXML
    private TextField postSalary;

    @FXML
    private Text postLibrary;

    @FXML
    private Text postBranch;

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

    /*
    ====================================================================================================================
    Reader screen
    */

    @FXML
    private TableView<Reader> readersTable;

    @FXML
    private TableColumn<Reader, String> readerPeselColumn;

    @FXML
    private TableColumn<Reader, String> readerForenameColumn;

    @FXML
    private TableColumn<Reader, String> readerSurnameColumn;

    @FXML
    private VBox readerBox;

    @FXML
    private TextField readerPESEL;

    @FXML
    private TextField readerForename;

    @FXML
    private TextField readerSurname;

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
    ====================================================================================================================
    Library/Branch screen
    */

    @FXML
    void onBranchTabSelected(Event event) {
        clearTree(libraryTree);

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
        root.getChildren().add(newLibrary);
        libraryTree.getSelectionModel().select(newLibrary);
        try {
            setLibraryData(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        branchBox.setVisible(false);
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

        removeEmptyItems();

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

    private void setBranchData(@Nullable ResultSet data) throws SQLException {
        branchNumber.setText(data != null ? data.getString("Numer") : "");
        branchAddress.setText(data != null ? data.getString("Adres") : "");
        branchType.setText(data != null ? data.getString("Typ") : "");
    }

    private void removeEmptyItems() {
        TreeItem<String> root = libraryTree.getRoot();
        if (root == null)
            return;
        ObservableList<TreeItem<String>> libraries = root.getChildren();
        for (TreeItem<String> library : libraries) {
            if (library.getValue().equals("*Nowa Biblioteka") &&
                    !libraryTree.getSelectionModel().getSelectedItem().equals(library))
                libraries.remove(library);
            else {
                ObservableList<TreeItem<String>> branches = library.getChildren();
                for (TreeItem<String> branch : branches) {
                    if (branch.getValue().equals("*Nowa Filia") &&
                            !(libraryTree.getSelectionModel().getSelectedItem().equals(branch) ||
                                    libraryTree.getSelectionModel().getSelectedItem().equals(branch.getParent())))
                        branches.remove(branch);
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
        update.setDate(2, Date.valueOf(foundDate));
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
        ObservableList<TreeItem<String>> libs = tree.getRoot().getChildren();
        for (TreeItem<String> lib : libs) {
            if (lib.getValue().equals(name))
                return lib;
            ObservableList<TreeItem<String>> branches = lib.getChildren();
            for (TreeItem<String> branch : branches) {
                if (branch.getValue().equals(name))
                    return branch;
            }
        }
        return null;
    }

    @FXML
    void onAddBranchButtonPressed(ActionEvent event) {
        String libName = getLibraryNameFromSelectedItem(libraryTree);
        if (libName.equals("*Nowa Biblioteka")) {
            //todo show user that they cannot do it
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
            } else {
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

    }

    @FXML
    void onAddLibrarianButtonPressed(ActionEvent event) {

    }

    private void onLibrarianTreeItemSelected(TreeItem<String> newValue) {

    }

    @FXML
    void onSaveLibrarianButtonPressed(ActionEvent event) {

    }

    @FXML
    void onAddPostButtonPressed(ActionEvent event) {

    }

    @FXML
    void onSavePostButtonPressed(ActionEvent event) {

    }

    @FXML
    private void onReturnLibrarianLendButtonPressed(ActionEvent actionEvent) {

    }

    /*
    ====================================================================================================================
    Reader screen
    */

    @FXML
    void onReaderTabSelected(Event event) {

    }

    @FXML
    void onAddReaderButtonPressed(ActionEvent event) {

    }

    private void onReaderTableItemSelected(Reader reader) {

    }

    @FXML
    void onSaveReaderButtonPressed(ActionEvent event) {

    }

    @FXML
    private void onReturnReaderBorrowButtonPressed(ActionEvent actionEvent) {

    }

    /*
    ====================================================================================================================
    Initialiser
    */

    @FXML
    void initialize() {
        branchNumber.setTextFormatter(new TextFormatter<Integer>(change ->
                change.getControlNewText().matches("[+-]?\\d*") ? change : null));

        libraryTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onLibraryTreeItemSelected(newValue));
        librarianTree.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onLibrarianTreeItemSelected(newValue));
        readersTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> onReaderTableItemSelected(newValue));

        readerForenameColumn.setCellValueFactory(
                new PropertyValueFactory<>("forename")
        );
        readerSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("surname")
        );
        readerPeselColumn.setCellValueFactory(
                new PropertyValueFactory<>("pesel")
        );

        readerBorrowTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        readerBorrowAuthor.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );
        readerBorrowSince.setCellValueFactory(
                new PropertyValueFactory<>("since")
        );
        readerBorrowTill.setCellValueFactory(
                new PropertyValueFactory<>("till")
        );
        readerBorrowLibrary.setCellValueFactory(
                new PropertyValueFactory<>("library")
        );
        readerBorrowBranch.setCellValueFactory(
                new PropertyValueFactory<>("branch")
        );
        readerBorrowSignature.setCellValueFactory(
                new PropertyValueFactory<>("signature")
        );
        readerBorrowISBN.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
        readerBorrowLibrarian.setCellValueFactory(
                new PropertyValueFactory<>("librarian")
        );

        librarianLendTitle.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        librarianLendAuthor.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );
        librarianLendReader.setCellValueFactory(
                new PropertyValueFactory<>("reader")
        );
        librarianLendSince.setCellValueFactory(
                new PropertyValueFactory<>("since")
        );
        librarianLendTill.setCellValueFactory(
                new PropertyValueFactory<>("till")
        );
        librarianLendLibrary.setCellValueFactory(
                new PropertyValueFactory<>("library")
        );
        librarianLendBranch.setCellValueFactory(
                new PropertyValueFactory<>("branch")
        );
        librarianLendSignature.setCellValueFactory(
                new PropertyValueFactory<>("signature")
        );
        librarianLendISBN.setCellValueFactory(
                new PropertyValueFactory<>("isbn")
        );
    }
}
