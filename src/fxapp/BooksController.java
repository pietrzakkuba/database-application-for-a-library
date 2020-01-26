package fxapp;

import fxapp.containers.Choice;
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

    @Override
    public void reload() {
        try {
            DatabaseConnection.loadBooks();
            data = FXCollections.observableArrayList(DatabaseConnection.getBooksTableArrayList());
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
    void add(ActionEvent event) {
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();
        /*private int id;
        private String title;
        private String author_first_name;
        private String author_last_name;
        private int number_of_copies;
        private int number_of_orders;

        ArrayList<String> list = new ArrayList<>();
        list.add("Title");
        list.add("Author last name");
        list.add("Author first name");

        ArrayList<Integer> dateElements = new ArrayList<>();
        dateElements.add(4);
        dateElements.add(5);

        try {
            AddElement.startAdding(this,currentWindow, list, dateElements, DatabaseConnection::addAuthor,"Add author");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();*/
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

        // Item here is the table view type:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        BooksTable item = mainTable.getItems().get(row);
        ArrayList<String> values = new ArrayList<>();
        values.add(Integer.toString(item.getId()));
        /*values.add(item.getFirst_name());
        values.add(item.getLast_name());
        values.add(item.getPseudonym());
        values.add(item.getNationality());
        try{
            values.add(formatter.format(item.getDate_of_birth()));
        }catch (NullPointerException e){
            values.add("");
        }
        try{
            values.add(formatter.format(item.getDate_of_death()));
        }catch (NullPointerException e){
            values.add("");
        }

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<String> list = new ArrayList<>();
        list.add("Id");
        list.add("FirstName");
        list.add("LastName");
        list.add("Pseudonym");
        list.add("Nationality");
        list.add("Date of birth");
        list.add("Date of death");

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, list, values, DatabaseConnection::deleteAuthor,"Delete author");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
        */
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

        // Item here is the table view type:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        BooksTable item = mainTable.getItems().get(row);
        ArrayList<String> values = new ArrayList<>();
        /*values.add(item.getFirst_name());
        values.add(item.getLast_name());
        values.add(item.getPseudonym());
        values.add(item.getNationality());
        try{
            values.add(formatter.format(item.getDate_of_birth()));
        }catch (NullPointerException e){
            values.add("");
        }
        try{
            values.add(formatter.format(item.getDate_of_death()));
        }catch (NullPointerException e){
            values.add("");
        }

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<String> list = new ArrayList<>();
        list.add("FirstName");
        list.add("LastName");
        list.add("Pseudonym");
        list.add("Nationality");
        list.add("Date of birth");
        list.add("Date of death");

        ArrayList<Integer> dateElements = new ArrayList<>();
        dateElements.add(4);
        dateElements.add(5);

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, list, dateElements, values, DatabaseConnection::modifyAuthor,"Add author");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
         */
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
