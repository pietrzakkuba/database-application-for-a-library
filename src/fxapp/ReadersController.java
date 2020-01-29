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
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.BooksTable;
import sql.tables.PositionsTable;
import sql.tables.ReadersTable;


public class ReadersController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<ReadersTable> mainTable;
    public TableColumn<ReadersTable, Integer> id;
    public TableColumn<ReadersTable, String> first_name;
    public TableColumn<ReadersTable, String> last_name;
    public TableColumn<ReadersTable, Date> year_of_birth;
    public TableColumn<ReadersTable, Date> date_of_signing_in;
    public TableColumn<ReadersTable, Integer> number_of_borrowed_books;
    public TextField filter_text_box;

    private static ObservableList<ReadersTable> data;
    private ArrayList<ReadersTable> filtered_data = new ArrayList<ReadersTable>();

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (ReadersTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getId(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadReaders();
        data = FXCollections.observableArrayList(DatabaseConnection.getReadersTableArrayList());
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

        ReadersTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("First name", true, item.getFirst_name()));
        parameters.add(new TextFieldParameter("Last name", true, item.getLast_name()));
        parameters.add(new DateParameter("Birthday", true, item.getYear_of_birth()));
        parameters.add(new DateParameter("Date of sing in", true, item.getDate_of_signing_in()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, parameters, DatabaseConnection::modifyReader,"Modify reader");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("First name", true));
        parameters.add(new TextFieldParameter("Last name", true));
        parameters.add(new DateParameter("Birthday", true));
        parameters.add(new DateParameter("Date of sing in", true));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addReader, "Add Reader");
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

        ReadersTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("First name", item.getFirst_name()));
        parameters.add(new LabelParameter("Last name", item.getLast_name()));
        parameters.add(new LabelParameter("Birthday", item.getYear_of_birth()));
        parameters.add(new LabelParameter("Date of sing in", item.getDate_of_signing_in()));

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, parameters, DatabaseConnection::deleteReader,"Delete reader");
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
        for (ReadersTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }

    @Override
    public void reload() {
        try {
            loadToArray();
            data = FXCollections.observableArrayList(DatabaseConnection.getReadersTableArrayList());
            id.setCellValueFactory(new PropertyValueFactory<ReadersTable, Integer>("id"));
            first_name.setCellValueFactory(new PropertyValueFactory<ReadersTable, String>("first_name"));
            last_name.setCellValueFactory(new PropertyValueFactory<ReadersTable, String>("last_name"));
            year_of_birth.setCellValueFactory(new PropertyValueFactory<ReadersTable, Date>("year_of_birth"));
            date_of_signing_in.setCellValueFactory(new PropertyValueFactory<ReadersTable, Date>("date_of_signing_in"));
            number_of_borrowed_books.setCellValueFactory(new PropertyValueFactory<ReadersTable, Integer>("number_of_borrowed_books"));
            mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
