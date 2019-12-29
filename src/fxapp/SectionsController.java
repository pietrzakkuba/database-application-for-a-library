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
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sql.DatabaseConnection;
import sql.tables.SectionsTable;

import java.io.IOException;

public class SectionsController implements Initializable {
    public Button toMainMenuButton;
    public TableView<SectionsTable> mainTable;
    public TableColumn<SectionsTable, Integer> section_id;
    public TableColumn<SectionsTable, String> section_name;
    public TableColumn<SectionsTable, String> section_short_name;
    public TableColumn<SectionsTable, Integer> number_of_books;
    public TableColumn<SectionsTable, String> affiliate_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadSections();
            ObservableList<SectionsTable> data = FXCollections.observableArrayList(DatabaseConnection.getSectionsTableArrayList());
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
