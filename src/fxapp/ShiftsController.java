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
import sql.tables.AffiliatesTable;
import sql.tables.ShiftScheduleTable;
import sql.tables.ShiftsTable;

import java.io.IOException;

public class ShiftsController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<ShiftsTable> mainTable;
    public TableColumn<ShiftsTable, String> schedule_name;
    public TableColumn<ShiftsTable, Double> time_from;
    public TableColumn<ShiftsTable, Double> time_to;
    public TableColumn<ShiftsTable, String> employee_first_name;
    public TableColumn<ShiftsTable, String> employee_last_name;
    public TableColumn<ShiftsTable, Double> shift_length;
    public TextField filter_text_box;

    private static ObservableList<ShiftsTable> data;
    private ArrayList<ShiftsTable> filtered_data = new ArrayList<ShiftsTable>();

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
        for (ShiftsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadShifts();
        data = FXCollections.observableArrayList(DatabaseConnection.getShiftsTableArrayList());
    }

        @Override
    public void reload() {
        try {
            loadToArray();
            schedule_name.setCellValueFactory(new PropertyValueFactory<ShiftsTable, String>("schedule_name"));
            time_from.setCellValueFactory(new PropertyValueFactory<ShiftsTable, Double>("time_from"));
            time_to.setCellValueFactory(new PropertyValueFactory<ShiftsTable, Double>("time_to"));
            employee_first_name.setCellValueFactory(new PropertyValueFactory<ShiftsTable, String>("employee_first_name"));
            employee_last_name.setCellValueFactory(new PropertyValueFactory<ShiftsTable, String>("employee_last_name"));
            shift_length.setCellValueFactory(new PropertyValueFactory<ShiftsTable, Double>("shift_length"));
            mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
