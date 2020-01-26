package fxapp;

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
import java.sql.Date;
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

import java.io.IOException;

public class AuthorsController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<AuthorsTable> mainTable;
    public TableColumn<AuthorsTable, Integer> id;
    public TableColumn<AuthorsTable, String> first_name;
    public TableColumn<AuthorsTable, String> last_name;
    public TableColumn<AuthorsTable, String> pseudonym;
    public TableColumn<AuthorsTable, Date> date_of_birth;
    public TableColumn<AuthorsTable, Date> date_of_death;
    public TableColumn<AuthorsTable, String> nationality;
    public TextField filter_text_box;

    private ObservableList<AuthorsTable> data;
    private ArrayList<AuthorsTable> filtered_data = new ArrayList<AuthorsTable>();

    @Override
    public void reload() {
        try {
            DatabaseConnection.loadAuthors();
            data = FXCollections.observableArrayList(DatabaseConnection.getAuthorsTableArrayList());
            id.setCellValueFactory(new PropertyValueFactory<AuthorsTable, Integer>("id"));
            first_name.setCellValueFactory(new PropertyValueFactory<AuthorsTable, String>("first_name"));
            last_name.setCellValueFactory(new PropertyValueFactory<AuthorsTable, String>("last_name"));
            pseudonym.setCellValueFactory(new PropertyValueFactory<AuthorsTable, String>("pseudonym"));
            date_of_birth.setCellValueFactory(new PropertyValueFactory<AuthorsTable, Date>("date_of_birth"));
            date_of_death.setCellValueFactory(new PropertyValueFactory<AuthorsTable, Date>("date_of_death"));
            nationality.setCellValueFactory(new PropertyValueFactory<AuthorsTable, String>("nationality"));
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
        /*Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

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
            AddElement.startAdding(this,currentWindow, list, dateElements, DatabaseConnection::addAuthor,"Add author");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();*/
    }

    @FXML
    void delete(ActionEvent event) {
        /*TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        // Item here is the table view type:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        AuthorsTable item = mainTable.getItems().get(row);
        ArrayList<String> values = new ArrayList<>();
        values.add(Integer.toString(item.getId()));
        values.add(item.getFirst_name());
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
        currentWindow.hide();*/
    }

    @FXML
    void modify(ActionEvent event) {
        /*TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        // Item here is the table view type:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        AuthorsTable item = mainTable.getItems().get(row);
        ArrayList<String> values = new ArrayList<>();
        values.add(item.getFirst_name());
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
        currentWindow.hide();*/
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
        for (AuthorsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
