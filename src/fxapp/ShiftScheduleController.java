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
import sql.tables.ShiftScheduleTable;

import java.io.IOException;

public class ShiftScheduleController implements Initializable {
    public Button toMainMenuButton;
    public TableView<ShiftScheduleTable> mainTable;
    public TableColumn<ShiftScheduleTable, Integer> id;
    public TableColumn<ShiftScheduleTable, Boolean> is_valid;
    public TableColumn<ShiftScheduleTable, String> affiliate_name;
    public TableColumn<ShiftScheduleTable, String> schedule_name;
    public TableColumn<ShiftScheduleTable, Date> valid_from;
    public TableColumn<ShiftScheduleTable, Date> valid_to;
    public TableColumn<ShiftScheduleTable, Integer> number_of_shifts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadShiftSchedule();
            ObservableList<ShiftScheduleTable> data = FXCollections.observableArrayList(DatabaseConnection.getShiftScheduleTableArrayList());
            id.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, Integer>("id"));
            is_valid.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, Boolean>("is_valid"));
            affiliate_name.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, String>("affiliate_name"));
            schedule_name.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, String>("schedule_name"));
            valid_from.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, Date>("valid_from"));
            valid_to.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, Date>("valid_to"));
            number_of_shifts.setCellValueFactory(new PropertyValueFactory<ShiftScheduleTable, Integer>("number_of_shifts"));
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
