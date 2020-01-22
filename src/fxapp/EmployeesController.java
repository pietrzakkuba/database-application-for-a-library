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
import sql.tables.EmployeesTable;

import java.io.IOException;

public class EmployeesController implements Initializable {
    public Button toMainMenuButton;
    public TableView<EmployeesTable> mainTable;
    public TableColumn<EmployeesTable, Integer> id;
    public TableColumn<EmployeesTable, String> first_name;
    public TableColumn<EmployeesTable, String> last_name;
    public TableColumn<EmployeesTable, String> position;
    public TableColumn<EmployeesTable, String> affiliate;
    public TableColumn<EmployeesTable, Date> date_of_employment;
    public TableColumn<EmployeesTable, Date> date_of_signing_contract;
    public TableColumn<EmployeesTable, Date> date_of_contract_expiration;
    public TableColumn<EmployeesTable, Double> hourly_rate;
    public TextField filter_text_box;

    private ObservableList<EmployeesTable> data;
    private ArrayList<EmployeesTable> filtered_data = new ArrayList<EmployeesTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DatabaseConnection.loadEmployees();
            data = FXCollections.observableArrayList(DatabaseConnection.getEmployeesTableArrayList());
            id.setCellValueFactory(new PropertyValueFactory<EmployeesTable, Integer>("id"));
            first_name.setCellValueFactory(new PropertyValueFactory<EmployeesTable, String>("first_name"));
            last_name.setCellValueFactory(new PropertyValueFactory<EmployeesTable, String>("last_name"));
            position.setCellValueFactory(new PropertyValueFactory<EmployeesTable, String>("position"));
            affiliate.setCellValueFactory(new PropertyValueFactory<EmployeesTable, String>("affiliate"));
            date_of_employment.setCellValueFactory(new PropertyValueFactory<EmployeesTable, Date>("date_of_employment"));
            date_of_signing_contract.setCellValueFactory(new PropertyValueFactory<EmployeesTable, Date>("date_of_signing_contract"));
            date_of_contract_expiration.setCellValueFactory(new PropertyValueFactory<EmployeesTable, Date>("date_of_contract_expiration"));
            hourly_rate.setCellValueFactory(new PropertyValueFactory<EmployeesTable, Double>("hourly_rate"));
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
        for (EmployeesTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }
}
