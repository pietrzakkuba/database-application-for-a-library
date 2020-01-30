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
import sql.tables.SectionsTable;

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

    private static ObservableList<AuthorsTable> data;
    private ArrayList<AuthorsTable> filtered_data = new ArrayList<AuthorsTable>();

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (AuthorsTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getId(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadAuthors();
        data = FXCollections.observableArrayList(DatabaseConnection.getAuthorsTableArrayList());
    }

    @Override
    public void reload() {
        try {
            loadToArray();
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
    void modify(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        AuthorsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("First name", true, item.getFirst_name(),20));
        parameters.add(new TextFieldParameter("Last name", true, item.getLast_name(),20));
        parameters.add(new TextFieldParameter("Pseudonym", false, item.getPseudonym()));
        parameters.add(new TextFieldParameter("Nationality", false, item.getNationality(),20));
        parameters.add(new DateParameter("Date of birth", false, item.getDate_of_birth()));
        parameters.add(new DateParameter("Date of death", false, item.getDate_of_death()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, parameters, DatabaseConnection::modifyAuthor,"Modify author");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("First name", true,20));
        parameters.add(new TextFieldParameter("Last name", true ,20));
        parameters.add(new TextFieldParameter("Pseudonym", false));
        parameters.add(new TextFieldParameter("Nationality", false,20));
        parameters.add(new DateParameter("Date of birth", false));
        parameters.add(new DateParameter("Date of death", false));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addAuthor,"Add author");
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

        AuthorsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("First name", item.getFirst_name()));
        parameters.add(new LabelParameter("Last name", item.getLast_name()));
        parameters.add(new LabelParameter("Pseudonym",  item.getPseudonym()));
        parameters.add(new LabelParameter("Nationality",  item.getNationality()));
        parameters.add(new LabelParameter("Date of birth",  item.getDate_of_birth()));
        parameters.add(new LabelParameter("Date of death",  item.getDate_of_death()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, parameters, DatabaseConnection::deleteAuthor,"Delete author");
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
        for (AuthorsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
