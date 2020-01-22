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
import sql.tables.AuthorsTable;

import java.io.IOException;

public class AuthorsController implements Initializable {
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
    public void initialize(URL location, ResourceBundle resources) {
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
        for (AuthorsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
