package ml.adamsprogs.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.prefs.Preferences;
import java.util.List;

public class AppController extends FxController {

    //todo feedback
    private List<String> posts = new ArrayList<>();
    private int index;
    private ObservableList<Lend> lendsData = FXCollections.observableArrayList();

    private Preferences preferences;
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
    private TextField librarianSurname;

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
    Books screen
    */

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
    private VBox bookBorrowBox;

    @FXML
    private TableView<Lend> bookBorrowsTable;

    @FXML
    private TableColumn<Lend, String> bookBorrowTitle;

    @FXML
    private TableColumn<Lend, String> bookBorrowAuthor;

    @FXML
    private TableColumn<Lend, String> bookBorrowReader;

    @FXML
    private TableColumn<Lend, String> bookBorrowSince;

    @FXML
    private TableColumn<Lend, String> bookBorrowTill;

    @FXML
    private TableColumn<Lend, String> bookBorrowLibrary;

    @FXML
    private TableColumn<Lend, String> bookBorrowBranch;

    @FXML
    private TableColumn<Lend, String> bookBorrowSignature;

    @FXML
    private TableColumn<Lend, String> bookBorrowISBN;

    @FXML
    private TableColumn<Lend, String> bookBorrowLibrarian;

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
        ObservableList<TreeItem<String>> libraries = root.getChildren();
        Iterator<TreeItem<String>> librariesIter = libraries.iterator();
        while (librariesIter.hasNext()) {
            TreeItem<String> library = librariesIter.next();
            if (library.getValue().equals("*Nowa Biblioteka") &&
                    !tree.getSelectionModel().getSelectedItem().equals(library))
                librariesIter.remove();
            else if(library.getValue().equals("*Nowy Bibliotekarz") &&
                    !tree.getSelectionModel().getSelectedItem().equals(library))
                librariesIter.remove();
            else {
                ObservableList<TreeItem<String>> branches = library.getChildren();
                Iterator<TreeItem<String>> branchesIter = branches.iterator();
                while (branchesIter.hasNext()) {
                    TreeItem<String> branch = branchesIter.next();
                    if (branch.getValue().equals("*Nowa Filia") &&
                            !(tree.getSelectionModel().getSelectedItem().equals(branch) ||
                                    tree.getSelectionModel().getSelectedItem().equals(branch.getParent())))
                        branchesIter.remove();
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
        clearTree(librarianTree);
        librarianBox.setVisible(false);
        postBox.setVisible(false);
        librarianLendBox.setVisible(false);

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
    }

    @FXML
    void onAddLibrarianButtonPressed(ActionEvent event) {
        //clearLibrarianTextBoxes();
        postBox.setVisible(false);
        librarianLendBox.setVisible(false);
        addPostButton.setDisable(true);
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

    private void clearLibrarianTextBoxes(){

    }

    private void onLibrarianTreeItemSelected(TreeItem<String> selectedItem) {
        if (selectedItem == null || selectedItem.getValue() == null || selectedItem.getValue().charAt(0) == '*')
            return;
        posts.clear();
        addPostButton.setDisable(false);
        removeEmptyItems(librarianTree);
        lendsData.clear();
        String[] label = selectedItem.getValue().split(" ");
            int librarianID = Integer.parseInt(label[0]);
            try {
                Statement statement = dbConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM INF122446.L_BIBLIOTEKARZE WHERE ID = '" + librarianID + "'");
                resultSet.next();
                setLibrarianData(resultSet);
                resultSet.close();
                statement.close();
                statement = dbConnection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM INF122446.L_ETATY WHERE BIBLIOTEKARZE_ID = '" + librarianID + "'");
                resultSetToArrayListOfPosts(resultSet);
                resultSet.close();
                statement.close();
                index = 0;
                if(posts.size() > 0){
                    setPostData(index);
                    if(posts.size() == 1){
                        nextPostButton.setDisable(true);
                    }
                    else{
                        nextPostButton.setDisable(false);
                    }
                }
                statement = dbConnection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM INF122446.L_WYPOŻYCZENIA WHERE BIBLIOTEKARZE_ID = '" + librarianID + "'");
                while(resultSet.next()){
                    lendsData.add(makeLendObjectFromResultSet(resultSet));
                }
                librarianLends.setItems(lendsData);
                //librarianLends.getColumns().addAll(librarianLendTitle, librarianLendAuthor, librarianLendReader, librarianLendSince, librarianLendTill, librarianLendLibrary,
                        //librarianLendBranch, librarianLendSignature, librarianLendISBN);
                librarianBox.setVisible(true);
                postBox.setVisible(true);
                librarianLendBox.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private Lend makeLendObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT Wydania_ISBN FROM inf122446.L_Kopie WHERE SYGNATURA = '" + resultSet.getString("Kopia_Sygnatura") + "'");
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

        String reader = resultSet.getString("Czytelnicy_PESEL");
        String since = resultSet.getString("DATA_WYPOZYCZENIA");
        String till = resultSet.getString("DATA_ODDANIA");
        statement = dbConnection.createStatement();
        rs = statement.executeQuery("SELECT Biblioteki_ID, Filie_Numer FROM inf122446.L_Kopie WHERE Sygnatura = '" + resultSet.getString("Kopie_Sygnatura"));
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
        String librarian = resultSet.getString("Bibliotekarze_ID");
        return new Lend(title, author, reader, since, till, library, branchNumber, signature, Wydania_ISBN, librarian);
    }

    private void setLibrarianData(@Nullable ResultSet data) throws SQLException{
        librarianForename.setText(data != null ? data.getString("imie") : "");
        librarianSurname.setText(data != null ? data.getString("nazwisko") : "");
    }

    private void resultSetToArrayListOfPosts(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (resultSet.next()) {
            String row = "";
            for (int i = 1; i < columnCount; i++) {
                row += resultSet.getString(i) + " ";
            }
            posts.add(row);
        }
    }

    private void setPostData(int index) {
        String[] arr = posts.get(index).split(" ");
        postFraction.setText(arr[0]);
        postSalary.setText(arr[1]);
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
                int how_many = update.executeUpdate();
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

    }

    @FXML
    void onSavePostButtonPressed(ActionEvent event) {

    }

    @FXML
    private void onReturnLibrarianLendButtonPressed(ActionEvent actionEvent) {

    }

    @FXML
    private void onNextPostButtonPressed(ActionEvent actionEvent) {
        if (index < posts.size() - 1) {
            System.out.println(index);
            System.out.println(posts.size());
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
        if (index > 0) {
            System.out.println(index);
            System.out.println(posts.size());
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
    Book screen
    */

    @FXML
    private void onBookTabSelected(Event event) {
        bookBox.setVisible(false);
        editionBox.setVisible(false);
        copyBox.setVisible(false);
        bookBorrowBox.setVisible(false);

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
                    if (preferences.get("selectedEdition", "").equals("*Nowe wydanie")) {
                        bookTree.getSelectionModel().select(findItemByName(bookTree, preferences.get("selectedBook", "")));
                        onAddEditionButtonPressed(null);
                    }
                    restoreEditionEditing();
                    ObservableList<Writer> translators = editionTranslationTable.getItems();
                    Writer newTranslator = new Writer(preferences.get("forename", ""), preferences.get("surname", ""),
                            "");
                    translators.add(newTranslator);
                    editionTranslationTable.setItems(translators);
                    break;
                case "publisher":
                    if (preferences.get("selectedEdition", "").equals("*Nowe wydanie")) {
                        onAddEditionButtonPressed(null);
                    }
                    restoreEditionEditing();
                    ObservableList<String> publishers = editionPublisher.getItems();
                    publishers.add(preferences.get("publisherR", ""));
                    editionPublisher.setItems(publishers);
                    editionPublisher.getSelectionModel().select(preferences.get("publisherR", ""));
                    break;
            }
        }

        TreeItem<String> rootItem = new TreeItem<>("Książki");
        rootItem.setExpanded(true);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, TYTUŁ FROM INF122446.L_KSIĄŻKI");
            while (resultSet.next()) {
                String itemString = resultSet.getString("TYTUŁ") + "( ";
                Statement authorship = dbConnection.createStatement();
                ResultSet authorshipResults = authorship.executeQuery("SELECT IMIĘ, NAZWISKO FROM L_KSIĄŻKI JOIN " +
                        "L_AUTORSTWA ON(ID = KSIĄŻKI_ID) JOIN L_AUTORZY ON(L_AUTORZY.ID=AUTORZY_ID)");
                while (authorshipResults.next())
                    itemString += authorshipResults.getString("IMIE") + " " +
                            authorshipResults.getString("NAZWISKO") + ", ";

                itemString = itemString.substring(0, itemString.length() - 2);
                itemString = itemString + ')';
                TreeItem<String> bookItem = new TreeItem<>(resultSet.getString(itemString));
                Statement editions = dbConnection.createStatement();
                ResultSet editionsResult = editions.executeQuery("SELECT TYTUŁ_TŁUMACZENIA, NAZWA, ISBN FROM INF122446.L_WYDANIA JOIN " +
                        "INF122446.L_WYDAWCY WHERE KSIĄŻKI_ID = " + resultSet.getInt("ID"));
                while (editionsResult.next()) {
                    TreeItem<String> editionItem = new TreeItem<>(composeEditionName(editionsResult.getString("Tytuł_tłumaczenia"),
                            editionsResult.getString("Nazwa"), editionsResult.getString("ISBN")));
                    Statement copies = dbConnection.createStatement();
                    ResultSet copiesResult = copies.executeQuery("SELECT SYGNATURA FROM INF122446.L_KOPIE WHERE" +
                            " WYDANIA_ISBN = " + editionsResult.getString("ISBN"));
                    while (copiesResult.next()) {
                        TreeItem<String> copyItem = new TreeItem<>(copiesResult.getString("Sygnatura"));
                        editionItem.getChildren().add(copyItem);
                    }
                    bookItem.getChildren().add(editionItem);
                    copies.close();
                    copiesResult.close();
                }
                editionsResult.close();
                editions.close();
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
        for (String authorString : preferences.get("authorship", "").split(",")) {
            authors.add(Writer.fromJson(authorString));
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
        editionPublisher.getSelectionModel().select(preferences.get("number", ""));
        editionIsbn.setText(preferences.get("isbn", ""));
        editionLanguage.setText(preferences.get("editionLanguage", ""));
        editionReleaseDate.setText(preferences.get("releaseDate", ""));
        editionTitle.setText(preferences.get("editionTitle", ""));

        ObservableList<Writer> translators = FXCollections.observableArrayList();
        for (String translatorString : preferences.get("translations", "").split(",")) {
            translators.add(Writer.fromJson(translatorString));
        }
        bookAuthorshipTable.setItems(translators);

        bookBox.setVisible(true);
        editionBox.setVisible(true);
    }

    private String composeEditionName(String tytuł_tłumaczenia, String nazwa, String isbn) {
        return tytuł_tłumaczenia + ", " + nazwa + " (" + isbn + ")";
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
    }

    private void onBookTreeItemSelected(TreeItem<String> selectedItem) {

    }

    @FXML
    void onAddBookAuthorButtonPressed(ActionEvent event) {
        preferences.put("callerRequest", "author");
        preferences.put("callerScreenPath", "ui/app.fxml");
        preferences.put("callerScreenTitle", "Librarian");

        preferences.put("title", bookTitle.getText());
        preferences.put("finishDate", bookFinishDate.getText());
        preferences.put("genre", bookGenre.getText());
        preferences.put("language", bookLanguage.getText());
        preferences.put("stream", bookStream.getText());
        String authorship = "[";
        for (Writer w : bookAuthorshipTable.getItems()) {
            authorship += w.toJson() + ", ";
        }
        authorship = authorship.substring(0, authorship.length() - 2);
        authorship += "]";
        preferences.put("authorship", authorship);

        preferences.put("selectedBook", bookTree.getSelectionModel().getSelectedItem().getValue());

        setScene("ui/picker.fxml", "Wybór autora");
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

    }

    @FXML
    void onAddEditionButtonPressed(ActionEvent event) {
        String selectedBookName = getBookNameFromSelectedItem(bookTree);
        if (selectedBookName.equals("*Nowa Książka")) {
            //todo show user they cannot do it
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
        editionNumber.setText(data != null ? data.getString("Numer") : "");
        editionPublisher.getSelectionModel().select(data != null ? data.getString("Nazwa") : "");
        editionIsbn.setText(data != null ? data.getString("ISBN") : "");
        editionLanguage.setText(data != null ? data.getString("Język") : "");
        editionReleaseDate.setText(data != null ? data.getString("Rok_wydania") : "");
        editionTitle.setText(data != null ? data.getString("Tytuł_tłumaczenia") : "");
    }

    private String getBookNameFromSelectedItem(@NotNull TreeView<String> tree) {
        TreeItem<String> selectedItem = tree.getSelectionModel().getSelectedItem();
        if (selectedItem.getValue().split("\\(")[1].split("\\)")[0].matches("[0-9]{13}"))
            selectedItem = selectedItem.getParent();
        return selectedItem.getValue();
    }

    @FXML
    void onNewPublisherButtonPressed(ActionEvent event) {
        putEditionToPreferences();

        setScene("ui/picker.fxml", "Wybór wydawcy");
    }

    private void putEditionToPreferences() {
        preferences.put("callerRequest", "publisher");
        preferences.put("callerScreenPath", "ui/app.fxml");
        preferences.put("callerScreenTitle", "Librarian");

        preferences.put("editionTitle", editionTitle.getText());
        preferences.put("releaseDate", editionReleaseDate.getText());
        preferences.put("editionLanguage", bookLanguage.getText());
        preferences.put("number", editionNumber.getText());
        preferences.put("isbn", editionIsbn.getText());
        preferences.put("publisher", editionPublisher.getSelectionModel().getSelectedItem());
        String translation = "[";
        for (Writer w : editionTranslationTable.getItems()) {
            translation += w.toJson() + ", ";
        }
        translation = translation.substring(0, translation.length() - 2);
        translation += "]";
        preferences.put("translations", translation);

        preferences.put("selectedEdition", bookTree.getSelectionModel().getSelectedItem().getValue());
        preferences.put("selectedBook", bookTree.getSelectionModel().getSelectedItem().getParent().getValue());
    }

    @FXML
    void onAddEditionTranslatorButtonPressed(ActionEvent event) {
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

    }

    @FXML
    void onAddCopyButtonPressed(ActionEvent event) {
        String selectedEditionName = getEditionNameFromSelectedItem(bookTree);
        if (selectedEditionName.equals("*Nowe Wydanie")) {
            //todo show user they cannot do it
            return;
        }
        TreeItem<String> selectedEdition = findItemByName(bookTree, selectedEditionName);
        if (selectedEdition == null)
            return;
        TreeItem<String> newCopy = new TreeItem<>("*Nowa Kopia");
        selectedEdition.getChildren().add(newCopy);
        bookTree.getSelectionModel().select(newCopy);
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

    private String getEditionNameFromSelectedItem(TreeView<String> tree) {
        TreeItem<String> selectedItem = tree.getSelectionModel().getSelectedItem();
        if (!selectedItem.getValue().matches("(.*)\\((.*)")) //todo signature cannot contain (
            selectedItem = selectedItem.getParent();
        return selectedItem.getValue();
    }

    @FXML
    void onAddCopyMaterialButtonPressed(ActionEvent event) {
        ObservableList<Material> materials = copyMaterialTable.getItems();
        materials.add(new Material("*Nowy", "Pusty materiał"));
        copyMaterialTable.setItems(materials);
    }

    @FXML
    void onDeleteCopyMaterialButtonPressed(ActionEvent event) {
        Material selectedMaterial = copyMaterialTable.getSelectionModel().getSelectedItem();
        ObservableList<Material> materials = copyMaterialTable.getItems();
        materials.removeAll(selectedMaterial);
        copyMaterialTable.setItems(materials);
    }

    @FXML
    void onSaveCopyButtonPressed(ActionEvent event) {

    }

    @FXML
    void onLendButtonPressed(ActionEvent event) {

    }

    @FXML
    void onReturnBookBorrowButtonPressed(ActionEvent event) {

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

        authorshipForenameColumn.setCellValueFactory(
                new PropertyValueFactory<>("forename")
        );
        authorshipSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("surname")
        );
        authorshipNationalityColumn.setCellValueFactory(
                new PropertyValueFactory<>("nationality")
        );

        translationForenameColumn.setCellValueFactory(
                new PropertyValueFactory<>("forename")
        );
        translationSurnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("surname")
        );

        preferences = Preferences.userNodeForPackage(this.getClass());
    }
}
