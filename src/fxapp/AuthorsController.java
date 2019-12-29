package fxapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadAuthors();
            ObservableList<AuthorsTable> data = FXCollections.observableArrayList(DatabaseConnection.getAuthorsTableArrayList());
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

    public void toMainMenu(ActionEvent actionEvent) {
        Platform.runLater( () -> {
            try {
                Main.getMainStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("main-menu.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
