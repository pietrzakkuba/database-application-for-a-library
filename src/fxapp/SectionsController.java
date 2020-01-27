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
import sql.tables.SectionsTable;

import java.io.IOException;

public class SectionsController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<SectionsTable> mainTable;
    public TableColumn<SectionsTable, Integer> section_id;
    public TableColumn<SectionsTable, String> section_name;
    public TableColumn<SectionsTable, String> section_short_name;
    public TableColumn<SectionsTable, Integer> number_of_books;
    public TableColumn<SectionsTable, String> affiliate_name;
    public TextField filter_text_box;

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadSections();
        data = FXCollections.observableArrayList(DatabaseConnection.getSectionsTableArrayList());
    }

    @Override
    public void reload() {
        try{
        loadToArray();
        section_id.setCellValueFactory(new PropertyValueFactory<SectionsTable, Integer>("section_id"));
        section_name.setCellValueFactory(new PropertyValueFactory<SectionsTable, String>("section_name"));
        section_short_name.setCellValueFactory(new PropertyValueFactory<SectionsTable, String>("section_short_name"));
        number_of_books.setCellValueFactory(new PropertyValueFactory<SectionsTable, Integer>("number_of_books"));
        affiliate_name.setCellValueFactory(new PropertyValueFactory<SectionsTable, String>("affiliate_name"));
        mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<SectionsTable> data;
    private ArrayList<SectionsTable> filtered_data = new ArrayList<SectionsTable>();

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (SectionsTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getSection_id(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
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
        for (SectionsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
