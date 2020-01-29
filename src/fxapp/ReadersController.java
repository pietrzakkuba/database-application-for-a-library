package fxapp;

import fxapp.containers.Choice;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.BooksTable;
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
    void add(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void modify(ActionEvent event) {

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
