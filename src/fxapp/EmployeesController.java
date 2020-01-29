package fxapp;

import fxapp.containers.*;
import fxapp.editWindows.AddElement;
import fxapp.editWindows.DeleteElement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.CopiesTable;
import sql.tables.EmployeesTable;

import java.io.IOException;

public class EmployeesController extends Controller implements Initializable {
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

    private static ObservableList<EmployeesTable> data;
    private ArrayList<EmployeesTable> filtered_data = new ArrayList<EmployeesTable>();

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadEmployees();
        data = FXCollections.observableArrayList(DatabaseConnection.getEmployeesTableArrayList());
    }

    @Override
    public void reload() {
        try {
            loadToArray();
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

        @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
    }

    @FXML
    void add(ActionEvent event) {
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("First name",true));
        parameters.add(new TextFieldParameter("Last name",true));
        parameters.add(new DateParameter("Date of employment", true));
        parameters.add(new TextFieldWithChoiceParameter("Affiliate",true, AffiliatesController::getMatchingRecords));
        parameters.add(new TextFieldWithChoiceParameter("Position",true, PositionsController::getMatchingRecords));
        parameters.add(new TextFieldParameter("Hourly rate",true));
        parameters.add(new DateParameter("Date of signing contract",true));
        parameters.add(new DateParameter("Date of contract expiration",false));

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addEmployee,"Add employee");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }


    @FXML
    void modify(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        // Item here is the table view type:
        EmployeesTable item = mainTable.getItems().get(row);

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("First name",true, item.getFirst_name()));
        parameters.add(new TextFieldParameter("Last name",true, item.getLast_name()));
        parameters.add(new DateParameter("Date of employment", true, item.getDate_of_employment()));
        parameters.add(new TextFieldWithChoiceParameter("Affiliate",true, AffiliatesController::getMatchingRecords, item.getAffiliate_id() + " " + item.getAffiliate(),item.getAffiliate_id()));
        parameters.add(new TextFieldWithChoiceParameter("Position",true, PositionsController::getMatchingRecords ,item.getPosition(), item.getPosition_id()));
        parameters.add(new TextFieldParameter("Hourly rate",true, Double.toString(item.getHourly_rate())));
        parameters.add(new DateParameter("Date of signing contract",true, item.getDate_of_signing_contract()));
        parameters.add(new DateParameter("Date of contract expiration",false, item.getDate_of_contract_expiration()));

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, parameters, DatabaseConnection::modifyEmployee,"Modify employee");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void delete(ActionEvent event) {
        TablePosition pos;
        try {
            pos = mainTable.getSelectionModel().getSelectedCells().get(0);
        }catch (IndexOutOfBoundsException e){
            return;
        }
        int row = pos.getRow();

        // Item here is the table view type:
        EmployeesTable item = mainTable.getItems().get(row);

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new LabelParameter("First name", item.getFirst_name()));
        parameters.add(new LabelParameter("Last name", item.getLast_name()));
        parameters.add(new LabelParameter("Date of employment", item.getDate_of_employment()));
        parameters.add(new LabelParameter("Affiliate",  item.getAffiliate_id() + " " + item.getAffiliate()));
        parameters.add(new LabelParameter("Position", item.getPosition()));
        parameters.add(new LabelParameter("Hourly rate", Double.toString(item.getHourly_rate())));
        parameters.add(new LabelParameter("Date of signing contract", item.getDate_of_signing_contract()));
        parameters.add(new LabelParameter("Date of contract expiration", item.getDate_of_contract_expiration()));

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, parameters, DatabaseConnection::deleteEmployee,"Delete employee");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
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
