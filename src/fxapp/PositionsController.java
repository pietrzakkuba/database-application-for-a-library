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
import sql.tables.PositionsTable;

import java.io.IOException;

public class PositionsController implements Initializable {
    public Button toMainMenuButton;
    public TableView<PositionsTable> mainTable;
    public TableColumn<PositionsTable, String> name;
    public TableColumn<PositionsTable, Double> minimal_hourly_rate;
    public TableColumn<PositionsTable, Integer> number_of_employees;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadPositions();
            ObservableList<PositionsTable> data = FXCollections.observableArrayList(DatabaseConnection.getPositionsTableArrayList());
            name.setCellValueFactory(new PropertyValueFactory<PositionsTable, String>("name"));
            minimal_hourly_rate.setCellValueFactory(new PropertyValueFactory<PositionsTable, Double>("minimal_hourly_rate"));
            number_of_employees.setCellValueFactory(new PropertyValueFactory<PositionsTable, Integer>("number_of_employees"));
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
