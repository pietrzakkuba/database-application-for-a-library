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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sql.DatabaseConnection;
import sql.tables.AffiliatesTable;
import sql.tables.CopiesTable;
import sql.tables.OrdersTable;
import sql.tables.PositionsTable;

import java.io.IOException;

public class PositionsController extends Controller implements Initializable {
    public Button toMainMenuButton;
    public TableView<PositionsTable> mainTable;
    public TableColumn<PositionsTable, String> name;
    public TableColumn<PositionsTable, Double> minimal_hourly_rate;
    public TableColumn<PositionsTable, Integer> number_of_employees;
    public TextField filter_text_box;

    private static ObservableList<PositionsTable> data;
    private ArrayList<PositionsTable> filtered_data = new ArrayList<PositionsTable>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reload();
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

        PositionsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Name", true, item.getName()));
        parameters.add(new TextFieldParameter("Minimum hourly rate", true, Double.toString(item.getMinimal_hourly_rate())));

        ((TextFieldParameter)parameters.get(1)).markAsDouble();

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startModifying(item.getId(),this,currentWindow, parameters, DatabaseConnection::modifyPosition,"Modify position");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentWindow.hide();
    }

    @FXML
    void add(ActionEvent event) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Name", true));
        parameters.add(new TextFieldParameter("Minimum hourly rate", false));

        ((TextFieldParameter)parameters.get(1)).markAsDouble();

        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            AddElement.startAdding(this,currentWindow, parameters, DatabaseConnection::addPosition, "Add Position");
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

        PositionsTable item = mainTable.getItems().get(row);

        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new TextFieldParameter("Name", true));
        parameters.add(new TextFieldParameter("Minimum hourly rate", false));
        Stage currentWindow = (Stage) ((Node)(event.getSource())).getScene().getWindow();

        try {
            DeleteElement.startDeleting(item.getId(),this,currentWindow, parameters, DatabaseConnection::deletePosition,"Delete position");
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
        for (PositionsTable datum : data) {
            if (datum.getAll().replaceAll("null", "").toLowerCase().contains(filter_text_box.getText().toLowerCase())) {
                filtered_data.add(datum);
            }
        }
        mainTable.setItems(FXCollections.observableArrayList(filtered_data));
    }

    public static ArrayList<Choice> getMatchingRecords(String string){
        ArrayList<Choice> matchingList = new ArrayList<>();
        for (PositionsTable datum : data) {
            if (datum.getToChoose().replaceAll("null", "").toLowerCase().contains(string.toLowerCase())) {
                Choice choice = new Choice(datum.getId(),datum.getToChoose().replaceAll("null", ""));
                matchingList.add(choice);
            }
        }
        return matchingList;
    }

    public static void loadToArray() throws SQLException {
        DatabaseConnection.loadPositions();
        data = FXCollections.observableArrayList(DatabaseConnection.getPositionsTableArrayList());
    }

    @Override
    public void reload() {
        try {
            loadToArray();
            name.setCellValueFactory(new PropertyValueFactory<PositionsTable, String>("name"));
            minimal_hourly_rate.setCellValueFactory(new PropertyValueFactory<PositionsTable, Double>("minimal_hourly_rate"));
            number_of_employees.setCellValueFactory(new PropertyValueFactory<PositionsTable, Integer>("number_of_employees"));
            mainTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
