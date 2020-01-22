package fxapp;

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

public class BooksController implements Initializable {
    public Button toMainMenuButton;
    public TableView<BooksTable> mainTable;
    public TableColumn<BooksTable, Integer> id;
    public TableColumn<BooksTable, String> title;
    public TableColumn<BooksTable, String> author_first_name;
    public TableColumn<BooksTable, String> author_last_name;
    public TableColumn<BooksTable, Integer> number_of_copies;
    public TableColumn<BooksTable, Integer> number_of_orders;
    public TextField filter_text_box;

    private ObservableList<BooksTable> data;
    private ArrayList<BooksTable> filtered_data = new ArrayList<BooksTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        for (BooksTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
