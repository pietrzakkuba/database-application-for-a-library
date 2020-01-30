package fxapp;

import fxapp.containers.*;
import fxapp.editWindows.AddElement;
import fxapp.editWindows.DeleteElement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.AuthorsTable;
import sql.tables.BooksTable;

public class BooksController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<BooksTable> mainTable;
    public TableColumn<BooksTable, Integer> id;
    public TableColumn<BooksTable, String> title;
    public TableColumn<BooksTable, String> author_first_name;
    public TableColumn<BooksTable, String> author_last_name;
    public TableColumn<BooksTable, Integer> number_of_copies;
    public TableColumn<BooksTable, Integer> number_of_orders;
    public TextField filter_text_box;

    private static ObservableList<BooksTable> data;
    private ArrayList<BooksTable> filtered_data = new ArrayList<BooksTable>();

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (BooksTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getId(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadBooks();
        data = FXCollections.observableArrayList(DatabaseConnection.getBooksTableArrayList());
    }

    @Override
    public void reload() {
        try {
            loadToArray();
            AuthorsController.loadToArray();
            id.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("id"));
            title.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("title"));
            author_first_name.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("author_first_name"));
            author_last_name.setCellValueFactory(new PropertyValueFactory<BooksTable, String>("author_last_name"));
            number_of_copies.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("number_of_copies"));
            number_of_orders.setCellValueFactory(new PropertyValueFactory<BooksTable, Integer>("number_of_orders"));
            mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
    }

    @FXML
    void modify(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        BooksTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Title", true, item.getTitle(),100));
        parameters.add(new TextFieldWithChoiceParameter("Author", true, AuthorsController::getMatchingRecords, item.getAuthor_first_name() + " " + item.getAuthor_last_name(), item.getAuthor_id()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, parameters, DatabaseConnection::modifyBook,"Modify book");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Title", true, 100));
        parameters.add(new TextFieldWithChoiceParameter("Author", true, AuthorsController::getMatchingRecords));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addBook, "Add book");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void delete(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        BooksTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("Title", item.getTitle()));
        parameters.add(new LabelParameter("Author", item.getAuthor_first_name() + " " + item.getAuthor_last_name()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, parameters, DatabaseConnection::deleteBook,"Delete book");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    public void toMainMenu(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("main-menu.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void filtering(KeyEvent onKeyReleased) {
        filtered_data.clear();
        for (BooksTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
